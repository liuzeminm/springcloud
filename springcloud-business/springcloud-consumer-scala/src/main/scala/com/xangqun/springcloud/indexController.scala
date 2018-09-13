package com.xangqun.springcloud

import com.alibaba.fastjson.JSONObject
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
class indexController {

  @RequestMapping(value = Array("/index"))
  def index(): JSONObject = {
    val json = new JSONObject
    json.put("code", 0)
    json.put("data", "success")
    json
  }
}
