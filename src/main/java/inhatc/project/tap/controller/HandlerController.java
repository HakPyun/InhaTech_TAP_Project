package inhatc.project.tap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HandlerController {

    @GetMapping("/403")
    public String accessDenied(){
        return "/Handler/403handler";
    }
}
