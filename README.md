# 学习任务
* 了解mysql索引
* 学习springcloud整合eureka、openFeign、RestTemplate、mybatis
    * eureka练习所用主要微服务:
        > 其中 `eureka-server` `eureka-server-7002` 模拟微服务集群环境
        > 其中 `eureka-payment` `eureka-payment8004` 模拟微服务与负载均衡
        * server端: `eureka-server` `eureka-server-7002`
        * client端: 
            * 服务提供者: 
                > `eureka-provider` `eureka-payment` `eureka-payment8004`
            * 服务消费者: `eureka-customer`
    * openFeign & RestTemplate练习所用微服务: `eureka-customer`
    * mybatis的crud
        > 均在服务提供者练习使用
* mybatis使用动态sql实现批量添加数据的练习
    > 在服务提供者`eureka-provider` dao/EmployeeMapper.insertBatch()
* 使用mybatis-generator自动生成entity、mapper、*mapper.xml
    > 在服务提供者`eureka-provider` 中练习使用

# 学习笔记
# 一、SQL高级-索引

## 1.索引是什么

> 排好序的快速查找数据结构，一般所说的索引是B树索引，聚集索引、复合索引、前缀索引、唯一索引使用的都是B+树索引，还有其他的索引类型，比如哈希索引等

## 2.影响范围

>  where & order by，影响查找和排序的速度

## 3.特点：

> 本身亦很大，一般不全部存储在内存中，往往是以索引文件的方式存储在磁盘上面

## 4.优势：

* 类似大学图书馆的目录结构，提高了检索效率，降低了数据库IO成本
* 通过索引对数据进行排序，降低数据排序成本，降低了CPU的消耗

## 5.劣势：

* 索引实质上也是一张表，保存了主键与索引字段，并指向实体表的记录，索引也是要占用空间的
* 虽然索引可以提高查询的速度，但是会降低更新表速度，如对表进行INSERT,UPDATE,DELETE，因为表在更新时，不仅数据发生了变化，而且需要更新索引信息（添加了索引列的字段）

## 6.索引分类

* 单值索引

  > 一个索引只包含一个字段，一张表可以有多个单值索引

* 唯一索引

  > 索引列的值必须唯一，但是可以为空，NULL可以出现多次

* 复合索引

  > 一个索引包含多个列

* 基本语法

  * 创建

    * CREATE [UNIQUE] INDEX indexName ON tableName(columnName(length)...);
    * ALTER tableName ADD indexName ON (columnName(length)...);

  * 删除

    * DROP INDEX [indexName] ON tableName;

  * 查看索引

    * SHOW INDEX FROM tableName\G;

  * 使用ALERT添加索引

    ```sql
    # 主键索引
    ALERT TABLE table_name ADD PRIMARY KEY(columnList)
    # 唯一索引
    ALERT TABLE table_name ADD UNIQUE indexName (columnList)
    # 普通索引
    ALERT TABLE table_name ADD INDEX indexName (columnList)
    # 全文索引
    ALERT TABLE table_name ADD FULLTEXT indexName (columnList)
    ```

## 7.索引结构

* BTree索引（重点）
* Hash索引
* FULLTEXT全文索引
* R-Tree索引

## 8.使用场景

* 主键自动建立唯一索引
* 频繁作为查询条件的字段
* 查询中与其他表关联的索引，外键应该建立索引
* 频繁更新的字段不应该建立索引
* where条件里用不到的字段不应该建立索引
* 单键/组合索引的选择问题，在高并发情况下倾向建立组合索引
* 查询中排序的字段，排序字段若通过索引去访问将会大大提高排序速度
* 查询中排序或分组的字段

## 9.不建立索引的场景

* 表的记录太少

* 经常增删改的表

* 字段值重复数据量高

  > k=不重复的字段值/字段值总量，k越接近于1，建立的索引意义越大
  >
  > 例：表中有2000条数据，某字段有1980条不重复，则k值为1980/2000=0.99

## 10.性能分析

* mysql query optimizer

* Mysql常见瓶颈(CPU,IO,hardware)

