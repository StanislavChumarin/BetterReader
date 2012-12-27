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

//		ImageLoader.getInstance().clearDiscCache();
//		ImageLoader.getInstance().clearMemoryCache();

		if (views.size() < 3)
			return;

		for (H2View view : views) {
			ContentViewFactory.createView(this, document, view);
		}
	}
}
