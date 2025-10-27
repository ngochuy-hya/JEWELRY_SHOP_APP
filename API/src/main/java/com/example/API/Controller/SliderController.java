package com.example.API.Controller;

import com.example.API.Entity.Slider;
import com.example.API.Service.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sliders")
@CrossOrigin(origins = "*")
public class SliderController {



    @Autowired
    private SliderService sliderService;

    @GetMapping
    public List<Slider> getActiveSliders() {
        return sliderService.getActiveSliders();
    }
}
