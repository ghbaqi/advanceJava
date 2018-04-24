package com.example.javaadvance.annotation;

import java.lang.reflect.Method;
/**
 * 注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。
 * <p>
 * 注解有许多用处，主要如下：
 * - 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息
 * - 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。
 * - 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取
 * <p>
 * 当开发者使用了Annotation 修饰了类、方法、Field 等成员之后，这些 Annotation 不会自己生效，
 * 必须由开发者提供相应的代码来提取并处理 Annotation 信息。
 * 这些处理提取和处理 Annotation 的代码统称为 APT（Annotation Processing Tool)。
 * <p>
 * 当开发者使用了Annotation 修饰了类、方法、Field 等成员之后，这些 Annotation 不会自己生效，
 * 必须由开发者提供相应的代码来提取并处理 Annotation 信息。
 * 这些处理提取和处理 Annotation 的代码统称为 APT（Annotation Processing Tool)。
 * <p>
 * 总结
 * 如果注解难于理解，你就把它类同于标签，标签为了解释事物，注解为了解释代码。
 * 注解的基本语法，创建如同接口，但是多了个 @ 符号。
 * 注解的元注解。
 * 注解的属性。
 * 注解主要给编译器及工具类型的软件用的。
 * 注解的提取需要借助于 Java 的反射技术，反射比较慢，所以注解使用时也需要谨慎计较时间成本
 */

// 注解主要针对的是编译器和其它工具软件(SoftWare tool)。

// 所以，再问我注解什么时候用？我只能告诉你，这取决于你想利用它干什么用


/**
 * 总结
 如果注解难于理解，你就把它类同于标签，标签为了解释事物，注解为了解释代码。
 注解的基本语法，创建如同接口，但是多了个 @ 符号。
 注解的元注解。
 注解的属性。
 注解主要给编译器及工具类型的软件用的。
 注解的提取需要借助于 Java 的反射技术，反射比较慢，所以注解使用时也需要谨慎计较时间成本
 */

/**
 * 在运行时分析处理类 A 的 annotation 信息
 */
public class AnalysisAnnotation {

    @org.junit.Test
    public void test() {

        Class<A> aClass = A.class;

        Method[] methods = aClass.getMethods();
        boolean flag = aClass.isAnnotationPresent(Description.class);

        if (flag) {
            Description description = aClass.getAnnotation(Description.class);
            System.out.println("description = " + description.value());

            for (Method method : methods) {
                if (method.isAnnotationPresent(Author.class)) {
                    Author author = method.getAnnotation(Author.class);
                    System.out.println("name = " + author.name() + ", group = " + author.group());
                }
            }
        }

    }
}
