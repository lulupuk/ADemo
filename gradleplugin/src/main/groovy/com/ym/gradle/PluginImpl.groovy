package com.ym.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginImpl implements  Plugin<Project>{

    @Override
    void apply(Project project){
        project.task("testTask") {
            group = "custom"
            description = "test custom task"
            println "hello gradle plugin"
        }
    }
}