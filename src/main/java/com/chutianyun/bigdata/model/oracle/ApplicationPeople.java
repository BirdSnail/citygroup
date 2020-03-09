package com.chutianyun.bigdata.model.oracle;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 申请返岗人员，使用这个对象方便插入oracle
 *
 * @author BirdSnail
 * @date 2020/3/9
 */
@Setter
@Getter
public class ApplicationPeople {

    /**
     * 填报时间
     */
    private String TBSJ;

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
    private String XZD_GWGZ;

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

    /**
     * 入库时间
     */
//    private String RKSJ;

    /**
     * 入库文件
     */
    private String RKWJ;

    /**
     * 健康证明
     */
//    private String HEALTH_INFO;

    /**
     * 文件目录
     */
    private String WJML;

    /**
     * 文件子目录
     */
    private String WJZML;
}
