package com.staschum.html2view;

import android.support.v4.app.Fragment;
import com.staschum.html2view.listadapter.BaseListAdapter;
import com.staschum.html2view.listadapter.ImageW2LinesAdapter;
import com.staschum.html2view.listadapter.OneLineListAdapter;
import com.staschum.html2view.listadapter.TwoLineListAdapter;
import com.staschum.html2view.objects.H2Adapter;
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
			public BaseListAdapter getListAdapter(Fragment fragment) {
				return new TwoLineListAdapter(fragment);
			}
		},
		ONE_LINE_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Fragment fragment) {
				return new OneLineListAdapter(fragment);
			}
		},
		IMAGE_WITH_TWO_LINES_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Fragment fragment) {
				ImageW2LinesAdapter imageW2LinesAdapter = new ImageW2LinesAdapter(fragment);
				imageW2LinesAdapter.imageLoader.clearCache();
				return imageW2LinesAdapter;
			}
		};

		public abstract BaseListAdapter getListAdapter(Fragment fragment);
	}

	public static BaseListAdapter createListAdapter(Fragment fragment, Elements elements, H2Adapter h2Adapter) {
		BaseListAdapter result;
		result = Adapter.valueOf(h2Adapter.adapterName.toUpperCase()).getListAdapter(fragment);
		result.addData(elements, h2Adapter.getViews(), h2Adapter.click);
		return result;
	}
}
