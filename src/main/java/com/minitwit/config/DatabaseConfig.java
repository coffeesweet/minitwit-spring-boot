package com.minitwit.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * 
 * @author chenchao
 * @date 2016年3月23日 上午11:31:02
 * @version 1.0
 * @throws
 */
@Configuration
public class DatabaseConfig {

  @Bean
  public DataSource dataSource() {

    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    EmbeddedDatabase db =
        builder.setType(EmbeddedDatabaseType.HSQL).addScript("sql/create-db.sql")
            .addScript("sql/insert-data.sql").build();
    return db;
  }

}
