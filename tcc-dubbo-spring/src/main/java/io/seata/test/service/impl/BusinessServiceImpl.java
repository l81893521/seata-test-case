package io.seata.test.service.impl;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.test.service.AccountService;
import io.seata.test.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * @author will.zjw
  * @date 2019-04-19 16:26
  */
public class BusinessServiceImpl implements BusinessService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);

    private AccountService accountService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "gts-tcc-purchase")
    public void purchase(String userId, String commodityCode, int orderCount) {
        logger.info("purchase begin ... xid: " + RootContext.getXID());
        accountService.debit(new BusinessActionContext(), userId, 10);
//        storageService.deduct(commodityCode, orderCount);
//        orderService.create(userId, commodityCode, orderCount);
        //制造异常
//        throw new RuntimeException("xxx");
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
