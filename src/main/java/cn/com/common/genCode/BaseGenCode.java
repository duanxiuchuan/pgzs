package cn.com.common.genCode;

import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.TableDesc;
import org.beetl.sql.core.kit.GenKit;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MDCodeGen;
import org.beetl.sql.ext.gen.MapperCodeGen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
/**
 * @author Lidada
 * @date 2019/10/29 17:26
 * @Meaning 仰首攀南斗, 翻身倚北辰, 举头天外望, 无我这般人。
 * @Version 1.0
 * @Description ：
 */
public class BaseGenCode {


    /**
     * 生成dao代码.
     */
    // ========数据库配置=========
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/hospitalcosmetology?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static String userName="root";
    private static String password ="19921107";
    // ========模板的路径, 示例是spring boot的[src/main/resources/beetlsqlTemplate 文件夹]=========
    private static String templatePath = "/beetlsqlTemplate";
    // ========md生成路径 要提前创建=========
    private static String mdPath = "/sql";
    // ========生成实体类所在的包=========
    private static String pojoPkg = "cn.com.entity";
    // ========生成mapper类所在的包=========
    private static String mapperPkg = "cn.com.dao";
    /**
     * 入口
     */

    public static void main(String[] args) throws Exception {
        genAll();
    }

    public static void genAll() throws Exception {
        //准备工作
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        SQLLoader loader = new ClasspathLoader(mdPath);
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, null);

        GenConfig config = new GenConfig(templatePath + "/pojo.btl");
        config.setDisplay(false);
        config.setPreferBigDecimal(true);
        config.setBaseClass("cn.com.entity.base.BaseEntity");
        System.out.println("======生成代码======");
        Set<String> tables = sqlManager.getMetaDataManager().allTable();

        for (String table : tables) {
                if(table.equals("wechat_menu")){
                System.out.printf("%-20s %s\n",table , "生成完毕");
                //默认生成实体类的实现
                sqlManager.genPojoCode(table, pojoPkg, config);
                //自定义实现
//                genMd(sqlManager, config, table);
                //自定义实现
//                genMapper(sqlManager, config, table);
            }
        }

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
}
