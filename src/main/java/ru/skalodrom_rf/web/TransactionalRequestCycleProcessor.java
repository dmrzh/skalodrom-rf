package ru.skalodrom_rf.web;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.RequestParameters;
import org.springframework.transaction.annotation.Transactional;

/**
*/
class TransactionalRequestCycleProcessor implements IRequestCycleProcessor {
    private final IRequestCycleProcessor rcp = new WebRequestCycleProcessor();

    public TransactionalRequestCycleProcessor() {
    }

    @Override
    public IRequestCodingStrategy getRequestCodingStrategy() {
        return rcp.getRequestCodingStrategy();
    }

    @Override
    public IRequestTarget resolve(RequestCycle requestCycle, RequestParameters requestParameters) {
        return rcp.resolve(requestCycle,requestParameters);
    }

    @Override
    @Transactional(readOnly = false)
    public void processEvents(RequestCycle requestCycle) {
        rcp.processEvents(requestCycle);
    }

    @Override
    @Transactional(readOnly = true)
    public void respond(RequestCycle requestCycle) {
        rcp.respond(requestCycle);
    }

    @Override
    @Transactional(readOnly = true)
    public void respond(RuntimeException e, RequestCycle requestCycle) {
        rcp.respond(e,requestCycle);
    }
}
