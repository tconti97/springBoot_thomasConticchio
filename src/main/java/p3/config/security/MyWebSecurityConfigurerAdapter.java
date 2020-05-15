/**
 * 
 */
package p3.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author tcont
 *
 */
@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
{
	private static final Logger logger = LoggerFactory.getLogger(MyWebSecurityConfigurerAdapter.class);
	
	@Value("${mh.security.authserver.BCryptPasswordEncoder.usedToEncodePassword:true}")
	private boolean useBCryptPasswordEncoder2encodePassword;
	
	private String encode(String rawPassword) 
	{
		String password = useBCryptPasswordEncoder2encodePassword ? passwordEncoder().encode(rawPassword) : rawPassword;
		logger.info("--tconti --> encode({}) with useBCryptPasswordEncoder2encodePassword:{} returning password as {}", rawPassword, useBCryptPasswordEncoder2encodePassword, password);
		return password;
	}

	@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests()
			.antMatchers("/", "/home","/login","/public","/public**").permitAll()
			.antMatchers("/anonymous").anonymous()	
			.antMatchers("/admin", "/h2_console/**").hasRole("ADMIN")
			.antMatchers("/cats").hasRole("catMaster")
			.antMatchers("/dogs").hasRole("dogMaster")
			.antMatchers("/rest/v1/dogs/**").hasAuthority("haveDogs")
			.antMatchers("/rest/v1/cats/**").hasRole("catMaster")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.logout().permitAll()
			;
		
		http.exceptionHandling().accessDeniedPage("/403");
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
	@Autowired
	public void configureGlobal_(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin").password(encode("admin")).roles("ADMIN")
				.and()
				.withUser("developer").password(encode("developer")).authorities("haveDogs", "ROLE_DEVELOPER", "ROLE_ADMIN", "ROLE_catMaster", "ROLE_dogMaster")
				.and()
				.withUser("catsUser").password(encode("catsUser")).roles("catMaster")
				.and()
				.withUser("dogsUser").password(encode("dogsUser")).roles("dogMaster").authorities("haveDogs")
				;
	}
}
