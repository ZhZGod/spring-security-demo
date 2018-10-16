package com.zzh.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//使用注解的权限形式时 这个注解和下面的authenticationManagerBean要加上
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private MyConfig myConfig;

    /**
     * 指定  校验用户信息的地方  和 密码编码方式
     */
    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(myUserDetailsService)
                    .passwordEncoder(myConfig.passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    /**
     * 忽略的路径
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
                .antMatchers("/h2-console/**")
//                .antMatchers("/user/**")
                .antMatchers("/mylogin");
    }
    /**
     * 匹配 "/", "/index", "/mylogin", "/register" 路径，不需要权限即可访问
     * 登录地址为 "/mylogin"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF 我这里给禁用了
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/mylogin", "/register").permitAll()//在这地方也可以配置哪些路径不需要权限
                //.antMatchers("/user/**").hasRole("USER")//不使用注解也可以在这里配置 /user/以下的 路径 都需要USER权限
                //.antMatchers("/user").hasRole("USER")//   /user路径需要USER权限
//                .anyRequest().authenticated()//其余的所有请求都需要验证
                .and()
                .formLogin().loginPage("/mylogin").defaultSuccessUrl("/user")//自定义 登录页面的地址  登录成功后的地址
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");//自定义 登出的地址  登出成功后的地址
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
