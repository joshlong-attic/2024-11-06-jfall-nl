package com.example.demo3;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.*;
import java.util.concurrent.atomic.AtomicBoolean;

//@SpringBootApplication
public class Demo3Application {

    public static void main(String[] args) {
//        ApplicationContext ac = SpringApplication.run(Demo3Application.class, args);

        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);

//        ApplicationContext ac = SpringApplication.run(Demo3Application.class, args);

    }

}

class MyBeanPostProcessor implements BeanPostProcessor {

    private boolean isLogged(Object o) {

        var loggedFound = new AtomicBoolean(false);

        if (o.getClass().getAnnotation(Logged.class) != null) {
            loggedFound.set(true);
        }
        ReflectionUtils.doWithMethods(o.getClass(), method -> {
            if (method.getAnnotation(Logged.class) != null) {
                loggedFound.set(true);
            }
        });
        return loggedFound.get();

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (isLogged(bean)) {
            var pfb = new ProxyFactoryBean();
            pfb.addAdvice((MethodInterceptor) invocation -> {
                System.out.println("before");
                var obj = invocation.proceed();
                System.out.println("after");
                return obj;
            });
            pfb.setTarget(bean);
            return pfb.getObject();
        }


        return bean;
    }
}


class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (String  bn : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition bd = beanFactory.getBeanDefinition(bn);
            System.out.println(bd.getResolvableType().toString());
            
        }
    }
}

@Component
class Initializing implements InitializingBean {

    private final CustomerService customerService;

    Initializing(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.customerService.doSomething();
    }
}

@Service
@Logged
class CustomerService {

    public void doSomething() {
        System.out.println("do something");
    }
}

@Service
class LoggingCustomerService extends CustomerService {

    @Override
    public void doSomething() {
        System.out.println("before");
        super.doSomething();
        System.out.println("after");
    }
}

@SanchirComponent
class Bar {
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Logged {
}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@interface SanchirComponent {

    /**
     * Alias for {@link Component#value}.
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

}

class Foo {

    final Bar bar;

    Foo(Bar bar) {
        this.bar = bar;
        System.out.println("bar!");
    }
}


@Configuration
@ComponentScan
class MyConfig {

    @Bean
    static MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean
    static MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean
    Foo foo(Bar bar) {
        return new Foo(bar);
    }
}