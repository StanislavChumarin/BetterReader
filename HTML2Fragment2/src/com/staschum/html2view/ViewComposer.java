package com.staschum.html2view;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.app.Fragment;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.staschum.R;
import com.staschum.html2view.objects.H2Filter;
import com.staschum.html2view.objects.H2Screen;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 04.12.12
 * Time: 17:40
 */
public class ViewComposer {

	private Activity activity;
	private Map<String, H2Filter> filterMap;

	public ViewComposer(Activity activity, Map<String, H2Filter> filterMap) {
		this.activity = activity;
		this.filterMap = filterMap;
		// Get singletone instance of ImageLoader
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.empty)
				.resetViewBeforeLoading()
				.cacheInMemory()
				.cacheOnDisc()
				.build();
		File cacheDir = new File(Environment.getExternalStorageDirectory(), "/BetterReaderData/Cache");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
				.defaultDisplayImageOptions(defaultOptions)
				.threadPoolSize(5)
				.memoryCache(new WeakMemoryCache())
				.discCache(new UnlimitedDiscCache(cacheDir))
				.build();
		// Initialize ImageLoader with configuration. Do it once.
		imageLoader.init(config);
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public Map<String, Fragment> createFragments(String baseUrl, String htmlSource, String h2FilterName) {
		return createFragments(baseUrl, htmlSource, defineFilterByName(h2FilterName));
	}

	public Map<String, Fragment> createFragments(String url, String h2FilterName) throws IOException {
		return createFragments(url, defineFilterByName(h2FilterName));
	}

	/**
	 * Creates map of title-fragment by parsing HTML with specified H2Filter and converting it to Fragments.
	 * Order as defined in json description.
	 *
	 * @param baseUrl    base url that will be used during creation of download or open clicks
	 * @param htmlSource string that contains HTML
	 * @param h2Filter   filter that should be used during parsing and creation of Fragments
	 * @return Map of string(title)-fragments(actual fragment). If nothing created returns empty Map.
	 * @throws IllegalArgumentException if should_exist does not found in htmlSource.
	 */
	public Map<String, Fragment> createFragments(String baseUrl, String htmlSource, H2Filter h2Filter) {
		Document doc = Jsoup.parse(htmlSource, baseUrl);
		return createFragments(doc, h2Filter);
	}

	public Map<String, Fragment> createFragments(String url, H2Filter h2Filter) throws IOException {
		Document document = Jsoup.connect(url).get();
		if (h2Filter == null) {
			h2Filter = defineFilter(document);
		}
		return createFragments(document, h2Filter);
	}

	private Map<String, Fragment> createFragments(Document doc, H2Filter h2Filter) {
		Map<String, Fragment> result = new LinkedHashMap<String, Fragment>(h2Filter.getScreens().size());
		if (doc.select(h2Filter.shouldExistSelector).outerHtml().trim().isEmpty()) {
			throw new IllegalArgumentException("No required content on page.");
		}
		for (H2Screen screen : h2Filter.getScreens()) {
			String title = doc.select(screen.titleSelector).text();
			result.put(title, ContentFragmentFactory.getFragment(doc, screen));
		}
		return result;
	}

	private H2Filter defineFilter(Document document) {
		if (document == null) {
			return null;
		}
		for (H2Filter h2Filter : filterMap.values()) {
			if (!document.select(h2Filter.shouldExistSelector).isEmpty()) {
				return h2Filter;
			}
		}
		return null;
	}

	/**
	 * Use this method to define filter by specified name.
	 *
	 * @param h2FilterName name of filter that should be found
	 * @return specified H2Filter or null if nothing found
	 */
	private H2Filter defineFilterByName(String h2FilterName) {
		if (h2FilterName == null)
			return null;
		for (String name : filterMap.keySet()) {
			if (h2FilterName.equals(name))
				return filterMap.get(h2FilterName);
		}
		return null;
	}
}
