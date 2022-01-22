package jdh.test.api.controller.view.api.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdh.test.api.util.api.ApiRequestUtil;
import jdh.test.api.util.api.ApiResponseUtil;

/**
 * @author 장대혁
 * @title api 암복호화 요청 로직 성능 테스트
 * @date 2022-01-22
 * @description api 암복호화 로직 문자열 길이에 따른 성능 테스트
 */
public class MsApiTestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void apiUsrCountTest() throws Exception {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		Map<String, Object> inputDataMap = new HashMap<String, Object>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		long totalBeforeTime = System.currentTimeMillis();
		
		logger.warn("[apiTest] 시간 측정 시작");
		for(int i = 0; i < 100000; i += 10000) {
			returnMap = new HashMap<String, String>();
			long beforeTime = System.currentTimeMillis();
			
			inputDataMap.put("count", Integer.toString(i));
			inputMap.put("data", inputDataMap);
			
			// public key 요청 후 입력 데이터 암호화
			Map<String, Object> dataMap = ApiRequestUtil.encryptInputData(inputMap);
			// 데이터 응답 후 복호화
			ApiResponseUtil.decryptReturnData(dataMap, "/api/test");
			
			long afterTime = System.currentTimeMillis();
			long secDiffTime = (afterTime - beforeTime);
			
			if(returnMap.get("result") != null && returnMap.get("result").toString().equals("fail")) {
				logger.warn("[apiTest] 에러 발생 : " + returnMap.get("msg").toString());
				break;
			}else {
				returnMap.put("count", Integer.toString(i));
				returnMap.put("secDiffTime", Long.toString(secDiffTime));
				returnList.add(returnMap);
				logger.warn("[apiTest] 시간차이(ms) : " + secDiffTime);
			}
		}
		
		long totalAfterTime = System.currentTimeMillis();
		long totalSecDiffTime = (totalAfterTime - totalBeforeTime);
		logger.warn("[apiTest] 총 소요시간 시간(ms) : " + totalSecDiffTime);
		
		logger.warn("[apiTest] 시간 측정 종료");
		
		System.out.println(returnList.toString());
	}
}
