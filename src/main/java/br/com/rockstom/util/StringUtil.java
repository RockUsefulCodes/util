package br.com.rockstom.util;

import java.util.Arrays;
import java.util.function.Consumer;

public final class StringUtil {

	public static final String STRING_VAZIA = "";

	private StringUtil() {
		super();
	}
	
	public static void isNotEmpty(final String value, Consumer<String> consumer) {
		if (!isEmpty(value)) {
			consumer.accept(value);
		}
	}

	public static boolean isEmpty(final String string) {
		return (string == null || string.trim().equals(""));
	}

	public static boolean isEmpty(final String... strings) {
		return Arrays.asList(strings).stream().anyMatch(StringUtil::isEmpty);
	}

	public static String remove(final String srcString, final String localizar) {
		String result = srcString;
		if (!StringUtil.isEmpty(srcString) && !StringUtil.isEmpty(localizar)) {
			result = srcString.replaceAll(localizar, "");
		}
		return result;
	}

	public static String removeSpecialCharacter(final String string) {
		String result = string;
		if (!StringUtil.isEmpty(string)) {
			result = StringUtil.remove(string, "[^0-9]");
		}
		return result;
	}

	public static boolean nonEmpty(String str) {
		return !isEmpty(str);
	}

	public static String toString(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		
		return null;
	}
}
