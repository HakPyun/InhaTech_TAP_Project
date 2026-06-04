package inhatc.project.tap.config;
import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws  Exception{

        http.formLogin(form -> form
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login/error")
                .usernameParameter("member_id")
                .passwordParameter("member_pw")
                .permitAll());
        http.authorizeHttpRequests(request->request
                .requestMatchers("/js/**","/css/**","/assets/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/","/member/**","/member/find/id/**").permitAll()
                .requestMatchers("/mypage/**","/member/check/**").permitAll()
                .requestMatchers("/**", "/shop/data","/example", "/shopDetails").permitAll()
                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated());
        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll());
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling(handler->handler.accessDeniedPage("/403"));
//        http.sessionManagement(session->session
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/")
//
//        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));




//        http.csrf(CsrfConfigurer::disable)   //최필묵이 401에러 때문에 추가한곳 ~60번째 줄까지
//                    .authorizeHttpRequests((authorize) -> authorize
//                            .requestMatchers("/**").permitAll()
//                            .anyRequest().authenticated()
//                    );
        return http.build();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //특정 url 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {web.ignoring()
                .requestMatchers("/favicon.ico", "/resources/**", "/error");};
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
