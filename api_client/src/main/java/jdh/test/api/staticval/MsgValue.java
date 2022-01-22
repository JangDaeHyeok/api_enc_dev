package jdh.test.api.staticval;

/**
 * @ë¶„ë¥˜ : 
 * @?´?˜?Š¤ëª? : MsgValue
 * @?´?˜?Š¤?„¤ëª? : ë©”ì‹œì§? ?ƒ?„± ?Š¤?…Œ?‹± ?´?˜?Š¤
 */
public class MsgValue {

	public static final String SORRY              = "ê´?ë¦¬ì?— ë¬¸ì˜ ?•´ì£¼ì„¸?š”.";
	public static final String WRONG_ACCESS       = "?˜ëª»ëœ ? ‘ê·¼ì…?‹ˆ?‹¤.";
	public static final String SORRY_NO_PAGE      = "?š”ì²??•˜?‹  ?˜?´ì§?ë¥? ì°¾ì„?ˆ˜ ?—†?Šµ?‹ˆ?‹¤.";
	public static final String SORRY_CALL_ADMIN   = "ë¶ˆí¸?„ ?“œ? ¤ ???‹¨?ˆ ì£„ì†¡?•©?‹ˆ?‹¤. ê´?ë¦¬ì?— ë¬¸ì˜ ?•´ì£¼ì„¸?š”.";
	public static final String WAIT_TRY_AGAIN     = "ë¶ˆí¸?„ ?“œ? ¤ ???‹¨?ˆ ì£„ì†¡?•©?‹ˆ?‹¤. ? ?‹œ ?›„ ?‹¤?‹œ ?‹œ?„?•´ì£¼ì„¸?š”.";
	public static final String SORRY_NAME_DEFFI   = "ë³¸ì¸ ëª…ì˜?˜ ?œ´???° ë²ˆí˜¸ë¡? ?¸ì¦í•˜?‹¤ ?ˆ˜ ?ˆ?Šµ?‹ˆ?‹¤.";
	public static final String SORRY_SEARCH_VALUE = "?…„?›”?„ ëª¨ë‘ ?„ ?ƒ ?›„ ê²??ƒ‰ ?•´ì£¼ì„¸?š”.";
	public static final String AUTH_NOT_FOUND	  = "ê¶Œí•œ?´ ?—†?Šµ?‹ˆ?‹¤.";
	
	public static final String SPACE = " ";
	public static final String ENTER = "\n";
	public static final String ENTER_ALERT = "\\n";
	public static final String TAB   = "\t";

	/****************************************************************************" CRUD */
	public static final String NOUN_INSERT = "ì¶”ê?ê°?";
	public static final String NOUN_UPDATE = "?ˆ˜? •?´";
	public static final String NOUN_SELECT = "ê²??ƒ‰?´";
	public static final String NOUN_DELETE = "?‚­? œê°?";
	
	public static final String[] OBJECTS = {"?„±ê³?", "?‹¤?Œ¨"};
	public static final String VERB = "?•˜???Šµ?‹ˆ?‹¤.";
	public static final String COMPLETE = "?™„ë£Œë˜?—ˆ?Šµ?‹ˆ?‹¤.";

	/****************************************************************************" EXCEPTION */
	public static final String EXCEPTION = "ë¬¸ì œê°? ë°œìƒ?•˜???Šµ?‹ˆ?‹¤.";
	
	/****************************************************************************" LOGIN */
	public static final String LOGIN_COMMON_FAIL     = "ë¡œê·¸?¸?— ?‹¤?Œ¨?•˜???Šµ?‹ˆ?‹¤." + ENTER_ALERT + "?‹¤?‹œ ?‹œ?„?•´ ì£¼ì„¸?š”.";
	public static final String LOGIN_FIVE_TIMES_FAIL = "ë¹„ë?ë²ˆí˜¸ 5?šŒ ?‹¤?Œ¨ë¡? ë¡œê·¸?¸?´ ë¶ˆê??•©?‹ˆ?‹¤." + ENTER_ALERT + "ê´?ë¦¬ì?—ê²? ë¬¸ì˜?•´ì£¼ì„¸?š”.";
	public static final String PW_PATTERN_FAIL = "ë¹„ë?ë²ˆí˜¸ ?˜•?‹?´ ??ë¦½ë‹ˆ?‹¤."+ ENTER_ALERT +"?‹¤?‹œ ?‹œ?„?•´ ì£¼ì„¸?š”.";
	
