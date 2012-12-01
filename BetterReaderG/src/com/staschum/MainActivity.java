package com.staschum;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

//		mHandler = new Handler();
//		checkUpdate.start();

//	private String html = "";
//	private Handler mHandler;
//
//	private Thread checkUpdate = new Thread() {
//		public void run() {
//			try {
//				URL updateURL = new URL("http://www.ex.ua");
//				URLConnection conn = updateURL.openConnection();
//				InputStream is = conn.getInputStream();
//				BufferedInputStream bis = new BufferedInputStream(is);
//				ByteArrayBuffer baf = new ByteArrayBuffer(50);
//
//				int current = 0;
//				while ((current = bis.read()) != -1) {
//					baf.append((byte) current);
//				}
//
//				/* Convert the Bytes read to a String. */
//				html = new String(baf.toByteArray());
//				mHandler.post(showUpdate);
//			} catch (Exception e) {
//				Log.e(MainActivity.class.getSimpleName(), "", e);
//			}
//		}
//	};
//
//	private Runnable showUpdate = new Runnable() {
//		public void run() {
//			TextView tv = (TextView) MainActivity.this.findViewById(R.id.text_v);
//			tv.setText(html);
//		}
//	};

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}
}
