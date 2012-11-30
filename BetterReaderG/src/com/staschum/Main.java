package com.staschum;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.slidingmenu.example.BaseActivity;
import com.slidingmenu.example.R;
import com.slidingmenu.lib.SlidingMenu;

public class Main extends BaseActivity {
		private Fragment mContent;

		public FragmentChangeActivity() {
			super(R.string.changing_fragments);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// set the Above View
			if (savedInstanceState != null)
				mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
			if (mContent == null)
				mContent = new ColorFragment(R.color.red);

			// set the Above View
			setContentView(R.layout.content_frame);
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, mContent)
					.commit();

			// set the Behind View
			setBehindContentView(R.layout.menu_frame);
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.menu_frame, new ColorMenuFragment())
					.commit();

			// customize the SlidingMenu
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
		}

		public void switchContent(Fragment fragment) {
			mContent = fragment;
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.commit();
			getSlidingMenu().showContent();
		}

	}
//		mHandler = new Handler();
//		checkUpdate.start();
	}

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
//				Log.e(Main.class.getSimpleName(), "", e);
//			}
//		}
//	};
//
//	private Runnable showUpdate = new Runnable() {
//		public void run() {
//			TextView tv = (TextView) Main.this.findViewById(R.id.text_v);
//			tv.setText(html);
//		}
//	};
}
