package com.staschum.html2view;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.staschum.html2view.action.Click;
import com.staschum.html2view.imageloader.ImageLoader;
import com.staschum.html2view.objects.H2Adapter;
import com.staschum.html2view.objects.H2Attribute;
import com.staschum.html2view.objects.H2View;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 20.12.12
 * Time: 15:10
 * This factory should be used for building views.
 */
public class ContentViewFactory {

	public static enum ViewType{
		TEXT {
			@Override
			public void setData(Activity activity, final Document document, final H2View view) {
				int id = activity.getResources().getIdentifier(view.viewId, "id", activity.getPackageName());
				TextView textView = (TextView) activity.findViewById(id);
				textView.setText(getAttributeValue(document.select(view.selector), (H2Attribute) view.innerStructure));

				if(view.click != null) {
					textView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							ContentClickFactory.createClick(document, view.click).click();
						}
					});
				}
			}
		},
		IMAGE {
			@Override
			public void setData(Activity activity, final Document document, final H2View view) {
				int id = activity.getResources().getIdentifier(view.viewId, "id", activity.getPackageName());
				String url = document.baseUri() + getAttributeValue(document.select(view.selector), (H2Attribute) view.innerStructure);
				ImageView imageView = (ImageView) activity.findViewById(id);
				ImageLoader.getInstance(activity).DisplayImage(url, imageView);

				if(view.click != null) {
					final Click click = ContentClickFactory.createClick(document, view.click);
					imageView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							click.click();
						}
					});
				}
			}
		},
		LIST {
			@Override
			public void setData(Activity activity, final Document document, final H2View view) {
				int id = activity.getResources().getIdentifier(view.viewId, "id", activity.getPackageName());
				ListView listView = (ListView) activity.findViewById(id);
				listView.setAdapter(ListAdapterFactory.createListAdapter(activity, document.select(view.selector), (H2Adapter)view.innerStructure));

				if(view.click != null) {
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
							ContentClickFactory.createClick(document, view.click).click();
						}
					});
				}
			}
		};

		public abstract void setData(Activity activity, Document document, H2View view);

	}
	public static void createView(Activity context, Document document, H2View view) {
		ViewType.valueOf(view.viewType.toUpperCase()).setData(context, document, view);
	}

	public static CharSequence getAttributeValue(Elements elements, H2Attribute attribute) {
		if ("text".equals(attribute.name)) {
			return elements.text();
		} else if ("html".equals(attribute.name)) {
			return Html.fromHtml(elements.html().replace("<a", "<b").replace("</a", "</b"));
		} else if ("outer_html".equals(attribute.name)) {
			return Html.fromHtml(elements.outerHtml().replace("<a", "<b").replace("</a", "</b"));
		} else {
			return elements.attr(attribute.name);
		}
	}

}
