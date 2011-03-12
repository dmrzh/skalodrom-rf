package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.web.HibernateModel;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SearchPage extends BasePage{
    @SpringBean
    ProfileDao profileDao;
    public SearchPage() {


        List<HibernateModel<Profile,Long>> hibernateModels= new ArrayList<HibernateModel<Profile,Long>>();
        for(Profile pe:profileDao.findAll()){
            hibernateModels.add(new HibernateModel<Profile,Long>(pe));
        }

        final ListDataProvider<HibernateModel<Profile,Long>> dataProvider = new ListDataProvider(hibernateModels);
        final DataView profilesTable = new DataView<HibernateModel<Profile,Long>>("profilesTable",dataProvider ){
            @Override
            protected void populateItem(Item<HibernateModel<Profile, Long>> hibernateModelItem) {
                final Profile profile = hibernateModelItem.getModelObject().getObject();
                hibernateModelItem.add(new Label("fio",profile.getFio()));
                hibernateModelItem.add(new Label("weight",""+profile.getWeight()));
                hibernateModelItem.add(new Label("level",profile.getClimbLevel().name()));

                final PageParameters pageParameters = new PageParameters("0="+profile.getUser().getLogin());
                hibernateModelItem.add(new BookmarkablePageLink("viewProfile",ProfileViewPage.class, pageParameters));               

            }

        };
        add(profilesTable);

    }
}
