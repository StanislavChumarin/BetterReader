package com.staschum.html2view.action;

import com.staschum.html2view.ContentViewer;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 21.12.12
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class Open implements Click {

	private final ContentViewer contentViewer;
	private final String url;
	private final String filterName;

	public Open(ContentViewer contentViewerActivity, String url, String filterName) {
		this.contentViewer = contentViewerActivity;
		this.url = url;
		this.filterName = filterName;
	}

	@Override
	public void click() {
		contentViewer.viewContent(url, filterName);
	}
}
