package ru.skalodrom_rf.web.hibernate;

import org.apache.wicket.Page;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**OpenSessionInView implementation.*/
public class TransactionalWebRequestCycle extends WebRequestCycle {

    private final HibernateTransactionManager transactionManager;

    private TransactionStatus status;


    public TransactionalWebRequestCycle(HibernateTransactionManager transactionManager,WebApplication application, WebRequest request, Response response) {
        super(application, request, response);
        this.transactionManager=transactionManager;
    }


    @Override
    public Page onRuntimeException(Page page, RuntimeException e) {
        status.setRollbackOnly();
        return super.onRuntimeException(page, e);
    }

    @Override
    protected void onEndRequest() {
        super.onEndRequest();
        transactionManager.commit(status);

    }

    @Override

    protected void onBeginRequest() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        super.onBeginRequest();
    }

}
