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
import android.widget.*;
import com.actionbarsherlock.app.SherlockFragment;
import com.staschum.R;
import com.staschum.Utils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getSherlockActivity();
		final String mainText = "main";
		final String secondaryText = "secondary";

		String url = getArguments().getString(URL, "http://www.google.com/");
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
						List<Map<String, String>> result = new ArrayList<Map<String, String>>();
						try {
							Object[] articles = tagNode.evaluateXPath(".//*[@id='body_element']//table[@class='include_0']/tbody//tr//td//a");
							for (int i = 0; i < articles.length; i++) {
								String value = Utils.removeTags(htmlCleaner.getInnerHtml((TagNode) articles[i]).trim());
								if ((i % 2) == 0) {

									HashMap<String, String> map = new HashMap<String, String>();
									map.put(mainText, value);
									result.add(map);
								} else {
									result.get(i/2).put(secondaryText, value);
								}
							}
						} catch (XPatherException e) {
							e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}
						return result;
					}

					@Override
					protected void onPostExecute(List<Map<String, String>> s) {
						SimpleAdapter rowAdapter = new SimpleAdapter(activity, s, R.layout.two_lines_list_row, new String[]{mainText, secondaryText}, new int[]{R.id.row_main_text, R.id.row_small_text});
						getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
						ListView contentList = (ListView) activity.findViewById(R.id.list_content);
						contentList.setVisibility(View.VISIBLE);
						contentList.setAdapter(rowAdapter);
					}
				}.execute();
			}
		});
	}

}
