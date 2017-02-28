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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Configuration
@Profile("!Test")
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired BasicUserDetailsService userDetailsService;
    @Autowired UserRepository userRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin")
                    .password("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC")
                    .roles("ADMIN");

        User owner = new DataOwner();
        owner.setUsername("owner");
        owner.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
        userRepository.save(owner);

        User user = new DataUser();
        user.setUsername("user");
        user.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
        userRepository.save(user);
    }
}