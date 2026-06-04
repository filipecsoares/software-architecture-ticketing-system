package com.fcs.customers.adapter.dataprovider;

import com.fcs.customers.adapter.dataprovider.jpa.entity.JpaAddress;
import com.fcs.customers.adapter.dataprovider.jpa.entity.JpaCustomer;
import com.fcs.customers.adapter.dataprovider.jpa.repository.JpaAddressRepository;
import com.fcs.customers.adapter.dataprovider.jpa.repository.JpaCustomerRepository;
import com.fcs.customers.entity.Customer;
import com.fcs.customers.usecase.gateway.CustomerGateway;
import com.fcs.customers.usecase.model.AddressResponseModel;
import com.fcs.customers.usecase.model.CustomerResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public record JpaCustomerDataProviderAdapter(JpaCustomerRepository jpaCustomerRepository, JpaAddressRepository jpaAddressRepository) implements CustomerGateway {

    @Override
    public Integer create(Customer toCreate) {

        JpaAddress jpaAddress = new JpaAddress();
        jpaAddress.setStreet(toCreate.getAddress().getStreet());
        jpaAddress.setNum(toCreate.getAddress().getNum());
        jpaAddress.setZipCode(toCreate.getAddress().getZipCode());

        jpaAddress.setBlock(toCreate.getAddress().getAdditionalAddressDetail().getBlock());
        jpaAddress.setFloor(toCreate.getAddress().getAdditionalAddressDetail().getFloor());
        jpaAddress.setNumber(toCreate.getAddress().getAdditionalAddressDetail().getNumber());

        jpaAddressRepository.save(jpaAddress);

        JpaCustomer jpaCustomer = new JpaCustomer();
        jpaCustomer.setEmail(toCreate.getEmail());
        jpaCustomer.setName(toCreate.getName());
        jpaCustomer.setDocument(toCreate.getDocument().getCode());
        jpaCustomer.setName(toCreate.getName());
        jpaCustomer.setAddress(jpaAddress);
        jpaCustomer.setPassword(toCreate.getPassword());

        jpaCustomerRepository.save(jpaCustomer);

        return jpaCustomer.getId();
    }

    @Override
    public boolean exists(String document) {
        Optional<JpaCustomer> jpaCustomerOptional = jpaCustomerRepository.findByDocument(document);
        return jpaCustomerOptional.isPresent();
    }

    @Override
    public List<CustomerResponseModel> getAll() {
        return jpaCustomerRepository.findAll().stream()
                .map(jpaCustomer -> new CustomerResponseModel(
                                jpaCustomer.getId(),
                                jpaCustomer.getEmail(),
                                jpaCustomer.getDocument(),
                                jpaCustomer.getName(),
                                new AddressResponseModel(
                                        jpaCustomer.getAddress().getStreet(),
                                        jpaCustomer.getAddress().getNum(),
                                        jpaCustomer.getAddress().getZipCode(),
                                        jpaCustomer.getAddress().getBlock(),
                                        jpaCustomer.getAddress().getFloor(),
                                        jpaCustomer.getAddress().getNumber()
                                )
                        )
                ).toList();
    }

    @Override
    public CustomerResponseModel getById(Integer customerId) {
        Optional<JpaCustomer> jpaCustomerOptional = jpaCustomerRepository.findById(customerId);
        if (jpaCustomerOptional.isPresent()) {
            JpaCustomer jpaCustomer = jpaCustomerOptional.get();
            return new CustomerResponseModel(
                    jpaCustomer.getId(),
                    jpaCustomer.getEmail(),
                    jpaCustomer.getDocument(),
                    jpaCustomer.getName(),
                    new AddressResponseModel(
                            jpaCustomer.getAddress().getStreet(),
                            jpaCustomer.getAddress().getNum(),
                            jpaCustomer.getAddress().getZipCode(),
                            jpaCustomer.getAddress().getBlock(),
                            jpaCustomer.getAddress().getFloor(),
                            jpaCustomer.getAddress().getNumber()
                    )
            );
        }
        return null;
    }

    @Override
    public void delete(Integer customerId) {
        Optional<JpaCustomer> jpaCustomerOptional = jpaCustomerRepository.findById(customerId);
        if (jpaCustomerOptional.isPresent()) {
            JpaCustomer jpaCustomer = jpaCustomerOptional.get();
            jpaCustomerRepository.delete(jpaCustomer);
        }
    }

    @Override
    public String getEncryptedPassword(String password) {
        return null;
    }

}
