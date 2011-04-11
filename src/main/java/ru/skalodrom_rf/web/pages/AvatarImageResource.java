package ru.skalodrom_rf.web.pages;

import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.web.hibernate.HibernateModel;

/**.*/
class AvatarImageResource extends DynamicImageResource {
    private final HibernateModel<Profile> model;

    public AvatarImageResource(HibernateModel<Profile> model) {
        this.model = model;
    }

    @Override
    protected byte[] getImageData() {
        return model.getObject().getAvatar().getImageData();
    }
}
