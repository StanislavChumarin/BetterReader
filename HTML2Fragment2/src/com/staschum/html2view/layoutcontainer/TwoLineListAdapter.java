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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class TwoLineListAdapter extends BaseListAdapter {

	int count;
	LayoutInflater inflater;
	String mainTextKey = "main";
	String secondaryTextKey = "secondary";

	private List<Map<String, String>> content = new ArrayList<Map<String, String>>();

	public TwoLineListAdapter(Context context) {
		super(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Map<String, String> getItem(int i) {
		return content.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		TextView mainText;
		TextView secondaryText;

		if (convertView == null) {
			view = inflater.inflate(R.layout.two_lines_list_row, parent, false);
		} else {
			view = convertView;
		}

		mainText = (TextView) view.findViewById(R.id.row_main_text);
		secondaryText = (TextView) view.findViewById(R.id.row_small_text);

		Map<String, String> item = getItem(position);
		mainText.setText(item.get(mainTextKey));
		secondaryText.setText(item.get(secondaryTextKey));

		return view;
	}

	@Override
	public void setData(List<TagNode> tagNodes, JSONArray jsonArray) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		String mainTextXPath = Utils.getString(Utils.getDataFromJsonArray(jsonArray, 0), "xpath");
		String secondaryTextXPath = Utils.getString(Utils.getDataFromJsonArray(jsonArray, 1), "xpath");
		for (TagNode tagNode : tagNodes) {
			List<TagNode> nodesByXPath = Utils.getNodesByXPath(tagNode, mainTextXPath, TagNode.class);
			if(nodesByXPath.isEmpty())
				continue;
			String mainText = Utils.readTextFromTag(nodesByXPath.get(0));
			if (mainText.isEmpty())
				continue;
			Map<String, String> entry = new HashMap<String, String>();
			entry.put(mainTextKey, mainText);
			List<TagNode> nodesByXPath1 = Utils.getNodesByXPath(tagNode, secondaryTextXPath, TagNode.class);
			String secondaryText = "";
			if (!nodesByXPath1.isEmpty()) {
				secondaryText = Utils.readTextFromTag(nodesByXPath1.get(0));
			}

			entry.put(secondaryTextKey, secondaryText);
			result.add(entry);
		}
		content = result;
		count = content.size();
	}


}
