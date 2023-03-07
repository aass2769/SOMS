package project.soms;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class FrontConfiguration implements WebMvcConfigurer {

  private final SessionInterceptor sessionInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    /**
     * 해당 인터셉터에 적용할 페이지 : 전체
     * 제외 페이지 : 로그인 페이지, 정적 페이지
     */
    registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").
        excludePathPatterns("/login", "/logincheck", "/css/**", "/img/**", "/js/**");
  }
}
