package com.staschum.html2view.managers;

import android.content.Context;
import com.staschum.html2view.DataViewDescription;
import com.staschum.html2view.Utils;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class DescriptionManager {

	private Context context;
	private JSONObject cachedJson;

	public DescriptionManager(Context context) {
		this.context = context;
	}

	public FragmentDescriptor getDescription(String url, TagNode tagNode, int fileId, boolean renewJson) {
		if (cachedJson == null || renewJson) {
			try {
				cachedJson = new JSONObject(readRawTextFile(context, fileId));
			} catch (JSONException e) {
				throw new IllegalArgumentException(e);
			}
		}
		Iterator<String> iterator = cachedJson.keys();
		Set<String> keys = new TreeSet<String>();
		while(iterator.hasNext()) {
			String key = iterator.next();
			keys.add(key);
		}
		for (String key : keys) {
			FragmentDescriptor description = new FragmentDescriptor(Utils.getJSONObject(cachedJson, key));
			if (url.startsWith(description.getTargetUrl()) && Utils.getNodesByXPath(tagNode, description.getRequiredXPath(), Object.class).size() != 0) {
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
