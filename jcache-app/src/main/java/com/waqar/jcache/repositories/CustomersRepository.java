package com.waqar.jcache.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waqar.jcache.Customer;

/**
 * Created by @shaikhwaqar
 */
@Repository
public interface CustomersRepository extends CrudRepository<Customer, Integer> {

    @Query("delete from Customer c where c.id IN :keys")
    void deleteAll(@Param("keys") Collection<Integer> keys);
}
