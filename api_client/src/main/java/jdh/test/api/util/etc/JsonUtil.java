package jdh.test.api.util.etc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * @param map Map<String, Object>.
	 * @return JSONObject.
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject getJsonStringFromMap(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				jsonObject.put(key, value);
			}
		}

		return jsonObject;
	}

	/**
	 * @param list List<Map<String, Object>>.
	 * @return JSONArray.
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray getJsonArrayFromList(List<Map<String, Object>> list) {
		JSONArray jsonArray = new JSONArray();
		for (Map<String, Object> map : list) {
			jsonArray.add(getJsonStringFromMap(map));
		}

		return jsonArray;
	}

	/**
	 * @param list List<Map<String, Object>>.
	 * @return String.
	 */
	public static String getJsonStringFromList(List<Map<String, Object>> list) {
		JSONArray jsonArray = getJsonArrayFromList(list);
		return jsonArray.toJSONString();
	}

	/**
	 * @param jsonObj JSONObject.
	 * @return Map<String, Object>.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) {
		Map<String, Object> map = null;

		try {

			map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class);

		} catch (JsonParseException e) {
			logger.error("[getMapFromJsonObject]?óê?Ñú ?ùµ?Öâ?Öò Î∞úÏÉù - " + e.getMessage());
		} catch (JsonMappingException e) {
			logger.error("[getMapFromJsonObject]?óê?Ñú ?ùµ?Öâ?Öò Î∞úÏÉù - " + e.getMessage());
		} catch (IOException e) {
			logger.error("[getMapFromJsonObject]?óê?Ñú ?ùµ?Öâ?Öò Î∞úÏÉù - " + e.getMessage());
		}

		return map;
	}

	/**
	 * @param jsonArray JSONArray.
	 * @return List<Map<String, Object>>.
	 */
	public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (jsonArray != null) {
			int jsonSize = jsonArray.size();
			for (int i = 0; i < jsonSize; i++) {
				Map<String, Object> map = JsonUtil.getMapFromJsonObject((JSONObject) jsonArray.get(i));
				list.add(map);
			}
		}

		return list;
	}
}
