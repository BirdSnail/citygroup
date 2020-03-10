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

import static com.chutianyun.bigdata.util.RecordUtil.mapToValueList;

/**
 * 宜昌的解析器
 *
 * @author BirdSnail
 * @date 2020/3/9
 */
public class YCParser extends AbstractParser{

    private Path current;

    public YCParser(Path current ) {
        this.current = current;
    }

    @Override
    public ApplicationUser parsePerson(Map<Integer, String> record) {
        ApplicationUser appUser = new ApplicationUser();

        appUser.setXH(record.get(0));
        appUser.setXM(record.get(1));
        appUser.setSFZH(foramtSFZH(record.get(2)));
        appUser.setXZD_SZ(record.get(3));
        appUser.setXZD_XSQ(record.get(4));
        appUser.setXZD_XXDZ(record.get(7));
        appUser.setXZD_SJ(record.get(8));
        appUser.setXZD_GW(record.get(9));
        appUser.setJSY_FGRY(record.get(10));
        appUser.setCLPZH(record.get(11));
        appUser.setQYSZDXZHBSHYJ(record.get(12));
        appUser.setXJLDXZHBJKZM(record.get(13));

        return appUser;
    }


    @Override
    public Path currentExcel() {
        return current;
    }

    @Override
    public CompanyName createCompanyName(Map<Integer, String> record) {
        return CompanyName.ofYC(record);
    }

    @Override
    public CompanyOwner createCompanyOwner(Map<Integer, String> record) {
        return CompanyOwner.of(record);
    }
}
