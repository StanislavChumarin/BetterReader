package com.staschum.html2view;

import android.util.Log;
import com.staschum.html2view.objects.H2Filter;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	private Map<String, H2Filter> filters;

	public SupportedSite(String name, String url, String jsonFilters) {
		this.name = name;
		this.url = url;
		try {
			filters = JSON2Objects.json2Fiters(jsonFilters);
		} catch (IOException e) {
			throw new IllegalArgumentException("Wrong of malformed json", e);
		}
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, H2Filter> getFilters() {
		return filters;
	}
}
