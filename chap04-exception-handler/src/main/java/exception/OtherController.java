package exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    /* 다른 컨트롤러에서 정의된 예외는 처리하지 않는다
    * (일부러 NullPoint 발생시킴)*/
    @GetMapping("other-controller-null")
    public String otherNullPointExceptionTest(){
        String str = null;

        System.out.println(str.charAt(0));

        return "/";
    }

    @GetMapping("other-controller-user")
    public String otherUserExceptionTest() throws MemberRegistException{
        boolean check = true;
        if(check){
            throw new MemberRegistException("당신을 회원으로 받을 수 없습니다");
        }
        return "/";
    }

    @GetMapping("other-controller-aray")
    public String otherArrayExceptionTest() throws MemberRegistException{
        double[] array = new double[0];
        System.out.println(array[0]);

        return "/";
    }

}
