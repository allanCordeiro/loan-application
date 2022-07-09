package com.allancordeiro.creditanalysis.infrastructure.gateway.customer;

import com.allancordeiro.creditanalysis.domain.customer.entity.Customer;
import com.allancordeiro.creditanalysis.domain.customer.exceptions.CustomerNotFoundException;
import com.allancordeiro.creditanalysis.domain.customer.gateway.CustomerGateway;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.domain.customer.valueObject.cpf.Cpf;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.AddressModel;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.CustomerModel;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.CustomerRepository;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.service.AddressService;
import org.springframework.stereotype.Component;


import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerGatewayDb implements CustomerGateway {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    public CustomerGatewayDb(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Customer> findByEmail(String email) throws Exception {

        Optional<CustomerModel> customerModel = this.customerRepository.findByEmail(email);

        if(customerModel.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = new Customer(
            customerModel.get().getId(),
            customerModel.get().getName(),
            customerModel.get().getEmail(),
            customerModel.get().getRg(),
            customerModel.get().getIncomeValue(),
            customerModel.get().getPassword()
        );

        Cpf cpf = new Cpf(customerModel.get().getCpf());
        customer.AddCpf(cpf);

        AddressService addressService = new AddressService(this.addressRepository);
        Optional<AddressModel> addressModel = addressService.findById(customerModel.get().getAddress());
        if(addressModel.isEmpty()) {
            return Optional.empty();
        }
        Address customerAddress = new Address(
                addressModel.get().getStreet(),
                addressModel.get().getNumber(),
                addressModel.get().getNeighborhood(),
                addressModel.get().getCep(),
                addressModel.get().getCity(),
                addressModel.get().getState(),
                addressModel.get().getComplement()
        );

        customer.ChangeAddress(customerAddress);
        if(customerModel.get().getStatus() == Boolean.TRUE) {
            customer.Activate();
        }

        return Optional.of(customer);
    }

    @Override
    public void create(Customer entity) {

        AddressService addressService = new AddressService(this.addressRepository);
        Long addressId = addressService.create(entity.getAddress());

        CustomerModel customerModel = new CustomerModel(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRg(),
                entity.getCpf().GetMaskedCpf(),
                addressId,
                entity.getIncomeValue().floatValue(),
                entity.getPassword(),
                entity.getStatus()
        );

        customerRepository.save(customerModel);

    }

    @Override
    public void update(Customer entity) throws Exception {
        AddressService addressService = new AddressService(this.addressRepository);
        CustomerModel customerModel = this.customerRepository
                .findById(entity.getId())
                .orElseThrow(CustomerNotFoundException::new);

        AddressModel addressModel = addressService
                .findById(customerModel.getAddress())
                .orElseThrow();
        addressService.delete(addressModel.getId());

        this.create(entity);
    }

    @Override
    public Optional<Customer> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findById(UUID id) throws Exception {

        CustomerModel customerModel = this.customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        AddressService addressService = new AddressService(this.addressRepository);
        Optional<AddressModel> addressModel = addressService.findById(customerModel.getAddress());
        if(addressModel.isEmpty()) {
            throw new Exception("address not found");
        }
        Address customerAddress = new Address(
                addressModel.get().getStreet(),
                addressModel.get().getNumber(),
                addressModel.get().getNeighborhood(),
                addressModel.get().getCep(),
                addressModel.get().getCity(),
                addressModel.get().getState(),
                addressModel.get().getComplement()
        );

        Customer customer = new Customer(
                customerModel.getId(),
                customerModel.getName(),
                customerModel.getEmail(),
                customerModel.getRg(),
                customerModel.getIncomeValue().floatValue(),
                customerModel.getPassword()
        );
        customer.AddCpf(new Cpf(customerModel.getCpf()));
        customer.ChangeAddress(customerAddress);
        if(customerModel.getStatus() == Boolean.TRUE) {
            customer.Activate();
        }
        return Optional.of(customer);
    }
}
