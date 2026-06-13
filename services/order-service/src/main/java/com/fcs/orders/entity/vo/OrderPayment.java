package com.fcs.orders.entity.vo;

import java.time.LocalDate;

public record OrderPayment(Long cardNumber, Integer cvv, String cardHolderName, LocalDate exp, CardBanner banner) {

}
