package com.staschum.html2view;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.staschum.html2view.objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: schumarin
 * Date: 19.12.12
 * Time: 14:52
 * This class is used to transform json to object transformations.
 */
public class JSON2Objects {

	private static final JsonFactory jsonFactory = new JsonFactory();

	/**
	 * Creates Map of filter names and actual filers.
	 *
	 * @param jsonString string to be converted
	 * @return  Map of filter name, filter
	 */
	public static Map<String, H2Filter> json2Fiters(String jsonString) throws IOException {

		Map<String, H2Filter> result = new TreeMap<String, H2Filter>();

		JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
				jsonParser.nextToken();
			}

			String fieldName = jsonParser.getCurrentName();
			result.put(fieldName, json2Filter(jsonParser));
		}
		return result;
	}

	/**
	 * Converts json string to H2Filter object with all underlying objects
	 *
	 * @param jsonParser parser that contains object filter.
	 * @return H2Filter object to use further
	 */
	public static H2Filter json2Filter(JsonParser jsonParser) throws IOException {
		String url = null;
		String shouldExistSelector = null;
		List<H2Screen> screens = new ArrayList<H2Screen>();

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("url".equals(jsonParser.getCurrentName())) {
						url = getStringValue(jsonParser);
					} else if ("should_exist".equals(jsonParser.getCurrentName())) {
						shouldExistSelector = getStringValue(jsonParser);
					} else if ("screens".equals(jsonParser.getCurrentName())) {
						while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
							screens.add(json2Screen(jsonParser));
						}
					}
					break;
			}
		}
		return new H2Filter(url, shouldExistSelector, screens);
	}

	public static H2Screen json2Screen(JsonParser jsonParser) throws IOException {
		String screenName = null;
		String titleSelector = null;
		List<H2View> views = new ArrayList<H2View>();

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("layout_name".equals(jsonParser.getCurrentName())) {
						screenName = getStringValue(jsonParser);
					} else if ("title_selector".equals(jsonParser.getCurrentName())) {
						titleSelector = getStringValue(jsonParser);
					} else if ("data".equals(jsonParser.getCurrentName())) {
						while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
							views.add(json2View(jsonParser));
						}
					}
					break;
			}
		}
		return new H2Screen(screenName, titleSelector, views);
	}

	public static H2View json2View(JsonParser jsonParser) throws IOException {
		String viewType = null;
		String viewId = null;
		String selector = null;
		H2Click click = null;
		H2Pager pager = null;
		Object innerData = null;

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("type".equals(jsonParser.getCurrentName())) {
						viewType = getStringValue(jsonParser);
					} else if ("id".equals(jsonParser.getCurrentName())) {
						viewId = getStringValue(jsonParser);
					} else if ("selector".equals(jsonParser.getCurrentName())) {
						selector = getStringValue(jsonParser);
					} else if ("data".equals(jsonParser.getCurrentName()) && "list".equals(viewType)) {
						innerData = json2Adapter(jsonParser);
					} else if ("attr".equals(jsonParser.getCurrentName())) {
						innerData = new H2Attribute(getStringValue(jsonParser));
					} else if ("click".equals(jsonParser.getCurrentName())) {
						click = json2Click(jsonParser);
					} else if ("pager".equals(jsonParser.getCurrentName())) {
						pager = json2Pager(jsonParser);
					}
					break;
			}
		}
		H2View resultView = null;
		if (innerData instanceof H2Attribute) {
			resultView = new H2View<H2Attribute>(viewType, viewId, selector, (H2Attribute) innerData, click, pager);
		} else if (innerData instanceof H2Adapter) {
			resultView = new H2View<H2Adapter>(viewType, viewId, selector, (H2Adapter) innerData, click, pager);
		}

		return resultView;
	}

	public static H2Pager json2Pager(JsonParser jsonParser) throws IOException {
		String selector = null;
		String attribute = null;

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("selector".equals(jsonParser.getCurrentName())) {
						selector = getStringValue(jsonParser);
					} else if ("attr".equals(jsonParser.getCurrentName())) {
						attribute = getStringValue(jsonParser);
					}
					break;
			}
		}
		return new H2Pager(selector, attribute);
	}

	public static H2Click json2Click(JsonParser jsonParser) throws IOException {
		String actionName = null;
		String selector = null;
		String attribute = null;

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("action".equals(jsonParser.getCurrentName())) {
						actionName = getStringValue(jsonParser);
					} else if ("selector".equals(jsonParser.getCurrentName())) {
						selector = getStringValue(jsonParser);
					} else if ("attr".equals(jsonParser.getCurrentName())) {
						attribute = getStringValue(jsonParser);
					}
					break;
			}
		}
		return new H2Click(actionName, selector, attribute);
	}

	public static H2Adapter json2Adapter(JsonParser jsonParser) throws IOException {

		String type = null;
		String adapterName = null;

		List<H2View> views = new ArrayList<H2View>();
		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			switch (jsonParser.getCurrentToken()) {
				case FIELD_NAME:
					if ("type".equals(jsonParser.getCurrentName())) {
						type = getStringValue(jsonParser);
					} else if ("layout_name".equals(jsonParser.getCurrentName())) {
						adapterName = getStringValue(jsonParser);
					} else if ("data".equals(jsonParser.getCurrentName())) {
						while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
							views.add(json2View(jsonParser));
						}
					}
					break;
			}
		}

		return new H2Adapter(type, adapterName, views);
	}

	public static String getStringValue(JsonParser jsonParser) throws IOException {
		jsonParser.nextValue();
		return jsonParser.getValueAsString();
	}

}
