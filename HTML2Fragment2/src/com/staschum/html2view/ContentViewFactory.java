package com.staschum.html2view;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.staschum.html2view.action.Click;
import com.staschum.html2view.listadapter.BaseListAdapter;
import com.staschum.html2view.objects.H2Adapter;
import com.staschum.html2view.objects.H2Attribute;
import com.staschum.html2view.objects.H2View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 20.12.12
 * Time: 15:10
 * This factory should be used for building views.
 */
public class ContentViewFactory {

	public static enum ViewType {
		TEXT {
			@Override
			public void setData(final Fragment fragment, final Document document, final H2View view) {
				int id = fragment.getResources().getIdentifier(view.viewId, "id", fragment.getActivity().getPackageName());
				TextView textView = (TextView) fragment.getView().findViewById(id);
				textView.setText(Utils.getAttributeValue(document.select(view.selector), (H2Attribute) view.innerStructure));

				if (view.click != null) {
					textView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ContentClickFactory.createClick(fragment, document, view.click).click();
						}
					});
				}
			}
		},
		IMAGE {
			@Override
			public void setData(Fragment fragment, final Document document, final H2View view) {
				int id = fragment.getResources().getIdentifier(view.viewId, "id", fragment.getActivity().getPackageName());
				String imageUrl = (String) Utils.getAttributeValue(document.select(view.selector), (H2Attribute) view.innerStructure);
				ImageView imageView = (ImageView) fragment.getView().findViewById(id);
				// Get singletone instance of ImageLoader
				ImageLoader imageLoader = ImageLoader.getInstance();
				// Initialize ImageLoader with configuration. Do it once.
				imageLoader.displayImage(imageUrl, imageView);

				if (view.click != null) {
					final Click click = ContentClickFactory.createClick(fragment, document, view.click);
					imageView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							click.click();
						}
					});
				}
			}
		},
		LIST {
			@Override
			public void setData(final Fragment fragment, final Document document, final H2View view) {
				int id = fragment.getResources().getIdentifier(view.viewId, "id", fragment.getActivity().getPackageName());
				ListView listView = (ListView) fragment.getView().findViewById(id);
				final BaseListAdapter baseListAdapter = ListAdapterFactory.createListAdapter(fragment, document.select(view.selector), (H2Adapter) view.innerStructure);
				listView.setAdapter(baseListAdapter);

				if (((H2Adapter) view.innerStructure).click != null) {
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
							Click click = baseListAdapter.getClick(i);
							if (click != null) {
								click.click();
							}
						}
					});
				}

				if (view.click != null) {
					listView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ContentClickFactory.createClick(fragment, document, view.click).click();
						}
					});
				}

				if (view.pager != null) {
					listView.setOnScrollListener(new AbsListView.OnScrollListener() {

						boolean loading = false;
						Document lastPage;

						@Override
						public void onScrollStateChanged(AbsListView absListView, int i) {
						}

						@Override
						public void onScroll(AbsListView absView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							if (lastPage == null)
								lastPage = document;
							if (!loading && firstVisibleItem + visibleItemCount == totalItemCount) {
								loading = true;
								final String url = Utils.getAttributeValue(lastPage.select(view.pager.selector), view.pager.attribute).toString();
								final String baseUri = document.baseUri();
								Utils.getHtmlByUrlAsync(url, new ResultReceiver(new Handler()) {
									@Override
									protected void onReceiveResult(int resultCode, Bundle resultData) {
										if (resultCode != Utils.STATUS_OK) {
											loading = false;
											return;
										}
										String html = resultData.getString(Utils.RESULT_KEY, "");
										lastPage = Jsoup.parse(html, baseUri);
										baseListAdapter.addData(lastPage.select(view.selector));
										loading = false;
									}
								});

							}
						}
					});
				}
			}
		};

		public abstract void setData(Fragment fragment, Document document, H2View view);

	}

	public static void createView(Fragment fragment, Document document, H2View view) {
		ViewType.valueOf(view.viewType.toUpperCase()).setData(fragment, document, view);
	}

}
