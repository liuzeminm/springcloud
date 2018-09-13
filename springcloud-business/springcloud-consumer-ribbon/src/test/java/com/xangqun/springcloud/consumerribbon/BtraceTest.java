/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerribbon;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

/**
 * @program: monitor_tuning
 * @description: BTrace脚本
 * @author: 01
 * @create: 2018-07-14 22:33
 **/

@BTrace  // 注明这是一个BTrace脚本
public class BtraceTest {

    // 指定需要拦截的方法
    @OnMethod(
            // 类的路径
            clazz = "com.xangqun.springcloud.consumerribbon.SpringDemoRibbonController",
            // 方法名
            method = "hellox",
            // 在什么时候进行拦截
            location = @Location(Kind.ENTRY)
    )
    public static void anyRead(@ProbeClassName String pcn, // 被拦截的类名
                               @ProbeMethodName String pmn, // 被拦截的方法名
                               AnyType[] args // 被拦截的方法的参数值
    ) {
        // 打印数组
        BTraceUtils.printArray(args);
        // 打印行
        BTraceUtils.println("className: " + pcn);
        BTraceUtils.println("MethodName: " + pmn);
        BTraceUtils.println();
    }
}
