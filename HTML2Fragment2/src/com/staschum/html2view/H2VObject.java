package com.staschum.html2view;

import org.htmlcleaner.TagNode;
import org.json.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public interface H2VObject {
	void setData(TagNode tagNode, JSONArray jsonArray);
}
