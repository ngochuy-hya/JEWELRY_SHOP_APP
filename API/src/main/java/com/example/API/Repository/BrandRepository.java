package com.example.API.Repository;

import com.example.API.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("SELECT DISTINCT b.name FROM Brand b")
    List<String> findAllBrandNames();

}