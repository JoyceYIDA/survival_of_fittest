package YIDA;

import YIDA.annotations.Benchmark;
import YIDA.annotations.Measurement;
import YIDA.annotations.Preheat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * 专门负责运行
 */

class CaseRun{
    private static final int DEFAULT_ITERATIONS=10;
    private static final int DEFAULT_GROUP=5;
    private static final int DEFAULT_PREHEAT=0;
    private final List<TestCase> caseList;
    public CaseRun(List<TestCase> caseList){
        this.caseList=caseList;
    }

    public void run() throws InvocationTargetException, IllegalAccessException {

        for(TestCase testCase:caseList){
            int iterations=DEFAULT_ITERATIONS;
            int group=DEFAULT_GROUP;
            //找到对象中哪些方法是需要测试的方法：得到所有方法，找方法中有BenchMark注解
            Method[] methods=testCase.getClass().getMethods();

            //获取类级别的配置
            Measurement classMeasurement=testCase.getClass().getAnnotation(Measurement.class);
            if(classMeasurement!=null){
                iterations=classMeasurement.iterations();
                group=classMeasurement.group();
            }

            for(Method method:methods){
                //获取每一个方法的注解
                Benchmark benchmark=method.getAnnotation(Benchmark.class);
                if(benchmark==null){
                    continue;
                }

                Measurement methodMeasurement=method.getAnnotation(Measurement.class);
                if(methodMeasurement!=null){
                    iterations=methodMeasurement.iterations();
                    group=methodMeasurement.group();
                }
                Preheat preheat=method.getAnnotation(Preheat.class);
                int interaction=DEFAULT_PREHEAT;
                if(preheat!=null){
                    interaction=preheat.interactions();
                }
                runCase(testCase,method,iterations,group,interaction);
            }
        }
    }
    private static void runCase(TestCase testCase,Method method,int iterations,int group,int interaction) throws InvocationTargetException, IllegalAccessException {
        System.out.println(method.getName());
        for(int k=0;k<interaction;k++){
            method.invoke(testCase);
        }
        System.out.println("进行预热");
        for(int i=0;i<group;i++){
            //每组实验之前进行预热
            System.out.println("第"+(i+1)+"次实验");
            long t1=System.nanoTime();
            for(int j=0;j<iterations;j++){
                method.invoke(testCase);
            }
            long t2=System.nanoTime();
            System.out.println("耗时"+(t2-t1)+"纳秒");
        }
    }
}

/**
 * 专门加载类
 */
public class CaseLoad {
    /**
     * 1、根据一个固定的类找到类加载器;创建类加载器接收找到的类加载器
     * 2、根据加载器找到类文件所在的路径
     * 3、扫描路径下的所有类文件
     * @param packageA 文件名称
     * @return
     */
    public CaseRun load(String packageA) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<String> classNameList=new ArrayList<String>();
        //Dot形式包名
        String packageB=packageA.replaceAll("/",".");
        ClassLoader classLoader=this.getClass().getClassLoader();
        Enumeration<URL> urls=classLoader.getResources(packageA);
        while(urls.hasMoreElements()){
            URL url=urls.nextElement();
            if(!url.getProtocol().equals("file")){
                //如果不是*.class,暂时不支持（了解jar包中加载文件）
                continue;
            }
            //解码
            String dirname=URLDecoder.decode(url.getPath(),"UTF-8");

            File dir=new File(dirname);
            //dir不是目录
            if(!dir.isDirectory()){
                continue;
            }
            //扫描该目录下的所有*.class文件，作为所有的类文件
            File[] files=dir.listFiles();
            if (files==null){
                continue;
            }
            for(File file:files){
                //判断后缀是否是*.class
                String fileName=file.getName();
                if(fileName.endsWith(".class")) {
                    int endIndex = fileName.length()-6;
                    String className = fileName.substring(0,fileName.length()-6 );
                    classNameList.add(className);
                }
            }
        }
        List<TestCase> caseList=new ArrayList<TestCase>();
        for(String className:classNameList){
            //反射得到一个类的实例
            Class<?> cls=Class.forName(packageB+"."+className);
            //如果这个类是Testcase的实现类，就进行实例化
            if(hasInterface(cls,TestCase.class)){
                caseList.add((TestCase)cls.newInstance());
            }
        }

        return new CaseRun(caseList);
    }

    /**
     * 判断这个类是不是接口
     * @param cls
     * @param interfaceName
     * @return
     */
    private boolean hasInterface(Class<?> cls,Class<?> interfaceName){
        Class<?>[] interfaceArr=cls.getInterfaces();
        for(Class<?> inter:interfaceArr){
            if(inter==interfaceName){
                return true;
            }
        }
        return false;
    }

}
