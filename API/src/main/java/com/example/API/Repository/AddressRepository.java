package com.example.API.Repository;

import com.example.API.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUserUserId(Integer userId);
    Address findByUserUserIdAndIsDefaultTrue(Integer userId);
}
