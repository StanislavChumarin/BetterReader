package com.staschum.html2view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.staschum.html2view.H2VObject;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONArray;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:03
 */
public abstract class ContentFragment extends Fragment implements H2VObject {

	protected String url;
	protected static String URL_KEY = "url";
	protected Document document;
	protected List<FragmentDescriptor> fragmentData;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		url = getArguments().getString(URL_KEY);
	}

	public void setData(Document doc, List<FragmentDescriptor> fragmentData) {
		this.document = doc;
		this.fragmentData = fragmentData;
	}
}