* Explain

  * 是什么

    > 执行计划

  * 怎么用

    > explain + SQL语句

    * 执行计划结果表头

      > id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra

    * 各字段解释

      * id 分三种情况

        * id相同，按顺序从上至下执行

          * SQL:`explain select e.* from employees e,jobs j,departments d where e.job_id = j.job_id and e.department_id = d.department_id;`

          * explain result

            | id   | select_type | tables | ...  |
            | ---- | ----------- | ------ | ---- |
            | 1    | SIMPLE      | d      | ...  |
            | 1    | SIMPLE      | e      | ...  |
            | 1    | SIMPLE      | j      | ...  |

        * id不同，id越大越先被执行**(<drived>衍生虚拟表,其中数字代表id列的值)**

          * SQL:`explain ...`

          * explain result

            | id   | select_type | tables    | ...  |
            | ---- | ----------- | --------- | ---- |
            | 1    | PRIMARY     | <drived2> | ...  |
            | 3    | SUBQUERY    | <drived2> | ...  |
            | 4    | DERIVED     | e         | ...  |
            | 2    | DERIVED     | e         | ...  |

        * id相同和id不同同时存在，执行顺序延顺以上两条

          * SQL:`...`

          * explain result

            | id   | select_type | tables    | ...  |
            | ---- | ----------- | --------- | ---- |
            | 1    | PRIMARY     | <drived3> | ...  |
            | 1    | PRIMARY     | d         | ...  |
            | 4    | SUBQUERY    | <drived5> | ...  |
            | 5    | DERIVED     | e         | ...  |
            | 3    | DERIVED     | e         |      |

      * select_type 查询类型

        * SIMPLE
          * 没有unique和子查询的简单查询
        * PRIMARY
          * 主查询，在嵌套了子查询的查询中的最外层的查询
        * SUNQUERY
          * 子查询
        * DERIVED
          * 虚拟衍生表，在from中嵌套的子查询会生成一张临时表
        * union
          * 若第二个select子句出现在union后，被标记为union
          * 若union包含在from子句的子查询中，外层select被包含为derived
        * union result
          * 从union表select获取的结果

      * type(常见的:system>const>eq_ref>range>index>ALL)

        * ALL 表明是全表扫描

        * system 

          > 表中只有一条记录，相当于系统表,const的特殊情况

        * const

          > 表时通过索引一次就能找到记录，const用于比较pk&unique，因为只匹配一行数据，所以很快；如将主键置于where中，MYSQL会将该查询转换为常量

        * eq_ref

          > 唯一性索引扫描器，对于一个索引键而言，表中只有一条记录与之匹配，常见于主键会唯一索引扫描

        * ref

          > 非唯一性索引，返回匹配某个单独值的所有记录。

        * range

          > 只检索给定范围的值，选择一个索引来选择行。

        * index

          > FULL INDEX SCAN，全索引扫描

      * `possible_keys` 

        * 显示可能会被查询使用到的索引，一个和多个
        * 查询涉及到的字段上存在索引则列出，但不一定被查询实际使用

      * `key`

        * 实际用到的索引，如果为NULL，则没有使用索引或没有索引
        * 查询中若使用了**覆盖索引**(查询的字段与索引字段完全吻合)，仅出现在key列表中

      * `key_len`

        * 表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。在不损失精度的情况下，长度越小越好。
        * key_len显示的值并不是实际的使用长度，只是索引长度的最大可能长度，根据定义计算得出，并非表中查询所得。

  * 能做什么

    * 表的读取顺序
    * 数据读取操作的操作类型
    * 哪些索引可以被使用
    * 哪些索引被实际使用
    * 表之间的引用
    * 每张表有多少行被优化器查询

# SpringBoot学习笔记

## 1.pom文件解析

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

* `spring-boot-starter-parent` 维护其他依赖的版本，亦称`版本仲裁中心`，不在其中的依赖还是需要声明版本号

## 2.springboot整合mybatis

* 引入依赖

  ```xml
  ...
      <dependency>
          <groupId>org.mybatis.spring.boot</groupId>
          <artifactId>mybatis-spring-boot-starter</artifactId>
          <version>2.2.0</version>
      </dependency>
  ...
  ```

