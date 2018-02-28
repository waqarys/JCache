package com.waqar.jcache;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.cache.Cache;
import javax.cache.processor.EntryProcessorResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waqar.jcache.process.CustomerEntryProcessor;

/**
 * Created by @shaikhwaqar
 */
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
@SuppressWarnings("unused")
public class CustomersController {

    private static final Log LOG = LogFactory.getLog(CustomersController.class);

    private Cache<Integer, Customer> customerCache;


    public CustomersController(Cache<Integer, Customer> customerCache) {
        this.customerCache = customerCache;
    }

    // GET /customers/{id}
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Customer get(@PathVariable("id") Integer id) {
        LOG.info("get for id " + id);
        return customerCache.get(id);
    }


    // POST /customers
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public boolean post(@RequestBody List<Customer> customers) {
        Map<Integer, Customer> map = customers
                .stream()
                .collect(Collectors.toMap(Customer::getId, customer -> customer));
        customerCache.putAll(map);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, Customer> getAll(@RequestParam("keys") Set<Integer> keys) {
        return customerCache.getAll(keys);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    public boolean remove(@PathVariable("key") Integer key) {
        return customerCache.remove(key);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void removeAll() {
        customerCache.clear();
    }
    
    @RequestMapping(value="/age", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, Integer> getAge(@RequestParam("keys") Set<Integer> keys,
                                        @RequestParam(name = "uppercase") Boolean uppercase) {

       Map<Integer, EntryProcessorResult<Integer>> result =
               customerCache.invokeAll(keys, new CustomerEntryProcessor(), uppercase);

       return result.entrySet().stream()
               .collect(Collectors.toMap(
                       Map.Entry::getKey,
                       e -> e.getValue().get()
               ));
    }



}
