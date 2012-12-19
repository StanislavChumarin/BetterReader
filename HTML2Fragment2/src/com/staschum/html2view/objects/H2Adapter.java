package com.staschum.html2view.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:33
 */
public class H2Adapter {

	public final String type;
	public final String adapterName;

	private List<H2View<H2Attribute>> views;

	public H2Adapter(String type, String adapterName, List<H2View<H2Attribute>> views) {
		this.type = type;
		this.adapterName = adapterName;
		this.views = views;
	}

	public List<H2View<H2Attribute>> getViews() {
		List<H2View<H2Attribute>> result = new ArrayList<H2View<H2Attribute>>();
		Collections.copy(result, views);
		return result;
	}
}
