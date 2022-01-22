package jdh.test.api.controller.view.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdh.test.api.util.api.ApiRequestUtil;
import jdh.test.api.util.api.ApiResponseUtil;

@RestController
@RequestMapping(value = "api")
public class MsApiController {
	// private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "msg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> apiUsrCount(@RequestBody Map<String, Object> input, HttpServletRequest req) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		// public key 요청 후 입력 데이터 암호화
		Map<String, Object> dataMap = ApiRequestUtil.encryptInputData(input);
		
		// api 요청 / 데이터 복호화
		returnMap = ApiResponseUtil.decryptReturnData(dataMap, "/api/msg");
		
		return returnMap;
	}
}
