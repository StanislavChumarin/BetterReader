package com.staschum.html2view.action;

import com.staschum.html2view.objects.FragmentDescriptor;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 10.12.12
 * Time: 12:32
 */
public class ActionFactory {

	private static enum Action{
		OPEN, DOWNLOAD
	}

	public static ClickAction createAction(FragmentDescriptor descriptor) {
		return new ClickAction() {
			@Override
			public String toString() {
				return super.toString();
			}
		};
	}

}
