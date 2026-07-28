package com.grocery.portal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Integer> {
    DiscountCode findByCode(String code);
}