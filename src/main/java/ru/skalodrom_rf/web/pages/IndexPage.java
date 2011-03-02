package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.model.Scalodrom;
import ru.skalodrom_rf.web.HibernateModel;

import java.util.List;

/**
 * Homepage
 */
public class IndexPage extends BasePage {
    @SpringBean
    ScalodromDao scalodromDao;

    public IndexPage() {
        ChoiceRenderer choiceRenderer = new ChoiceRenderer("name", "name");
        final List<Scalodrom> list = scalodromDao.findAll();
        final DropDownChoice dropDownChoice = new DropDownChoice("scalodroms", list, choiceRenderer);
        dropDownChoice.setModel(new HibernateModel(list.get(0)));
        add(dropDownChoice);

    }

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
//    public IndexPage(final PageParameters parameters) {
//
//    }
}
