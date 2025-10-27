package com.example.API.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product_imports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer importId;

    // Nếu bạn có bảng suppliers thì liên kết ở đây, nullable nếu không bắt buộc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "imported_at")
    private LocalDateTime importedAt;

    @Column(columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "productImport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockHistory> stockHistories;

    @PrePersist
    protected void onCreate() {
        if (importedAt == null) {
            importedAt = LocalDateTime.now();
        }
    }
}
