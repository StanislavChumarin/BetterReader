package com.staschum.html2view;

import com.staschum.html2view.fragments.DescriptionWithImageAndFileList;
import com.staschum.html2view.fragments.SingleListFragment;
import com.staschum.html2view.fragments.ContentFragment;
import com.staschum.html2view.objects.Layouts;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:00
 */
public class ContentFragmentFactory {

	public static ContentFragment getFragment(String baseUrl, String layout_name) {
		ContentFragment result = null;
		Layouts layout = Layouts.valueOf(layout_name.toUpperCase());
		switch (layout) {
			case SINGLE_LIST:
				result = SingleListFragment.createFragment(baseUrl);
				break;
			case DESCRIPTION_WITH_IMAGE_AND_FILE_LIST:
				result = DescriptionWithImageAndFileList.createFragment(baseUrl);
				break;
		}
		return result;
	}
}
