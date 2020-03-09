package com.chutianyun.bigdata;

import com.alibaba.excel.EasyExcel;
import com.chutianyun.bigdata.listener.ParserApplicationExcelListener;
import com.chutianyun.bigdata.model.ApplicationFileInfo;
import com.chutianyun.bigdata.model.UserApplicationOfReturn;
import com.chutianyun.bigdata.parse.ExcelParser;
import com.chutianyun.bigdata.parse.NormalParser;
import com.chutianyun.bigdata.parse.XYparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BirdSnail
 * @date 2020/2/4
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String start = "C:\\Users\\31472\\Desktop\\返岗";
        Path entry = new File(start).toPath();
        List<Path> files = getAllExcelFile(entry);

        List<UserApplicationOfReturn> allApplicationPeople = new ArrayList<>();

        for (Path file : files) {
            final ApplicationFileInfo fileInfo = ApplicationFileInfo.of(file, start);
            if (fileInfo.isXiangYang()) {
                // 襄阳的解析
                ExcelParser xyParser = new XYparser();
                EasyExcel.read(file.toFile(),
                        new ParserApplicationExcelListener(xyParser, allApplicationPeople, fileInfo))
                        .sheet().doRead();
            } else {
                ExcelParser normalParser = new NormalParser();
                EasyExcel.read(file.toFile(),
                        new ParserApplicationExcelListener(normalParser, allApplicationPeople, fileInfo))
                        .sheet().doRead();
            }
        }

        System.out.println(allApplicationPeople.size());
    }


    private static List<Path> getAllExcelFile(Path entry) throws IOException {
        String xlsToFind = ".xls";
        String xlsxToFind = ".xlsx";
        List<Path> result = new ArrayList<>();

        Files.walkFileTree(entry, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileString = file.toAbsolutePath().toString();
                if (fileString.endsWith(xlsToFind) || fileString.endsWith(xlsxToFind)) {
//                    System.out.println("file found at path: " + file.toAbsolutePath());
                    result.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }
}
