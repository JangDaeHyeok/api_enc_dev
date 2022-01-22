package jdh.test.api.util.etc;

public class ValidateUtil {
	public static boolean notEmpty(Object o) {
		// check null
		if(o == null) {
			return false;
		}
		
		// if object instance is String
		if(o instanceof String && o.toString().equals("")) {
			return false;
		}
		
		return true;
	}
}
