package com.staschum.html2view.objects;

import com.staschum.html2view.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class FragmentDescriptor {
	public DataType type;
	JSONObject fragmentDescriptorSrc;

	public FragmentDescriptor(JSONObject src) {
		fragmentDescriptorSrc = src;
	}

	public String getLayoutName() {
		return Utils.getString(fragmentDescriptorSrc, "layout_name");
	}

	public JSONArray getData() {
		try {
			return fragmentDescriptorSrc.getJSONArray("data");
		} catch (JSONException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}

	public String getTargetUrl() {
		return Utils.getString(fragmentDescriptorSrc, "url");
	}

	public String getRequiredXPath() {
		return Utils.getString(fragmentDescriptorSrc, "should_exist");
	}
}
