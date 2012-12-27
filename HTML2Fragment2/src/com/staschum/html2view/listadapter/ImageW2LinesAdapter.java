package com.staschum.html2view.listadapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.staschum.R;
import com.staschum.html2view.ContentClickFactory;
import com.staschum.html2view.Utils;
import com.staschum.html2view.objects.H2Attribute;
import com.staschum.html2view.objects.H2Click;
import com.staschum.html2view.objects.H2View;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 07.12.12
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class ImageW2LinesAdapter extends BaseListAdapter {

	private final List<H2View> h2Views;
	private final H2Click click;
	private int count;
	LayoutInflater inflater;

	private List<RowContent> content = new ArrayList<RowContent>();

	public ImageW2LinesAdapter(Fragment fragment, List<H2View> views, H2Click click) {
		super(fragment);
		this.h2Views = views;
		this.click = click;
		inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public RowContent getItem(int i) {
		return content.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.image_w_two_lines_list_row, null);

		TextView mainText = (TextView) vi.findViewById(R.id.row_main_text);
		TextView secondaryText = (TextView) vi.findViewById(R.id.row_small_text);

		ImageView image = (ImageView) vi.findViewById(R.id.row_image);

		RowContent rowContent = getItem(position);

		mainText.setText(rowContent.getMainText());
		secondaryText.setText(rowContent.getSecondaryText());

		ImageLoader.getInstance().displayImage(rowContent.getImageUrl(), image);
		return vi;
	}

	@Override
	public void addData(Elements elements) {

		for (Element parentElement : elements) {

			CharSequence mainText = "";

			String imageUrl = "";
			CharSequence secondaryText = "";

			for (H2View view : h2Views) {
				if ("row_main_text".equals(view.viewId)) {
					mainText = Utils.getAttributeValue(parentElement.select(view.selector), (H2Attribute) view.innerStructure);
					if (mainText.toString().trim().isEmpty())
						break;
				} else if ("row_small_text".equals(view.viewId)) {
					secondaryText = Utils.getAttributeValue(parentElement.select(view.selector), (H2Attribute) view.innerStructure);
				} else if ("row_image".equals(view.viewId)) {
					imageUrl = Utils.getAttributeValue(parentElement.select(view.selector), (H2Attribute) view.innerStructure).toString();
				}

			}
			if (mainText.toString().trim().isEmpty())
				continue;
			if (click != null) {
				itemClicks.add(ContentClickFactory.createClick(fragment, parentElement, click));
			}
			content.add(new RowContent(imageUrl, mainText, secondaryText));
		}
		count = content.size();
		notifyDataSetChanged();
	}

	private static class RowContent {
		private final String imageUrl;
		private final CharSequence mainText;
		private final CharSequence secondaryText;

		public RowContent(String imageUrl, CharSequence mainText, CharSequence secondaryText) {
			this.imageUrl = imageUrl;
			this.mainText = mainText;
			this.secondaryText = secondaryText;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public CharSequence getMainText() {
			return mainText;
		}

		public CharSequence getSecondaryText() {
			return secondaryText;
		}
	}
}
