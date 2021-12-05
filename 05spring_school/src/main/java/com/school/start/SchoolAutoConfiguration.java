package com.school.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhaobaozhi
 */
@Configuration
@ConditionalOnClass(MySchool.class)
@EnableConfigurationProperties(SchoolProperties.class)
@ConditionalOnProperty(prefix = "school", value = "enabled", havingValue = "true")
@PropertySource("classpath:application.properties")
public class SchoolAutoConfiguration {

    @Autowired
    private SchoolProperties properties;

    @Bean
    public MySchool mySchool() {
        List<Integer> studentIds = properties.getStudentIds();
        List<String> studentNames = properties.getStudentNames();
        List<Integer> classIds = properties.getClassIds();
        List<String> classNames = properties.getClassNames();
        List<Map<String, Integer>> studentOfClass = properties.getStudentOfClass();
        List<Student> students = new ArrayList<>(studentIds.size());
        for (int i=0; i<studentIds.size(); i++) {
            students.add(new Student(studentIds.get(i), studentNames.get(i)));
        }

        List<KClass> lists = new ArrayList<>();
        for (int i=0; i<classIds.size(); i++) {
            lists.add(new KClass(classIds.get(i), classNames.get(i)));
        }

        for (Map info: studentOfClass) {
            lists.get((Integer) info.get("classId")).addStudent(students.get((Integer) info.get("studentId")));
        }

        System.out.println(studentIds.toString());
        System.out.println(studentNames.toString());
        System.out.println(classIds.toString());
        System.out.println(classNames.toString());
        System.out.println(studentOfClass.toString());

        return new MySchool(lists);
    }
}
