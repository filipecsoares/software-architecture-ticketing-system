package com.fcs.payment.adapter.dataprovider;

import com.fcs.payment.adapter.dataprovider.security.CryptoUtil;
import com.fcs.payment.adapter.dataprovider.security.KeyUtil;
import com.fcs.payment.usecase.gateway.DecryptPaymentRequestDataGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JceDecryptDataProviderAdapter implements DecryptPaymentRequestDataGateway {

    @Value(value = "${hash.encrypt.secret}")
    private String hashEncryptSecret;

    @Override
    public String decrypt(String encryptedValue) {
        return CryptoUtil.decrypt(encryptedValue, KeyUtil.stringToKey(hashEncryptSecret));
    }
}
