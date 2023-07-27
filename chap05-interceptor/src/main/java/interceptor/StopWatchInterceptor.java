package interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 핸들러 인터셉터를 구현하면 된다
*  default 메소드 이전에는 모두 오버라이딩 해야 하는 책임을 가지기 때문에 HandlerInterceptorAdapter를
*  이용해 부담을 줄여서 사용하였다
*  default 메소드가 인터페이스에서 사용 가능하게 된 1.8이후부터는 인터페이스만 구현하여
*  필요한 메소드만 오버라이딩 해서 사용할 수 있다 */

@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    private final MenuService menuService;

    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    // 전처리 메소드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pihandle 메소드를 호출함");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        // 반환 타입이 false -> 다음 핸들러 메소드를 호출하지 않음
        // 반환 타입이 true  -> 다음 핸들러 메소드 호출함
        return true;
    }
    // 후처리 메소드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post handle 호출함");
        long startTime = (Long)request.getAttribute("startTime"); // 전처리시 시간
        long endTiem = System.currentTimeMillis(); // 호출시 시간
        modelAndView.addObject("interval",endTiem-startTime);

    }
    // 마지막에 작업을 처리하는 메소드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 호출함");
        menuService.method();
    }

}
