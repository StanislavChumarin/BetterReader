package com.staschum.html2view;

import com.staschum.html2view.objects.FragmentDescriptor;
import org.json.JSONArray;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public interface H2VObject {
	void setData(Document document, List<FragmentDescriptor> descriptors);
}
