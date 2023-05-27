package cn.pzhu.dcims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:security-config.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登陆前不能拦截的静态资源
    @Value("${security.ignore.resource}")
    private String[] securityIgnoreResource;
    //登陆前不能拦截的请求
    @Value("${security.ignore.api}")
    private String[] securityIgnoreApi;
    //登陆的请求路径
    @Value("${security.login.url}")
    private String loginApi;
    //退出注销的请求路径
    @Value("${security.logout.url}")
    private String logoutApi;
    //前端登陆需对应的用户名字符串
    @Value("${security.login.username.key:username}")
    private String usernameKey;
    //前端登陆需对应的密码字符串
    @Value("${security.login.password.key:password}")
    private String passwordKey;
    //登录界面
    @Value("${security.loginPage}")
    private String loginPage;


    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    DataSource dataSource;

    /**
     * 与数据关联以便将登陆信息存放到数据库
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().and()
                .csrf().disable()
                .authorizeRequests()
                //对于静态资源的获取允许匿名访问
                .antMatchers(HttpMethod.GET, securityIgnoreResource).permitAll()
                // 对登录注册要允许匿名访问;
                .antMatchers(securityIgnoreApi).permitAll()
                //其余请求全部需要登录后访问
                .anyRequest().authenticated()
                //这里配置的loginProcessingUrl为页面中对应表单的 action ，该请求为 post，并设置可匿名访问
                .and()
                .formLogin().loginPage(loginPage).loginProcessingUrl(loginApi).permitAll()
                .defaultSuccessUrl(loginPage)
                //这里指定的是表单中name="username"的参数作为登录用户名，name="password"的参数作为登录密码
                .usernameParameter(usernameKey).passwordParameter(passwordKey)
                // 登录成功
                .successHandler(new LoginSuccessHandler())
                // 登录失败
                .failureHandler(new LoginFailureHandler()).permitAll()
                .and()
                // 注销成功
                .logout().logoutSuccessHandler(new MyLogoutHandler()).deleteCookies("JSESSIONID")//登出之后删除cookie
                .and().rememberMe().rememberMeParameter("rememberMe")//.alwaysRemember(true)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(7*24*3600)
                .userDetailsService(userDetailsService);
//                .and()
//                // 未登录请求资源
//                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());

    }

}
