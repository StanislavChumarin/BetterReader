package com.staschum.html2view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Html;
import android.util.Log;
import com.staschum.html2view.objects.H2Attribute;
import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 02.12.12
 * Time: 16:37
 * Utils contains some methods that are used everywere.
 */
public abstract class Utils {

	public static int STATUS_OK = 0;
	public static int STATUS_ERROR = 1;
	public static final String RESULT_KEY = "result";

	public static void getHtmlByUrlAsync(String url, ResultReceiver resultReceiver) {
		new AsyncHtmlGetter(url, resultReceiver).execute();
	}

	public static List<String> getValues(Elements items, String attr) {
		List<String> result = new ArrayList<String>();
		for (Element item : items) {
			result.add(item.attr(attr));
		}
		return result;
	}

	public static CharSequence getAttributeValue(Elements elements, String attribute) {
		CharSequence result;
		String regexp = null;
		if (attribute.contains("(") && attribute.contains(")")) {
			regexp = attribute.substring(attribute.indexOf('(') + 1, attribute.lastIndexOf(')'));
			attribute = attribute.substring(0, attribute.indexOf('('));
		}
		if ("text".equals(attribute)) {
			result = elements.text();
		} else if ("html".equals(attribute)) {
			result = Html.fromHtml(elements.html().replace("<a", "<b").replace("</a", "</b"));
		} else if ("outer_html".equals(attribute)) {
			result = Html.fromHtml(elements.outerHtml().replace("<a", "<b").replace("</a", "</b"));
		} else {
			result = elements.attr(attribute);
		}

		if (regexp != null) {
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				result = matcher.group();
			}
		}
		return result;
	}

	public static CharSequence getAttributeValue(Elements elements, H2Attribute attribute) {
		return getAttributeValue(elements, attribute.name);
	}

	//convert it to ExecutorService
	private static class AsyncHtmlGetter extends AsyncTask<Void, Void, Void> {

		private String url;
		private String html;
		private ResultReceiver resultReceiver;

		public AsyncHtmlGetter(String url, ResultReceiver resultReceiver) {
			this.url = url;
			if (resultReceiver == null)
				throw new IllegalArgumentException("ResultReceiver cannot be null");
			this.resultReceiver = resultReceiver;
		}

		@Override
		protected void onPreExecute() {
			if (url == null || url.trim().length() == 0) {
				resultReceiver.send(STATUS_ERROR, null);
				this.cancel(true);
			}
		}

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				URL updateURL = new URL(url);
				URLConnection conn = updateURL.openConnection();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer baf = new ByteArrayBuffer(50);

				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}

				/* Convert the Bytes read to a String. */
				html = new String(baf.toByteArray());

			} catch (Exception e) {
				Log.e(Utils.class.getSimpleName(), "", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			Bundle bundle = new Bundle();
			bundle.putString(RESULT_KEY, html);
			resultReceiver.send(STATUS_OK, bundle);
		}

	}

}
