package objects;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 19.12.12
 * Time: 18:43
 * This is object to represent paging
 */
public class H2Pager {
	public final String selector;
	public final String attribute;

	public H2Pager(String selector, String attribute) {
		this.selector = selector;
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return "\n	H2Pager: [" + selector + ", " + attribute + "]";
	}
}
