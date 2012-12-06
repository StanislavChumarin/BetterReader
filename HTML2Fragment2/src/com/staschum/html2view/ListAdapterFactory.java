package com.staschum.html2view;

import android.content.Context;
import android.widget.ListAdapter;
import com.staschum.html2view.layoutcontainer.BaseListAdapter;
import com.staschum.html2view.layoutcontainer.OneLineListAdapter;import com.staschum.html2view.layoutcontainer.TwoLineListAdapter;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;
import org.json.JSONObject;

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
		};

		public abstract BaseListAdapter getListAdapter(Context context);
	}

	public static BaseListAdapter createListAdapter(Context context, List<TagNode> tagNodes, JSONArray data) {
		BaseListAdapter result;
		JSONObject item = Utils.getDataFromJsonArray(data, 0);
		result = Adapter.valueOf(Utils.getString(item, "layout_name").toUpperCase()).getListAdapter(context);
		result.setData(tagNodes, Utils.getJSONArray(item, "data"));

		return result;
	}
}
