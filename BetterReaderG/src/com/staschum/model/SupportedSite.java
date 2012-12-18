package com.staschum.model;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 15:07
 * SupportedSite represents all sites that are supported by application.
 * Includes rules of parsing the site. (in future)
 */
public class SupportedSite {
	private String name;
	private String url;
	private JSONObject json;

	public SupportedSite(String name, String url, JSONObject jsonObject) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public JSONObject getJsonFilters() {
		return json;
	}
}
