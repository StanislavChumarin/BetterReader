package com.staschum.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.staschum.R;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 24.12.12
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class TabListenter<T extends Fragment> implements ActionBar.TabListener {

	private Activity activity;
	private String tag;
	private Fragment fragment;
	private boolean wasShown = false;

	public TabListenter(Activity activity, String tag, Fragment fragment) {

		this.activity = activity;
		this.tag = tag;
		this.fragment = fragment;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		if(wasShown)
		ft.attach(fragment);
		else {
			ft.add(R.id.content, fragment, tag);
			wasShown = true;
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		ft.detach(fragment);
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
