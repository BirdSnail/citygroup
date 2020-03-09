package com.chutianyun.bigdata.model;

import lombok.Data;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
@Data
public class ApplicationUser {

    /**
     * 序号
     */
    private String XH;

    /**
     * 姓名
     */
    private String XM;

    /**
     * 身份证号
     */
    private String SFZH;

    /**
     * 现住地——市州
     */
    private String XZD_SZ;

    /**
     * 现住地-县市区
     */
    private String XZD_XSQ;

    /**
     * 现住地-街办
     */
//    private String XZD_JB;

    /**
     * 现住地-社区
     */
//    private String XZD_SQ;

    /**
     * 现住地-详细地址
     */
    private String XZD_XXDZ;

    /**
     * 手机
     */
    private String XZD_SJ;

    /**
     * 岗位（工种)
     */
    private String XZD_GW;

    /**
     * 驾驶员/返岗人员
     */
    private String JSY_FGRY;

    /**
     * 车辆牌照号
     */
    private String CLPZH;

    /**
     * 企业所在地县指挥部审核意见
     */
    private String QYSZDXZHBSHYJ;

    /**
     * 现居留地县指挥部健康证明
     */
    private String XJLDXZHBJKZM;

    private CompanyInfo company;
}
