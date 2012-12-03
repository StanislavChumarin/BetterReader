package com.staschum.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.staschum.R;
import com.staschum.Utils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 17:16
 */
public class MenuFragment extends SherlockListFragment {

	private List<String> menuNames = new ArrayList<String>();
	private HtmlViewer htmlViewer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		htmlViewer = (HtmlViewer) getSherlockActivity();
		final String url = "http://www.ex.ua";
		Utils.getHtmlByUrlAsync(url + "/", new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				if (resultCode != Utils.STATUS_OK)
					return;
				HtmlCleaner htmlCleaner = new HtmlCleaner();
				// take default cleaner properties
				CleanerProperties props = htmlCleaner.getProperties();

				TagNode tagNode = htmlCleaner.clean(resultData.getString(Utils.RESULT_KEY, ""));
				try {
					Object[] menus = tagNode.evaluateXPath("/body/table/tbody/tr/td/table/tbody/tr/td[1]//a");
					final List<String> urls = new ArrayList<String>();
					for (int i = 0; i < menus.length; i++) {
						TagNode menu = (TagNode)menus[i];
						menuNames.add(Utils.removeTags(htmlCleaner.getInnerHtml(menu)));
						urls.add(url + menu.getAttributeByName("href"));
					}
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.menu_list_row, R.id.text);
					arrayAdapter.addAll(menuNames);
					getListView().setAdapter(arrayAdapter);
					getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
							htmlViewer.viewHtml(urls.get(i));
							((MainActivity) htmlViewer).getSlidingMenu().showContent(true);
						}
					});
				} catch (XPatherException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
//				TagNode menuRow = tagNode.getElementsByAttValue("class", "menu_text", true, false)[0];
//				TagNode[] menus = menuRow.getElementsByName("a", false);

			}
		});


	}

	public static interface HtmlViewer {
		void viewHtml(String url);
	}
}
