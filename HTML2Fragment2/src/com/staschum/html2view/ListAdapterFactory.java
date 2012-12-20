package com.staschum.html2view;

import android.content.Context;
import com.staschum.html2view.listadapter.BaseListAdapter;
import com.staschum.html2view.listadapter.ImageW2LinesAdapter;
import com.staschum.html2view.listadapter.OneLineListAdapter;
import com.staschum.html2view.listadapter.TwoLineListAdapter;
import com.staschum.html2view.objects.H2Adapter;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 05.12.12
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class ListAdapterFactory {

	private static enum Adapter {
		TWO_LINES_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Context context) {
				return new TwoLineListAdapter(context);
			}
		},
		ONE_LINE_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Context context) {
				return new OneLineListAdapter(context);
			}
		},
		IMAGE_W_TWO_LINES_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Context context) {
				ImageW2LinesAdapter imageW2LinesAdapter = new ImageW2LinesAdapter(context);
				imageW2LinesAdapter.imageLoader.clearCache();
				return imageW2LinesAdapter;
			}
		};

		public abstract BaseListAdapter getListAdapter(Context context);
	}

	public static BaseListAdapter createListAdapter(Context context, Elements elements, H2Adapter h2Adapter) {
		BaseListAdapter result;
		result = Adapter.valueOf(h2Adapter.adapterName.toUpperCase()).getListAdapter(context);
		result.addData(elements, h2Adapter.getViews());
		return result;
	}
}
