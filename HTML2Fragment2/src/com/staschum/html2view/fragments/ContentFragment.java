package com.staschum.html2view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.staschum.html2view.H2ViewContainer;
import com.staschum.html2view.objects.H2View;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:03
 */
public abstract class ContentFragment extends Fragment implements H2ViewContainer {

	protected Document document;
	protected List<H2View> views;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void setData(Document doc, List<H2View> views) {
		this.document = doc;
		this.views = views;
	}
}
