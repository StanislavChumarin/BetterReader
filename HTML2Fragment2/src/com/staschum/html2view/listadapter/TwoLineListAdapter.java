package com.staschum.html2view.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.staschum.R;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	public void addData(Elements elements, List<FragmentDescriptor> descriptors) {
		if(descriptors.size() < 2) {
			return;
		}
		String mainTextSelector = descriptors.get(0).getSelector();
		String secondaryTextSelector = descriptors.get(1).getSelector();
		for (Element parentElement : elements) {
			String mainText = parentElement.select(mainTextSelector).text();
			if (mainText.isEmpty())
				continue;
			Map<String, String> entry = new HashMap<String, String>();
			entry.put(mainTextKey, mainText);
			String secondaryText = parentElement.select(secondaryTextSelector).text();

			entry.put(secondaryTextKey, secondaryText);
			content.add(entry);
		}
		count = content.size();
		notifyDataSetChanged();
	}


}
