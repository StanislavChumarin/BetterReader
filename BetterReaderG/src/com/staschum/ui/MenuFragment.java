package com.staschum.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.staschum.R;
import com.staschum.html2view.ContentViewer;
import com.staschum.html2view.managers.DescriptionManager;
import com.staschum.html2view.SupportedSite;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 17:16
 */
public class MenuFragment extends SherlockFragment {

	private List<String> menuNames = new ArrayList<String>();
	private ContentViewer contentViewer;
	private List<SupportedSite> supportedSites;

	public MenuFragment() {
		supportedSites = new ArrayList<SupportedSite>();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		contentViewer = (ContentViewer) getSherlockActivity();
		try {
			supportedSites.add(new SupportedSite("EX.UA", "http://www.ex.ua", new JSONObject(DescriptionManager.readRawTextFile(getActivity(), R.raw.exua))));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.menu_list_row, R.id.text);
		for (String name : getNames(supportedSites)) {
			arrayAdapter.add(name);
		}

		ListView listView = (ListView) getActivity().findViewById(R.id.menu_content);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				contentViewer.viewContent(supportedSites.get(i).getUrl(), "/");

			}
		});

	}

	private List<String> getNames(List<SupportedSite> supportedSites) {
		List<String> result = new ArrayList<String>();
		for (SupportedSite site : supportedSites) {
			result.add(site.getName());
		}
		return result;
	}
}
