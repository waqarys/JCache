package com.waqar.jcache.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waqar.jcache.annotation.Address;

/**
 * Created by @shaikhwaqar
 */
@Repository
public interface AddressesRepository extends CrudRepository<Address, Integer> {

}
