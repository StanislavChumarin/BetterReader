package com.staschum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 17:16
 */
public class MenuFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fun_image, null);
	}
}
