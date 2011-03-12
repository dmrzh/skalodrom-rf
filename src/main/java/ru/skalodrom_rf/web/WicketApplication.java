package ru.skalodrom_rf.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import ru.skalodrom_rf.web.pages.ActivateUserPage;
import ru.skalodrom_rf.web.pages.IndexPage;
import ru.skalodrom_rf.web.pages.LoginPage;
import ru.skalodrom_rf.web.pages.ProfileEditPage;
import ru.skalodrom_rf.web.pages.ProfileViewPage;
import ru.skalodrom_rf.web.pages.RegisterPage;
import ru.skalodrom_rf.web.pages.ReminderPage;
import ru.skalodrom_rf.web.pages.SearchPage;
import ru.skalodrom_rf.web.pages.SendMessagePage;
import ru.skalodrom_rf.web.pages.SkalodromPage;

import javax.annotation.Resource;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see ru.skalodrom_rf.Start#main(String[])
 */
public class WicketApplication extends WebApplication{

    @Resource
    IRequestCycleProcessor openSessionInView;

    /**
     * Constructor
     */
	public WicketApplication(){
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<IndexPage> getHomePage(){
		return IndexPage.class;
	}

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new SpringComponentInjector(this));

        mountBookmarkablePage("/register.html", RegisterPage.class);
        mountBookmarkablePage("/index.html", IndexPage.class);
        mountBookmarkablePage("/login.html", LoginPage.class);
        mountBookmarkablePage("/activate.html", ActivateUserPage.class);

        mountBookmarkablePage("/sendMessage.html", SendMessagePage.class);
        mountBookmarkablePage("/skalodrom.html", SkalodromPage.class);
        mountBookmarkablePage("/search.html", SearchPage.class);
        mountBookmarkablePage("/reminder.html", ReminderPage.class);
        mountBookmarkablePage("/profileView.html", ProfileViewPage.class);
        mountBookmarkablePage("/profileEdit.html", ProfileEditPage.class);
        mount(new IndexedParamUrlCodingStrategy("/users",ProfileViewPage.class));
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return openSessionInView;
    }

}
