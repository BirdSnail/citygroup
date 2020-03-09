package com.chutianyun.bigdata.model;

import com.chutianyun.bigdata.util.RecordUtil;
import lombok.Getter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Getter
public class CompanyOwner {

    /**
     * 企业联系人
     */
    private String QYLXR;

    /**
     * 企业申请返回时间
     */
    private String QYSQFHSJ;

    /**
     * 企业手机
     */
    private String QYSJ;

    public static final Pattern COMPANY_OWNER = Pattern.compile("^联系人([\\s\\S]*)申请返回时间([\\s\\S]*)手机([\\s\\S]*)");
    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public CompanyOwner(String QYLXR, String QYSQFHSJ, String QYSJ) {
        this.QYLXR = QYLXR;
        this.QYSQFHSJ = QYSQFHSJ;
        this.QYSJ = QYSJ;
    }

    public static CompanyOwner of(Map<Integer, String> record) {
        List<String> info = RecordUtil.mapToValueList(record);
        String lxr = "";
        String phoneNum = "";
        String returnTime = "";

        String line = String.join("", info).trim();
        Matcher matcher = COMPANY_OWNER.matcher(line);
        if (matcher.matches()) {
            lxr = matcher.group(1);
            returnTime = matcher.group(2);
            if (returnTime.startsWith("439")) {
                final LocalDate baseYear = LocalDate.ofYearDay(1900, 1);
                returnTime = baseYear.plusDays(Integer.parseInt(returnTime)-2).format(format);
            }
            phoneNum = matcher.group(3);
        }
        return new CompanyOwner(lxr, returnTime, phoneNum);
    }
}
