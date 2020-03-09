package com.chutianyun.bigdata.model;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Data
public class CompanyInfo {

    /**
     * 填报时间
     */
    private String TBSJ;

    private CompanyName name;

    private CompanyOwner owner;

    public CompanyInfo(CompanyName name, CompanyOwner owner, String TBSJ) {
        this.name = name;
        this.owner = owner;
        this.TBSJ = TBSJ;
    }
}
