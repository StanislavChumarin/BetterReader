package com.staschum.html2view.layoutcontainer;

import android.content.Context;
import android.widget.BaseAdapter;
import com.staschum.html2view.H2VListAdapter;
import com.staschum.html2view.H2VObject;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseListAdapter extends BaseAdapter implements H2VListAdapter {

	protected Context context;

	public BaseListAdapter(Context context) {
		this.context = context;
	}
}
