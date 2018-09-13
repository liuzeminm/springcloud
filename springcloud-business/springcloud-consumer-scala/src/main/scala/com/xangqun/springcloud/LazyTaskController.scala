package com.xangqun.springcloud

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

/**
  * @author laixiangqun
  * @since 2018-8-14
  */
@RestController
class LazyTaskController @Autowired()(val httpApiController: HttpApiController ) {

  @RequestMapping(value = {
    Array("/newTask.do")
  })
  def newTask_do() = {
    new ModelAndView("ylazy/newTask")
  }

  @RequestMapping(value = {
    Array("/ylazy/newTask")
  }, method = Array(RequestMethod.POST))
  @ResponseBody
  def newTask(@ModelAttribute lazyTask: Person) = {
    lazyTask.setName("232")
    lazyTask.setSex("2")
    httpApiController.debugTest("","","")
  }

  @RequestMapping(value = { Array("/main.do","/","/index","/home") }, method = Array(RequestMethod.GET))
  def about() = new ModelAndView("ylazy/main")
}
