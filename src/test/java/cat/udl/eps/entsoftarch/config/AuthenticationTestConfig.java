package cat.udl.eps.entsoftarch.config;

import cat.udl.eps.entsoftarch.domain.DataOwner;
import cat.udl.eps.entsoftarch.domain.DataUser;
import cat.udl.eps.entsoftarch.domain.User;
import cat.udl.eps.entsoftarch.repository.UserRepository;
import cat.udl.eps.entsoftarch.service.BasicUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("Test")
public class AuthenticationTestConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired BasicUserDetailsService userDetailsService;
    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.inMemoryAuthentication()
            .withUser("admin").password("password").roles("ADMIN");

        User owner = new DataOwner();
        owner.setUsername("owner");
        owner.setPassword("password");
        userRepository.save(owner);

        User owner2 = new DataOwner();
        owner2.setUsername("owner2");
        owner2.setPassword("password");
        userRepository.save(owner2);

        User user = new DataUser();
        user.setUsername("user");
        user.setPassword("password");
        userRepository.save(user);
    }
}