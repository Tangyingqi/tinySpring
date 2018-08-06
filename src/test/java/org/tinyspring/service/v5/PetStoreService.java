package org.tinyspring.service.v5;

import org.tinyspring.beans.factory.annotation.Autowired;
import org.tinyspring.dao.v5.AccountDao;
import org.tinyspring.dao.v5.ItemDao;
import org.tinyspring.stereotype.Component;
import org.tinyspring.util.MessageTracker;

/**
 * @author tangyingqi
 * @date 2018/7/30
 */
@Component(value = "petStore")
public class PetStoreService {

    @Autowired
    AccountDao accountDao;

    @Autowired
    ItemDao itemDao;

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }

    public void placeOrderWithException(){
        throw new NullPointerException();
    }
}
