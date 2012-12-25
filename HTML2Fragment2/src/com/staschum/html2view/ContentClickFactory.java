package com.staschum.html2view;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.staschum.html2view.action.Click;
import com.staschum.html2view.action.Download;
import com.staschum.html2view.action.Open;
import com.staschum.html2view.objects.H2Click;
import org.jsoup.nodes.Element;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 20.12.12
 * Time: 19:22
 * This factory creates click actions.
 */
public class ContentClickFactory {


	private static enum ClickType {
		OPEN {
			@Override
			Click getClick(Activity activity, String url, String filterName) {
				return new Open((ContentViewer) activity, url, filterName);
			}
		}, DOWNLOAD {
			@Override
			Click getClick(Activity activity, String url, String filterName) {
				return new Download(activity, url);
			}
		}, PICTURE_GALLERY {
			@Override
			Click getClick(Activity activity, String url, String filterName) {
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		};

		abstract Click getClick(Activity activity, String url, String filterName);
	}

	/**
	 * Creates Click objects.
	 *
	 * @param fragment fragment, that can be responsible for some actions
	 * @param element  Jsoup Element that contains url of click (note: Document is also Element)
	 * @param click    H2Click object that represents parameters for future Click
	 * @return created Click.
	 */
	public static Click createClick(Fragment fragment, Element element, H2Click click) {
		String url = (String) Utils.getAttributeValue(element.select(click.selector), click.attribute);
		return ClickType.valueOf(click.actionName.toUpperCase()).getClick(fragment.getActivity(), url, click.filterName);
	}

}
