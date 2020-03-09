package com.chutianyun.bigdata.util;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
public class RecordUtil {

    public static final Pattern TBSJ = Pattern.compile("^填报时间[:：\\s]?([\\s|\\S]*)");

    public static final Pattern NUM = Pattern.compile("[0-9].*");

    /**
     * 获取填报时间
     */
    public static String getTBSJ(Map<Integer, String> recordMap) {
        List<String> strings = mapToValueList(recordMap);

        if (strings.size() == 2) {
            return strings.get(1).replace(":", "").replace("：", "");
        } else {
            return extractTime(strings.get(0));
        }
    }

    /**
     * 过滤null
     *
     * @param recordMap map
     * @return 不包含null的字段
     */
    public static List<String> mapToValueList(Map<Integer, String> recordMap) {
        Collection<String> values = recordMap.values();
        return values.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static String extractTime(String str) {
        Matcher matcher = TBSJ.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        String group = matcher.group(1);
//        System.out.println(group);
        return group;
    }

    /**
     * 判断是否是申请返岗记录
     *
     * @param record record
     * @return true:是申请返岗的记录
     */
    public static boolean isUserRecord(Map<Integer, String> record) {
        return Objects.nonNull(record.get(0)) && record.size() > 8
                && Character.isDigit(record.get(0).trim().charAt(0));
    }

    /**
     * 襄阳市，判断是不是申请返岗记录
     */
    public static boolean isUserRecordWithXY(Map<Integer, String> record) {
        return Objects.nonNull(record.get(0)) && record.size() > 8;
    }

    public static void main(String[] args) {

        Matcher matcher = TBSJ.matcher("填报时间：2020年3月5日");
        System.out.println(matcher.matches());
        System.out.println(matcher.group(1));

    }
}
