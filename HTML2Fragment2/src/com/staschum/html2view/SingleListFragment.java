package com.staschum.html2view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.staschum.R;
import com.staschum.html2view.layoutcontainer.ContentFragment;
import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stanislavchumarin
 * Date: 01.12.12
 * Time: 18:25
 */
public class SingleListFragment extends ContentFragment {


	//TODO refactor this to be useful for different sites
	private String basicUrl = "";

	private String title;

	public static ContentFragment createFragment(String url) {
		ContentFragment fragment = new SingleListFragment();
		Bundle args = new Bundle();
		args.putString(URL_KEY, url);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getActivity();

		basicUrl = getArguments().getString(URL_KEY);

		activity.setTitle(document.select("title").text());

		if(fragmentData.isEmpty())
			return;

		FragmentDescriptor data = fragmentData.get(0);
		ListView listView = (ListView) activity.findViewById(R.id.list_content);
		Elements dataForAdapter = document.select(data.getSelector());
		ListAdapter listAdapter = ListAdapterFactory.createListAdapter(activity, dataForAdapter, data.getData());
		listView.setAdapter(listAdapter);

		FragmentDescriptor action = data.getAction();
		final List<String> actions = Utils.getValues(document.select(action.getItemSelector()), action.getAttr());

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				((ContentViewer) activity).viewContent(basicUrl, actions.get(i));
			}
		});
	}


}
