package com.xangqun.springcloud

import org.apache.commons.lang.StringUtils
import org.springframework.web.bind.annotation._

/**
  *
  *
  * @author laixiangqun
  * @since 2018-8-14
  */
@RestController
class HttpApiController {

  @RequestMapping(value = Array("httpapi.do"),
    method = Array(RequestMethod.GET))
  @ResponseBody
  def debugTest(@RequestParam(value = "url") url: String,
                @RequestParam(value = "method") method: String,
                @RequestParam(value = "paramJson") paramJson: String): String = {
    if (StringUtils.isEmpty(url)) return "url cannot be empty"

    if (StringUtils.isEmpty(method)) return "method cannot be empty"

    var result = "method should be GET or POST"
    return result
  }
}
