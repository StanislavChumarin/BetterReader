package com.staschum.html2view.listadapter;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.staschum.R;
import com.staschum.html2view.ContentClickFactory;
import com.staschum.html2view.Utils;
import com.staschum.html2view.objects.H2Attribute;
import com.staschum.html2view.objects.H2Click;
import com.staschum.html2view.objects.H2View;
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
 * This adapter is used to show rows with two lines of text: Main text(bold, big) and secondary text(thin, small)
 */
public class TwoLineListAdapter extends BaseListAdapter {

	int count;
	LayoutInflater inflater;
	String mainTextKey = "main";
	String secondaryTextKey = "secondary";

	private List<Map<String, CharSequence>> content = new ArrayList<Map<String, CharSequence>>();

	public TwoLineListAdapter(Fragment fragment) {
		super(fragment);
		inflater = fragment.getLayoutInflater(null);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Map<String, CharSequence> getItem(int i) {
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

		Map<String, CharSequence> item = getItem(position);
		mainText.setText(item.get(mainTextKey));
		secondaryText.setText(item.get(secondaryTextKey));

		return view;
	}

	@Override
	public void addData(Elements elements, List<H2View> h2Views, H2Click click) {
		if (h2Views.size() < 2) {
			return;
		}

		for (Element parentElement : elements) {

			CharSequence mainText = "";
			CharSequence secondaryText = "";

			for (H2View view : h2Views) {
				if ("row_main_text".equals(view.viewId)) {
					mainText = Utils.getAttributeValue(parentElement.select(view.selector), (H2Attribute) view.innerStructure);
					if (mainText.toString().trim().isEmpty())
						break;
				} else if ("row_small_text".equals(view.viewId)) {
					secondaryText = Utils.getAttributeValue(parentElement.select(view.selector), (H2Attribute) view.innerStructure);
				}

			}
			if (mainText.toString().trim().isEmpty())
				continue;

			if (click != null) {
				itemClicks.add(ContentClickFactory.createClick(fragment, parentElement, click));
			}

			Map<String, CharSequence> entry = new HashMap<String, CharSequence>();
			entry.put(mainTextKey, mainText);

			entry.put(secondaryTextKey, secondaryText);
			content.add(entry);
		}
		count = content.size();
		notifyDataSetChanged();
	}


}
