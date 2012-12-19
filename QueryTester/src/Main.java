import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
//		Document doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775").get();
//		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href"));
//
//		doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775&p=1").get();
//		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href"));
//
//		doc = Jsoup.connect("http://www.ex.ua/ru/video/foreign?r=23775&p=2851").get();
//		System.out.println(doc.select(".include_0 ~ table td:has(img[src*=arr_r]) a").attr("href").isEmpty());
		Map<String, H2Filter> result = new TreeMap<String, H2Filter>();

		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
				jsonParser.nextToken();
			}

			String fieldName = jsonParser.getCurrentName();
			System.out.println(fieldName);
			result.put(fieldName, json2Filter(jsonParser));
		}
		jsonParser.close();
		System.out.println(result);
	}

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

	private static H2Screen json2Screen(JsonParser jsonParser) throws IOException {
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

	private static H2View json2View(JsonParser jsonParser) throws IOException {
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

	private static H2Pager json2Pager(JsonParser jsonParser) throws IOException {
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

	private static H2Click json2Click(JsonParser jsonParser) throws IOException {
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

	private static H2Adapter json2Adapter(JsonParser jsonParser) throws IOException {

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

	private static String getStringValue(JsonParser jsonParser) throws IOException {
		jsonParser.nextValue();
		return jsonParser.getValueAsString();
	}


	private static String getJsonObjectAsString(JsonParser jsonParser) throws IOException {
		int deepCount = 0;
		String result = "";
		if (jsonParser.nextToken() == JsonToken.START_OBJECT) {
			deepCount++;
			result += "{";
		}
		while (deepCount != 0) {
			JsonToken token = jsonParser.nextToken();
			switch (token) {
				case START_OBJECT:
					result += "{";
					deepCount++;
					break;
				case END_OBJECT:
					result += "}";
					deepCount--;
					break;
				case FIELD_NAME:
					result += jsonParser.getCurrentName() + ":";
					break;
				case START_ARRAY:
					result += "[";
					break;
				case END_ARRAY:
					result += "]";
					break;
				case VALUE_STRING:
					result += jsonParser.getValueAsString() + ",";
			}
		}
//		result = "filter parsed";
		return result;
	}

	private static String jsonString = "{\n" +
			"   \"filter1\" :\n" +
			"      {\n" +
			"         \"url\" : \"http://www.ex.ua/\",\n" +
			"         \"should_exist\" : \".list\",\n" +
			"         \"screens\" :\n" +
			"            [\n" +
			"               {\n" +
			"                  \"layout_name\" : \"description_with_image_and_file_list\",\n" +
			"                  \"title_selector\" : \"title\",\n" +
			"                  \"data\" :\n" +
			"                     [\n" +
			"                        {\n" +
			"                           \"type\" : \"text\",\n" +
			"                           \"id\" : \"title_text\",\n" +
			"                           \"selector\" : \"td>h1\",\n" +
			"                           \"attr\" : \"text\"\n" +
			"                        },\n" +
			"                        {\n" +
			"                           \"type\" : \"image\",\n" +
			"                           \"id\" : \"big_image\",\n" +
			"                           \"selector\" : \"#body_element tr:eq(0) img\",\n" +
			"                           \"attr\" : \"src\"\n" +
			"                        },\n" +
			"                        {\n" +
			"                           \"type\" : \"text\",\n" +
			"                           \"id\" : \"description_text\",\n" +
			"                           \"selector\" : \"#body_element td:eq(0) p:not(:has(.r_button))\",\n" +
			"                           \"attr\" : \"text\"\n" +
			"                        },\n" +
			"                        {\n" +
			"                           \"type\" : \"list\",\n" +
			"                           \"id\" : \"list_content\",\n" +
			"                           \"selector\" : \".list tr:not(:eq(0))\",\n" +
			"                           \"data\" :\n" +
			"                              {\n" +
			"                                 \"type\" : \"list_adapter\",\n" +
			"                                 \"layout_name\" : \"image_w_two_lines_list_row\",\n" +
			"                                 \"data\" :\n" +
			"                                    [\n" +
			"                                       {\n" +
			"                                          \"type\" : \"image\",\n" +
			"                                          \"id\" : \"row_image\",\n" +
			"                                          \"selector\" : \"a>img\",\n" +
			"                                          \"attr\" : \"src\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_main_text\",\n" +
			"                                          \"selector\" : \"td:eq(1)>a\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_small_text\",\n" +
			"                                          \"selector\" : \".small b\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       }\n" +
			"                                    ]\n" +
			"                              }\n" +
			"                        }\n" +
			"                     ]\n" +
			"               }\n" +
			"            ]\n" +
			"      },\n" +
			"   \"filter2\" :\n" +
			"      {\n" +
			"         \"url\" : \"http://www.ex.ua/\",\n" +
			"         \"should_exist\" : \".include_0>tbody>tr>td>a>img\",\n" +
			"         \"screens\" :\n" +
			"            [\n" +
			"               {\n" +
			"                  \"layout_name\" : \"single_list\",\n" +
			"                  \"title_selector\" : \"title\",\n" +
			"                  \"data\" :\n" +
			"                     [\n" +
			"                        {\n" +
			"                           \"type\" : \"list\",\n" +
			"                           \"id\" : \"list_content\",\n" +
			"                           \"selector\" : \".include_0>tbody>tr>td\",\n" +
			"                           \"data\" :\n" +
			"                              {\n" +
			"                                 \"type\" : \"list_adapter\",\n" +
			"                                 \"layout_name\" : \"image_w_two_lines_list_row\",\n" +
			"                                 \"data\" :\n" +
			"                                    [\n" +
			"                                       {\n" +
			"                                          \"type\" : \"image\",\n" +
			"                                          \"id\" : \"row_image\",\n" +
			"                                          \"selector\" : \"a>img\",\n" +
			"                                          \"attr\" : \"src\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_main_text\",\n" +
			"                                          \"selector\" : \"a>b\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_small_text\",\n" +
			"                                          \"selector\" : \".info\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       }\n" +
			"                                    ]\n" +
			"                              },\n" +
			"                           \"click\" :\n" +
			"                              {\n" +
			"                                 \"action\" : \"open\",\n" +
			"                                 \"selector\" : \".include_0>tbody>tr>td>p>a:eq(0):not(.info)\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              },\n" +
			"                           \"pager\" :\n" +
			"                              {\n" +
			"                                 \"selector\" : \".include_0 ~ table td:has(img[src*=arr_r]) a\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              }\n" +
			"                        }\n" +
			"                     ]\n" +
			"               }\n" +
			"            ]\n" +
			"      },\n" +
			"   \"filter3\" :\n" +
			"      {\n" +
			"         \"url\" : \"http://www.ex.ua/\",\n" +
			"         \"should_exist\" : \".include_1 img\",\n" +
			"         \"screens\" :\n" +
			"            [\n" +
			"               {\n" +
			"                  \"layout_name\" : \"single_list\",\n" +
			"                  \"title_selector\" : \"title\",\n" +
			"                  \"data\" :\n" +
			"                     [\n" +
			"                        {\n" +
			"                           \"type\" : \"list\",\n" +
			"                           \"id\" : \"list_content\",\n" +
			"                           \"selector\" : \".include_1 td\",\n" +
			"                           \"data\" :\n" +
			"                              {\n" +
			"                                 \"type\" : \"list_adapter\",\n" +
			"                                 \"layout_name\" : \"image_w_two_lines_list_row\",\n" +
			"                                 \"data\" :\n" +
			"                                    [\n" +
			"                                       {\n" +
			"                                          \"type\" : \"image\",\n" +
			"                                          \"id\" : \"row_image\",\n" +
			"                                          \"selector\" : \"img\",\n" +
			"                                          \"attr\" : \"src\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_main_text\",\n" +
			"                                          \"selector\" : \"a b\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_small_text\",\n" +
			"                                          \"selector\" : \".info\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       }\n" +
			"                                    ]\n" +
			"                              },\n" +
			"                           \"click\" :\n" +
			"                              {\n" +
			"                                 \"action\" : \"open\",\n" +
			"                                 \"selector\" : \".include_1 td a:has(b)\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              },\n" +
			"                           \"pager\" :\n" +
			"                              {\n" +
			"                                 \"selector\" : \".include_1 ~ table td:has(img[src*=arr_r]) a\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              }\n" +
			"                        }\n" +
			"                     ]\n" +
			"               }\n" +
			"            ]\n" +
			"      },\n" +
			"   \"filter4\" :\n" +
			"      {\n" +
			"         \"url\" : \"http://www.ex.ua/\",\n" +
			"         \"should_exist\" : \".include_0\",\n" +
			"         \"screens\" :\n" +
			"            [\n" +
			"               {\n" +
			"                  \"layout_name\" : \"single_list\",\n" +
			"                  \"title_selector\" : \"title\",\n" +
			"                  \"data\" :\n" +
			"                     [\n" +
			"                        {\n" +
			"                           \"type\" : \"list\",\n" +
			"                           \"id\" : \"list_content\",\n" +
			"                           \"selector\" : \".include_0>tbody>tr>td\",\n" +
			"                           \"data\" :\n" +
			"                              {\n" +
			"                                 \"type\" : \"list_adapter\",\n" +
			"                                 \"layout_name\" : \"two_lines_list_row\",\n" +
			"                                 \"data\" :\n" +
			"                                    [\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_main_text\",\n" +
			"                                          \"selector\" : \"a>b\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       },\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"row_small_text\",\n" +
			"                                          \"selector\" : \".info\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       }\n" +
			"                                    ]\n" +
			"                              },\n" +
			"                           \"click\" :\n" +
			"                              {\n" +
			"                                 \"action\" : \"open\",\n" +
			"                                 \"selector\" : \".include_0>tbody>tr>td>a\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              }\n" +
			"                        }\n" +
			"                     ]\n" +
			"               }\n" +
			"            ]\n" +
			"      },\n" +
			"   \"filter5\" :\n" +
			"      {\n" +
			"         \"url\" : \"http://www.ex.ua/\",\n" +
			"         \"should_exist\" : \".menu_text\",\n" +
			"         \"screens\" :\n" +
			"            [\n" +
			"               {\n" +
			"                  \"layout_name\" : \"single_list\",\n" +
			"                  \"title_selector\" : \"title\",\n" +
			"                  \"data\" :\n" +
			"                     [\n" +
			"                        {\n" +
			"                           \"type\" : \"list\",\n" +
			"                           \"id\" : \"list_content\",\n" +
			"                           \"selector\" : \".menu_text:eq(0)\",\n" +
			"                           \"data\" :\n" +
			"                              {\n" +
			"                                 \"type\" : \"list_adapter\",\n" +
			"                                 \"layout_name\" : \"one_line_list_row\",\n" +
			"                                 \"data\" :\n" +
			"                                    [\n" +
			"                                       {\n" +
			"                                          \"type\" : \"text\",\n" +
			"                                          \"id\" : \"text\",\n" +
			"                                          \"selector\" : \"a[href!=/]\",\n" +
			"                                          \"attr\" : \"text\"\n" +
			"                                       }\n" +
			"                                    ]\n" +
			"                              },\n" +
			"                           \"click\" :\n" +
			"                              {\n" +
			"                                 \"action\" : \"open\",\n" +
			"                                 \"selector\" : \".menu_text:eq(0)>a[href!=/]\",\n" +
			"                                 \"attr\" : \"href\"\n" +
			"                              }\n" +
			"                        }\n" +
			"                     ]\n" +
			"               }\n" +
			"            ]\n" +
			"      }\n" +
			"}";

}
