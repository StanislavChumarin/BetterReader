package com.staschum.html2view.managers;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
