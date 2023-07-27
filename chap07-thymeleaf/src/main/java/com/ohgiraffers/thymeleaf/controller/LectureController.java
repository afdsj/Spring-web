package com.ohgiraffers.thymeleaf.controller;

import com.ohgiraffers.thymeleaf.model.MemberDTO;
import com.ohgiraffers.thymeleaf.model.SelectCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("lecture")
public class LectureController {

    @GetMapping("expression")
    public ModelAndView expression(ModelAndView mv){
        System.out.println("dkssud");
        mv.addObject("member", new MemberDTO("홍길동", 20, '남', "서울시 서초구"));
        mv.addObject("hello", "hello!<h3>Thymealf</h3>");
        mv.setViewName("/lecture/expression");

        return mv;
    }

    @GetMapping("conditional")
    public ModelAndView conditional(ModelAndView mv){
        mv.addObject("num",1);
        mv.addObject("str","바나나");

        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(new MemberDTO("홍길동", 20, '남', "서울시 서초구"));
        memberList.add(new MemberDTO("김길동", 27, '여', "김포 비밀"));
        memberList.add(new MemberDTO("단길동", 25, '여', "고양 원흥역"));
        memberList.add(new MemberDTO("고길동", 25, '여', "천안역"));

        mv.addObject("memberList", memberList);
        mv.setViewName("/lecture/conditional");

        return mv;
    }

    @GetMapping("/etc")
    public ModelAndView etc(ModelAndView mv){
        SelectCriteria selectCriteria = new SelectCriteria(1, 10,3);
        mv.addObject(selectCriteria);

        MemberDTO member = new MemberDTO("고길동", 25, '여', "천안역");
        mv.addObject("member", member);

        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(new MemberDTO("홍길동", 20, '남', "서울시 서초구"));
        memberList.add(new MemberDTO("김길동", 27, '여', "김포 비밀"));
        memberList.add(new MemberDTO("단길동", 25, '여', "고양 원흥역"));
        memberList.add(new MemberDTO("고길동", 25, '여', "천안역"));

        mv.addObject("memberList", memberList);

        Map<String, MemberDTO> memberMap = new HashMap<>();
        memberMap.put("m01",new MemberDTO("홍길동", 20, '남', "서울시 서초구"));
        memberMap.put("m02",new MemberDTO("김길동", 27, '여', "김포 비밀"));
        memberMap.put("m03",new MemberDTO("단길동", 25, '여', "고양 원흥역"));
        memberMap.put("m04",new MemberDTO("고길동", 25, '여', "천안역"));

        mv.addObject("memberMap", memberMap);
        mv.setViewName("/lecture/etc");

        return mv;
    }
}