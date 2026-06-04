package inhatc.project.tap.controller;

import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MapController {

    @GetMapping("/map")
    public String responseViewV5(Model model) {
        return "maptest";
    }

    @GetMapping("/shopDetails")
    public String shopDetails() {return "shopDetails";}
}
