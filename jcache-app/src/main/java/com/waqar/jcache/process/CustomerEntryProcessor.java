package com.waqar.jcache.process;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

import com.waqar.jcache.Customer;

/**
 * Created by @shaikhwaqar
 */
public class CustomerEntryProcessor
    implements EntryProcessor<Integer, Customer, Integer> {

    @Override
    public Integer process(MutableEntry<Integer, Customer> entry, Object... arguments)
            throws EntryProcessorException {
        Customer customer = entry.getValue();
        //Test block to check for exceptions
        /*if(customer.getId() == 1){
        	throw new RuntimeException();
        }*/
        boolean uppercase = (boolean)arguments[0];
        if (uppercase) {
            customer.setLastName(customer.getLastName().toUpperCase());
        } else {
            customer.setLastName(customer.getLastName().toLowerCase());
        }
        entry.setValue(customer);
        return calculateAge(customer);
    }

    private Integer calculateAge(Customer customer) {
        if (null == customer) {
            return null;
        }

        LocalDate dobAtLocalTimezone = customer
                .getDob()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return Period.between(dobAtLocalTimezone, LocalDate.now()).getYears();

    }
}

