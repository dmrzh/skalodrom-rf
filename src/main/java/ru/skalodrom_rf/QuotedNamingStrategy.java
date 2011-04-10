package ru.skalodrom_rf;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * NamingStrategy
 */
public class QuotedNamingStrategy extends DefaultNamingStrategy{
    @Override
    public String classToTableName(String className) {
        return "\""+super.classToTableName(className)+"\"";
    }
}

