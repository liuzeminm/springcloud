package com.xangqun.springcloud

import scala.beans.BeanProperty


/**
  * @ClassName Person.java
  * @author laixiangqun
  * @version 1.0.0
  * @Description TODO
  * @createTime 2018年09月12日 11:37
  */
class Person {

  @BeanProperty var name: String = _
  @BeanProperty var sex: String = _
  @BeanProperty var birthday: String = _

}
