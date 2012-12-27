package com.staschum.html2view.action;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 25.12.12
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
public class Download implements Click {

	private Activity activity;
	private final String url;
	private ProgressDialog mProgressDialog;

	public Download(Activity activity, String url) {
		this.activity = activity;
		this.url = url;
	}

	@Override
	public void click() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... voids) {
				int redirect = 0;
				String fromUrl = url;
				int contentLength = -1;
				while (contentLength < 0) {
					try {
						URL url = new URL(fromUrl);

						URLConnection urlConnection = url.openConnection();
						urlConnection.connect();
						contentLength = urlConnection.getContentLength();
						if (contentLength == -1) {
							redirect++;
							fromUrl = urlConnection.getHeaderField("Location");
							if (fromUrl == null) { /* I'd love to leave it as "Que Dios se apiade de nosotros" XD */
								throw new IOException("No content or redirect found for URL " + url + " with " + redirect + " redirects.");
							}
						} else {
							fromUrl = urlConnection.getURL().toString();
						}
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				}
				return fromUrl;
			}

			@Override
			protected void onPostExecute(String s) {
				String actualUrl = Uri.decode(s);
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Uri.encode(s, "%/:")));
				request.setDescription(actualUrl);
				String fileName = getFileName(actualUrl);
				request.setTitle(fileName);
// in order for this if to run, you must use the android 3.2 to compile your app
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
				File file = new File(Environment.getExternalStorageDirectory(), "/BetterReaderData/Files");
				if (!file.exists())
					file.mkdirs();
				file = new File(file, fileName);
				request.setDestinationUri(Uri.fromFile(file));
//				request.setDestinationInExternalPublicDir(android.os.Environment.getExternalStorageDirectory() + "/BetterReaderData/Files", fileName);

// get download service and enqueue file
				DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
			}
		}.execute();


	}

	private String getFileName(String url) {
		if (url == null)
			return null;
		File f = new File(url);
		String result = f.getName();
		return result;
	}
}
