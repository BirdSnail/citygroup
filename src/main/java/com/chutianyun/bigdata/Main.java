package com.chutianyun.bigdata;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.chutianyun.bigdata.listener.CityDataListener;
import com.chutianyun.bigdata.model.Record;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author BirdSnail
 * @date 2020/2/4
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("参数：<文件输入路径>，<结果输出路径>");
            return;
        }

        File file = new File(args[0]);
        Path targetPath = Paths.get(args[1]);
        if (Files.notExists(targetPath)) {
            Files.createDirectory(targetPath);
        }
        ExcelReader excelReader = EasyExcel.read(file, Record.class, new CityDataListener(args[1])).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        try {
            excelReader.read(readSheet);
        }finally {
            // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
            excelReader.finish();
        }
    }
}
