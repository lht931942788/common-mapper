package cn.org.rookie.mapper.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern humpPattern = Pattern.compile("[A-Z]");
    public static final Pattern underlinePattern = Pattern.compile("_(\\w)");

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

    public static String humpToUnderline(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String underlineToHump(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
