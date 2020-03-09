package com.chutianyun.bigdata.model;

import com.chutianyun.bigdata.util.RecordUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Data
public class CompanyName {

    /**
     * 企业名称
     */
    private String QYMC;

    /**
     * 企业统一社会信用代码
     */
    private String QYTYSHXYDM;

    /**
     * 企业详细地址
     */
    private String QYXXDZ;

    public CompanyName(String QYMC, String QYTYSHXYDM, String QYXXDZ) {
        this.QYMC = QYMC;
        this.QYTYSHXYDM = QYTYSHXYDM;
        this.QYXXDZ = QYXXDZ;
    }

    private static final Pattern COMPANY_NAME = Pattern.compile("^企业名称([\\s\\S]*)机构代码([\\s\\S]*)详细地址([\\s\\S]*)");
    private static final Pattern COMPANY_NAME_YC = Pattern.compile("^企业名称([\\s\\S]*)统一社会信用代码([\\s\\S]*)详细地址([\\s\\S]*)");


    public static CompanyName of(Map<Integer, String> nameRecord) {
        return create(nameRecord, COMPANY_NAME);
    }

    /**针对宜昌的申请*/
    public static CompanyName ofYC(Map<Integer, String> nameRecord) {
        CompanyName name =create(nameRecord, COMPANY_NAME_YC);
        if (name == null) {
            name = create(nameRecord, COMPANY_NAME);
        }
        return name;
    }


    private static CompanyName create(Map<Integer, String> nameRecord, Pattern pattern) {
        String name = "";
        String yxdm = "";
        String dz = "";

        String line = RecordUtil.recordMapToStr(nameRecord);
        final Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            name = matcher.group(1);
            yxdm = matcher.group(2);
            dz = matcher.group(3);
            return new CompanyName(name, yxdm, dz);
        } else {
            return null;
        }
    }
}
