package com.staschum.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.actionbarsherlock.app.SherlockFragment;
import com.staschum.DataViewDescription;
import com.staschum.R;
import com.staschum.Utils;
import com.staschum.managers.DescriptionManager;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 18:25
 */
public class MainFragment extends SherlockFragment {

	private static String URL = "url";

	//TODO refactor this to be useful for different sites
	private String basicUrl = "http://www.ex.ua";

	private String title;

	public static Fragment createFragment(String url) {
		Fragment fragment = new MainFragment();
		Bundle args = new Bundle();
		args.putString(URL, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getSherlockActivity();
		final String mainText = "main";
		final String secondaryText = "secondary";
		final String valueUrl = "url";
		title = activity.getTitle().toString();
		final String url = getArguments().getString(URL, "http://www.google.com/");
		getActivity().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.list_content).setVisibility(View.GONE);

		Utils.getHtmlByUrlAsync(url, new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, final Bundle resultData) {
				if (resultCode != Utils.STATUS_OK)
					return;



				new AsyncTask<Void, Void, List<Map<String, String>>>() {

					@Override
					protected List<Map<String, String>> doInBackground(Void... voids) {
						String htmlSource = resultData.getString(Utils.RESULT_KEY, "");

						HtmlCleaner htmlCleaner = new HtmlCleaner();

						TagNode tagNode = htmlCleaner.clean(htmlSource);

						DataViewDescription description;
						try {
							description = new DescriptionManager(getActivity()).getDescription(url, tagNode);
							title = htmlCleaner.getInnerHtml(tagNode.findElementByName("title", true));
						} catch (JSONException e) {
							e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						} catch (XPatherException e) {
							e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}

						title = htmlCleaner.getInnerHtml(tagNode.findElementByName("title", true));

						List<Map<String, String>> result = new ArrayList<Map<String, String>>();
						try {
							Object[] articles = tagNode.evaluateXPath(".//*[@id='body_element']//table[@class='include_0']/tbody//tr//td/a");
							List<TagNode> nodesToPublish = new ArrayList<TagNode>();
							for (int i = 0; i < articles.length; i++) {
								TagNode article = (TagNode) articles[i];
								if (article.getAttributeByName("href").contains("user") || htmlCleaner.getInnerHtml(article).contains("img"))
									continue;
								nodesToPublish.add(article);
							}
							for (int i = 0; i < nodesToPublish.size(); i++) {
								TagNode article = nodesToPublish.get(i);
								String value = Utils.removeTags(htmlCleaner.getInnerHtml(article).trim());
								if (article.hasAttribute("class")) {
									result.get(result.size() - 1).put(secondaryText, value);
								} else {

									HashMap<String, String> map = new HashMap<String, String>();
									map.put(mainText, value);
									map.put(valueUrl, basicUrl + article.getAttributeByName("href"));
									result.add(map);
								}
							}
						} catch (XPatherException e) {
							e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}
						return result;
					}

					@Override
					protected void onPostExecute(final List<Map<String, String>> mapList) {
						activity.setTitle(title);
						SimpleAdapter rowAdapter = new SimpleAdapter(activity, mapList, R.layout.two_lines_list_row, new String[]{mainText, secondaryText}, new int[]{R.id.row_main_text, R.id.row_small_text});
						getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
						ListView contentList = (ListView) activity.findViewById(R.id.list_content);
						contentList.setVisibility(View.VISIBLE);
						contentList.setAdapter(rowAdapter);

						contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
								((MenuFragment.HtmlViewer) activity).viewHtml(mapList.get(i).get(valueUrl));
							}
						});
					}
				}.execute();
			}
		});
	}

}
