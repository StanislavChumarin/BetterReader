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
	public final H2Click click;

	private List<H2View> views;

	public H2Adapter(String type, String adapterName, List<H2View> views, H2Click click) {
		this.type = type;
		this.adapterName = adapterName;
		this.views = views;
		this.click = click;
	}

	public List<H2View> getViews() {
		List<H2View> result = new ArrayList<H2View>(views);
		return result;
	}

	@Override
	public String toString() {
		return "\n	H2Adapter: [" + type + ", " + adapterName + ", " +views + ", " + click + "]";
	}
}
