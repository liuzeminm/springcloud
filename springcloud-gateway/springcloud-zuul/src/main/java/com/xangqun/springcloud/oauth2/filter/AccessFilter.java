package com.xangqun.springcloud.oauth2.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by owen on 2017/9/10.
 */
@Component
public class AccessFilter extends ZuulFilter {
    protected static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
        	//解决zuul token传递问题
        	Authentication user = SecurityContextHolder.getContext()
                    .getAuthentication();
    		if(user!=null){
    			if(user instanceof OAuth2Authentication){
    				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) user.getDetails() ;
    				ctx.addZuulRequestHeader("Authorization", "bearer "+details.getTokenValue());
    			}
    		}
        } catch (Exception e) {
            logger.error("AccessFilter error",e);
        }
        return null;
    }

     
}
