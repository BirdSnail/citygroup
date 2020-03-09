package com.chutianyun.bigdata.parse;

import com.chutianyun.bigdata.model.*;
import com.chutianyun.bigdata.util.RecordUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 解析襄阳市的申请表
 *
 * @author BirdSnail
 * @date 2020/3/8
 */
public class XYparser implements ExcelParser {

    private Path current;

    public XYparser(Path current) {
        this.current = current;
    }

    @Override
    public ApplicationUser parsePerson(Map<Integer, String> record) {
        CompanyName name = new CompanyName(record.get(12), record.get(13), record.get(16));
        CompanyOwner owner = new CompanyOwner(record.get(17), record.get(19), record.get(18));
        CompanyInfo company = new CompanyInfo(name, owner, record.get(23));

        ApplicationUser person = new ApplicationUser();
        person.setCompany(company);
        person.setXM(record.get(0));
        person.setSFZH(record.get(1));
        person.setXZD_SZ(record.get(3));
        person.setXZD_XSQ(record.get(4));
        person.setXZD_XXDZ(record.get(5));
        person.setXZD_SJ(record.get(6));
        person.setXZD_GW(record.get(7));
        person.setJSY_FGRY(record.get(8));
        person.setCLPZH(record.get(9));
        person.setQYSZDXZHBSHYJ(record.get(10));
        person.setXJLDXZHBJKZM(record.get(11));
        return person;
    }


    @Override
    public List<ApplicationUser> parser(List<Map<Integer, String>> records, List<Path> badExcel) {
        if (records.size() < 2) {
            throw new IllegalStateException();
        }
        List<ApplicationUser> result = new ArrayList<>();
        for (int i = 1; i < records.size(); i++) {
            if (RecordUtil.isUserRecordWithXY(records.get(i))) {
                ApplicationUser person = parsePerson(records.get(i));
                person.setXH(String.valueOf(i));
                result.add(person);
            }
        }

        return result;
    }

    @Override
    public Path currentExcel() {
        return current;
    }
}
