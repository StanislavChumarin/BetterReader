package com.staschum.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.staschum.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 27.12.12
 * Time: 18:39
 * Shows list of downloaded files. Maybe managing downloads.
 */
public class DownloadedItemsFragment extends SherlockFragment {

	File fileDir = new File(Environment.getExternalStorageDirectory(), "/BetterReaderData/Files");
	ArrayAdapter<String> filesListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.downloads_frame, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		List<String> downloads = getDownloadsList();

		ListView listView = (ListView) getActivity().findViewById(R.id.download_items_list);

		filesListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.one_line_list_row, R.id.text);

		listView.setAdapter(filesListAdapter);

		for (String fileName : downloads) {
			filesListAdapter.add(fileName);
		}

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

				File file = new File(fileDir, filesListAdapter.getItem(i));
				Uri uri = Uri.fromFile(file);
				String fileMimeType = getFileMimeType(uri);
				if (fileMimeType == null) {
					Toast.makeText(getActivity(), "No viewer for this file: " + file.getName(), Toast.LENGTH_LONG).show();
					fileMimeType = "*/*";
				}
//				else {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(uri, fileMimeType);
				startActivity(intent);
//				}

			}
		});

	}

	private String getFileMimeType(Uri fileUri) {
		String fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
		return mimeType;
	}

	private List<String> getDownloadsList() {
		List<String> result = new ArrayList<String>();

		if (!fileDir.exists()) {
			return result;
		}

		String[] list = fileDir.list();
		Collections.addAll(result, list);
		return result;
	}

	public void refreshList() {
		List<String> freshList = getDownloadsList();
		if (freshList.size() != filesListAdapter.getCount()) {
			filesListAdapter.clear();
			for (String fileName : freshList) {
				filesListAdapter.add(fileName);
			}
		}
	}
}
