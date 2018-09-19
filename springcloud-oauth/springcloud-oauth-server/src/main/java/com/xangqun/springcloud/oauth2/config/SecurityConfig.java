package com.xangqun.springcloud.oauth2.config;

import com.xangqun.springcloud.oauth2.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.ServletException;

/**
 * spring security配置
 *
 * @author owen 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51 2017年10月16日
 *          在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({PermitUrlProperties.class,ValidateCodeProperties.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	// @Autowired
	// private LogoutSuccessHandler logoutSuccessHandler;
	@Autowired(required = false)
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OauthLogoutHandler oauthLogoutHandler;
	@Autowired
	private PermitUrlProperties permitUrlProperties ;

	@Autowired
	private ValidateCodeProperties validateCodeProperties;
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**", "/doc.html", "/login.html");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/health");
		// 忽略登录界面
		web.ignoring().antMatchers("/login.html");
		web.ignoring().antMatchers("/hello.html");
		web.ignoring().antMatchers("/oauth/user/token");
		web.ignoring().antMatchers("/oauth/client/token");
	}
	/**
	 * 认证管理
	 *
	 * @return 认证管理对象
	 * @throws Exception
	 *             认证异常信息
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//关闭跨站请求防护
		http.csrf().disable();
		//请求授权
		http.authorizeRequests()
		//不需要权限认证的url
				.antMatchers( permitUrlProperties.getHttp_urls() ).permitAll()
		//需要身份认证
				.anyRequest().authenticated();



		http.formLogin().loginPage("/login.html").loginProcessingUrl("/user/login")
				.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler);

		// 基于密码 等模式可以无session,不支持授权码模式
		if (authenticationEntryPoint != null) {
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		} else {
			// 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		}

		http.logout().logoutUrl("/user/logout").clearAuthentication(true)
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				.addLogoutHandler(oauthLogoutHandler);

		// http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
		// 解决不允许显示在iframe的问题
		http.headers().frameOptions().disable();
		http.headers().cacheControl();

	}

	/**
	 * 全局用户信息
	 *
	 * @param auth
	 *            认证管理
	 * @throws Exception
	 *             用户认证异常信息
	 */
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	/**
	 * 创建 验证码 过滤器 ，并将该过滤器的Handler 设置成自定义登录失败处理器
	 */
	@Bean
	public FilterRegistrationBean<ValidateCodeFilter> validateCodeFilterFilter() throws ServletException {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setFailureHandler(authenticationFailureHandler);
		validateCodeFilter.setValidateCodeProperties(validateCodeProperties);
		validateCodeFilter.setRedisTemplate(redisTemplate);
		//调用 装配 需要图片验证码的 url 的初始化方法
		validateCodeFilter.afterPropertiesSet();
		FilterRegistrationBean<ValidateCodeFilter> bean = new FilterRegistrationBean(validateCodeFilter);
		// 这个顺序很重要哦，为避免麻烦请设置在最前
		bean.setOrder(0);
		bean.addUrlPatterns("/*");
		return bean;
	}
}
