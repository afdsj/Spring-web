package method;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Map;

@Controller // Spring Bean에 등록함
@RequestMapping("/first/*")
@SessionAttributes("id") // 키 설정
public class firstController {

    /* 핸들러 메소드에 파라미터로 트정 몇가지 타입을 선언하게 되면 핸들러 메소드 호출 시 인자로 값을 전달해준다 */

    /* 컨트롤러 핸들러 메서드의 반환 값을 void로 설정하면 오청 주소가 view의 이름이 된다
    *  -> first/regist 요청이 들어오면 /first/regist 뷰가 응답을 하게 된다*/
    @GetMapping("regist")
    public void regist(){}

//    @GetMapping("regist")
//    public String regist1(){
//        return "/first/regist";
//    }

//    @GetMapping("regist/{no}")
//    public String regist(@PathVariable("no") int no){
//        System.out.println(no+"요청이 들어옴");
//        return "/first/regist";
//    }

    @PostMapping("regist")
    public String registPost(Model model, WebRequest webRequest){
        String name = webRequest.getParameter("name");
        int price =Integer.parseInt(webRequest.getParameter("price"));
        int categoryCode = Integer.parseInt(webRequest.getParameter("categoryCode"));

        String message = name +"을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록";
        model.addAttribute("message",message);

        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modifyMethod(){}

    /* 2.@RequestParam으로 요청 파라미터 전달하기
    *  요청 파라미터를 매핑하여 호출 시 값을 넣어주는 어노테이션으로 매개 변수 앞에 작성한다
    *  form의 name 속성값과 매ㅐ변수의 이름이 다른 경우 @RequestParam("name")을 설정하면 된다
    *  또한 어노테이션은 생략 가능하지만 명시적으로 작성하는 것이 의미 파악에 쉽다
    *
    *  전달하는 form의 name 소성이 일치하는 것이 없는 경우 404에러가 발생하는데
    *  이는 required 속성의 기본 값이 true이면 허용, 속성을 false로 하게되면 해당 값이 존재하지 않아도 null로 처리하여 에러가 발생하지 않는다*/
    @PostMapping("modify")
    public String modifyMenuPrice(Model model,@RequestParam(required = false) String modifyName,
                                  @RequestParam(defaultValue = "0") int modifyPrice){
        String message = modifyName + "메뉴의 가격을 " + modifyPrice + "원으로 가격을 변경하였습니다.";
        System.out.println(message);
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters){
        String modifyMenu = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = "메뉴의 이름을 " + modifyMenu +"(으)로, 가격을 "+ modifyPrice + "원으로 변경하였습니다.";
        System.out.println(message);
        model.addAttribute("message",message);

        return "first/messagePrinter";
    }

    @GetMapping("search")
    public void search(){}

    @PostMapping("search")
    public String searchPost(@ModelAttribute("menu") MenuDTO menu){
        System.out.println(menu);
        return "first/messagePrinter";
    }

    @GetMapping("login")
    public void login(){}

    /* 4-1 Session 이용학
    *  httpSession을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 넣어서 호출한다*/
    @PostMapping("login1")
    public String sessionTest1(HttpSession session, @RequestParam String id){
        session.setAttribute("id",id); //세션에 값을 담아줌

        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session){
        session.invalidate();
        return "first/loginResult";
    }

    /* 4-2 @SessionAttributes를 이용하여 session에 값 담기
    *  클래스 레벨에 @SessionAttributes 어노테이션을 이용하여 세션에 값을 담을 key 값으로 설정해두면
    *  Model 영역에 해당하는 key로 값이 추가되는 경우 session에 자동으로 등록한다 */
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id){
        model.addAttribute("id", id);
        return "first/loginResult";
    }

    /* sessionAttributes로 등록된 값은 sesison의 상태를 관리하는 SessionStatus SetComplate 메소드를 호출해야 사용이 만료된다*/
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus){
        /* 현재 컨트롤러 세션에 저장된 모든 정보를 제거한다
        *  개별 제거는 불가능하다*/
        sessionStatus.setComplete();
        return "first/loginResult";
    }

    @GetMapping("body")
    public void body(){}

    /* 5.@RequestBody를 이용하는 방법
    *  해당 어노테이션은 http 본문 자체를 읽는 부분을 모델로 변환시켜 주는 어노테이션이다
    *
    *  출력을 해보면 쿼리스트링 형태의 문자열이 전송된다
    *  json으로 전달하는 경우 jackson의 컨버터로 자동 파싱하여 사용할 수 있다
    *
    *  주로 restapi 작성 시 많이 사용되며, 일반적인 form을 전송할 때는 거의 사용하지 않는다
    *
    *  추가적으로 헤더에 대한 정보도
    *  @RequestHeader 어노테이션을 이용해서 가져올 수 있다
    *  @CookieValue를 이용해서 쿠키 정보도 쉽게 불러올 수 있다 */
    @PostMapping("body")
    public void bodyTest(@RequestBody String body,
                         @RequestHeader("content-type") String contentType, // api 요청시에 자주 사용함 body, header
                         @CookieValue(value = "JSESSIONID", required = false) String sessionId){
        System.out.println(contentType);
        System.out.println(sessionId);
        System.out.println(body);
        System.out.println(URLDecoder.decode(body));
    }
}
