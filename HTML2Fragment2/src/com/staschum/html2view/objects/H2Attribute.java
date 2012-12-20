package com.staschum.html2view.objects;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 19.12.12
 * Time: 12:25
 */
public class H2Attribute {
	public final String name;

	public H2Attribute(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Attribute: [" + name + "]";
	}
}
