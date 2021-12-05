package com.school.start;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author zhaobaozhi
 */

@Data
@ConfigurationProperties(prefix = "school")
public class SchoolProperties {
    private List<Integer> studentIds;
    private List<String> studentNames;
    private List<Integer> classIds;
    private List<String> classNames;
    private List<Map<String, Integer>> studentOfClass;
}
