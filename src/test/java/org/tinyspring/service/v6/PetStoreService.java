package org.tinyspring.service.v6;

import org.tinyspring.stereotype.Component;
import org.tinyspring.util.MessageTracker;

/**
 * @author tangyingqi
 * @date 2018/9/30
 */
@Component(value = "petStore")
public class PetStoreService implements IPetStoreService{

    public PetStoreService() {
    }

    @Override
    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }
}
