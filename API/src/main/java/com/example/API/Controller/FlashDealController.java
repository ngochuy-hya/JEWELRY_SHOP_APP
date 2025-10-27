package com.example.API.Controller;

import com.example.API.DTO.Reponse.TodayOfferResponse;
import com.example.API.Service.FlashDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flash-deal")
@RequiredArgsConstructor
public class FlashDealController {

    private final FlashDealService flashDealService;

    @GetMapping("/today-offers")
    public List<TodayOfferResponse> getTodayOffers() {
        return flashDealService.getTodayOffers();
    }
}
