package jp.johkakka.lunch;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public String from(ModelMap modelMap, @RequestParam("from") String name) throws MalformedURLException {
        if (name.isBlank()){
            return "index";
        }

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get("internal"), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            String m = e.toString();
            modelMap.addAttribute("message", m);
            return "error";
        }

        if (lines.isEmpty()){
            String m = "APIキーが見つかりません";
            modelMap.addAttribute("message", m);
            return "error";
        }

        String geoUrlPath = "https://maps.googleapis.com/maps/api/geocode/json?address="+name+"&region=jp&key="+lines.get(0);

        String geoJson = "";

        try {
            URL geoUrl = new URL(geoUrlPath);
            InputStream is = geoUrl.openStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String temp;

            while ((temp = br.readLine()) != null){
                geoJson = geoJson + temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
            String m = e.toString();
            modelMap.addAttribute("message", m);
            return "error";
        }


        modelMap.addAttribute("from", name);
        return "roulett";
    }
}