* 编写mybatis-config.xml全局文件(不建议编写mybatis的全局配置文件，配置在mybatis.configuration中)

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
  </configuration>
  ```

  

* 在application.yaml中声明全局配置文件的位置以及mapper文件的位置

  ```yaml
  ...
  #配置mybatis全局配置以及mapper文件的位置
  mybatis:
    config-location: classpath:mybatis/mybatis-config.xml
    mapper-locations: clsspath:mybatis/mapper/*.xml
  ```

## 3.mybatis-generator的使用

* 导入pom依赖

  ```xml
  <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.5</version>
  </dependency>
  ```

* 添加构建参数

  ```xml
  <build>
      ...
          <plugin>
              <groupId>org.mybatis.generator</groupId>
              <artifactId>mybatis-generator-maven-plugin</artifactId>
              <version>1.3.2</version>
              <configuration>
                  <!--mybatis generator插件配置文件位置，默认值${basedir}/src/main/resources/generatorConfig.xml-->
                				<configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
                  <overwrite>true</overwrite>
                  <!-- 是否打印进度日志信息 -->
                  <verbose>true</verbose>
              </configuration>
          </plugin>
      </plugins>
  </build>
  ```

* 在resources下创建`generatorConfig.xml`&`generator.properties`

  * generatorConfig.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE generatorConfiguration
            PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
            "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
    
    <generatorConfiguration>
        <!--参数配置文件位置-->
        <properties resource="generator/generator.properties"/>
        <!--驱动jar-->
        <classPathEntry location="${classPathEntry}"/>
    
        <context id="OracleTables" targetRuntime="MyBatis3">
            <jdbcConnection driverClass="${driverClass}" connectionURL="${connectionURL}" userId="${userId}" password="${password}">
            </jdbcConnection>
    
            <javaTypeResolver>
                <property name="forceBigDecimals" value="false"/>
            </javaTypeResolver>
    
            <javaModelGenerator targetPackage="${modelTargetPackage}" targetProject="src/main/java">
                <property name="enableSubPackages" value="true"/>
                <property name="trimStrings" value="true"/>
            </javaModelGenerator>
    
            <sqlMapGenerator targetPackage="${sqlMapTargetPackage}" targetProject="src/main/resources">
                <property name="enableSubPackages" value="true"/>
            </sqlMapGenerator>
    
            <javaClientGenerator type="XMLMAPPER" targetPackage="${javaClientTargetPackage}" targetProject="src/main/java">
                <property name="enableSubPackages" value="true"/>
            </javaClientGenerator>
    
            <table tableName="${tableName1}" domainObjectName="${domainObjectName1}">
            </table>
            <table tableName="${tableName2}" domainObjectName="${domainObjectName2}">
            </table>
            <table tableName="${tableName3}" domainObjectName="${domainObjectName3}">
            </table>
            <table tableName="${tableName4}" domainObjectName="${domainObjectName4}">
            </table>
            <table tableName="${tableName5}" domainObjectName="${domainObjectName5}">
            </table>
            <table tableName="${tableName6}" domainObjectName="${domainObjectName6}">
            </table>
            <table tableName="${tableName7}" domainObjectName="${domainObjectName7}">
            </table>
            <table tableName="${tableName8}" domainObjectName="${domainObjectName8}">
            </table>
    
    
        </context>
    </generatorConfiguration>
    ```

    

  * generator.properties

    ```properties
    #数据库驱动包在磁盘上的绝对路径classPathEntry=C:/Users/Administrator/.m2/repository/mysql/mysql-connector-java/8.0.25/mysql-connector-java-8.0.25.jar#配置驱动连接信息driverClass=com.mysql.cj.jdbc.DriverconnectionURL=jdbc:mysql://127.0.0.1:3306/springboot-mybatis?serverTimezone=UTC&characterEncoding=utf-8userId=rootpassword=root# 自动生成的实体类的存放位置modelTargetPackage=com.example.springbootmybatis.entity# 自动生成的mapping文件的存放位置sqlMapTargetPackage=mybatis/mapper# 自动生成的mapper类的存放位置javaClientTargetPackage=com.example.springbootmybatis.dao# 哪个用户（数据库）的表schema=springboot-mybatistableName1=employeesdomainObjectName1=EmployeetableName2=jobsdomainObjectName2=JobtableName3=departmentsdomainObjectName3=DepartmenttableName4=locationsdomainObjectName4=LocationtableName5=regionsdomainObjectName5=RegiontableName6=countriesdomainObjectName6=CountrytableName7=job_gradesdomainObjectName7=JobGradetableName8=job_historydomainObjectName8=JobHistory
    ```

## 4.整合swagger2

* 在pom.xml中引入依赖

  ```xml
  <!--swagger2--><dependency>    <groupId>io.springfox</groupId>    <artifactId>springfox-swagger2</artifactId>    <version>2.2.2</version></dependency><dependency>    <groupId>io.springfox</groupId>    <artifactId>springfox-swagger-ui</artifactId>    <version>2.2.2</version></dependency>
  ```

* 编写配置类扫描controller包

  ```java
  package com.example.eurekaprovider.config;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import springfox.documentation.builders.ApiInfoBuilder;import springfox.documentation.builders.PathSelectors;import springfox.documentation.builders.RequestHandlerSelectors;import springfox.documentation.service.Contact;import springfox.documentation.spi.DocumentationType;import springfox.documentation.spring.web.plugins.Docket;import springfox.documentation.swagger2.annotations.EnableSwagger2;/** * @author: guoqing.chen01@hand-china.com 2021-07-08 19:45 **/@Configuration@EnableSwagger2public class SwaggerConfig {    @Bean    public Docket createRestApi() {        return new Docket(DocumentationType.SWAGGER_2)                .pathMapping("/")                .select()                .apis(RequestHandlerSelectors.basePackage("com.example.eurekaprovider.controller"))                .paths(PathSelectors.any())                .build().apiInfo(new ApiInfoBuilder()                        .title("SpringBoot整合Swagger")                        .description("SpringBoot整合Swagger，详细信息......")                        .version("9.0")                        .contact(new Contact("Guoqing.Chen","","guoqing.chen01@hand-china.com"))                        .license("The Apache License")                        .licenseUrl("http://www.baidu.com")                        .build());    }}
  ```

  

* 使用注解在controller上标明api信息

  ```
  @Api(tags = "员工管理相关接口")@RestControllerclass	@ApiOperation("查询所有员工的接口")	@ApiImplicitParams(            @ApiImplicitParam(name = "id",value = "员工编号",defaultValue = "110",required = true)            ...    )	function
  ```

## 5.使用druid数据源

* 引入pom依赖

  ```xml
  <dependency>    <groupId>com.alibaba</groupId>    <artifactId>druid</artifactId>    <version>1.1.8</version></dependency>
  ```

  

* 在application.yaml中配置相关参数

  ```yaml
  spring:  datasource:    url: jdbc:mysql://127.0.0.1:3306/datasourcetest?serverTimezone=UTC&characterEncoding=utf-8    username: root    password: root    driver-class-name: com.mysql.cj.jdbc.Driver    # druid配置，配合Druid配置类使用    type: com.alibaba.druid.pool.DruidDataSource	# 可选参数    initialSize: 5    minIdle: 5    maxActive: 20    maxWait: 60000    timeBetweenEvictionRunMillis: 60000    minEvicatableIdleTimeMillis: 300000    validationQuery: SELECT 1 FROM DUAL    testWhileIdle: true    testOnBorrow: false    testOnReturn: false    poolPreparedStatement: true    filters: stat,wall,log4j    maxPoolPreparedStatementPerConnectionSize: 20    useGlobalDataSourceStat: true    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
  ```

* 编写druid配置类

  ```java
  package com.example.test1.config;import com.alibaba.druid.pool.DruidDataSource;import com.alibaba.druid.support.http.StatViewServlet;import com.alibaba.druid.support.http.WebStatFilter;import org.springframework.boot.context.properties.ConfigurationProperties;import org.springframework.boot.web.servlet.FilterRegistrationBean;import org.springframework.boot.web.servlet.ServletRegistrationBean;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import javax.sql.DataSource;import java.util.Arrays;import java.util.HashMap;import java.util.Map;/** * @author: guoqing.chen01@hand-china.com 2021-07-12 18:25 **/@Configurationpublic class DruidConfig {    /**     * 扫描application,配置spring.datasource下的参数     * @return     */    @ConfigurationProperties(prefix = "spring.datasource")    @Bean    public DataSource druid() {        return new DruidDataSource();    }    /**     * 配置Druid监控     * @return     */    @Bean    public ServletRegistrationBean<StatViewServlet> statViewServlet() {        ServletRegistrationBean bean = new ServletRegistrationBean(                new StatViewServlet(), "/druid/*");        Map<String, String> param = new HashMap<>();        param.put("loginUsername","admin");        param.put("loginPassword","admin");        param.put("allow","");        param.put("deny","10.211.169.235");        bean.setInitParameters(param);        return bean;    }    /**     * 配置web监控的filter     * @return     */    @Bean    public FilterRegistrationBean<WebStatFilter> webStatFilter() {        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();        bean.setFilter(new WebStatFilter());        Map<String, String> param = new HashMap<>();        param.put("exclusions","*.js,*.css,/druid/*");        bean.setInitParameters(param);        bean.setUrlPatterns(Arrays.asList("/*"));        return bean;    }}
  ```


## 6.注册中心

### 1.服务端

* 在pom.xml中引入依赖

  ```xml
  <dependency>    <groupId>org.springframework.cloud</groupId>    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>    <version>2.0.2.RELEASE</version></dependency>
  ```

* 在启动配置文件application.yaml中配置相关信息

  ```yaml
  spring:  application:  # 应用名称    name: eurekaServer7001.com    # spring-security,在登入注册中心或注册中心注册到注册中心需要验证  security:    user:      name: admin      password: adminserver:  port: 7001eureka:  client:  	# 是否注册到注册中心，作为服务端不需要    registerWithEureka: false    fetchRegistry: false    serviceUrl:      # 集群配置，指向其他eureka服务器      defaultZone: http://admin:admin@eurekaServer7002.com:7002/eureka/      # 单机配置，指向自己      # defaultZone: http://eurekaServer7001.com:7001/eureka/  server:    # 关闭自我保护    enable-self-preservation: false    eviction-interval-timer-in-ms: 2000
  ```

* 在Application.java启动类加入注解`@EnableEurekaServer`

  ```java
  @EnableEurekaServer@SpringBootApplicationpublic class EurekaServerApplication {	public static void main(String[] args) {		SpringApplication.run(EurekaServerApplication.class, args);	}}
  ```

### 2.客户端

* 在pom.xml中引入依赖

  ```xml
  <dependency>    <groupId>org.springframework.cloud</groupId>    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>    <version>2.0.2.RELEASE</version></dependency>
  ```

* 在启动配置文件application.yaml中配置相关信息

  ```yaml
  server:  port: 8081spring:  application:    name: provider  datasource:    url: jdbc:mysql://127.0.0.1:3306/springboot-mybatis?serverTimezone=UTC&characterEncoding=utf-8    username: root    password: root    driver-class-name: com.mysql.cj.jdbc.Driver    type: com.alibaba.druid.pool.DruidDataSource    ...eureka:  client:    service-url:      defaultZone: http://admin:admin@eurekaServer7001.com:7001/eureka/,http://admin:admin@eurekaServer7002.com:7002/eureka/  instance:    prefer-ip-address: true    instance-id: ${spring.application.name}-${spring.cloud.client.ip-address}:${server.port}mybatis:  mapper-locations: classpath:mybatis/mapper/*.xml  configuration:    map-underscore-to-camel-case: true  type-aliases-package: com.example.eurekaprovider.entity
  ```

* 在Application.java启动类加入注解`@EnableEurekaServer`

  ```java
  @SpringBootApplication@EnableEurekaClientpublic class EurekaProviderApplication {    public static void main(String[] args) {        SpringApplication.run(EurekaProviderApplication.class,args);    }}
  ```

## 7.集成security

* pom.xml

  ```xml
  <!-- HTTP basic用户认证模块 --><dependency>    <groupId>org.springframework.boot</groupId>    <artifactId>spring-boot-starter-security</artifactId></dependency>
  ```

  

* application.yaml

  ```yaml
  spring:  application:    name: eurekaServer7001.com  security:    user:      name: admin      password: admin
  ```

  

* application.java

  ```java
  @Configuration@EnableWebSecuritypublic class SecurityConfig extends WebSecurityConfigurerAdapter {    @Override    protected void configure(HttpSecurity http) throws Exception {        // 关闭csrf        http.csrf().disable();        // 支持httpBasic        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();    }}
  ```

  

## 8.集成openFeign实现服务的远程调用

* 引入依赖

  ```xml
  <dependency>    <groupId>org.springframework.cloud</groupId>    <artifactId>spring-cloud-starter-openfeign</artifactId>    <version>2.0.2.RELEASE</version></dependency>
  ```

* 在远程调用客户端（调用端）加入注解@EnableFeignClients，并定义参数`basePackages`，用于扫描使用了Feign注解的包

  > org:`@EnableFeignClients(basePackages = "com.example.eurekacustomer.api")`

* 定义接口文件

  ```java
  /** * 声明这是一个Feign客户端，用于远程调用其他服务 * value参数定义调用的目标服务名 * configuration参数声明Feign的配置类 */@FeignClient(value = "provider",configuration=FeignConfig.class)public interface UserRemoteClient {    @GetMapping("/employee/findById/{id}")    Object hello(@PathVariable Long id);}
  ```

* 在controller中

  ```java
  @RestController@RequestMapping("/customer/payment")public class HelloController {    private final Logger logger = LoggerFactory.getLogger(HelloController.class);    private final static String PAYMENT_URL = "http://PAYMENT";    private final static String EMPLOYEE_URL = "http://localhost:8081/employee";    //自动注入声明的Feign客户端接口    @Autowired    UserRemoteClient userRemoteClient;    //使用restTemplate实现远程调用get请求接口    @Resource    RestTemplate restTemplate;    @GetMapping("/get/{id}")    @ResponseBody    public CommonResult<Object> getOne(@PathVariable Long id) {        CommonResult<Object> result = restTemplate.getForObject(PAYMENT_URL+"/payment/findById/"+id, CommonResult.class);        return result;    }    //使用restTemplate实现远程调用post请求接口    @GetMapping("/create")    @ResponseBody    public CommonResult<Object> createOne(Object object) {        CommonResult<Object> result = restTemplate.postForObject(PAYMENT_URL+"/create/",object, CommonResult.class);        return result;    }    //使用Feign实现远程调用post请求接口    @GetMapping("/callHello/{id}")    public Object callHello(@PathVariable Long id) {        //return restTemplate.getForObject("http://localhost:8083/house/hello",String.class);        //String result = restTemplate.getForObject("http://eureka-client-user-service/user/hello",String.class);        Object result = userRemoteClient.hello(id);        logger.info("调用结果：{}",result);        return result;    }}
  ```

* 编写配置文件FeignConfig.java，自定义Feign配置，实现打印完整日志（配置失败）

  ```java
  @Configurationpublic class FeignConfig {    /**     * 定义Feign的日志级别     * @return     */    @Bean    Logger.Level getLoggerLevel() {        return Logger.Level.FULL;    }}
  ```

  







# 踩坑记录

## 1.Unable to start embedded(内嵌) Tomcat

* 详细错误信息

  ```text
  org.springframework.context.ApplicationContextException: Unable to start web server; nested exception is org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat...Caused by: org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat...Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'traceFilterRegistration' defined in class path resource [org/springframework/cloud/netflix/eureka/server/EurekaServerAutoConfiguration.class]: Unsatisfied dependency expressed through method 'traceFilterRegistration' parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'javax.servlet.Filter' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Qualifier(value=httpTraceFilter)}Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'javax.servlet.Filter' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Qualifier(value=httpTraceFilter)}
  ```

* 原因分析

  * ①jdk9及以后，模块化的概念使得JAXB默认没有加载；jaxb-api是存在jdk中的，只是默认没有加载而已，我们需要手动引入。
  * ②springboot版本与springcloud版本不兼容（例2.2.2->Finchley.SR2）

* 解决方案

  * 引入jaxb-api

    ```xml
    <dependency>    <groupId>com.sun.xml.bind</groupId>    <artifactId>jaxb-core</artifactId>    <version>2.2.11</version></dependency><dependency>    <groupId>javax.xml.bind</groupId>    <artifactId>jaxb-api</artifactId></dependency><dependency>    <groupId>com.sun.xml.bind</groupId>    <artifactId>jaxb-impl</artifactId>    <version>2.2.11</version></dependency><dependency>    <groupId>org.glassfish.jaxb</groupId>    <artifactId>jaxb-runtime</artifactId>    <version>2.2.10-b140310.1920</version></dependency><dependency>    <groupId>javax.activation</groupId>    <artifactId>activation</artifactId>    <version>1.1.1</version></dependency>
    ```

  * 更改对应的版本号,使得springboot与springcloud兼容

    > org: springboot vertion 2.2.2 更换为 2.0.2

    * 官方对照表 （2021年7月15日摘录）

    | Release Train                                                | Boot Version                          |
    | :----------------------------------------------------------- | :------------------------------------ |
    | [2020.0.x](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2020.0-Release-Notes) aka Ilford | 2.4.x, 2.5.x (Starting with 2020.0.3) |
    | [Hoxton](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-Hoxton-Release-Notes) | 2.2.x, 2.3.x (Starting with SR5)      |
    | [Greenwich](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Greenwich-Release-Notes) | 2.1.x                                 |
    | [Finchley](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Finchley-Release-Notes) | 2.0.x                                 |
    | [Edgware](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Edgware-Release-Notes) | 1.5.x                                 |
    | [Dalston](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Dalston-Release-Notes) | 1.5.x                                 |

## 2.服务间使用RestTemplate调用404 null

* 错误详情

  ```text
  org.springframework.web.client.HttpClientErrorException: 404 null	at org.springframework.web.client.DefaultResponseErrorHandler.handleError(DefaultResponseErrorHandler.java:94) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.DefaultResponseErrorHandler.handleError(DefaultResponseErrorHandler.java:79) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.ResponseErrorHandler.handleError(ResponseErrorHandler.java:63) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.RestTemplate.handleResponse(RestTemplate.java:766) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:724) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:680) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]	at org.springframework.web.client.RestTemplate.getForObject(RestTemplate.java:332) ~[spring-web-5.0.6.RELEASE.jar:5.0.6.RELEASE]
  ```

* 错误分析

  * RestTemplate调用服务的路径错误

## 3.因重复声明jar包依赖产生的错误

* 错误详情

  ```text
  org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration': Unexpected exception during bean creation; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.core.env.ConfigurableEnvironment' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
  ```

* 错误分析

  * 在pom.xml中重复声明spring-boot-starter-actuator

* 解决办法

  * 使用`mvn dependency:tree` 检查依赖冲突，去掉重复声明的依赖

    > 扫描结果

    ```text
    [WARNING] Some problems were encountered while building the effective model for org.example:eureka-server:jar:1.0-SNAPSHOT[WARNING] 'dependencyManagement.dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: org.springframework.boot:spring-boot-starter-actuator:jar -> duplicate declaration of version 2.0.2.RELEASE @ org.example:springcloud-getting-started:1.0-SNAPSHOT, C:\project\springcloud-getting-started\pom.xml, line 105, column 25[WARNING][WARNING] Some problems were encountered while building the effective model for org.example:eureka-provider:jar:1.0-SNAPSHOT[WARNING] 'dependencyManagement.dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: org.springframework.boot:spring-boot-starter-actuator:jar -> duplicate declaration of version 2.0.2.RELEASE @ org.example:springcloud-getting-started:1.0-SNAPSHOT, C:\project\springcloud-getting-started\pom.xml, line 105, column 25[WARNING] 'build.plugins.plugin.version' for org.mybatis.generator:mybatis-generator-maven-plugin is missing. @ org.example:eureka-provider:[unknown-version], C:\project\springcloud-getting-started\eureka-provider\pom.xml, line 91, column 21
    ```

    