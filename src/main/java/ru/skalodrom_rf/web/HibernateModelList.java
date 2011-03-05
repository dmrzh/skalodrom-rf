package ru.skalodrom_rf.web;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.model.LoadableDetachableModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**.*/
public class HibernateModelList<P extends PersistentEntity<K>,  K extends Serializable> 
                            extends LoadableDetachableModel<List<P>> {
    List<HibernateModel<P,K>> list;
     public HibernateModelList(Set<P> hList) {
        super(new ArrayList<P>(hList));
        this.list = new ArrayList<HibernateModel<P,K>>(hList.size());
        for(P pe:hList){
            list.add(new HibernateModel<P,K>(pe));
        }
    }

    public HibernateModelList(List<P> hList) {
        super(hList);
        this.list = new ArrayList<HibernateModel<P,K>>(hList.size());
        for(P pe:hList){
            list.add(new HibernateModel<P,K>(pe));
        }
    }
       @Override
    public void detach() {
        for(HibernateModel obj: list){
            obj.detach();
        }
    }


    @Override
    protected List<P> load() {
        List<P> hList= new ArrayList<P>(list.size());
        for(HibernateModel<P,K> obj: list){
            obj.load();
            hList.add(obj.getObject());
        }
        return hList;
    }
}
