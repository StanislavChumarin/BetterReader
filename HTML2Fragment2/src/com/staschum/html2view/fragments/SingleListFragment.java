package com.staschum.html2view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.staschum.R;
import com.staschum.html2view.ContentViewFactory;
import com.staschum.html2view.objects.H2View;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 18:25
 */
public class SingleListFragment extends ContentFragment {

	//	private PagesReceiver pagesReceiver;
	private boolean updating;


	public static ContentFragment createFragment() {
		return new SingleListFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (views.isEmpty())
			return;

		for (H2View view : views) {
			ContentViewFactory.createView(this, document, view);
		}

//		pager = data.getPager();
//
//		updateData(data, document);
//
//		if (pager != null) {
//
//			View footerView = activity.getLayoutInflater().inflate(R.layout.listfooter, null, false);
//			listView.addFooterView(footerView);
//
//
//			listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//				@Override
//				public void onScrollStateChanged(AbsListView absListView, int i) {
//				}
//
//				@Override
//				public void onScroll(AbsListView absListView, int firstVisibleItem, int shownItems, int itemsCount) {
//					if (firstVisibleItem + shownItems == itemsCount && !updating) {
//						updating = true;
//						new Thread(new Runnable() {
//							@Override
//							public void run() {
//								Utils.getHtmlByUrlAsync(nextPageUrl, pagesReceiver);
//							}
//						}).start();
//
//					}
//				}
//			});
//		}
	}

//	private void updateData(FragmentDescriptor descriptor, Document doc) {
//		Elements dataForAdapter = doc.select(descriptor.getSelector());
//
//		FragmentDescriptor adapterDescriptor = descriptor.getData().get(0);
//		listAdapter.addData(dataForAdapter, adapterDescriptor.getData(), h2Adapter.click);
//
//		FragmentDescriptor action = descriptor.getAction();
//		actions.addAll(Utils.getValues(doc.select(action.getItemSelector()), action.getAttr()));
//
//		if (pager != null) {
//			nextPageUrl = url + doc.select(pager.getSelector()).attr(pager.getAttr());
//			pagesReceiver = new PagesReceiver(new Handler(), descriptor);
//		}
//	}

//	private class PagesReceiver extends ResultReceiver {
//
//		private FragmentDescriptor descriptor;
//
//		public PagesReceiver(Handler handler, FragmentDescriptor descriptor) {
//			super(handler);
//			this.descriptor = descriptor;
//		}
//
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode != Utils.STATUS_OK) {
//				return;
//			}
//
//			String htmlSource = resultData.getString(Utils.RESULT_KEY);
//
//			Document doc = Jsoup.parse(htmlSource, url);
//
//			updateData(descriptor, doc);
//			updating = false;
//		}
//	}
}
