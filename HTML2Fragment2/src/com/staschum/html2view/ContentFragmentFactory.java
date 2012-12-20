package com.staschum.html2view;

import android.support.v4.app.Fragment;
import com.staschum.html2view.fragments.DescriptionWithImageAndFileList;
import com.staschum.html2view.fragments.SingleListFragment;
import com.staschum.html2view.fragments.ContentFragment;
import com.staschum.html2view.objects.H2Screen;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 13:00
 */
public class ContentFragmentFactory {

	public static enum FragmentLayouts {
		SINGLE_LIST {
			@Override
			public ContentFragment getFragment() {
				return SingleListFragment.createFragment();
			}
		},
		DESCRIPTION_WITH_IMAGE_AND_FILE_LIST {
			@Override
			public ContentFragment getFragment() {
				return DescriptionWithImageAndFileList.createFragment();
			}
		};

		public abstract ContentFragment getFragment();
	}

	public static Fragment getFragment(Document document, H2Screen h2Screen) {
		ContentFragment fragment = FragmentLayouts.valueOf(h2Screen.screenName.toUpperCase()).getFragment();
		fragment.setData(document, h2Screen.getViews());
		return fragment;
	}
}
