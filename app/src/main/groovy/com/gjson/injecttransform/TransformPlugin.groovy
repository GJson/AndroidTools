package com.gjson.groovy.injecttransform
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 定义插件，加入Transform
 */
class TransformPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {

    // 获取Android扩展
    def android = project.extensions.getByType(AppExtension)
    // 注册Transform，其实就是添加了Task
    android.registerTransform(new InjectTransform(project))

    // 这里只是随便定义一个Task而已，和Transform无关
    project.task('JustTask') {
      doLast {
        println('InjectTransform task')
      }
    }

  }
}