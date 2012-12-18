package com.staschum.html2view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.staschum.html2view.fragments.ContentFragment;
import com.staschum.html2view.managers.DescriptionManager;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:40
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

				String htmlSource = resultData.getString(Utils.RESULT_KEY);

				Document doc = Jsoup.parse(htmlSource, baseUrl);
//				Elements menuLinks = doc.select(".menu_text:eq(0)>a[href!=/]");
//				for(Element menuItem : menuLinks) {
//					Log.v("MENU", menuItem.text() + " link: " + menuItem.attr("href"));
//				}

//				HtmlCleaner htmlCleaner = new HtmlCleaner();
//				TagNode tagNode = htmlCleaner.clean(htmlSource);

				FragmentDescriptor descriptor = new DescriptionManager(context).getDescription(url, doc, fileId, false);

				ContentFragment fragment = ContentFragmentFactory.getFragment(baseUrl, descriptor.getLayoutName());
				List<FragmentDescriptor> fragmentData = descriptor.getData();
				fragment.setData(doc, fragmentData);

				fragmentReady.fragmentReady(fragment);
			}
		});
	}
}
