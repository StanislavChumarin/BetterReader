package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:31
 * This is object representation of json description
 */
public class H2Screen {

	public final String screenName;
	public final String titleSelector;
	private List<H2View> views;

	public H2Screen(String screenName, String titleSelector, List<H2View> views) {
		this.screenName = screenName;
		this.titleSelector = titleSelector;
		this.views = views;
	}

	public List<H2View> getViews() {
		List<H2View> result = new ArrayList<H2View>();
		Collections.copy(result, views);
		return result;
	}

	@Override
	public String toString() {
		return "\n	H2Screen: [" + screenName + ", " + titleSelector + ", " + views + "]";
	}
}
