package ru.skalodrom_rf.web.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**
 */
public class LoginPage extends BasePage {

    public LoginPage(PageParameters parameters) {
        super(parameters);
        if (parameters.get("wrongPassword") != null) {
            add(new Label("feedback", "Неверный логин или пароль"));
        } else {
            add(new Label("feedback"));
        }
    }
}
