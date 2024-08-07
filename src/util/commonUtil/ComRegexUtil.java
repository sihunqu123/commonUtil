package util.commonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.commonUtil.model.CheckResult;
import util.commonUtil.model.RegRule;

/**
 * Regex相关操作
 */
public class ComRegexUtil extends CommonUtil {
	private static final String GREPMARK = "ComRegexUtil";

	public static final String SEPARATOR = File.separator;

	public static final String EOLRegex= "(\\r|\\n|\\v)+";

	/**
	 * 用指定replacement替换掉源字符串中匹配的部门(只匹配一次)
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param replacement
	 *            用来替换的字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return 替换后的字符串
	 */
	public static String replaceByRegex(String sourceStr, Object regex, String replacement) {
		Matcher matcher = Pattern.compile(regex + "").matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		if (matcher.find()) { // 最多只匹配替换一次
			matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement)); // 把从"上次替换处"到"当前匹配处"用replacement替换后的字符串输入到sb里去
			// System.out.println(sb.toString());
		}
		// System.out.println(sb.toString());
		matcher.appendTail(sb);// 把最后一次匹配之后的字符串也输入到sb里去
		// StringBuffer sb2 = new StringBuffer();
		// matcher.appendTail(sb2);//把最后一次匹配之后的字符串也输入到sb里去
		// System.out.println(sb2.toString());
		// System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 *  CASE_INSENSITIVE replacement, escaping the slash and dollarSign in the replacement
	 * @param sourceStr
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceByRegexI(String sourceStr, Object regex, String replacement) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.CASE_INSENSITIVE).matcher(sourceStr);
//		Matcher matcher = Pattern.compile(regex + "").matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		if (matcher.find()) { // 最多只匹配替换一次
			matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement)); // 把从"上次替换处"到"当前匹配处"用replacement替换后的字符串输入到sb里去
			// System.out.println(sb.toString());
		}
		// System.out.println(sb.toString());
		matcher.appendTail(sb);// 把最后一次匹配之后的字符串也输入到sb里去
		// StringBuffer sb2 = new StringBuffer();
		// matcher.appendTail(sb2);//把最后一次匹配之后的字符串也输入到sb里去
		// System.out.println(sb2.toString());
		// System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 *  CASE_INSENSITIVE replacement, without escapse the slash and dollarSign in the replacement
	 * @param sourceStr
	 * @param regex
	 * @param replacement group
	 * @return
	 */
	public static String replaceByRegexIGroup(String sourceStr, Object regex, String replacement) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.CASE_INSENSITIVE).matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		if (matcher.find()) {
			try {
				matcher.appendReplacement(sb, replacement);
			} catch (Exception e) {
				ComLogUtil.error("replaceByRegexIGroup failed. "
						+ " sourceStr:" + sourceStr
						+ " regex:" + regex
						+ " replacement:" + replacement
						);
				throw e;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 *  CASE SENSITIVE replacement, without escapse the slash and dollarSign in the replacement
	 * @param sourceStr
	 * @param regex
	 * @param replacement group
	 * @return
	 */
	public static String replaceByRegexGroup(String sourceStr, Object regex, String replacement) {
		Matcher matcher = Pattern.compile(regex + "").matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		if (matcher.find()) {
			try {
				matcher.appendReplacement(sb, replacement);
			} catch (Exception e) {
				ComLogUtil.error("replaceByRegexIGroup failed. "
						+ " sourceStr:" + sourceStr
						+ " regex:" + regex
						+ " replacement:" + replacement
						);
				throw e;
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 用指定replacement替换掉源字符串中匹配的部门(只匹配一次)
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param replacement
	 *            用来替换的字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAllByRegex(String sourceStr, Object regex, String replacement) {
		// Matcher matcher = Pattern.compile(regex + "",
		// Pattern.MULTILINE).matcher(sourceStr);
		Matcher matcher = Pattern.compile(regex + "", Pattern.DOTALL).matcher(sourceStr);
		if (matcher.find()) { // 最多只匹配替换一次
			sourceStr = matcher.replaceAll(Matcher.quoteReplacement(replacement));
		}
		return sourceStr;
	}

	/**
	 *
	 * @param sourceStr
	 * @param str2Match
	 * @param replacement the replacement literally. e.g sourceStr:a\b\c str2Match:\ replacement:\\  will return: a\\b\\c
	 * @return
	 */
	public static String replaceAllLiterally(String sourceStr, String str2Match, String replacement) {
		// Matcher matcher = Pattern.compile(regex + "",
		// Pattern.MULTILINE).matcher(sourceStr);
		Matcher matcher = Pattern.compile(str2Match + "", Pattern.LITERAL).matcher(sourceStr);
		if (matcher.find()) { // 最多只匹配替换一次
			sourceStr = matcher.replaceAll(Matcher.quoteReplacement(replacement));
		}
		return sourceStr;
	}

	public static String replaceLiterally(String sourceStr, String str2Match, String replacement) {
		// Matcher matcher = Pattern.compile(regex + "",
		// Pattern.MULTILINE).matcher(sourceStr);
		Matcher matcher = Pattern.compile(str2Match + "", Pattern.LITERAL).matcher(sourceStr);
		if (matcher.find()) { // 最多只匹配替换一次
			sourceStr = matcher.replaceFirst(Matcher.quoteReplacement(replacement));
		}
		return sourceStr;
	}

	/**
	 * 从字符串中得到匹配的第一个子字符串
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return
	 */
	public static String getMatchedString(String sourceStr, Object regex) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.DOTALL).matcher(sourceStr);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}
	
	/**
	 * 从字符串中得到匹配的第一个子字符串 ignorecase
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return
	 */
	public static String getMatchedStringIg(String sourceStr, Object regex) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.CASE_INSENSITIVE).matcher(sourceStr);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}
	
	public static Boolean test(String sourceStr, Object regex) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.DOTALL).matcher(sourceStr);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param sourceStr
	 * @param regRules
	 * @return {
	 * 	result: 1 for matched.
	 *  reason: the reg that matched.
	 * }
	 */
	public static CheckResult testRegs(String sourceStr, RegRule[] regRules) {
		
		for(int i = 0; i < regRules.length; i++) {
			RegRule currentRule = regRules[i];
			String currentReg = currentRule.getReg();
			Boolean isCaseSensitive = currentRule.getIsCaseSensitive();
			
//			if(currentReg.indexOf("rarbg") > -1) {
//				ComLogUtil.info("oriAbsPath: " + oriAbsPath + ", currentReg:" + currentReg);
//			}
			
//			ComLogUtil.info("oriAbsPath: " + oriAbsPath + ", currentReg:" + currentReg);
			
			boolean isMatched = false;
			
			try {
				if(isCaseSensitive) {
					isMatched = ComRegexUtil.test(sourceStr, currentReg);
				} else {
					isMatched = ComRegexUtil.testIg(sourceStr, currentReg);
				}

				if(isMatched) return new CheckResult(1, currentReg);
			} catch(Exception e) {
				ComLogUtil.error("regex error on:" + currentReg);
				throw e;
			}
			
		}
		return new CheckResult(0, "");
	}
	
	
	
	/**
	 * test ignore case
	 * @param sourceStr
	 * @param regex
	 * @return
	 */
	public static Boolean testIg(String sourceStr, Object regex) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.CASE_INSENSITIVE).matcher(sourceStr);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 从字符串中得到匹配的第一个子字符串
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return
	 */
	public static String getMatchedString(String sourceStr, Object regex, int flags) {
		Matcher matcher = Pattern.compile(regex + "", flags).matcher(sourceStr);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 从字符串中得到匹配的第一个子字符串
	 *
	 * @param sourceStr
	 *            被用来匹配的源字符串
	 * @param regex
	 *            去匹配的regex字符串
	 * @return
	 */
	public static String[] getMatchedStringArr(String sourceStr, Object regex) {
		Matcher matcher = Pattern.compile(regex + "", Pattern.DOTALL).matcher(sourceStr);
		// int resNum = 0;
		// System.out.println(resNum);
		// String[] strArr = new String[];
		int i = 0;
		List<String> list = new ArrayList<String>();
		while (matcher.find() && i++ < 100) {
			// System.out.println("cc: " + matcher.groupCount());
			// System.out.println(matcher.group());
			list.add(matcher.group());

		}
		return list.toArray(new String[list.size()]);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(ComRegexUtil.replaceByRegex("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals[-_ ][^\\\\\\//]+$", "SLR_Originals"));
		System.out.println(ComRegexUtil.replaceByRegex("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals[-_ ][^\\\\\\//]+$", "SLR_Originals"));
		System.out.println(ComRegexUtil.replaceByRegex("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals(?=[ -_][^\\\\]+$)", "SLR_Originals"));
		System.out.println(ComRegexUtil.replaceByRegexGroup("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals(?=[ -_][^\\\\]+$)", "SLR_Originals"));
		System.out.println(ComRegexUtil.replaceByRegexI("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals[ -_]{1,99}$", "aaaa"));
		System.out.println(ComRegexUtil.replaceByRegexIGroup("T:\\toMove\\VR\\SLROriginals\\SLR_Originals-All_the_News_thats_Fit_to_Fuck_original_26630_MKX200.mp4", "(?<=\\\\)SLROriginals[ -_]{1,99}$", "aaaa"));
		System.out.println(ComRegexUtil.getMatchedString("hhd800.com@aqube00028.part1.mp4", "(?<=.{1,150}[-_. ]{1,9}(part|R))\\d{1,2}.mp4$"));
		System.out.println(ComRegexUtil.getMatchedString("hhd800.com@aqube00028.R1.mp4", "(?<=.{1,150}[-_.]{1,9}(part|R))\\d{1,2}.mp4$"));
		
		System.out.println(Matcher.quoteReplacement("\\\\"));
		System.out.println(replaceAllLiterally("abcdefgbchij", "bc", "*"));
		if(true) return;
		String aa = "st\\\\\\\"r\\\"aa\\\\\\\"a23aaaa4\"raaa\\\\\\\\\"sd\\\\\"f";
		System.out.println(aa);
		System.out.println(aa.replaceAll("(?<!(?<!\\\\)(\\\\{2}){0,9999}\\\\{1}(?!\\\\))\"", "\\\\\""));
		String str = ComFileUtil.readFile2String("e:\\download\\youtube\\downloadLog20161127170434.html");
		// System.out.println(ComRegexUtil.getMatchedString(str, "(?<=\\<div
		// id=\"unavailable-submessage\" class=\"submessage\">).+"));
		System.out.println(ComRegexUtil.getMatchedString(str,
				"(?<=<div id=\"unavailable-submessage\" class=\"submessage\">\\s{0,99}+)((?!</div>).)*</div>"));
		// Matcher matcher = Pattern.compile("(?<=\\<div
		// id=\"unavailable-submessage\" class=\"submessage\">).+",
		// Pattern.MULTILINE).matcher(str);
		// if(matcher.find()){
		// System.out.println("result" + matcher.group(0));
		// }
		// System.out.println(ComRegexUtil.getMatchedString(str, "<div
		// id=\"unavailable((?!\"submessage).)+"));
		// System.out.println(ComRegexUtil.getMatchedString(str, "<div
		// id=\"unavailable((?!=\"submessage).)+"));

		// ComLogUtil.printArr(ComRegexUtil.getMatchedStringArr(str, "(?<=<a
		// href=\")/watch\\?v=[^\"]+(?=\"\\s{1,20}class=\")"), "url");
		// ComLogUtil.printArr(ComRegexUtil.getMatchedStringArr(str, "(?<=<a
		// href=\")/watch\\?v=[^\"]+"), "url");
		//
		// System.out.println(ComRegexUtil.getMatchedString("en.00042",
		// "^(\\w|-){2,5}\\.\\d{5}$"));
		//
		//
		try {
			// System.out.println(ComFileUtil.readFile2String(new
			// File("c:\\dddsdf.js")));
			// System.out.println("SELECT id, 'cc\\'c' bb FROM billing_id ");
			// System.out.println("\\\\".length());
			// System.out.println(getMatchedString("sdf/sdfdsfdfdf/dsfds/fds/fffdd=aa",
			// "(?<=/)(?!(.*/.*))[^?/]*"));
			// System.out.println(replaceAllByRegex("cca\\\\ccacca", "\\\\",
			// "11"));
			// String src = "<!--Notes ACF aaaaaaaaaaaaaa\\naaaaa
			// -->sdfsda\\n\\nfsd <!--
			// -->\\n\\nsdfds<!--Notes ACF \\nsdafsdfsdf\\nsdfsdafsd\\n";

			// System.out.println(replaceAllByRegex(src, "<!--((?!-->).)+-->",
			// ""));
			// System.out.println(replaceAllByRegex("dsssfsdfsd", "((?!ss).)+",
			// ""));

			/**
			 * String regex = "(x)(y\\w*)(z)";
			 *
			 * String input = "exy123z,xy456z"; Pattern p =
			 * Pattern.compile(regex, Pattern.CASE_INSENSITIVE); Matcher m =
			 * p.matcher(input);
			 *
			 * while (m.find()) { System.out.println(m.group(0)); }
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
