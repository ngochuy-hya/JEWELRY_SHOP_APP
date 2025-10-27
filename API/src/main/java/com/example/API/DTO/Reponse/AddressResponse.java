package com.example.API.DTO.Reponse;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private Integer addressId;
    private Integer userId;
    private String receiverName;
    private String phone;
    private String email;
    private String addressLine;
    private String area;
    private String landmark;
    private String city;
    private String state;
    private Boolean isDefault;
    private String createdAt;
}

