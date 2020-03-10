package com.chutianyun.bigdata.parse;

import com.chutianyun.bigdata.model.*;

import java.nio.file.Path;
import java.util.Map;


/**
 * @author BirdSnail
 * @date 2020/3/8
 */
public class NormalParser extends AbstractParser {

    private Path current;

    public NormalParser(Path current) {
        this.current = current;
    }

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
        appUser.setSFZH(foramtSFZH(record.get(2)));
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
    public Path currentExcel() {
        return current;
    }

    @Override
    public CompanyName createCompanyName(Map<Integer, String> record) {
        return CompanyName.of(record);
    }

    @Override
    public CompanyOwner createCompanyOwner(Map<Integer, String> record) {
        return CompanyOwner.of(record);
    }

}
