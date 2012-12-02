package com.staschum.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.staschum.R;
import org.htmlcleaner.TagNode;

public class MainActivity extends BaseActivity implements MenuFragment.HtmlViewer {

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_fragment_activity);

		getSupportFragmentManager().beginTransaction().add(R.id.content, MainFragment.createFragment("http://www.ex.ua/")).commit();

	}

	private boolean notHomeLink(TagNode tagNode) {
		boolean result = true;
		if (tagNode.getAttributeByName("a").equals("/")) {
			result = false;
		}
		return result;
	}

	@Override
	public void viewHtml(String url) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content, MainFragment.createFragment(url));
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
