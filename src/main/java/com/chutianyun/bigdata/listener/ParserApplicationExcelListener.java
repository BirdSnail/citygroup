package com.chutianyun.bigdata.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.chutianyun.bigdata.model.ApplicationFileInfo;
import com.chutianyun.bigdata.model.ApplicationUser;
import com.chutianyun.bigdata.model.ReturnValue;
import com.chutianyun.bigdata.model.UserApplicationOfReturn;
import com.chutianyun.bigdata.parse.ExcelParser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author BirdSnail
 * @date 2020/3/8
 */
public class ParserApplicationExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    private List<Map<Integer, String>> records = new ArrayList<>();
    private ExcelParser parser;
    private List<UserApplicationOfReturn> allApplicationPerson;
    private ApplicationFileInfo fileInfo;
    private List<Path> badExcel;

    public ParserApplicationExcelListener(ExcelParser parser,
                                          List<UserApplicationOfReturn> allApplicationPerson,
                                         List<Path> badExcel,
                                         ApplicationFileInfo fileInfo) {
        this.parser = parser;
        this.allApplicationPerson = allApplicationPerson;
        this.badExcel = badExcel;
        this.fileInfo = fileInfo;
    }

    @Override
    public void invoke(Map<Integer, String> map, AnalysisContext analysisContext) {
//        System.out.println(map);
        records.add(map);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<ApplicationUser> people = this.parser.parser(records, badExcel);

        if (Objects.isNull(people)) {
            return;
        }

        System.out.println(new ReturnValue(people.size(), fileInfo.getCurrentPath()));

        for (ApplicationUser person : people) {
            allApplicationPerson.add(new UserApplicationOfReturn(fileInfo, person));
        }
    }
}
