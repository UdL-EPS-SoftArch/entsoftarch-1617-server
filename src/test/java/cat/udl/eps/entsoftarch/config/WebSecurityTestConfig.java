package cat.udl.eps.entsoftarch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("Test")
public class WebSecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
                .antMatchers(HttpMethod.POST, "/**/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()
                .antMatchers(HttpMethod.PATCH, "/**/*").authenticated()
            .anyRequest().permitAll()
            .and()
            .httpBasic().realmName("SoftArch 2016-17")
            .and()
            .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .csrf().disable();
    }
}
