package com.staschum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.ViewComposer;
import com.staschum.model.SupportedSite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 17:16
 */
public class MenuFragment extends SherlockFragment {

	private List<String> menuNames = new ArrayList<String>();
	private ContentViewer contentViewer;
	private List<SupportedSite> supportedSites;

	public MenuFragment() {
		supportedSites = new ArrayList<SupportedSite>();
		supportedSites.add(new SupportedSite("EX.UA", "http://www.ex.ua"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		contentViewer = (ContentViewer) getSherlockActivity();

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.menu_list_row, R.id.text);
		arrayAdapter.addAll(getNames(supportedSites));

		ListView listView = (ListView) getActivity().findViewById(R.id.list_content);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				((MainActivity) contentViewer).getSlidingMenu().showContent(true);
				contentViewer.viewContent(supportedSites.get(i).getUrl(), "/");

			}
		});
		getActivity().findViewById(R.id.list_content).setVisibility(View.VISIBLE);

//		Utils.getHtmlByUrlAsync(url + "/", new ResultReceiver(new Handler()) {
//			@Override
//			protected void onReceiveResult(int resultCode, Bundle resultData) {
//				if (resultCode != Utils.STATUS_OK)
//					return;
//				HtmlCleaner htmlCleaner = new HtmlCleaner();
//				// take default cleaner properties
//				CleanerProperties props = htmlCleaner.getProperties();
//
//				TagNode tagNode = htmlCleaner.clean(resultData.getString(Utils.RESULT_KEY, ""));
//				try {
//					Object[] menus = tagNode.evaluateXPath("/body/table/tbody/tr/td/table/tbody/tr/td[1]//a");
//					final List<String> urls = new ArrayList<String>();
//					for (int i = 0; i < menus.length; i++) {
//						TagNode menu = (TagNode) menus[i];
//						menuNames.add(Utils.removeTags(htmlCleaner.getInnerHtml(menu)));
//						urls.add(url + menu.getAttributeByName("href"));
//					}
//
//					arrayAdapter.addAll(menuNames);
//
//				} catch (XPatherException e) {
//					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//				}
////				TagNode menuRow = tagNode.getElementsByAttValue("class", "menu_text", true, false)[0];
////				TagNode[] menus = menuRow.getElementsByName("a", false);
//
//			}
//		});


	}

	private List<String> getNames(List<SupportedSite> supportedSites) {
		List<String> result = new ArrayList<String>();
		for (SupportedSite site : supportedSites) {
			result.add(site.getName());
		}
		return result;
	}
}
