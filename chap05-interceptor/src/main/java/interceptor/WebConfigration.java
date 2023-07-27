package interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigration implements WebMvcConfigurer {

    private final StopWatchInterceptor stopWatchInterceptor;

    public WebConfigration(StopWatchInterceptor stopWatchInterceptor) {
        this.stopWatchInterceptor = stopWatchInterceptor;
    }

    // interceptor 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(stopWatchInterceptor)
              .addPathPatterns("/*") // 모든 요청에 대한 것들을 가로채게 함
              .excludePathPatterns("/css/**")
              .excludePathPatterns("/images/**")
              .excludePathPatterns("/js/**")
              .excludePathPatterns("/error");
    }
}
