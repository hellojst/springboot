关于打包部署
1. jar包
    <packaging>jar</packaging>
    cd 项目跟目录（和pom.xml同级）
    mvn clean package
    ## 或者执行下面的命令
    ## 排除测试代码后进行打包
    mvn clean package  -Dmaven.test.skip=true
    启动 ：  java -jar  名称.jar
    这种方式，只要控制台关闭，服务就不能访问了。下面我们使用在后台运行的方式来启动: nohup java -jar target/spring-boot-scheduler-1.0.0.jar &
    在启动的时候选择读取不同的配置文件 ： java -jar 名称.jar --spring.profiles.active=dev

2. war包
    <packaging>war</packaging>
    打包时排除tomcat.
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    在这里将scope属性设置为provided，这样在最终形成的WAR中不会包含这个JAR包，因为Tomcat或Jetty等服务器在运行时将会提供相关的API类。
    注册启动类
    创建ServletInitializer.java，继承SpringBootServletInitializer ，覆盖configure()，把启动类Application注册进去。外部web应用服务器构建Web Application Context的时候，会把启动类添加进去。
    public class ServletInitializer extends SpringBootServletInitializer {
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(Application.class);
        }
    }
    mvn clean package  -Dmaven.test.skip=true
    会在target目录下生成：项目名+版本号.war文件，拷贝到tomcat服务器中启动即可


生产运维：
    查看JVM参数的值：
    jinfo -flags pid
    来查看jar 启动后使用的是什么gc、新生代、老年代分批的内存都是多少，示例如下：
    -XX:CICompilerCount=3 -XX:InitialHeapSize=234881024 -XX:MaxHeapSize=3743416320 -XX:MaxNewSize=1247805440 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=78118912 -XX:OldSize=156762112
    -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:+UseParallelGC

    -XX:CICompilerCount ：最大的并行编译数
    -XX:InitialHeapSize 和 -XX:MaxHeapSize ：指定JVM的初始和最大堆内存大小
    -XX:MaxNewSize ： JVM堆区域新生代内存的最大可分配大小
    ...
    -XX:+UseParallelGC ：垃圾回收使用Parallel收集器

    如何重启：
        简单粗暴：
        直接kill掉进程再次启动jar包：
        ps -ef|grep java
        ##拿到对于Java程序的pid
        kill -9 pid
        ## 再次重启
        Java -jar  xxxx.jar

        启动方式：
        1、 可以直接./yourapp.jar 来启动
        2、注册为服务
        也可以做一个软链接指向你的jar包并加入到init.d中，然后用命令来启动。

