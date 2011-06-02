package ru.skalodrom_rf.model;

import net.sf.autodao.PersistentEntity;

import java.io.Serializable;
import java.util.Comparator;

/**Compare two PersistentEntity implementations using they primary keys.*/
public class IdCompartor implements Comparator<PersistentEntity>{
    /**
     * Compare two Persistent entity.
     * @param o1 primary key of o1 must be comparable with primary key of o2.
     */

    @Override
    public int compare(PersistentEntity o1, PersistentEntity o2) {
        Comparable<Serializable> p1 = (Comparable)o1.getPrimaryKey();
        Serializable p2 = o2.getPrimaryKey();
        if(p1 ==null || p2== null) {
            return 0;
        }
        return p1.compareTo(p2);
    }
}
