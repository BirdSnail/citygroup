package com.chutianyun.bigdata.mybatis;

import com.chutianyun.bigdata.model.UserApplicationOfReturn;
import com.chutianyun.bigdata.model.oracle.ApplicationPeople;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author BirdSnail
 * @date 2020/3/9
 */
public class OracleOperator {

    public static void batchInsert(List<ApplicationPeople> people) throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            System.out.println("获取一个oracle session成功");
            OracleMapper mapper = session.getMapper(OracleMapper.class);
            mapper.batchInsert(people);
            session.commit();
        }
    }

}
