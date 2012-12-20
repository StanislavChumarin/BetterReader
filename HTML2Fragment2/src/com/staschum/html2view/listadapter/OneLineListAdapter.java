package com.staschum.html2view.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.staschum.R;
import com.staschum.html2view.ContentViewFactory;
import com.staschum.html2view.objects.H2Attribute;
import com.staschum.html2view.objects.H2View;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

	private List<CharSequence> content;

	public OneLineListAdapter(Context context) {
		super(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public CharSequence getItem(int i) {
		return content.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
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

		CharSequence item = getItem(position);
		text.setText(item);

		return view;
	}

	@Override
	public void addData(Elements elements, List<H2View> views) {
		List<CharSequence> result = new ArrayList<CharSequence>();
		if(views.isEmpty()) {
			return;
		}
		for (Element element : elements) {
			for (H2View view : views) {
				CharSequence text = ContentViewFactory.getAttributeValue(element.select(view.selector), (H2Attribute) view.innerStructure);
				if (text.toString().trim().isEmpty()) {
					break;
				}
				result.add(text);
			}
		}
		content = result;
		count = content.size();
		notifyDataSetChanged();
	}
}
