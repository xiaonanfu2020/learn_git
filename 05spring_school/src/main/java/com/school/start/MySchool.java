package com.school.start;

import java.util.List;


public class MySchool {

    private List<KClass> kClasses;

    public MySchool(List<KClass> kClasses) {
        this.kClasses = kClasses;
    }

    @Override
    public String toString() {
        return "MyClass::" + kClasses.toString();
    }
}
