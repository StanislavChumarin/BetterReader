package objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:33
 */
public class H2Adapter {

	public final String type;
	public final String adapterName;

	private List<H2View> views;

	public H2Adapter(String type, String adapterName, List<H2View> views) {
		this.type = type;
		this.adapterName = adapterName;
		this.views = views;
	}

	public List<H2View> getViews() {
		List<H2View> result = new ArrayList<H2View>();
		Collections.copy(result, views);
		return result;
	}

	@Override
	public String toString() {
		return "\n	H2Adapter: [" + type + ", " + adapterName + ", " +views + "]";
	}
}
