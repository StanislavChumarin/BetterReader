package com.staschum.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.ActionBar;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.SupportedSite;
import com.staschum.html2view.ViewComposer;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends BaseActivity implements ContentViewer {

	private ViewComposer viewComposer;

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_fragment_activity);

//		setBehindContentView(R.layout.menu_frame);
//
//		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
//		mFrag = new MenuFragment();
//		t.replace(R.id.menu_fragment, mFrag);
//		t.commit();

//		getSupportFragmentManager().beginTransaction().add(R.id.content, new Fragment()).commit();

	}

	@Override
	public void viewContent(final String url, final String filterName) {

		getSlidingMenu().showContent();

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
				if(stringFragmentMap == null)
					return;
				ActionBar actionBar = getSupportActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				actionBar.setDisplayShowTitleEnabled(true);
				actionBar.removeAllTabs();
				for(Map.Entry<String, Fragment> entry : stringFragmentMap.entrySet()) {
					ActionBar.Tab tab = actionBar.newTab().setText(entry.getKey())
							.setTabListener(new TabListenter<Fragment>(MainActivity.this, entry.getKey(), entry.getValue()));
					actionBar.addTab(tab);
				}
			}
		}.execute();

//		viewComposer.createFragment(baseUrl + url, R.raw.exua, new FragmentReady() {
//			@Override
//			protected void fragmentReady(Fragment fragment) {
//				FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
//				fragmentTransaction.replace(R.id.content, fragment);
//				fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
//			}
//		});
	}

	@Override
	public void openSite(SupportedSite supportedSite) {
		viewComposer = new ViewComposer(MainActivity.this, supportedSite.getFilters());
		setTitle(supportedSite.getName());
		viewContent(supportedSite.getUrl() + "/", null);
	}

}
