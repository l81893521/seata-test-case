package io.seata.test.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 账户
 * @Author will
 */
@LocalTCC
public interface AccountService {

    /**
     * 余额扣款
     * @param userId 用户id
     * @param money 扣款金额
     */
    @TwoPhaseBusinessAction(name = "debit", commitMethod = "commitDebit", rollbackMethod = "rollbackDebit")
    void debit(BusinessActionContext actionContext,
               @BusinessActionContextParameter(paramName = "userId") String userId,
               @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 提交扣款
     * @author jiawei.zhang
     * @date 2020/4/9
     * @return boolean
     */
    boolean commitDebit(BusinessActionContext actionContext);

    /**
     * 回滚扣款
     * @author jiawei.zhang
     * @date 2020/4/9
     * @return boolean
     */
    boolean rollbackDebit(BusinessActionContext actionContext);
}
