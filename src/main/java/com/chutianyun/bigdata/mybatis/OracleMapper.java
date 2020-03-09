package com.chutianyun.bigdata.mybatis;

import com.chutianyun.bigdata.model.UserApplicationOfReturn;
import com.chutianyun.bigdata.model.oracle.ApplicationPeople;

import java.util.List;

/**
 * @author BirdSnail
 * @date 2020/3/9
 */
public interface OracleMapper {

   int batchInsert(List<ApplicationPeople> people);
}
