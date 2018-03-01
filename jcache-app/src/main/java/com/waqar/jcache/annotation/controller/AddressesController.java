package com.waqar.jcache.annotation.controller;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.waqar.jcache.annotation.Address;
import com.waqar.jcache.repositories.AddressesRepository;

/**
 * Created by @shaikhwaqar
 */
@SuppressWarnings("WeakerAccess")
@RestController
@RequestMapping(value = "/addresses", produces = "application/json")
@CacheDefaults(cacheName = "addresses")
public class AddressesController {

    private AddressesRepository addressesRepository;

    public AddressesController(AddressesRepository addressesRepository) {
        this.addressesRepository = addressesRepository;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.POST, consumes = "application/json")
    @CachePut
    public boolean post(@PathVariable("id")
                        @CacheKey
                            Integer id,
                        @RequestBody
                        @CacheValue
                            Address address) throws Exception {
        addressesRepository.save(address);
        return true;
    }

    @RequestMapping(value = "/{addressId}", method = RequestMethod.GET)
    @CacheResult
    public Address get(@PathVariable Integer addressId) throws Exception {
        return addressesRepository.findOne(addressId);
    }

    @RequestMapping(value = "/{addressId}", method = RequestMethod.DELETE)
    @CacheRemove
    public void delete(@PathVariable Integer addressId) throws Exception {
        Address address = addressesRepository.findOne(addressId);
        addressesRepository.delete(address);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @CacheRemoveAll
    public void deleteAll() throws Exception {
        addressesRepository.deleteAll();
    }

}
