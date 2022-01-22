package jdh.test.api.util.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jdh.test.api.staticval.JSONReturnValue;
import jdh.test.api.util.encrypt.AES256;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 장대혁
 * @title api 응답 util
 * @date 2022-01-22
 * @description 암호화 된 input data로 api 요청 후 반환받은 데이터 복호화
 *              1. 암호화 된 JSON 파라미터 input data로 api 요청
 *              2. 결과 데이터 AES256 키로 복호화
 */
@Slf4j
public class ApiResponseUtil {
	private final static MediaType mediaType   = MediaType.parse("application/json");
	private final static String API_URL = "http://192.168.50.59:8080";
	
	/**
	 * @param Map<String, Object> input, String url
	 * @param input.get -> key	 : aes256 key
	 * @param input.get -> dataStr : input data String 암호화
	 * @description 
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> decryptReturnData(Map<String, Object> input, String url) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try {
			log.info("=====[ApiResponseUtil] 실행=====");
			// request 성공 여부 체크
			if(input.get("result") != null && input.get("result").toString().equals("fail")) {
				returnMap.put(JSONReturnValue.KEY_RESULT, input.get("result").toString());
				returnMap.put(JSONReturnValue.KEY_MSG, input.get("msg").toString());
				return returnMap;
			}
			
			// AES256 키 조회
			String aesKey = input.get("key").toString();
			
			// 1. 암호화된 JSON 파라미터로 API POST 요청
			log.info("[ApiResponseUtil] 1. 암호화된 JSON 파라미터로 API POST 요청");
			JsonObject responseJson = requestResultData(input, url);
			// 에러가 발생한 경우
			if(responseJson.get("status") != null && responseJson.get("message") != null) {
				returnMap.put(JSONReturnValue.KEY_RESULT, JSONReturnValue.RESULT_FAIL);
				returnMap.put(JSONReturnValue.KEY_MSG, replaceQuot(responseJson.get("message").toString()));
				return returnMap;
			}
			
			// 2. 결과 데이터 AES256 키로 복호화
			log.info("[ApiResponseUtil] 2. 결과 데이터 AES256 키로 복호화");
			// return data 복호화 후 리턴
			String decryptData = AES256.decrypt(aesKey, responseJson.get("data").toString());
			log.info("[ApiResponseUtil] decryptData :: {}", decryptData);
			responseJson.addProperty("data", replaceQuot(decryptData));
			ObjectMapper mapper = new ObjectMapper();
			returnMap = mapper.readValue(responseJson.toString(), Map.class);
			returnMap.put(JSONReturnValue.KEY_RESULT, replaceQuot(responseJson.get("result").toString()));
			returnMap.put(JSONReturnValue.KEY_MSG, replaceQuot(responseJson.get("msg").toString()));
			
			log.info("=====[ApiResponseUtil] 종료=====");
			
			return returnMap;
		} catch (Exception e) {
			log.error("[ApiResponseUtil] API 요청 후 응답 중 오류 발생 : " + e.getMessage());
			returnMap.put(JSONReturnValue.KEY_RESULT, JSONReturnValue.RESULT_FAIL);
			returnMap.put(JSONReturnValue.KEY_MSG, "[ApiResponseUtil] API 요청 후 응답 중 오류 발생 : " + e.getMessage());
			return returnMap;
		}
	}
	
	/**
	 * @param input
	 * @param url
	 * @return JsonObject
	 * @description API 요청 후 응답 데이터 반환
	 */
	@SuppressWarnings("deprecation")
	private static JsonObject requestResultData(Map<String, Object> input, String url) throws Exception {
		// okhttp3 요청용
		OkHttpClient httpClient = new OkHttpClient();
		JsonParser parser = new JsonParser();
		Object obj = null;
		
		Request jsonRequest = new Request.Builder().url(API_URL + url)
				.addHeader("Content-Type", "application/json")
				.post(RequestBody.create(mediaType, input.get("dataStr").toString()))
				.build();
		Response response = httpClient.newCall(jsonRequest).execute();
		
		// return data 조회
		String responseBody = response.body().string();
		obj = parser.parse(responseBody);
		JsonObject responseJson = (JsonObject) obj;
		
		return responseJson;
	}
	
	/**
	 * @param str
	 * @return String
	 * @description 첫 큰따옴표와 마지막 큰따옴표 제거
	 */
	private static String replaceQuot(String str) {
		str = str.replaceFirst("\"", "");
		
		int regexIndexOf = str.lastIndexOf("\"");
		if(regexIndexOf == -1){
			return str;
		}else{
			return str.substring(0, regexIndexOf) + str.substring(regexIndexOf).replace("\"", "");
		}
	}
}
