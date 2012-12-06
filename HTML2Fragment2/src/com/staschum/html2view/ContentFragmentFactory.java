package com.staschum.html2view;

import com.staschum.html2view.layoutcontainer.ContentFragment;
import com.staschum.html2view.objects.Layouts;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class ContentFragmentFactory {

	public static ContentFragment getFragment(String baseUrl, String layout_name) {
		ContentFragment result = null;
		Layouts layout = Layouts.valueOf(layout_name.toUpperCase());
		switch (layout) {
			case SINGLE_LIST:
				result = SingleListFragment.createFragment(baseUrl);
				break;
		}
		return result;
	}
}
