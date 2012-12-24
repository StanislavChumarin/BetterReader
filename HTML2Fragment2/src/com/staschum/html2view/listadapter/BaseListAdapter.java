package com.staschum.html2view.listadapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;
import com.staschum.html2view.action.Click;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:46
 */
public abstract class BaseListAdapter extends BaseAdapter implements H2VListAdapter {

	protected Fragment fragment;

	protected List<Click> itemClicks = new ArrayList<Click>();

	public BaseListAdapter(Fragment fragment) {
		this.fragment = fragment;
	}

	public Click getClick(int position) {
		if (itemClicks.isEmpty() || itemClicks.size() <= position)
			return null;
		return itemClicks.get(position);
	}
}
