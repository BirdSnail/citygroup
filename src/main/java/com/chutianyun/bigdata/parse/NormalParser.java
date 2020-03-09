package com.chutianyun.bigdata.parse;

import com.chutianyun.bigdata.model.*;
import com.chutianyun.bigdata.util.RecordUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
public class NormalParser implements ExcelParser{

    /**
     * 正常模式下解析返岗申请表
     * 这次解析不会设置申请人属公司
     *
     * @param record record
     * @return 申请返岗人员
     */
    @Override
    public ApplicationUser parsePerson(Map<Integer, String> record) {
        ApplicationUser appUser = new ApplicationUser();

        appUser.setXH(record.get(0));
        appUser.setXM(record.get(1));
        appUser.setSFZH(record.get(2));
        appUser.setXZD_SZ(record.get(3));
        appUser.setXZD_XSQ(record.get(4));
        appUser.setXZD_XXDZ(record.get(5));
        appUser.setXZD_SJ(record.get(6));
        appUser.setXZD_GW(record.get(7));
        appUser.setJSY_FGRY(record.get(8));
        appUser.setCLPZH(record.get(9));
        appUser.setQYSZDXZHBSHYJ(record.get(10));
        appUser.setXJLDXZHBJKZM(record.get(11));

        return appUser;

    }


    @Override
    public List<ApplicationUser> parser(List<Map<Integer, String>> records) {
        if (records.size() < 6) {
            throw new IllegalStateException();
        }

        List<ApplicationUser> result = new ArrayList<>();

        String tbsj = RecordUtil.getTBSJ(records.get(0));
        CompanyName companyName = CompanyName.of(records.get(1));
        CompanyOwner companyOwner = CompanyOwner.of(records.get(2));

        for (int i = 5; i < records.size(); i++) {
            if (RecordUtil.isUserRecord(records.get(i))) {
                ApplicationUser person = parsePerson(records.get(i));
                person.setCompany(new CompanyInfo(companyName, companyOwner, tbsj));
                result.add(person);
            }
        }

        return result;
    }
}
