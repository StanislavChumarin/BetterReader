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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
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

		showParsedContent(url, filterName, false);

	}

	private void showParsedContent(final String url, final String filterName, boolean inNewActivity) {

		if (inNewActivity) {
			startActivity(createActivity(this, url, filterName));
		} else {

			if (url == null || url.isEmpty()) {
				findViewById(R.id.spinning_thing).setVisibility(View.GONE);
				return;
			}

			new AsyncTask<Void, Void, Map<String, Fragment>>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					getSupportActionBar().removeAllTabs();
				}

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


					ActionBar actionBar = getSupportActionBar();
					for (int i = 0; i < stringFragmentMap.entrySet().size(); i++) {
						actionBar.addTab(getTab(actionBar));
					}
					findViewById(R.id.spinning_thing).setVisibility(View.GONE);
				}

			}.execute();
		}
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

		showParsedContent(url, filterName, true);
	}

	@Override
	public void openSite(SupportedSite supportedSite) {
		viewComposer = new ViewComposer(MainActivity.this, supportedSite.getFilters());
		setTitle(supportedSite.getName());
		getSlidingMenu().showContent();
		url = supportedSite.getUrl() + "/";
		filterName = null;
		showParsedContent(url, filterName, false);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.refresh_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh_menu_item:
				findViewById(R.id.spinning_thing).setVisibility(View.VISIBLE);
				getSupportActionBar().removeAllTabs();
				showParsedContent(url, filterName, false);
				return true;
			case R.id.downloads_menu_item:
				refreshDownloads();
				getSlidingMenu().showSecondaryMenu();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
