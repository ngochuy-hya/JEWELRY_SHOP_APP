package com.example.API.Controller;

import com.example.API.DTO.Reponse.AddressResponse;
import com.example.API.Entity.Address;
import com.example.API.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Thêm địa chỉ mới cho user
    @PostMapping("/add")
    public ResponseEntity<AddressResponse> addAddress(@RequestParam Integer userId,
                                                      @RequestBody Address address) {
        AddressResponse saved = addressService.addAddress(userId, address);
        return ResponseEntity.ok(saved);
    }

    // Lấy danh sách địa chỉ theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Integer userId) {
        List<AddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    // Cập nhật địa chỉ
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Integer addressId,
                                                         @RequestBody Address newData) {
        AddressResponse updated = addressService.updateAddress(addressId, newData);
        return ResponseEntity.ok(updated);
    }

    // Xóa địa chỉ
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.ok().build();
    }

    // Đặt địa chỉ mặc định
    @PostMapping("/set-default")
    public ResponseEntity<Void> setDefaultAddress(@RequestParam Integer userId,
                                                  @RequestParam Integer addressId) {
        addressService.setDefaultAddress(userId, addressId);
        return ResponseEntity.ok().build();
    }
}

