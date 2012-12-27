package com.staschum.html2view;

import android.support.v4.app.Fragment;
import com.staschum.html2view.listadapter.BaseListAdapter;
import com.staschum.html2view.listadapter.ImageW2LinesAdapter;
import com.staschum.html2view.listadapter.OneLineListAdapter;
import com.staschum.html2view.listadapter.TwoLineListAdapter;
import com.staschum.html2view.objects.H2Adapter;
import com.staschum.html2view.objects.H2Click;
import com.staschum.html2view.objects.H2View;
import org.jsoup.select.Elements;

import java.util.List;

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
			public BaseListAdapter getListAdapter(Fragment fragment, List<H2View> views, H2Click click) {
				return new TwoLineListAdapter(fragment, views, click);
			}
		},
		ONE_LINE_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Fragment fragment, List<H2View> views, H2Click click) {
				return new OneLineListAdapter(fragment, views, click);
			}
		},
		IMAGE_WITH_TWO_LINES_LIST_ROW {
			@Override
			public BaseListAdapter getListAdapter(Fragment fragment, List<H2View> views, H2Click click) {
				//				imageW2LinesAdapter.imageLoader.clearMemoryCache();
//				imageW2LinesAdapter.imageLoader.clearDiscCache();
				return new ImageW2LinesAdapter(fragment, views, click);
			}
		};

		public abstract BaseListAdapter getListAdapter(Fragment fragment, List<H2View> views, H2Click click);
	}

	public static BaseListAdapter createListAdapter(Fragment fragment, Elements elements, H2Adapter h2Adapter) {
		BaseListAdapter result;
		result = Adapter.valueOf(h2Adapter.adapterName.toUpperCase()).getListAdapter(fragment, h2Adapter.getViews(), h2Adapter.click);
		result.addData(elements);
		return result;
	}
}
