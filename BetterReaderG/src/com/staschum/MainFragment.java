package com.staschum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 18:25
 */
public class MainFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, null);
	}
}
