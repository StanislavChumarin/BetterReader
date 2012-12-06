package com.staschum.html2view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import org.apache.http.util.ByteArrayBuffer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 02.12.12
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class Utils {


	public static int STATUS_OK = 0;
	public static int STATUS_ERROR = 1;
	public static final String RESULT_KEY = "result";

	public static void getHtmlByUrlAsync(String url, ResultReceiver resultReceiver) {
		new AsyncHtmlGetter(url, resultReceiver).execute();
	}

	public static String removeTags(String innerHtml) {
		String result = "";
		int deepnesInTag = 0;
		boolean isInAttributValue = false;
		for (int i = 0; i < innerHtml.length(); i++) {
			char c = innerHtml.charAt(i);
			if (c == '<') {
				deepnesInTag++;
				continue;
			}
			if (deepnesInTag != 0 && c == '"') {
				i = innerHtml.indexOf('"', i);
				continue;
			}
			if (c == '>') {
				deepnesInTag--;
				continue;
			}
			if (deepnesInTag == 0)
				result += c;
		}
		return result;
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
		try {
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
		try {
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
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


	public static JSONObject getDataFromJsonArray(JSONArray jsonArray, int index) {
		try {
			return jsonArray.getJSONObject(index);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getString(JSONObject data, String key) {
		try {
			return data.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> getNodesByXPath(TagNode tagNode, String xPath, Class<T> returnClass) {
		List<T> result = new ArrayList<T>();
		try {
			Object[] objects = tagNode.evaluateXPath(xPath);
			for (Object object : objects) {
				result.add((T) object);
			}
			return result;
		} catch (XPatherException e) {
			e.printStackTrace();
			return result;
		}
	}

	public static String readTextFromTag(TagNode tagNode) {
		String mainText = tagNode.getText().toString();
		if(mainText == null || mainText.isEmpty()) {
			return "";
		}
		return mainText;
	}
}
