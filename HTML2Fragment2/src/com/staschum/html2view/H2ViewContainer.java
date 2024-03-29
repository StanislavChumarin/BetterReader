package com.staschum.html2view;

import android.app.Activity;
import com.staschum.html2view.objects.H2View;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 13:58
 */
public interface H2ViewContainer {
	void setData(Document doc, List<H2View> views);
}
