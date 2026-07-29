package com.grocery.portal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DiscountCodeData implements CommandLineRunner {

    private final DiscountCodeRepository discountCodeRepository;

    public DiscountCodeData(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (discountCodeRepository.count() == 0) {
            discountCodeRepository.save(createDiscountCode("SAVE10", "PERCENTAGE", "0.10"));
            discountCodeRepository.save(createDiscountCode("SAVE20", "PERCENTAGE", "0.20"));
            discountCodeRepository.save(createDiscountCode("WELCOME5", "PERCENTAGE", "0.05"));
        }
    }

    private DiscountCode createDiscountCode(String code, String discountType, String percentage) {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setCode(code);
        discountCode.setDiscountType(discountType);
        discountCode.setPercentage(new BigDecimal(percentage));
        discountCode.setActive(true);
        return discountCode;
    }
}
