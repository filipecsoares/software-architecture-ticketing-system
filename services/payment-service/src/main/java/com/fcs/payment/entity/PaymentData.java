package com.fcs.payment.entity;

import com.fcs.payment.entity.vo.CardBanner;

import java.time.LocalDate;

public record PaymentData(Long cardNumber, Integer cvv, String cardHolderName, LocalDate exp, CardBanner banner) {

    public boolean isValid() {
        return cardNumber != null &&
                cvv != null &&
                cardHolderName != null && !cardHolderName.isEmpty() &&
                exp != null &&
                banner != null;
    }
}
