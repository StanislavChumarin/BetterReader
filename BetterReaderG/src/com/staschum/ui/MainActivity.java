package com.staschum.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.actionbarsherlock.app.ActionBar;
import com.slidingmenu.lib.SlidingMenu;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.SupportedSite;
import com.staschum.html2view.ViewComposer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements ContentViewer {

	private static ViewComposer viewComposer;

	private static String URL = "url";
	private static String FILTER_NAME = "filterName";

	private String url;
	private String filterName;
	private ViewPager viewPager;

	public static Intent createActivity(Context context, String url, String filterName) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(URL, url);
		intent.putExtra(FILTER_NAME, filterName);
		return intent;
	}

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_fragment_activity);

		url = getIntent().getStringExtra(URL);
		filterName = getIntent().getStringExtra(FILTER_NAME);

		if (url == null || url.isEmpty()) {
			findViewById(R.id.spinning_thing).setVisibility(View.GONE);
			return;
		}

		new AsyncTask<Void, Void, Map<String, Fragment>>() {

			@Override
			protected Map<String, Fragment> doInBackground(Void... voids) {
				Map<String, Fragment> fragments = null;
				try {
					fragments = viewComposer.createFragments(url, filterName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return fragments;
			}

			@Override
			protected void onPostExecute(Map<String, Fragment> stringFragmentMap) {
				super.onPostExecute(stringFragmentMap);
				if (stringFragmentMap == null)
					return;

				viewPager = (ViewPager) findViewById(R.id.pager);
				viewPager.setVisibility(View.VISIBLE);
				viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), stringFragmentMap));
				viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int i, float v, int i2) {
					}

					@Override
					public void onPageSelected(int position) {
						switch (position) {
							case 0:
								getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
								break;
							default:
								getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
								break;
						}
					}

					@Override
					public void onPageScrollStateChanged(int position) {
					}
				});

				ActionBar actionBar = getSupportActionBar();
				for (int i = 0; i < stringFragmentMap.entrySet().size(); i++) {
					actionBar.addTab(getTab(actionBar));
				}
				findViewById(R.id.spinning_thing).setVisibility(View.GONE);
			}

		}.execute();

	}

	private ActionBar.Tab getTab(ActionBar actionBar) {
		return actionBar.newTab().setTabListener(new ActionBar.TabListener() {

			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
			}
		});
	}

	@Override
	public void viewContent(String url, String filterName) {

		getSlidingMenu().showContent();

		startActivity(createActivity(this, url, filterName));
	}

	@Override
	public void openSite(SupportedSite supportedSite) {
		viewComposer = new ViewComposer(MainActivity.this, supportedSite.getFilters());
		setTitle(supportedSite.getName());
		viewContent(supportedSite.getUrl() + "/", null);
	}


	private static class MainPagerAdapter extends FragmentStatePagerAdapter {

		private final List<Fragment> fragmentList = new ArrayList<Fragment>();
		private final List<String> titlesList = new ArrayList<String>();

		public MainPagerAdapter(FragmentManager fm, Map<String, Fragment> stringFragmentMap) {
			super(fm);
			for (Map.Entry<String, Fragment> entry : stringFragmentMap.entrySet()) {
				fragmentList.add(entry.getValue());
				titlesList.add(entry.getKey());
			}

		}

		@Override
		public Fragment getItem(int i) {
			return fragmentList.get(i);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titlesList.get(position);
		}
	}
}
