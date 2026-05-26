package com.fcs.orders.entity.vo;

import java.time.LocalDate;

public record OrderPayment(Integer cardNumber, Integer cvv, String cardHolderName, LocalDate exp, CardBanner banner) {

}