	/**=====================================[NAMO]
	 * @ë¶„ë¥˜ : 
	 * @ë©”ì†Œ?“œëª? : getDeleteMsg
	 * @?…Œ?Š¤?Š¸?¼? : 2020. 7. 23.
	 * @ê°œë°œ? : ?´ê°•ë??
	 * @ë¦¬í„´ : 
	 * @ë©”ì†Œ?“œ?„¤ëª? : type = "ê¸°ëŠ¥ëª?" (?˜ˆ:?‚¬?š©?) / success = true / false
	 * ===========================================*/
	public static String getDeleteMsg(String type) {
		return type + SPACE + NOUN_DELETE + SPACE + OBJECTS[0] + SPACE + VERB;
	}
	public static String getDeleteMsg(String type, boolean success) {
		String successTxt = OBJECTS[0];
		if(!success) { successTxt = OBJECTS[1]; }
		return type + SPACE + NOUN_DELETE + SPACE + successTxt + SPACE + VERB;
	}
	
	/**=====================================[NAMO]
	 * @ë¶„ë¥˜ : 
	 * @ë©”ì†Œ?“œëª? : getSelectMsg
	 * @?…Œ?Š¤?Š¸?¼? : 2020. 7. 23.
	 * @ê°œë°œ? : ?´ê°•ë??
	 * @ë¦¬í„´ : 
	 * @ë©”ì†Œ?“œ?„¤ëª? : type = "ê¸°ëŠ¥ëª?" (?˜ˆ:?‚¬?š©?) / success = true / false
	 * ===========================================*/
	public static String getSelectMsg(String type) {
		return type + SPACE + NOUN_SELECT + SPACE + OBJECTS[0] + SPACE + VERB;
	}
	public static String getSelectMsg(String type, boolean success) {
		String successTxt = OBJECTS[0];
		if(!success) { successTxt = OBJECTS[1]; }
		return type + SPACE + NOUN_SELECT + SPACE + successTxt + SPACE + VERB;
	}
	
	/**=====================================[NAMO]
	 * @ë¶„ë¥˜ : 
	 * @ë©”ì†Œ?“œëª? : getUpdateMsg
	 * @?…Œ?Š¤?Š¸?¼? : 2020. 7. 23.
	 * @ê°œë°œ? : ?´ê°•ë??
	 * @ë¦¬í„´ : type ?ˆ˜? •?´ ?„±ê³? ?•˜???Šµ?‹ˆ?‹¤.
	 * @ë©”ì†Œ?“œ?„¤ëª? : type = "ê¸°ëŠ¥ëª?" (?˜ˆ:?‚¬?š©?) / success = true / false
	 * ===========================================*/
	public static String getUpdateMsg(String type) {
		return type + SPACE + NOUN_UPDATE + SPACE + OBJECTS[0] + SPACE + VERB;
	}
	public static String getUpdateMsg(String type, boolean success) {
		String successTxt = OBJECTS[0];
		if(!success) { successTxt = OBJECTS[1]; }
		return type + SPACE + NOUN_UPDATE + SPACE + successTxt + SPACE + VERB;
	}
	
	/**=====================================[NAMO]
	 * @ë¶„ë¥˜ : 
	 * @ë©”ì†Œ?“œëª? : getInsertMsg
	 * @?…Œ?Š¤?Š¸?¼? : 2020. 7. 23.
	 * @ê°œë°œ? : ?´ê°•ë??
	 * @ë¦¬í„´ : type ?“±ë¡ì´ ?„±ê³? ?•˜???Šµ?‹ˆ?‹¤.
	 * @ë©”ì†Œ?“œ?„¤ëª? : type = "ê¸°ëŠ¥ëª?" (?˜ˆ:?‚¬?š©?) / success = true / false
	 * ===========================================*/
	public static String getInsertMsg(String type) {
		return type + SPACE + NOUN_INSERT + SPACE + OBJECTS[0] + SPACE + VERB;
	}
	public static String getInsertMsg(String type, boolean success) {
		String successTxt = OBJECTS[0];
		if(!success) { successTxt = OBJECTS[1]; }
		return type + SPACE + NOUN_INSERT + SPACE + successTxt + SPACE + VERB;
	}
}
