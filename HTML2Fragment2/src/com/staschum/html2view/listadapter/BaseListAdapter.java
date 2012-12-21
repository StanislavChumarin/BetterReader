package com.staschum.html2view.listadapter;

import android.content.Context;
import android.widget.BaseAdapter;
import com.staschum.html2view.action.Click;
import com.staschum.html2view.objects.H2View;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:46
 */
public abstract class BaseListAdapter extends BaseAdapter implements H2VListAdapter {

	protected Context context;

	protected List<Click> itemClicks = new ArrayList<Click>();

	public BaseListAdapter(Context context) {
		this.context = context;
	}

	public Click getClick(int position) {
		if (itemClicks.isEmpty() || itemClicks.size() <= position)
			return null;
		return itemClicks.get(position);
	}
}
