package com.staschum.html2view.layoutcontainer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.staschum.R;
import com.staschum.html2view.ImageLoader;
import com.staschum.html2view.objects.FragmentDescriptor;
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

	private int count;
	LayoutInflater inflater;
	public ImageLoader imageLoader;

	private List<RowContent> content = new ArrayList<RowContent>();

	public ImageW2LinesAdapter(Context context) {
		super(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(context);
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

		mainText.setText(rowContent.getMaintext());
		secondaryText.setText(rowContent.getSecondaryText());

		imageLoader.DisplayImage(rowContent.getImageUrl(), image);
		return vi;
	}

	@Override
	public void setData(Elements elements, List<FragmentDescriptor> descriptors) {
		if (descriptors.size() < 3)
			return;

		String imageSelector = descriptors.get(0).getSelector();
		String mainTextSelector = descriptors.get(1).getSelector();
		String secondaryTextSelector = descriptors.get(2).getSelector();

		for (Element parentElement : elements) {
			String mainText = parentElement.select(mainTextSelector).text();
			if (mainText.isEmpty())
				continue;
			String imageUrl = parentElement.select(imageSelector).attr("src");
			String secondaryText = parentElement.select(secondaryTextSelector).text();

			content.add(new RowContent(imageUrl, mainText, secondaryText));
		}
		count = content.size();
		notifyDataSetChanged();
	}

	private static class RowContent {
		private final String imageUrl;
		private final String maintext;
		private final String secondaryText;

		public RowContent(String imageUrl, String mainText, String secondaryText) {
			this.imageUrl = imageUrl;
			this.maintext = mainText;
			this.secondaryText = secondaryText;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public String getMaintext() {
			return maintext;
		}

		public String getSecondaryText() {
			return secondaryText;
		}
	}
}
