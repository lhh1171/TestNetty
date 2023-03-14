package org;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class mybatisp {


        public static void main(String[] args) {

            FastAutoGenerator.create("jdbc:mysql://localhost:3306/doudingimooc?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                    "root","root")
                    .globalConfig(builder -> {
                        builder.author("lhh").fileOverride().outputDir("/MyProject/TestNetty/src/main/java");
                    })
                    .packageConfig(builder -> {
                        builder.parent("org")
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                        "/MyProject/TestNetty/src/main/resources/mapper"));
                    })
                    .strategyConfig(builder -> {
                        // 表名
                        builder.addInclude("user");
                    })
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        }


}
