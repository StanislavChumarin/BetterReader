package com.staschum.html2view.action;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.*;
import android.widget.Toast;
import com.codeslap.groundy.Groundy;
import com.staschum.R;

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
//		Bundle extras = new Bundle();
//		extras.putString(DownloadResolver.PARAM_URL, url);
//		Groundy.execute(activity, DownloadResolver.class, new DownloadReceiver(), extras);
//
//		mProgressDialog = new ProgressDialog(activity);
//		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		mProgressDialog.setCancelable(false);
//		mProgressDialog.show();
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
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(s));
				request.setDescription("Some descrition");
				request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				}
				request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name-of-the-file.ext");

// get download service and enqueue file
				DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
				manager.enqueue(request);
			}
		}.execute();


	}

	private class DownloadReceiver extends ResultReceiver {
		public DownloadReceiver() {
			super(new Handler());
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			super.onReceiveResult(resultCode, resultData);
			switch (resultCode) {
				case Groundy.STATUS_PROGRESS:
					mProgressDialog.setProgress(resultData.getInt(Groundy.KEY_PROGRESS));
					break;
				case Groundy.STATUS_FINISHED:
					Toast.makeText(activity, activity.getString(R.string.file_downloaded), Toast.LENGTH_LONG);
					mProgressDialog.dismiss();
					break;
				case Groundy.STATUS_ERROR:
					Toast.makeText(activity, resultData.getString(Groundy.KEY_ERROR), Toast.LENGTH_LONG).show();
					mProgressDialog.dismiss();
					break;
			}
		}
	}
}
