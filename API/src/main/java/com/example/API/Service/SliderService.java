package com.example.API.Service;

import com.example.API.Entity.Slider;
import com.example.API.Repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderService {

    @Autowired
    private SliderRepository sliderRepository;

    public List<Slider> getActiveSliders() {
        return sliderRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }
}
