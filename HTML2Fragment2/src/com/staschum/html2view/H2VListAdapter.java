package com.staschum.html2view;

import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONArray;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public interface H2VListAdapter {
	void setData(Elements elements, List<FragmentDescriptor> jsonArray);

}
