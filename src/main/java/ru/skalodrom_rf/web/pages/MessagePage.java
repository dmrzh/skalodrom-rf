package ru.skalodrom_rf.web.pages;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**.*/
public class MessagePage extends BasePage{
    public MessagePage(PageParameters parameters) {
        super(parameters);
        add(new Label("message", i18n(extractParam(parameters, "message"))));
        add(new Label("error", i18n(extractParam(parameters, "error"))));
    }
    String i18n(String key){

        return Application.get().getResourceSettings().getLocalizer().getString(key,this, key);
     // return new ResourceModel(key).getObject();
    }

    public static String extractParam(PageParameters parameters, String s) {
        final Object o = parameters.get(s);
        if (!(o instanceof String[])) {
            return "";
        }
        String[] sa = (String[]) o;

        if (sa[0] == null) {
            return "";
        }
        String result ="";
        for(String line:sa){
           result=result+line;
        }

        return result ;
    }
}
