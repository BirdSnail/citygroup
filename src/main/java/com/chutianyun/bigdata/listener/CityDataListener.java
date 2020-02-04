package com.chutianyun.bigdata.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.chutianyun.bigdata.model.Record;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author BirdSnail
 * @date 2020/2/4
 */
public class CityDataListener extends AnalysisEventListener<Record> {

    /**
     * 结果生成目录
     */
    private String targetDirectory;

    public CityDataListener(String target) {
        this.targetDirectory = target;
    }

    List<Record> hubei = new ArrayList<>();
    List<Record> otherProvince = new ArrayList<>();

    public void invoke(Record city, AnalysisContext analysisContext) {
        System.out.println(city);

        if ("湖北".equals(city.getPROVINCE())) {
            hubei.add(city);
        } else {
            otherProvince.add(city);
        }
    }

    private void writeResult() {
        execute(groupByKey(hubei, Record::getCITY));
        execute(groupByKey(otherProvince, Record::getPROVINCE));
    }

    private void execute(Map<String, List<Record>> groupByKey) {
        Set<String> cityNames = groupByKey.keySet();
        for (String name : cityNames) {
            toExcel(name, groupByKey.get(name));
        }
    }

    private void toExcel(String name, List<Record> records) {
        String fileName = name + "（" + records.size() + "）.xlsx";
        File file = new File(targetDirectory, fileName);
        if (Files.notExists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ExcelWriter excelWriter = EasyExcel.write(file, Record.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
        excelWriter.write(records, writeSheet);

        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private Map<String, List<Record>> groupByKey(List<Record> records, Function<Record, String> keyExtractor) {
        Map<String, List<Record>> cityMap = records.stream()
                .map(city -> {
                    if (Objects.isNull(city.getCITY()) || city.getCITY().equals("\\N") || city.getCITY().isEmpty()) {
                        city.setCITY("未知");
                    }
                    return city;
                })
                .collect(Collectors.groupingBy(keyExtractor));
//        System.out.println(cityMap);
        return cityMap;
    }


    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        writeResult();
    }
}
