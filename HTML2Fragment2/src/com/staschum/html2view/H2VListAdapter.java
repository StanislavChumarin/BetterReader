package com.staschum.html2view;

import org.htmlcleaner.TagNode;
import org.json.JSONArray;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public interface H2VListAdapter {
	void setData(List<TagNode> tagNode, JSONArray jsonArray);

}
