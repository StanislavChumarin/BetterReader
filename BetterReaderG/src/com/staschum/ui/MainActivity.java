package com.staschum.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.FragmentReady;
import com.staschum.html2view.ViewComposer;

public class MainActivity extends BaseActivity implements ContentViewer {

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_fragment_activity);

		setBehindContentView(R.layout.menu_frame);

		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mFrag = new MenuFragment();
		t.replace(R.id.menu_fragment, mFrag);
		t.commit();

//		getSupportFragmentManager().beginTransaction().add(R.id.content, new Fragment()).commit();

	}

	@Override
	public void viewContent(String baseUrl, String url) {

		getSlidingMenu().showContent();

		ViewComposer vc = new ViewComposer(MainActivity.this, baseUrl);

		vc.createFragment(baseUrl + url, R.raw.exua, new FragmentReady() {
			@Override
			protected void fragmentReady(Fragment fragment) {
				FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.content, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
	}

}
