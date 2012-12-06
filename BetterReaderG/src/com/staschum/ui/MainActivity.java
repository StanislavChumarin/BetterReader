package com.staschum.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.staschum.R;
import com.staschum.WaitingFragment;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.FragmentReady;
import com.staschum.html2view.SingleListFragment;
import com.staschum.html2view.ViewComposer;
import com.staschum.html2view.managers.DescriptionManager;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.htmlcleaner.TagNode;

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

	private boolean notHomeLink(TagNode tagNode) {
		boolean result = true;
		if (tagNode.getAttributeByName("a").equals("/")) {
			result = false;
		}
		return result;
	}

	@Override
	public void viewContent(String baseUrl, String url) {
//		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//		fragmentTransaction.replace(R.id.content, new WaitingFragment());
//		fragmentTransaction.commit();
//
//		getSlidingMenu().showContent();

		getSlidingMenu().showContent();

		ViewComposer vc = new ViewComposer(MainActivity.this, baseUrl);

		vc.createFragment(baseUrl + url, R.raw.exua, new FragmentReady() {
			@Override
			protected void fragmentReady(Fragment fragment) {
				replaceContent(fragment);
//				FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
//				fragmentTransaction.replace(R.id.content, fragment);
//				fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();

			}
		});
	}

	private void replaceContent(Fragment fragment) {
		FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
