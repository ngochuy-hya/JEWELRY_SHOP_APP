package com.example.API.Service;

import com.example.API.Entity.Product;
import com.example.API.Entity.ProductVariant;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> withFilters(
            List<String> brandNames,
            List<String> categoryNames,
            List<String> sizes,
            Float minPrice,
            Float maxPrice
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (brandNames != null && !brandNames.isEmpty()) {
                predicates.add(root.get("brand").get("name").in(brandNames));
            }

            if (categoryNames != null && !categoryNames.isEmpty()) {
                predicates.add(root.get("category").get("name").in(categoryNames));
            }

            if (sizes != null && !sizes.isEmpty()) {
                Join<Product, ProductVariant> variantJoin = root.join("variants", JoinType.INNER);
                predicates.add(variantJoin.get("size").in(sizes));
                query.distinct(true); // Tránh trùng sản phẩm
            }

            if (minPrice != null || maxPrice != null) {
                Join<Product, ProductVariant> variantJoin = root.join("variants", JoinType.INNER);
                if (minPrice != null) {
                    predicates.add(cb.greaterThanOrEqualTo(variantJoin.get("price"), minPrice));
                }
                if (maxPrice != null) {
                    predicates.add(cb.lessThanOrEqualTo(variantJoin.get("price"), maxPrice));
                }
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
