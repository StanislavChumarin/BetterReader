package com.staschum.html2view.objects;

import com.staschum.html2view.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
		return getString(fragmentDescriptorSrc, "layout_name");
	}

	public List<FragmentDescriptor> getData() {
		List<FragmentDescriptor> result = new ArrayList<FragmentDescriptor>();
		try {
			JSONArray array = fragmentDescriptorSrc.getJSONArray("data");
			for(int i = 0; i < array.length(); i++) {
				result.add(new FragmentDescriptor(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			return result;
		}
		return result;
	}

	public String getTargetUrl() {
		return getString(fragmentDescriptorSrc, "url");
	}

	public String getRequiredXPath() {
		return getString(fragmentDescriptorSrc, "should_exist");
	}

	public String getSelector() {
		return getString(fragmentDescriptorSrc, "selector");
	}



	private static String getString(JSONObject data, String key) {
		try {
			return data.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
		try {
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FragmentDescriptor getAction() {
		return new FragmentDescriptor(getJSONObject(fragmentDescriptorSrc, "action"));
	}

	public String getItemSelector() {
		return getString(fragmentDescriptorSrc, "item");
	}

	public String getAttr() {
		return getString(fragmentDescriptorSrc, "attr");
	}
}
