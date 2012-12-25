package com.staschum.html2view.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:31
 */
public class H2Filter {
	public final String url;
	public final String shouldExistSelector;
	private List<H2Screen> screens;

	public H2Filter(String url, String shouldExistSelector, List<H2Screen> screens) {
		this.url = url;
		this.shouldExistSelector = shouldExistSelector;
		this.screens = screens;
	}

	public List<H2Screen> getScreens() {
		List<H2Screen> result = new ArrayList<H2Screen>(screens);
		return result;
	}

	@Override
	public String toString() {
		return "\nH2Filter: [" + url + ", " + shouldExistSelector + ", " + screens + "]\n";
	}
}
