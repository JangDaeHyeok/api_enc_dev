package jdh.test.api.util.api;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jdh.test.api.staticval.JSONReturnValue;
import jdh.test.api.util.encrypt.AES256;
import jdh.test.api.util.encrypt.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 장대혁
 * @title api 요청 util
 * @date 2022-01-22
 * @description api 요청 시 데이터 암호화에 필요한 public key 요청 및 데이터 암호화 util
 *              1. public key 요청
 *              2. AES256 키 생성
 *              3. AES256 키로 요청 파라미터 input data 암호화
 *              4. AES256 키로 api 요청 인증 토큰 암호화
 *              5. public key로 AES256키 암호화
 *              6. JSON 형태로 사용하기 위해 Map<String, Object> 형태로 반환
 */
@Slf4j
public class ApiRequestUtil {
	
	// TODO url, token db에서 조회하도록 수정 필요
	private final static String API_URL = "http://192.168.50.59:8080";
	private final static MediaType mediaType   = MediaType.parse("application/json");
	private final static String API_TOKEN = "api-test-token";
	
	/**
	 * @param Map<String, Object>
	 * @title public key 요청/응답 후 요청 데이터 암호화
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> encryptInputData(Map<String, Object> input) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		log.info("=====[ApiRequestUtil] 실행=====");
		
		try {
			// 1. public key 요청
			log.info("[ApiRequestUtil] 1. public key 요청");
			String publicKey = requestPublicKey();
			
			// 2. AES256 키 생성
			log.info("[ApiRequestUtil] 2. AES256 키 생성");
			String aesKey = AES256.getKey();
			returnMap.put("key", aesKey);
			log.info("[ApiRequestUtil] aesKey :: {}", aesKey);
			
			// 3. AES256 키로 input data 암호화
			log.info("[ApiRequestUtil] 3. AES256 키로 input data 암호화");
			Map<String, Object> dataMap = (Map<String, Object>) input.get("data");
			Gson gson = new Gson();
			JsonObject inputJson = gson.toJsonTree(dataMap).getAsJsonObject();
			String encryptJson = AES256.encrypt(aesKey, inputJson.toString());
			
			// 4. AES256 키로 인증 토큰 암호화
			log.info("[ApiRequestUtil] 4. AES256 키로 인증 토큰 암호화");
			String encryptToken = AES256.encrypt(aesKey, API_TOKEN);
			
			// 5. public key로 AES256 키 암호화
			log.info("[ApiRequestUtil] 5. public key로 AES256 키 암호화");
			PublicKey pk = CipherUtil.strToPublicKey(publicKey);
			String encryptAesKey = CipherUtil.encryptRSA(aesKey, pk);
			
			// 6. JSON String 형태로 저장
			log.info("[ApiRequestUtil] 6. JSON String 형태로 저장");
			JsonObject dataJson = new JsonObject();
			dataJson.addProperty("token", encryptToken);
			dataJson.addProperty("data", encryptJson);
			dataJson.addProperty("key", encryptAesKey);
			returnMap.put("dataStr", dataJson.toString());
		} catch (Exception e) {
			log.error("[ApiRequestUtil] API 요청 중 문제가 발생했습니다. : {}", e.getMessage());
			returnMap.put(JSONReturnValue.KEY_RESULT, JSONReturnValue.RESULT_FAIL);
			returnMap.put(JSONReturnValue.KEY_MSG, "API 요청 중 문제가 발생했습니다. : " + e.getMessage());
			return returnMap;
		}
		
		log.info("=====[ApiRequestUtil] 종료=====");
		
		return returnMap;
	}
	
	/**
	 * @return String
	 * @description API 서버에 Public Key 요청 후 반환
	 */
	@SuppressWarnings("deprecation")
	private static String requestPublicKey() throws Exception {
		String publicKey = "";
		
		// okhttp3 요청용
		OkHttpClient httpClient = new OkHttpClient();
		JsonParser parser = new JsonParser();
		Object obj = null;
		
		Request jsonRequest = new Request.Builder().url(API_URL + "/pk")
				.addHeader("Content-Type", "application/json")
				.post(RequestBody.create(mediaType, "{}"))
				.build();
		Response response = httpClient.newCall(jsonRequest).execute();
		String responseBody = response.body().string();
		obj = parser.parse(responseBody);
		JsonObject responseJson = (JsonObject) obj;
		
		publicKey = responseJson.get("publicKey").toString().replaceAll("\"", "");
		log.info("[ApiRequestUtil] publicKey :: {}", publicKey);
		
		return publicKey;
	}
}
