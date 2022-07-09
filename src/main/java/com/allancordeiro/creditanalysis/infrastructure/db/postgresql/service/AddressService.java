package com.allancordeiro.creditanalysis.infrastructure.db.postgresql.service;

import com.allancordeiro.creditanalysis.domain.customer.valueObject.address.Address;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.model.AddressModel;
import com.allancordeiro.creditanalysis.infrastructure.db.postgresql.repositories.customer.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AddressService  {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<AddressModel> findById(Long id) {
        return addressRepository.findById(id);
    }

    public void delete(Long id) {
        Optional<AddressModel> addressToBeDeleted = this.findById(id);
        addressToBeDeleted.ifPresent(addressRepository::delete);
    }

    public Long create(Address entity) {
        AddressModel addressModel = new AddressModel(
                entity.getStreet(),
                entity.getNumber(),
                entity.getNeighborhood(),
                entity.getCep(),
                entity.getCity(),
                entity.getState(),
                entity.getComplement()
        );
        AddressModel savedAddress = this.addressRepository.save(addressModel);

        return savedAddress.getId();
    }
}
