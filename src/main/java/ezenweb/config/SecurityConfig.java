package ezenweb.config;

import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig { // 시큐리티를 커스텀 하는 곳 (Version에 따라서 다름 버전  꼭 확인 할 것)
    @Autowired
    MemberService memberService;
    //1. filterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        // HTTP 요청에 따른 부여된 권한/상태
        http.authorizeHttpRequests(
                authorizeRequest //매개변수
                    ->           //람다식(자바 화살표 함수)
               authorizeRequest
                    // getHTTP : /board : 인증(로그인)된 회원이면 허가
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/board")).authenticated()
                    // getHTTP : /board/write : 인증(로그인)된 회원이면서 ROLE이 USER 이면 허가
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/board/write")).hasAnyRole("USER")
                    //getHTTP : /chat : 인증(로그인)되고 ROLE 이 TEAM1 이거나 TEAM2 이면 허가
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/chat")).hasAnyRole("TEAM1","TEAM2")
                    //getHTTP : 그외 PATH(/**)는 모두 허가
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll()

        );

        //2. 로그인 폼 커스텀
        //http.formLogin(AbstractHttpConfigurer :: disable); // 시큐리티가 제공하는 로그인 폼을 사용하지 않겠다.
        http.formLogin( // AXIOS,AJAX 사용시 contentType : form
                로그인관련매개변수
                        ->
                        로그인관련매개변수
                                .loginPage("/member/login")                     //로그인을 할 view url 정의
                                .loginProcessingUrl("/member/login/post.do")    //로그인을 처리할 url 정의
                                .usernameParameter("memail")                    //로그인에 사용할 아이디
                                .passwordParameter("mpassword")                 //로그인에 사용할 패스워드
//                                .defaultSuccessUrl("/")                         //로그인 성공하면 반환될 url
//                                .failureForwardUrl("/member/login")             //로그인 실패시 반환될 url
//                                .successHandler(((request, response, authentication) -> {}))
                                .successHandler((request, response, authentication) -> {
                                    System.out.println("authentication = " + authentication);
                                    response.setContentType("application/json;utf-8");
                                    response.getWriter().print("true");
                                })
                                .failureHandler(((request, response, exception) -> {
                                    System.out.println("exception = " + exception);
                                    response.setContentType("application/json;utf-8");
                                    response.getWriter().print("false");
                                }))
                );

        //3. 로그아웃 커스텀
        http.logout(
                로그아웃관련매개변수
                        ->
                        로그아웃관련매개변수
                                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout/get.do")) //로그아웃 처리요청 url
                                .logoutSuccessUrl("/")       // 로그아웃 성공시 이동할 url
                                .invalidateHttpSession(true) // 세션 초기화
                );
        //4. csrf( post, put 요청 금지) 공격 방지 : 특정(인증/허가) post, put 만 가능하도록
        http.csrf(AbstractHttpConfigurer :: disable); // csrf 사용 안함. // 개발 작업시 에만 사용 해야함 실제 배포시에는 특정 부분만 열어야함....

        //5. 로그인 처리할 서비스를 등록
        http.userDetailsService(memberService);

        //6. 소셜로그인 oauth2
        http.oauth2Login(oAuth2LoginConfigurer -> {
            oAuth2LoginConfigurer
                    .loginPage("/member/login")
                    .userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(memberService);
                    });
        });



        return http.build();

        }



        //2. 시큐리티가 패스워드 검증할때 사용할 암호화 객체
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


}
