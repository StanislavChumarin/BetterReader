package com.staschum.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.staschum.R;
import com.staschum.Utils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 18:25
 */
public class MainFragment extends SherlockFragment {

	private static String URL = "url";

	public static Fragment createFragment(String url) {
		Fragment fragment = new MainFragment();
		Bundle args = new Bundle();
		args.putString(URL, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String url = getArguments().getString(URL, "http://www.google.com/");
		getActivity().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
		getActivity().findViewById(R.id.text_v).setVisibility(View.GONE);
		Utils.getHtmlByUrlAsync(url, new ResultReceiver(new Handler()) {
			@Override
			protected void onReceiveResult(int resultCode, final Bundle resultData) {
				if (resultCode != Utils.STATUS_OK)
					return;

				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... voids) {
						String htmlSource = resultData.getString(Utils.RESULT_KEY, "");

						HtmlCleaner htmlCleaner = new HtmlCleaner();

						TagNode tagNode = htmlCleaner.clean(htmlSource);
						return htmlCleaner.getInnerHtml(tagNode.findElementByName("body", true)).trim();
					}

					@Override
					protected void onPostExecute(String s) {
						TextView tv = (TextView) getActivity().findViewById(R.id.text_v);
						tv.setText(s);
						getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
						tv.setVisibility(View.VISIBLE);
					}
				}.execute();
			}
		});
	}

}
