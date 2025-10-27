package com.example.API.Service;

import com.example.API.DTO.Reponse.AddressResponse;
import com.example.API.Entity.Address;
import com.example.API.Entity.User;
import com.example.API.Repository.AddressRepository;
import com.example.API.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddressResponse addAddress(Integer userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user);

        // Nếu isDefault = true thì unset tất cả các địa chỉ khác của user
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            unsetOtherDefaults(userId);
        }

        return toResponse(addressRepository.save(address));
    }

    public List<AddressResponse> getAddressesByUserId(Integer userId) {
        return addressRepository.findByUserUserId(userId)
                .stream()
                // Sắp xếp: địa chỉ mặc định lên đầu
                .sorted(Comparator.comparing(Address::getIsDefault).reversed())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse updateAddress(Integer addressId, Address newData) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setReceiverName(newData.getReceiverName());
        address.setPhone(newData.getPhone());
        address.setEmail(newData.getEmail());
        address.setAddressLine(newData.getAddressLine());
        address.setArea(newData.getArea());
        address.setLandmark(newData.getLandmark());
        address.setCity(newData.getCity());
        address.setState(newData.getState());

        if (Boolean.TRUE.equals(newData.getIsDefault())) {
            unsetOtherDefaults(address.getUser().getUserId());
            address.setIsDefault(true);
        } else {
            address.setIsDefault(false);
        }

        return toResponse(addressRepository.save(address));
    }

    public void deleteAddress(Integer addressId) {
        addressRepository.deleteById(addressId);
    }

    @Transactional
    public void setDefaultAddress(Integer userId, Integer addressId) {
        unsetOtherDefaults(userId);

        Address newDefault = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        newDefault.setIsDefault(true);
        addressRepository.save(newDefault);
    }

    private void unsetOtherDefaults(Integer userId) {
        List<Address> addresses = addressRepository.findByUserUserId(userId);
        for (Address addr : addresses) {
            if (Boolean.TRUE.equals(addr.getIsDefault())) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
        }
    }

    private AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .userId(address.getUser().getUserId())
                .receiverName(address.getReceiverName())
                .phone(address.getPhone())
                .email(address.getEmail())
                .addressLine(address.getAddressLine())
                .area(address.getArea())
                .landmark(address.getLandmark())
                .city(address.getCity())
                .state(address.getState())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt() != null ? address.getCreatedAt().toString() : null)
                .build();
    }
}
