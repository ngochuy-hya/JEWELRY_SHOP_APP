package com.example.API.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")  // tên bảng viết thường snake_case
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
}
