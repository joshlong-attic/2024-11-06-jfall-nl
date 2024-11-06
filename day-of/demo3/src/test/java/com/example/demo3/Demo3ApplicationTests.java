package com.example.demo3;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

class Demo3ApplicationTests {

    interface Calc {
        int calc(int a, int b);
    }

    public static class MyCalc implements Calc {

        @Override
        public int calc(int a, int b) {
            return a + b;
        }
    }

    @Test
    void contextLoads() {

        var calc = (Calc) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{MyCalc.class}, (proxy, method, args) -> {
                    System.out.println("before");
                    try {
                        return ((Integer) args[0]) +
                                ((Integer) args[1]);

                    }  // 
                    finally {
                        System.out.println("after");
                    }
                });
        var result = calc.calc(1, 2);
        System.out.println("result:" + result);


    }

}
