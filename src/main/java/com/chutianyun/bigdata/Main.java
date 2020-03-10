package com.chutianyun.bigdata;

import com.alibaba.excel.EasyExcel;
import com.chutianyun.bigdata.listener.ParserApplicationExcelListener;
import com.chutianyun.bigdata.model.ApplicationFileInfo;
import com.chutianyun.bigdata.model.ApplicationUser;
import com.chutianyun.bigdata.model.CompanyInfo;
import com.chutianyun.bigdata.model.UserApplicationOfReturn;
import com.chutianyun.bigdata.model.oracle.ApplicationPeople;
import com.chutianyun.bigdata.mybatis.OracleOperator;
import com.chutianyun.bigdata.parse.ExcelParser;
import com.chutianyun.bigdata.parse.NormalParser;
import com.chutianyun.bigdata.parse.XYparser;
import com.chutianyun.bigdata.parse.YCParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BirdSnail
 * @date 2020/2/4
 */
public class Main {


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("参数不正确: usage: java -jar xxx.jar <filePath> <targetPath>");
            return;
        }

        String start = args[0];
        Path entry = new File(start).toPath();

        List<UserApplicationOfReturn> allApplicationPeople = new ArrayList<>();
        List<Path> badExcel = new ArrayList<>();

        try {
            List<Path> files = getAllExcelFile(entry);
            for (Path file : files) {
                final ApplicationFileInfo fileInfo = ApplicationFileInfo.of(file, start);
                ExcelParser excelParser;
                if (fileInfo.isXiangYang()) {
                    // 襄阳的解析
                    excelParser = new XYparser(file);
                } else if (fileInfo.isYiChang()) {
                    excelParser = new YCParser(file);
                } else {
                    excelParser = new NormalParser(file);
                }

                EasyExcel.read(file.toFile(),
                        new ParserApplicationExcelListener(excelParser, allApplicationPeople, badExcel, fileInfo))
                        .sheet().doRead();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("读取到的申请人数：" + allApplicationPeople.size());
        List<ApplicationPeople> finalResult = transToApplicationPeopleList(allApplicationPeople);

        File target = new File(args[1] + "result.xlsx");
        if (target.exists()) {
            target.delete();
        }
        EasyExcel.write(target, ApplicationPeople.class).sheet().doWrite(finalResult);
        System.out.println("我将所有的申请人保存到了一个excel方便查看：" + target.getAbsolutePath());

        writeToOracleAfterParse(badExcel, finalResult);
    }

    private static void writeToOracleAfterParse(List<Path> badExcel, List<ApplicationPeople> allApplicationPeople) {
        if (badExcel.size() != 0) {
            badExcel.forEach(e -> System.out.println("该excel有问题：" + e.toAbsolutePath().toString()));
            try {
                OracleOperator.batchInsert(Collections.singletonList(allApplicationPeople.get(0)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("恭喜全部解析成功。。。开始入库");
            try {
                OracleOperator.batchInsert(allApplicationPeople);
                System.out.println("入库成功");
            } catch (IOException e) {
                System.out.println("入库失败");
                e.printStackTrace();
            }
        }
    }


    private static List<Path> getAllExcelFile(Path entry) throws IOException {
        String xlsToFind = ".xls";
        String xlsxToFind = ".xlsx";
        List<Path> result = new ArrayList<>();

        Files.walkFileTree(entry, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileString = file.toAbsolutePath().toString();
                if (file.getFileName().toString().startsWith("~$")) {
                    return FileVisitResult.CONTINUE;
                }
                if (fileString.endsWith(xlsToFind) || fileString.endsWith(xlsxToFind)) {
//                    System.out.println("file found at path: " + file.toAbsolutePath());
                    result.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }

    private static List<ApplicationPeople> transToApplicationPeopleList(List<UserApplicationOfReturn> list) {
        return list.stream().map(p -> {
            ApplicationPeople oraclePerson = new ApplicationPeople();
            CompanyInfo company = p.getUser().getCompany();
            oraclePerson.setTBSJ(company.getTBSJ());
            oraclePerson.setQYMC(company.getName().getQYMC());
            oraclePerson.setQYTYSHXYDM(company.getName().getQYTYSHXYDM());
            oraclePerson.setQYXXDZ(company.getName().getQYXXDZ());
            oraclePerson.setQYLXR(company.getOwner().getQYLXR());
            oraclePerson.setQYSQFHSJ(company.getOwner().getQYSQFHSJ());
            oraclePerson.setQYSJ(company.getOwner().getQYSJ());

            ApplicationUser person = p.getUser();
            oraclePerson.setXH(person.getXH());
            oraclePerson.setXM(person.getXM());
            oraclePerson.setSFZH(person.getSFZH());
            oraclePerson.setXZD_SZ(person.getXZD_SZ());
            oraclePerson.setXZD_XSQ(person.getXZD_XSQ());
            oraclePerson.setXZD_XXDZ(person.getXZD_XXDZ());
            oraclePerson.setXZD_SJ(person.getXZD_SJ());
            oraclePerson.setXZD_GWGZ(person.getXZD_GW());
            oraclePerson.setJSY_FGRY(person.getJSY_FGRY());
            oraclePerson.setCLPZH(person.getCLPZH());
            oraclePerson.setQYSZDXZHBSHYJ(person.getQYSZDXZHBSHYJ());
            oraclePerson.setXJLDXZHBJKZM(person.getXJLDXZHBJKZM());

            ApplicationFileInfo fileInfo = p.getApplicationFileInfo();
            oraclePerson.setRKWJ(fileInfo.getRKWJ());
            oraclePerson.setWJML(fileInfo.getWJML());
            oraclePerson.setWJZML(fileInfo.getWJZML());

            return oraclePerson;
        }).collect(Collectors.toList());
    }
}
