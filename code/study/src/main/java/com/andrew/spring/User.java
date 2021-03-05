package com.andrew.spring;

import java.lang.reflect.Field;

public class User {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName("com.andrew.spring.User");
        Object instance = clazz.newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(Integer.class) ) {
                field.set(instance, 1);
            } else {
                field.set(instance, "Andrew");
            }

        }
        System.out.println(instance.toString());
    }

}
