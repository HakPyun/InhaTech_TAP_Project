package inhatc.project.tap.controller;

import inhatc.project.tap.dto.ShopImgDto;
import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.service.ShopImgService;
import inhatc.project.tap.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopImgService shopImgService;

    @GetMapping("/map/data")
    public ResponseEntity<List<ShopMenuFormDto>> getShopData() {
        List<ShopMenuFormDto> ShopMenuFormDtoList = shopService.findAll();
        return ResponseEntity.ok(ShopMenuFormDtoList);
    }

    @GetMapping("/map/imgdata")
    public ResponseEntity<List<ShopImgDto>> getShopImgData() {
        List<ShopImgDto> ShopImgDtoList = shopImgService.findAll();
        return ResponseEntity.ok(ShopImgDtoList);
    }
}
