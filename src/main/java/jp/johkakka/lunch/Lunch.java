package jp.johkakka.lunch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Lunch {

    @GetMapping(value={"/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/roulett")
    public String here(){
        return "roulett";
    }

    @PostMapping("/roulett")
    public String from(ModelMap modelMap, @RequestParam("from") String name){
        if (name.isBlank()){
            return "index";
        }
        modelMap.addAttribute("from", name);
        return "roulett";
    }
}
