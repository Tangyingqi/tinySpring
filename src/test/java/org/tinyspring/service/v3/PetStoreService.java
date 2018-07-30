package org.tinyspring.service.v3;

import org.tinyspring.dao.AccountDao;
import org.tinyspring.dao.ItemDao;

/**
 * @author tangyingqi
 * @date 2018/7/9
 */
public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;
    private int version;
    public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = -1;
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public int getVersion() {
        return version;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}
