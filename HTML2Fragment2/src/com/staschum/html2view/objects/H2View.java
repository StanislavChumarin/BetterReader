package com.staschum.html2view.objects;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:32
 */
public class H2View<T> {

	public final String viewType;
	public final String viewId;
	public final String selector;
	public final String attribute;
	private T innerStructure;

	public H2View(String viewType, String viewId, String selector, String attribute, T innerStructure) {
		this.viewType = viewType;
		this.viewId = viewId;
		this.selector = selector;
		this.attribute = attribute;
		this.innerStructure = innerStructure;
	}

	public T getInnerStructure() {
		return innerStructure;
	}
}
