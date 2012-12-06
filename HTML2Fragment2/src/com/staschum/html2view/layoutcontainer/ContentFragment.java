package com.staschum.html2view.layoutcontainer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.staschum.html2view.H2VObject;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class ContentFragment extends Fragment implements H2VObject {

	protected String url;
	protected static String URL_KEY = "url";
	protected TagNode tagNode;
	protected JSONArray fragmentData;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		url = getArguments().getString(URL_KEY, "");
	}

	public void setData(TagNode tagNode, JSONArray fragmentData) {
		this.tagNode = tagNode;
		this.fragmentData = fragmentData;
	}
}
