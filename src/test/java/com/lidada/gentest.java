package com.lidada;


import cn.com.LidadaApplication;
import cn.com.utils.UUIDUtil;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.*;
import org.beetl.sql.core.kit.*;
import org.beetl.sql.ext.gen.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Set;
import java.util.UUID;

/**
 * @author Lidada
 * @date 2019/10/29 17:25
 * @Meaning 仰首攀南斗, 翻身倚北辰, 举头天外望, 无我这般人。
 * @Version 1.0
 * @Description ：
 */
@SpringBootTest(classes = LidadaApplication.class)
@RunWith(SpringRunner.class)
public class gentest {

    // ========数据库配置=========
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://127.0.0.1:3306/pgzs?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static String userName = "root";
    private static String password = "root";
    // ========模板的路径, 示例是spring boot的[src/main/resources/beetlsqlTemplate 文件夹]=========
    private static String templatePath = "/beetlsqlTemplate";
    // ========md生成路径 要提前创建=========
    private static String mdPath = "/sql";
    // ========生成实体类所在的包=========
    private static String pojoPkg = "cn.com.entity.admin";
    // ========生成mapper类所在的包=========
    private static String mapperPkg = "cn.com.dao.admin";

    /**
     * 入口
     */
    /*public static void main(String[] args) throws Exception {
        genAll();
    }*/

    @Test
    public  void genAll() throws Exception {
        //准备工作
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        SQLLoader loader = new ClasspathLoader(mdPath);
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, null);

        GenConfig config = new GenConfig();
        config.setDisplay(false);
        config.setPreferBigDecimal(true);

        System.out.println("======生成代码======");
        Set<String> tables = sqlManager.getMetaDataManager().allTable();
        /*for (String table : tables) {
            System.out.printf("%-20s %s\n",table , "生成完毕");
            //默认生成实体类的实现
            sqlManager.genPojoCode(table, pojoPkg, config);
            //自定义实现
            genMd(sqlManager, config, table);
            //自定义实现
            genMapper(sqlManager, config, table);
        }*/
        //默认生成实体类的实现
        sqlManager.genPojoCode("wiki", pojoPkg, config);
        //自定义实现
        genMd(sqlManager, config, "wiki");
        //自定义实现
        genMapper(sqlManager, config, "wiki");
        System.out.println("=====生成完毕=====");
    }

    /**
     * 生成md文件
     */
    public static void genMd(SQLManager sqlManager, GenConfig config, String table) throws IOException {
        String fileName = StringKit.toLowerCaseFirstOne(sqlManager.getNc().getClassName(table));
        if (config.getIgnorePrefix() != null && !config.getIgnorePrefix().trim().equals("")) {
            fileName = fileName.replaceFirst(StringKit.toLowerCaseFirstOne(config.getIgnorePrefix()), "");
            fileName = StringKit.toLowerCaseFirstOne(fileName);
        }
        String target = GenKit.getJavaResourcePath() + "/" + mdPath + "/" + fileName + ".md";
        TableDesc desc = sqlManager.getMetaDataManager().getTable(table);
        FileWriter writer = new FileWriter(new File(target));
        MDCodeGen mdCodeGen = new MDCodeGen();
        mdCodeGen.setMapperTemplate(config.getTemplate(templatePath + "/md.btl"));
        mdCodeGen.genCode(sqlManager.getBeetl(), desc, sqlManager.getNc(), null, writer);
        writer.close();
    }

    /**
     * 生成mapper
     */
    public static void genMapper(SQLManager sqlManager, GenConfig config, String table) {
        MapperCodeGen mapperCodeGen = new MapperCodeGen(mapperPkg);
        mapperCodeGen.setMapperTemplate(config.getTemplate(templatePath + "/mapper.btl"));
        mapperCodeGen.genCode(pojoPkg, sqlManager.getNc().getClassName(table), sqlManager.getMetaDataManager().getTable(table), null, false);
    }

    @Test
    public void test2(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        System.out.println(replace);
    }
}

