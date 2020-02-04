package com.chutianyun.bigdata.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author BirdSnail
 * @date 2020/2/4
 */
public class NoObjectListener extends AnalysisEventListener<Map<Integer, String>> {

    private List<Map<Integer, String>> hubei = new ArrayList<>();
    private List<Map<Integer, String>> otherProvince = new ArrayList<>();

    private int provinceIndex;
    private int cityIndex;

    private Map<Integer, String> headMap;

    /**
     * 结果生成目录
     */
    private String targetDirectoryOfHubei;
    private String targetDirectoryOther;

    public NoObjectListener(String target, String targetDirectoryOther, int provinceIndex, int cityIndex) {
        this.targetDirectoryOfHubei = target;
        this.targetDirectoryOther = targetDirectoryOther;
        this.provinceIndex = provinceIndex;
        this.cityIndex = cityIndex;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        System.out.println(data);

        if ("湖北".equals(data.get(provinceIndex).trim())) {
            hubei.add(data);
        } else {
            otherProvince.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        int count1 = doit(hubei, cityIndex, targetDirectoryOfHubei);
        System.out.println("湖北省内一共有记录数：" + count1);
        int count2 = doit(otherProvince, provinceIndex, targetDirectoryOther);
        System.out.println("湖北省外一共有记录数：" + count2);
    }

    private int doit(List<Map<Integer, String>> record, int index, String targetDirectoryOther) {
        Map<String, List<Map<Integer, String>>> collect = record.stream()
                .map(this::replaceMap)
                .collect(Collectors.groupingBy(map -> map.get(index)));

//        System.out.println(collect);

        Set<String> cityNames = collect.keySet();
        int count = 0;

        for (String name : cityNames) {
            List<Map<Integer, String>> cities = collect.get(name);

            int size = cities.size();
            String fileName = name + "（" + size + "）.xlsx";
            File file = new File(targetDirectoryOther, fileName);
            if (Files.notExists(file.toPath())) {
                try {
                    Files.createFile(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            count += size;

            EasyExcel.write(file).sheet(0).head(head()).doWrite(
                    cities.stream()
                            .map(this::getValueFromMap)
                            .collect(Collectors.toList()));
        }
        return count;
    }

    private List<String> getValueFromMap(Map<Integer, String> city) {
        List<String> result = new ArrayList<>();
        for (Integer index : city.keySet()) {
            result.add(city.get(index));
        }
        return result;
    }

    private List<List<String>> head() {
        List<List<String>> head = new ArrayList<>();
        for (Integer index : headMap.keySet()) {
            List<String> cellHead = new ArrayList<>();
            cellHead.add(headMap.get(index));
            head.add(cellHead);
        }
        return head;
    }

    private Map<Integer, String> replaceMap(Map<Integer, String> record) {
        String city = record.get(cityIndex);
        if (Objects.isNull(city) || "\\N".equals(city) || city.isEmpty()) {
            record.put(cityIndex, "未知");
        }
        return record;
    }
}
