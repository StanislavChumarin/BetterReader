package com.staschum.html2view;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class DataViewDescription {

	private JSONObject jObject;
	private List<DataDescription> dataDescription;
	private Context context;

	public DataViewDescription(Context context, String jsonString) {
		this.context = context;
		try {
			jObject = new JSONObject((jsonString));
		} catch (JSONException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public DataViewDescription(JSONObject jObject) {
		this.jObject = jObject;
	}

	public String getTargetUrl() {
		return getStringValue("url");
	}

	public String getRequiredXPath() {
		return getStringValue("should_exist");
	}

	public int getLayoutId() {
		return context.getResources().getIdentifier(getStringValue("layout_name"), "layout", context.getPackageName());
	}

	private String getStringValue(String key) {
		String result;
		try {
			result = jObject.getString(key);
		} catch (JSONException e) {
			result = "";
		}
		return result;
	}

	public List<DataDescription> getDataDescription() {
		return dataDescription;
	}

	public String getLayoutName() {
		return getStringValue("layout_name");
	}
}
