package ru.skalodrom_rf.web.hibernate;

import net.sf.autodao.PersistentEntity;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**.*/
public class HibernateModelList<P extends PersistentEntity>
                            extends LoadableDetachableModel<List<P>> {
    List<HibernateModel<P>> list;
     public HibernateModelList(Set<P> hList) {
        this(new ArrayList<P>(hList));
    }

    public HibernateModelList(List<P> hList) {
        super(hList);
        this.list = new ArrayList<HibernateModel<P>>(hList.size());
        for(P pe:hList){
            list.add(new HibernateModel<P>(pe));
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
        for(HibernateModel<P> obj: list){
            obj.load();
            hList.add(obj.getObject());
        }
        return hList;
    }
}
