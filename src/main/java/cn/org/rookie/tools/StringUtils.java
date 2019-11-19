package cn.org.rookie.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, Collection<String> source) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (String s : source) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public static String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, String[] source) {
        return join(delimiter, prefix, suffix, Arrays.asList(source));
    }

    public static String join(CharSequence delimiter, Collection<String> source) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (String s : source) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public static String join(CharSequence delimiter, String[] source) {
        return join(delimiter, Arrays.asList(source));
    }

    public static String camelCaseToUnderscore(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
