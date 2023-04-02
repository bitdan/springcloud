package com.example.mybatis_plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
    //模块包名
//    private static String module = "device";
    private static String module = "";
    private static String moduleName = "product";
    //要生成的表名
    //"t_substation_info","t_airport_info","t_uav_info","t_charging_house_info","t_robot_info","t_camera_info","t_component_info","t_device_info"
    //"t_inspection_route","t_air_route_point","t_air_shooting_point","t_inspection_task_point","t_inspection_task","t_inspection_schedule","t_inspection_result_title","t_inspection_result_detail"
    //"t_uav_state","t_robot_state","t_site_weather","t_terminal_excp"
    //"t_defect_management"
    //"t_ms_org"

    //需要  t_ng_tran_node_relation , t_ng_pole_tower , t_ng_transmission_line_info
    private static String[] tables = {"t_menu"};

    //项目路径
    private static String canonicalPath = "/mybatis_plus";
    //基本包名
    private static String basePackage = "com.example.mybatis_plus";

    //    private static String controllerPackage = "controller" + "." + module;
    private static String controllerPackage = "controller";

    //    private static String entityPackage = "entity" + "." + module;
    private static String entityPackage = "entity";
    //    private static String mapperPackage = "mapper" + "." + module;
    private static String mapperPackage = "mapper";
    //    private static String servicePackage = "service" + "." + module;
    private static String servicePackage = "service";
    //    private static String serviceImplPackage = "service" + "." + module + ".impl";
    private static String serviceImplPackage = "service" + ".impl";
    //作者
    private static String authorName = "";
    //table前缀
    private static String prefix = "t_";
    //    private static String prefix = "";
    //数据库类型
    private static DbType dbType = DbType.MYSQL;
    //数据库配置四要素
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3307/springboot_mybatis?serverTimezone=Asia/Shanghai";
    private static String username = "root";
    private static String password = "root";

    public static void main(String[] args) {

        AutoGenerator gen = new AutoGenerator();

        /**
         * 获取项目路径
         */
        try {
            canonicalPath = new File("").getCanonicalPath() + canonicalPath;
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 数据库配置
         */
        gen.setDataSource(new DataSourceConfig()
                .setDbType(dbType)
                .setDriverName(driverName)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    //@Override
                    //public DbColumnType processTypeConvert(String fieldType) {
                    //System.out.println("转换类型：" + fieldType);
                    // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                    //    return DbColumnType.BOOLEAN;
                    // }
                    //return super.processTypeConvert(fieldType);
                    //}
                }));

        /**
         * 全局配置
         */
        gen.setGlobalConfig(new GlobalConfig()
                .setOutputDir(canonicalPath + "/src/main/java")//输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(false)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setOpen(false)//生成后打开文件夹
                .setAuthor(authorName)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setSwagger2(true)
                .setIdType(IdType.ASSIGN_UUID)
        );

        /**
         * 策略配置
         */
        gen.setStrategy(new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        //.setDbColumnUnderline(true)//全局下划线命名
                        .setTablePrefix(new String[]{prefix})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setColumnNaming(NamingStrategy.underline_to_camel)
                        .setInclude(tables) // 需要生成的表
                        .setRestControllerStyle(false)
                        //.setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
                        //.setSuperEntityColumns(new String[]{"test_id"})
                        //.setTableFillList(tableFillList)
                        // 自定义 mapper 父类 默认BaseMapper
                        //.setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper")
                        // 自定义 service 父类 默认IService
                        // .setSuperServiceClass("cn.com.chgit.oms_wx.common.base.service.IBaseService")
                        // 自定义 service 实现类父类 默认ServiceImpl
                        // .setSuperServiceImplClass("cn.com.chgit.oms_wx.common.base.service.BaseService")
                        // 自定义 controller 父类
                        // .setSuperControllerClass("cn.com.chgit.oms.common.base.controller.CommonController")
                        // 【实体】是否生成字段常量（默认 false）
                        // public static final String ID = "test_id";
                        // .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        // public User setName(String name) {this.name = name; return this;}
                        // .setEntityBuilderModel(true)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)

        );

        /**
         * 包配置
         */
        gen.setPackageInfo(new PackageConfig()
                .setModuleName(moduleName)
                .setParent(basePackage)// 自定义包路径
                .setController(controllerPackage)// 这里是控制器包名，默认 web
                .setEntity(entityPackage) // 设置Entity包名，默认entity
                .setMapper(mapperPackage) // 设置Mapper包名，默认mapper
                .setService(servicePackage) // 设置Service包名，默认service
                .setServiceImpl(serviceImplPackage) // 设置Service Impl包名，默认service.impl
                .setXml("mapper") // 设置Mapper XML包名，默认mapper.xml
        );

        /**
         * 注入自定义配置
         */
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig abc = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        //自定义文件输出位置（非必须）
        List<FileOutConfig> fileOutList = new ArrayList<FileOutConfig>();
        fileOutList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
//                return canonicalPath + "/src/main/resources/mapper/" + module + "/" + tableInfo.getEntityName() + "Mapper.xml";
                return canonicalPath + "/src/main/resources/mapper/" + moduleName + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        abc.setFileOutConfigList(fileOutList);
        gen.setCfg(abc);

        /**
         * 指定模板引擎 默认是VelocityTemplateEngine ，需要引入相关引擎依赖
         */
        //gen.setTemplateEngine(new FreemarkerTemplateEngine());

        /**
         * 模板配置
         */
        gen.setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                // .setController("...");
                // .setEntity("...");
                // .setMapper("...");
                // .setXml("...");
                // .setService("...");
                // .setServiceImpl("...");
        );

        // 执行生成
        gen.execute();
    }

}
