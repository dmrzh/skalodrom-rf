package ru.skalodrom_rf.dataloader;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * Wrapper for transactions.
 */
public class DataloaderInit implements InitializingBean {
    @Resource
    Dataloader dataloader;

    @Override
    public void afterPropertiesSet() throws Exception {
        dataloader.initializeDatabase();
    }
}
