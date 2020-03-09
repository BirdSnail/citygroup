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

    public static CompanyName of(Map<Integer, String> nameRecord) {
        List<String> info = RecordUtil.mapToValueList(nameRecord);
        String name = "";
        String yxdm = "";
        String dz = "";

        String line = String.join("", info).trim();
        final Matcher matcher = COMPANY_NAME.matcher(line);
        if (matcher.matches()) {
            name = matcher.group(1);
            yxdm = matcher.group(2);
            dz = matcher.group(3);
        }

        return new CompanyName(name, yxdm, dz);
    }
}
