package com.school.start;

import java.util.ArrayList;
import java.util.List;


public class KClass {
    private int id;
    private String name;

    public KClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public String toString() {
        return "Class::" + students.toString();
    }
}
