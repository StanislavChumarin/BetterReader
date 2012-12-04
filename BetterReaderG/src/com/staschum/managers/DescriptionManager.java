package com.staschum.managers;

import android.content.Context;
import com.staschum.App;
import com.staschum.DataViewDescription;
import com.staschum.R;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class DescriptionManager {

	private Context context;

	public DescriptionManager(Context context) {
		this.context = context;
	}

	public DataViewDescription getDescription(String url, TagNode tagNode) throws JSONException, XPatherException {
		JSONObject filters = new JSONObject(readRawTextFile(context, R.raw.exua));
		Iterator<String> iterator = filters.keys();
		while(iterator.hasNext()) {
			 String key = iterator.next();
			DataViewDescription description = new DataViewDescription(filters.getJSONObject(key));
			if (url.startsWith(description.getTargetUrl()) && tagNode.evaluateXPath(description.getRequiredXPath()).length != 0) {
				return description;
			}
		}
		return null;
	}

	public static String readRawTextFile(Context ctx, int resId)
	{
		InputStream inputStream = ctx.getResources().openRawResource(resId);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();

		try {
			while (( line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			return null;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		return text.toString().replace("\r", "").replace("\n", " ");
	}
}
