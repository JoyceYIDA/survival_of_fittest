package YIDA;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * JMH是专门用于进行代码微基准测试的工具API
 *
 * 功能：
 * 1.自动加载测试用例
 * 2.通过接口标记待测试类
 * 3.通过注解标记待测试方法
 * 4.通过注解设置多级配置
 */
public class Test {
    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException, InvocationTargetException {
        CaseLoad caseLoad=new CaseLoad();

        caseLoad.load("YIDA/cases").run();
    }
}
