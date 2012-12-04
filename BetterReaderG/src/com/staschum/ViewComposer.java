package com.staschum;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import com.staschum.managers.DescriptionManager;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class ViewComposer {

	private IApplication app = App.getInstance();
	private DescriptionManager descriptionManager;
	private DataViewDescription description;

	public ViewComposer(Context context) {
		descriptionManager = app.getDescriptionManager();
	}

	public Fragment getFragment(String url, TagNode tagNode) throws JSONException, XPatherException {
		description = descriptionManager.getDescription(url, tagNode);
		return null;
	}
}
