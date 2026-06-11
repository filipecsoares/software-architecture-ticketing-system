package com.fcs.payment.adapter.dataprovider;

import com.fcs.payment.adapter.dataprovider.security.CryptoUtil;
import com.fcs.payment.adapter.dataprovider.security.KeyUtil;
import com.fcs.payment.entity.PaymentData;
import com.fcs.payment.usecase.gateway.EncryptPaymentRequestDataGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JceEncryptDataProviderAdapter implements EncryptPaymentRequestDataGateway {

    @Value(value = "${hash.encrypt.secret}")
    private String hashEncryptSecret;

    @Override
    public String encrypt(PaymentData paymentData) {
        return CryptoUtil.encrypt(paymentData.toString(), KeyUtil.stringToKey(hashEncryptSecret));
    }
}
