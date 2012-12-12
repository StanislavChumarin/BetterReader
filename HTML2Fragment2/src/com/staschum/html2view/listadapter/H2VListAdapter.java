package com.staschum.html2view.listadapter;

import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONArray;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 15:23
 */
public interface H2VListAdapter {
	void addData(Elements elements, List<FragmentDescriptor> descriptors);

}
