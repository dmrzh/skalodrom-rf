package ru.skalodrom_rf;

import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;

/**
 * NamingStrategy
 */
public class QuotedNamingStrategy extends DefaultComponentSafeNamingStrategy {
    @Override
    public String classToTableName(String className) {
        return "\""+super.classToTableName(className)+"\"";
    }
}

