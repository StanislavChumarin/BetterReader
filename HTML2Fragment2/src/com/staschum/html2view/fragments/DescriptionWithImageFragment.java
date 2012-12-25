package com.staschum.html2view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.staschum.R;
import com.staschum.html2view.ContentViewFactory;
import com.staschum.html2view.imageloader.ImageLoader;
import com.staschum.html2view.objects.H2View;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 10.12.12
 * Time: 11:22
 */
public class DescriptionWithImageFragment extends ContentFragment {

	public static ContentFragment createFragment() {
		return new DescriptionWithImageFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.title_image_description_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getActivity();

		ImageLoader imageLoader = ImageLoader.getInstance(activity);
		imageLoader.clearCache();

		if (views.size() < 3)
			return;

		for (H2View view : views) {
			ContentViewFactory.createView(this, document, view);
		}
//		FragmentDescriptor data = views.get(0);
//		View header_description = activity.getLayoutInflater().inflate(R.layout.title_image_description_layout, null);
//		TextView titleText = (TextView) header_description.findViewById(R.id.title_text);
//		titleText.setText(document.select(data.getSelector()).text());
//
//		data = views.get(1);
//		ImageView bigImage = (ImageView) header_description.findViewById(R.id.big_image);
//		imageLoader.DisplayImage(document.select(data.getSelector()).attr("src"), bigImage);
//		bigImage.setVisibility(View.VISIBLE);
//
//		data = views.get(2);
//		TextView descriptionText = (TextView) header_description.findViewById(R.id.description_text);
//		descriptionText.setText(Html.fromHtml(document.select(data.getSelector()).outerHtml().replace("<a", "<b").replace("</a", "</b")));
//		descriptionText.setVisibility(View.VISIBLE);
//
//		data = views.get(3);
//		final ListView listView = (ListView) activity.findViewById(R.id.list_content);
//		listView.addHeaderView(header_description, null, false);
//		Elements dataForAdapter = document.select(data.getSelector());
//		FragmentDescriptor adapterDescriptor = data.getData().get(0);
//		BaseListAdapter listAdapter = ListAdapterFactory.createListAdapter(activity, adapterDescriptor);
//		listAdapter.addData(dataForAdapter, adapterDescriptor.getData(), h2Adapter.click);
//		listView.setAdapter(listAdapter);
//
//		FragmentDescriptor action = data.getAction();
//		if (action != null) {
//			final List<String> actions = Utils.getValues(document.select(action.getItemSelector()), action.getAttr());
//
//			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//					((ContentViewer) activity).viewContent(url, actions.get(i - listView.getHeaderViewsCount()));
//				}
//			});
//		}
	}
}