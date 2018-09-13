/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.autobean;

/**
 * @author laixiangqun
 * @since 2018-8-16
 */
public class AuoService {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void print(){
        System.out.println("动态载入bean,name="+name);
    }
}
