package com.staschum;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class DataViewDescription {

	private JSONObject jObject;

	public DataViewDescription(String jsonString) throws JSONException {
		jObject = new JSONObject((jsonString));
	}

	public DataViewDescription(JSONObject jObject) {
		this.jObject = jObject;
	}

	public String getTargetUrl() throws JSONException {
		return jObject.getString("url");
	}

	public String getRequiredXPath() throws JSONException {
		return jObject.getString("should_exist");
	}

	public int getLayoutId() throws JSONException {
		return App.getContext().getResources().getIdentifier(jObject.getString("layout_name"), "layout", App.getContext().getPackageName());
	}


}
