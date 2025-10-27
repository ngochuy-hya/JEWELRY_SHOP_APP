package com.example.API.Repository;

import com.example.API.Entity.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<Slider, Integer> {
    List<Slider> findByIsActiveTrueOrderByDisplayOrderAsc();
}
