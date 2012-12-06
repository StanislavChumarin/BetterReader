package com.staschum.html2view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.staschum.html2view.layoutcontainer.ContentFragment;
import com.staschum.html2view.managers.DescriptionManager;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class ViewComposer {

	private DataViewDescription description;
	private Context context;
	private String baseUrl;

	public ViewComposer(Context context, String baseUrl) {
		this.context = context;
		this.baseUrl = baseUrl;
	}

	public void createFragment(final String url, final int fileId, final FragmentReady fragmentReady) {
		Utils.getHtmlByUrlAsync(url, new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				if (resultCode != Utils.STATUS_OK) {
					return;
				}

				String htmlSource = resultData.getString(Utils.RESULT_KEY, "");

				HtmlCleaner htmlCleaner = new HtmlCleaner();
				TagNode tagNode = htmlCleaner.clean(htmlSource);

				FragmentDescriptor descriptor = new DescriptionManager(context).getDescription(url, tagNode, fileId, false);

				ContentFragment fragment = ContentFragmentFactory.getFragment(baseUrl, descriptor.getLayoutName());
				JSONArray fragmentData = descriptor.getData();
				fragment.setData(tagNode, fragmentData);

				fragmentReady.fragmentReady(fragment);
			}
		});
	}

//	@SuppressWarnings("unchecked")
//	public Fragment getFragment(String url, final TagNode tagNode) {
//
//		ContentFragment result = ContentFragmentFactory.getFragment(url, description);
//
//		Activity activity = result.getActivity();
//		String title = tagNode.findElementByName("title", true).getText().toString();
//
//		List<DataDescription> dataDescriptionList = description.getDataDescription();
//
//		for(DataDescription dataDescription : dataDescriptionList) {
//			switch (dataDescription) {
//				case LIST:
//
//					break;
//				case TEXT:
//					break;
//			}
//		}
//
//		new AsyncTask<Void, Void, List<Map<String, String>>>() {
//
//			@Override
//			protected List<Map<String, String>> doInBackground(Void... voids) {
//
//				List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//				try {
//
//					HtmlCleaner htmlCleaner = new HtmlCleaner();//remove
//					Object[] articles = tagNode.evaluateXPath(".//*[@id='body_element']//table[@class='include_0']/tbody//tr//td/a");
//					List<TagNode> nodesToPublish = new ArrayList<TagNode>();
//					for (int i = 0; i < articles.length; i++) {
//						TagNode article = (TagNode) articles[i];
//						if (article.getAttributeByName("href").contains("user") || htmlCleaner.getInnerHtml(article).contains("img"))
//							continue;
//						nodesToPublish.add(article);
//					}
//					for (int i = 0; i < nodesToPublish.size(); i++) {
//						TagNode article = nodesToPublish.get(i);
//						String value = Utils.removeTags(article.getText().toString().trim());
//						if (article.hasAttribute("class")) {
////							result.get(result.size() - 1).put(secondaryText, value);
//						} else {
//
//							HashMap<String, String> map = new HashMap<String, String>();
////							map.put(mainText, value);
////							map.put(valueUrl, basicUrl + article.getAttributeByName("href"));
//							result.add(map);
//						}
//					}
//				} catch (XPatherException e) {
//					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//				}
//				return result;
//			}
//
//			@Override
//			protected void onPostExecute(final List<Map<String, String>> mapList) {
////				activity.setTitle(title);
////				SimpleAdapter rowAdapter = new SimpleAdapter(activity, mapList, R.layout.two_lines_list_row, new String[]{mainText, secondaryText}, new int[]{R.id.row_main_text, R.id.row_small_text});
////				activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);
////				ListView contentList = (ListView) activity.findViewById(R.id.list_content);
////				contentList.setVisibility(View.VISIBLE);
////				contentList.setAdapter(rowAdapter);
////
////				contentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////					@Override
////					public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////						((MenuFragment.ContentViewer) activity).viewContent(mapList.get(i).get(valueUrl));
////					}
////				});
//			}
//		}.execute();
//
//
//		return result;
//	}

}
