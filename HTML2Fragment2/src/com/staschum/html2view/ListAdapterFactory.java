package com.staschum.html2view;

import android.content.Context;
import com.staschum.html2view.layoutcontainer.BaseListAdapter;
import com.staschum.html2view.layoutcontainer.ImageW2LinesAdapter;
import com.staschum.html2view.layoutcontainer.OneLineListAdapter;
import com.staschum.html2view.layoutcontainer.TwoLineListAdapter;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONArray;
import org.json.JSONObject;
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

	public static BaseListAdapter createListAdapter(Context context, Elements elements, List<FragmentDescriptor> data) {
		BaseListAdapter result;
		if(data.isEmpty()) {
			return null;
		}
		FragmentDescriptor item = data.get(0);
		result = Adapter.valueOf(item.getLayoutName().toUpperCase()).getListAdapter(context);
		result.setData(elements, item.getData());

		return result;
	}
}
