package com.example.API.Service;

import com.example.API.DTO.Reponse.TodayOfferResponse;
import com.example.API.Repository.FlashDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashDealService {

    private final FlashDealRepository flashDealRepository;

    public List<TodayOfferResponse> getTodayOffers() {
        return flashDealRepository.getTodayOffers();
    }
}
