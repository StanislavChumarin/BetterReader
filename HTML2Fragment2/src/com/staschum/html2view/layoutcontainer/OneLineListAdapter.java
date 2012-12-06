package com.staschum.html2view.layoutcontainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.staschum.R;
import com.staschum.html2view.Utils;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:44
 */
public class OneLineListAdapter extends BaseListAdapter {

	int count;
	LayoutInflater inflater;

	private List<String> content;

	public OneLineListAdapter(Context context) {
		super(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public String getItem(int i) {
		return content.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		TextView text;

		if (convertView == null) {
			view = inflater.inflate(R.layout.one_line_list_row, parent, false);
		} else {
			view = convertView;
		}

		text = (TextView) view.findViewById(R.id.text);

		String item = getItem(position);
		text.setText(item);

		return view;
	}

	@Override
	public void setData(List<TagNode> tagNodes, JSONArray jsonArray) {
		List<String> result = new ArrayList<String>();
		String textXPath = Utils.getString(Utils.getDataFromJsonArray(jsonArray, 0), "xpath");
		List<TagNode> candidates = new ArrayList<TagNode>();
		for (TagNode parentNode : tagNodes) {
			for (TagNode tagNode : Utils.getNodesByXPath(parentNode, textXPath, TagNode.class)) {
				String text = Utils.readTextFromTag(tagNode);
				if (text.isEmpty())
					continue;
				result.add(text);
			}
		}
		content = result;
		count = content.size();
	}
}
