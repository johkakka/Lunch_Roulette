package jp.johkakka.lunch;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Lunch {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/here")
    public String here(){
        return "index";
    }

    @PostMapping
    public String from(){
        return "index";
    }
}
