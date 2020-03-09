package com.chutianyun.bigdata.parse;

import com.chutianyun.bigdata.model.ApplicationUser;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 解析excel文件
 */
public interface ExcelParser {

    /**
     *  将一行记录解析为一个申请返岗人员
     *
     * @param record record
     * @return 返岗人员
     */
    ApplicationUser parsePerson(Map<Integer, String> record);

    /**
     * 读取一张excel，获取所有的申请返岗人员信息
     *
     * @param records 所有行
     * @param badExcel 存放不合格的excel
     * @return
     */
    List<ApplicationUser> parser(List<Map<Integer, String>> records, List<Path> badExcel);

    /**
     * 当前文件的绝对路径
     *
     * @return
     */
    Path currentExcel();

}
