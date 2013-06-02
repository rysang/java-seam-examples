package org.price.test.tx.interceptors;

import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.appengine.api.datastore.DatastoreService;

public class TransactionInterceptor implements MethodInterceptor {

    private static final Logger LOG = Logger.getLogger(TransactionInterceptor.class.getName());

    private DatastoreService    dataStoreService;
    private String              startsWith;

    public TransactionInterceptor() {

    }

    public DatastoreService getDataStoreService() {
        return dataStoreService;
    }

    public void setDataStoreService(DatastoreService dataStoreService) {
        this.dataStoreService = dataStoreService;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        boolean wasActive = false;

        if (methodInvocation.getMethod().getName().startsWith(startsWith)) {

            try {
                wasActive = dataStoreService.getCurrentTransaction().isActive();
            }
            catch (Exception e) {
                LOG.warning(e.toString());
            }

            if (!wasActive) {
                LOG.info("Opened transaction.");
                dataStoreService.beginTransaction();
            }

            try {
                Object result = methodInvocation.proceed();
                if (!wasActive) {
                    dataStoreService.getCurrentTransaction().commit();
                    LOG.info("Commited transaction.");
                }
                return result;
            }
            catch (Exception e) {
                if (wasActive) {
                    dataStoreService.getCurrentTransaction().rollback();
                    LOG.info("Rolled back transaction.");
                }
                throw e;
            }
        }

        return methodInvocation.proceed();
    }
}
