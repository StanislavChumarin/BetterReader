package com.staschum.html2view;

import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.staschum.html2view.objects.H2Filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	 * @param jsonString
	 * @return
	 */
	public static Map<String, H2Filter> json2Fiters(String jsonString) throws IOException {

		Map<String, H2Filter> result = new HashMap<String, H2Filter>();

		JsonParser jsonParser = jsonFactory.createJsonParser(jsonString);

		while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jsonParser.getCurrentName();
			jsonParser.nextToken();
//			jsonParser.
		}
		return null;
	}

	/**
	 * Converts json string to H2Filter object with all underlying objects
	 * @param jsonString string to be converted
	 * @return H2Filter object to use further
	 */
	public static H2Filter json2Filter (String jsonString) throws IOException {
		return null;
	}


}
