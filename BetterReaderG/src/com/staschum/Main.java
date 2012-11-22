package com.staschum;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mHandler = new Handler();
		checkUpdate.start();
	}

	private String html = "";
	private Handler mHandler;

	private Thread checkUpdate = new Thread() {
		public void run() {
			try {
				URL updateURL = new URL("http://www.ex.ua");
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
				mHandler.post(showUpdate);
			} catch (Exception e) {
				Log.e(Main.class.getSimpleName(), "", e);
			}
		}
	};

	private Runnable showUpdate = new Runnable() {
		public void run() {
			TextView tv = (TextView) Main.this.findViewById(R.id.text_v);
			tv.setText(html);
		}
	};
}
