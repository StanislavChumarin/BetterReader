package com.staschum.html2view.listadapter;

import android.app.Activity;
import com.staschum.html2view.objects.H2Click;
import com.staschum.html2view.objects.H2View;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 06.12.12
 * Time: 15:23
 */
public interface H2VListAdapter {
	void addData(Elements elements, List<H2View> descriptors, H2Click click);

}
