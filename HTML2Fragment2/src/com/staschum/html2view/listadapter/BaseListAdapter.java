package com.staschum.html2view.listadapter;

import android.content.Context;
import android.widget.BaseAdapter;
import com.staschum.html2view.objects.H2View;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:46
 */
public abstract class BaseListAdapter extends BaseAdapter implements H2VListAdapter {

	protected Context context;

	public BaseListAdapter(Context context) {
		this.context = context;
	}

}
