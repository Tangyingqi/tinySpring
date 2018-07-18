package org.tinyspring.service.v4;

import org.tinyspring.beans.factory.annotation.Autowired;
import org.tinyspring.dao.v4.AccountDao;
import org.tinyspring.dao.v4.ItemDao;
import org.tinyspring.stereotype.Component;

/**
 * @author tangyingqi
 * @date 2018/7/17
 */
@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}
