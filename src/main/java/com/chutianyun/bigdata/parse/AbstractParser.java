package com.chutianyun.bigdata.parse;

import com.chutianyun.bigdata.model.ApplicationUser;
import com.chutianyun.bigdata.model.CompanyInfo;
import com.chutianyun.bigdata.model.CompanyName;
import com.chutianyun.bigdata.model.CompanyOwner;
import com.chutianyun.bigdata.util.RecordUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author BirdSnail
 * @date 2020/3/9
 */
public abstract class AbstractParser implements ExcelParser {

    abstract CompanyName createCompanyName(Map<Integer, String> record);

    abstract CompanyOwner createCompanyOwner(Map<Integer, String> record);

    public String getTBSJ(List<Map<Integer, String>> records){
        return RecordUtil.getTBSJ(records.get(0));
     }

    @Override
    public List<ApplicationUser> parser(List<Map<Integer, String>> records, List<Path> badExcel) {
        if (checkLines(records)) {
            badExcel.add(currentExcel());
            return null;
        }

        // excel格式不对
        if (!checkTBSJ(RecordUtil.recordMapToStr(records.get(0)))) {
            badExcel.add(currentExcel());
            return null;
        }

        List<ApplicationUser> result = new ArrayList<>();
        String tbsj = getTBSJ(records);
        CompanyName companyName = createCompanyName(records.get(1));
        CompanyOwner companyOwner = createCompanyOwner(records.get(2));

        if (Objects.isNull(companyName) || Objects.isNull(companyOwner)) {
            badExcel.add(currentExcel());
            return null;
        }

        for (int i = 5; i < records.size(); i++) {
            if (RecordUtil.isUserRecord(records.get(i))) {
                ApplicationUser person = parsePerson(records.get(i));
                person.setCompany(new CompanyInfo(companyName, companyOwner, tbsj));
                result.add(person);
            }
        }
        return result;
    }


    public boolean checkTBSJ(String line) {
        return line.contains("填报时间");
    }

    public boolean checkLines(List<Map<Integer, String>> records) {
        return records.size() < 6;
    }
}
