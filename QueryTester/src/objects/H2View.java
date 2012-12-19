package objects;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 18.12.12
 * Time: 18:32
 */
public class H2View<T> {

	public final String viewType;
	public final String viewId;
	public final String selector;
	public final T innerStructure;
	public final H2Click click;
	public final H2Pager pager;

	public H2View(String viewType, String viewId, String selector, T innerStructure, H2Click click, H2Pager pager) {
		this.viewType = viewType;
		this.viewId = viewId;
		this.selector = selector;
		this.innerStructure = innerStructure;
		this.click = click;
		this.pager = pager;
	}

	@Override
	public String toString() {
		return "\n		H2View: [" + viewType + ", " + viewId + ", " + selector + ", " + innerStructure + ", " + click  + ", " + pager + "]";
	}
}
