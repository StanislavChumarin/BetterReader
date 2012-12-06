package com.staschum.model;

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

	public SupportedSite(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
