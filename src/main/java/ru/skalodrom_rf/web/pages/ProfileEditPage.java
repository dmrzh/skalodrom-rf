package ru.skalodrom_rf.web.pages;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import ru.skalodrom_rf.dao.ProfileDao;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.dao.UserDao;
import ru.skalodrom_rf.dao.Utils;
import ru.skalodrom_rf.model.ClimbLevel;
import ru.skalodrom_rf.model.ClimbTime;
import ru.skalodrom_rf.model.Profile;
import ru.skalodrom_rf.model.Scalodrom;
import ru.skalodrom_rf.web.EnumRendererer;
import ru.skalodrom_rf.web.HibernateFieldDataProvider;
import ru.skalodrom_rf.web.HibernateModel;
import ru.skalodrom_rf.web.HibernateModelList;
import ru.skalodrom_rf.web.components.DatesPanel;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 */
public class ProfileEditPage extends BasePage{
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private ProfileDao profileDao;
        @SpringBean
        ScalodromDao scalodromDao;

    public ProfileEditPage() {

        final FileUploadField avatarFi = new FileUploadField("avatarFi", new Model());

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        
        final Profile pe = Utils.getCurrntUser(userDao).getProfile();
        final HibernateModel<Profile,Long> model = new  HibernateModel<Profile,Long>(pe);
        final HibernateModelList<Scalodrom, Long> selectedScalodroms = new HibernateModelList<Scalodrom, Long>(pe.getWhereClimb());
        final Form<Profile> form = new Form<Profile>("form", new CompoundPropertyModel<Profile>(model)){
            @Override
            protected void onSubmit() {
                final Profile p = model.getObject();
                p.setWhereClimb(new TreeSet<Scalodrom>(selectedScalodroms.getObject()));
                final FileUpload fileUpload = avatarFi.getFileUpload();
                if(fileUpload!=null){
                    p.getAvatar().setImageData(fileUpload.getBytes());
                }
                profileDao.saveOrUpdate(p);
                System.out.println("profile saved "+model.getObject());
            }
        };
        form.add(new TextField("fio"));
        form.add(new TextField("email"));
        form.add(new TextField("phone"));

        form.setMultiPart(true);

        form.add(avatarFi);

        final DynamicImageResource avatarImageResource = new DynamicImageResource() {
            @Override
            protected byte[] getImageData() {
                return model.getObject().getAvatar().getImageData();
            }
        };
        form.add(new Image("avatar", avatarImageResource));
        form.setMaxSize(Bytes.kilobytes(100));

        form.add(new TextArea("about"));
        form.add(new TextField("site"));

        form.add(new TextField<Double>("weight",Double.class));
        form.add(new DropDownChoice<ClimbLevel>("climbLevel", Arrays.asList(ClimbLevel.values()),new EnumRendererer<ClimbLevel>(ClimbLevel.class)));

        addSkalodromForm(form,selectedScalodroms );


        form.add(new CheckBox("prefferedWeekDays.monday"));
        form.add(new CheckBox("prefferedWeekDays.tuesday"));
        form.add(new CheckBox("prefferedWeekDays.wednesday"));
        form.add(new CheckBox("prefferedWeekDays.thursday"));
        form.add(new CheckBox("prefferedWeekDays.friday"));
        form.add(new CheckBox("prefferedWeekDays.saturday"));
        form.add(new CheckBox("prefferedWeekDays.sunday"));

        form.add(new DatesPanel("whenClimb", new HibernateFieldDataProvider<Profile,Long, ClimbTime>(pe,"whenClimb")));


        
        add(form);

    }

    private void addSkalodromForm(Form<Profile> form,HibernateModelList<Scalodrom, Long> selectedScalodroms ) {
        Profile profile=Utils.getCurrntUser(userDao).getProfile();
        final HibernateModel<Profile,Long> profileModel=new HibernateModel<Profile,Long>(profile);

        final List<Scalodrom> scalodroms = scalodromDao.findAll();
        final HibernateModelList<Scalodrom, Long> scalodromsModel = new HibernateModelList<Scalodrom, Long>(scalodroms);
         final ChoiceRenderer<Scalodrom> rendererer = new ChoiceRenderer<Scalodrom>("name", "id");





        final Palette palette = new Palette("palette", selectedScalodroms,scalodromsModel,rendererer,10,false);
           form.add(palette);

    }
}
