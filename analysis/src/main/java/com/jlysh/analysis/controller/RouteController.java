package com.jlysh.analysis.controller;

import com.jlysh.analysis.pojo.beans.GlobalVariable;
import com.jlysh.analysis.pojo.beans.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 江冷易水寒
 * @data 2021/4/20 9:51
 * 页面跳转控制
 */



@Controller
public class RouteController {
    @GetMapping({"/","/index"})
        public String index(){
            return "index";
        }

    @GetMapping("/personal")
    public String person(){
        return "personal";
    }

    @RequestMapping("/login")
    public String log(){
        return "log";
    }

    @RequestMapping("/reg")
    public String reg(){
        return "reg";
    }

    @GetMapping("/main")
    public String main(){
        return "/main";
    }

    @GetMapping("/perReport")
    public String perReport(){
        return "perReport";
    }

    @GetMapping("/resume")
    public String resume(){
        return "resume";
    }

    //查询topic  关键词查询
//    @GetMapping("/topicReport")
//    public ModelAndView topicReport(String topic) {
////        System.out.println(topic);
//        //转发到业务层
//          return new ModelAndView("forward:/service/topicReport","topic",topic);
//        }
//    综合查询
    @GetMapping("/synthesisReport")
    public ModelAndView synthesisReport() {
            //转发到业务层
            return new ModelAndView("forward:/service/synthesisReport");
    }

    @GetMapping("/topicAnalysis")
    public String topicAnalysis(){
        return "topicAnalysis";
    }

    @ResponseBody
    @GetMapping("/save_topicAnalysis_keyword")
    public String topicAnalysis(@RequestParam(value = "keyword",required = false,defaultValue = "") String keyword){
        GlobalVariable.keyword = keyword;
        return "save_ok";
    }

    @GetMapping("/topic")
    public String topic(){
        return "topic";
    }
}
