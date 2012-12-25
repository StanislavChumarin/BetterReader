package com.staschum.html2view.objects;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 19.12.12
 * Time: 11:51
 */
public class H2Click {

	public final String actionName;
	public final String selector;
	public final String attribute;
	public final String filterName;

	public H2Click(String actionName, String selector, String attribute, String filterName) {
		this.actionName = actionName;
		this.selector = selector;
		this.attribute = attribute;
		this.filterName = filterName;
	}

	@Override
	public String toString() {
		return "\n	H2Click: [" + actionName + ", " + selector + ", " + attribute + ", " + filterName + "]";
	}
}
