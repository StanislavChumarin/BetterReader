package com.staschum.html2view;

import android.app.Activity;
import android.view.View;
import com.staschum.html2view.action.Click;
import com.staschum.html2view.objects.H2Click;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 20.12.12
 * Time: 19:22
 * This factory creates click actions.
 */
public class ContentClickFactory {

	private static enum ClickType {
		OPEN, DOWNLOAD, PICTURE_GALLERY
	}

	public static Click createClick(Activity activity, Document document, H2Click click) {
		ClickType.valueOf(click.actionName.toUpperCase());
		return null;
	}

}
