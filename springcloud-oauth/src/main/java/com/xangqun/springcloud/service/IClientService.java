package com.xangqun.springcloud.service;



import com.xangqun.springcloud.dto.ClientDto;
import com.xangqun.springcloud.dto.PageResult;
import com.xangqun.springcloud.dto.Result;
import com.xangqun.springcloud.model.Client;

import java.util.List;
import java.util.Map;

public interface IClientService {

	
	Client getById(Long id) ;
	 
    void saveClient(ClientDto clientDto);

    Result saveOrUpdate(ClientDto clientDto);

    void deleteClient(Long id);
    
    public PageResult<Client> listRoles(Map<String, Object> params);
    
    List<Client> findList(Map<String, Object> params) ;
    
    List<Client> listByUserId(Long userId) ;
    
}
