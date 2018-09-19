package com.xangqun.springcloud.controller;

import com.google.common.collect.Maps;
import com.xangqun.springcloud.dto.ClientDto;
import com.xangqun.springcloud.dto.PageResult;
import com.xangqun.springcloud.dto.Result;
import com.xangqun.springcloud.model.Client;
import com.xangqun.springcloud.service.IClientService;
import com.xangqun.springcloud.service.impl.IClientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色相关接口
 *
 * @author owen 624191343@qq.com
 */
@Api(tags = "应用")
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientServiceImpl iClientService;

    @PostMapping
    @ApiOperation(value = "保存应用")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public void saveRole(@RequestBody ClientDto clientDto) {
    	iClientService.saveClient(clientDto);
    }

    @GetMapping
    @ApiOperation(value = "应用列表")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public PageResult<Client> listRoles(@RequestParam Map<String, Object> params) {
        return iClientService.listRoles(params) ;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取应用")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public Client get(@PathVariable Long id) {
        return iClientService.getById(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有应用")
    @PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
    public List<Client> roles() {
        return iClientService.findList(Maps.newHashMap());
    }

    @GetMapping(params = "userId")
    @ApiOperation(value = "根据用户id获取拥有的角色")
    @PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
    public List<Client> roles(Long userId) {
        return iClientService.listByUserId(userId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除应用")
    @PreAuthorize("hasAuthority('sys:role:del')")
    public void delete(@PathVariable Long id) {
    	iClientService.deleteClient(id);
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存或者修改应用")
    @PreAuthorize("hasAuthority('sys:role:saveOrUpdate')")
    public Result saveOrUpdate(@RequestBody ClientDto clientDto){
        return  iClientService.saveOrUpdate(clientDto);
    }
}
