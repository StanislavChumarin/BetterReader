import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
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
//		Map<String, H2Filter> result = new TreeMap<String, H2Filter>();

		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);

		while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
			if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
				jsonParser.nextToken();
			}

			String fieldName = jsonParser.getCurrentName();
			System.out.println(fieldName);
//			result.put(fieldName, json2Filter(jsonParser));
		}
		jsonParser.close();
//		System.out.println(result);
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
