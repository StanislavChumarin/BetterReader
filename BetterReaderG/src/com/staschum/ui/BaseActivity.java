package com.staschum.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.staschum.R;

public class BaseActivity extends SlidingFragmentActivity {

	private DownloadedItemsFragment downloadedItemsFragment = new DownloadedItemsFragment();


	private int titleRes;
	protected SherlockFragment mFrag;

	public BaseActivity(int titleRes) {
		this.titleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(titleRes);


		setBehindContentView(R.layout.menu_frame);

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mFrag = new MenuFragment();
		t.replace(R.id.menu_fragment, mFrag);
		t.commit();

		SlidingMenu slidingMenu = getSlidingMenu();
		setSlidingActionBarEnabled(true);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.defaultshadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);

		getSlidingMenu().setSecondaryMenu(R.layout.downloaded_items);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.defaultshadowright);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.downloads_list, downloadedItemsFragment)
				.commit();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void refreshDownloads() {
		downloadedItemsFragment.refreshList();
	}
}