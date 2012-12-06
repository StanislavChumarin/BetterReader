package com.staschum.html2view;

import com.staschum.html2view.objects.ClickAction;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public enum DataDescription {
	LIST,
	TEXT,
	IMAGE;

	private int id;
	private int layoutId;
	private String xPath;
	private String excludeData;
	private DataDescription dataDescription;
	private ClickAction action;

	public static DataDescription construct(int id, int layoutId, String xPath, String excludeData, String action) {
		return null;
	}
}
