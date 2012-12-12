package com.staschum.html2view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.ImageLoader;
import com.staschum.html2view.ListAdapterFactory;
import com.staschum.html2view.Utils;
import com.staschum.html2view.listadapter.BaseListAdapter;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 10.12.12
 * Time: 11:22
 */
public class DescriptionWithImageAndFileList extends ContentFragment{

	public static ContentFragment createFragment(String url) {
		ContentFragment fragment = new DescriptionWithImageAndFileList();
		Bundle args = new Bundle();
		args.putString(URL_KEY, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getActivity();

		ImageLoader imageLoader = new ImageLoader(activity);
		imageLoader.clearCache();

		activity.setTitle(document.select("title").text());

		if(fragmentData.size() < 4)
			return;

		FragmentDescriptor data = fragmentData.get(0);
		View header_description = activity.getLayoutInflater().inflate(R.layout.title_image_description_layout, null);
		TextView titleText = (TextView) header_description.findViewById(R.id.title_text);
		titleText.setText(document.select(data.getSelector()).text());

		data = fragmentData.get(1);
		ImageView bigImage = (ImageView) header_description.findViewById(R.id.big_image);
		imageLoader.DisplayImage(document.select(data.getSelector()).attr("src"), bigImage, true);
		bigImage.setVisibility(View.VISIBLE);

		data = fragmentData.get(2);
		TextView descriptionText = (TextView) header_description.findViewById(R.id.description_text);
		descriptionText.setText(Html.fromHtml(document.select(data.getSelector()).html()));
		descriptionText.setVisibility(View.VISIBLE);

		data = fragmentData.get(3);
		final ListView listView = (ListView) activity.findViewById(R.id.list_content);
		listView.addHeaderView(header_description, null, false);
		Elements dataForAdapter = document.select(data.getSelector());
		FragmentDescriptor adapterDescriptor = data.getData().get(0);
		BaseListAdapter listAdapter = ListAdapterFactory.createListAdapter(activity, adapterDescriptor);
		listAdapter.addData(dataForAdapter, adapterDescriptor.getData());
		listView.setAdapter(listAdapter);

		FragmentDescriptor action = data.getAction();
		final List<String> actions = Utils.getValues(document.select(action.getItemSelector()), action.getAttr());

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				((ContentViewer) activity).viewContent(url, actions.get(i - listView.getHeaderViewsCount()));
			}
		});
	}
}
