package org.tinyspring.service.v2;

import org.tinyspring.dao.AccountDao;
import org.tinyspring.dao.ItemDao;

/**
 * @author tangyingqi
 * @date 2018/7/2
 */
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
