package ru.skalodrom_rf.web.pages;

import net.sf.wicketautodao.model.HibernateFieldDataProvider;
import net.sf.wicketautodao.model.HibernateModel;
import net.sf.wicketautodao.model.HibernateModelList;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.skalodrom_rf.dao.*;
import ru.skalodrom_rf.model.*;
import ru.skalodrom_rf.web.EnumRendererer;
import ru.skalodrom_rf.web.components.DatesPanel;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 */
public class ProfileEditPage extends BasePage{
    private static final Logger LOG= LoggerFactory.getLogger(ProfileEditPage .class);

    @SpringBean
    private UserDao userDao;
    @SpringBean
    private ProfileDao profileDao;
    @SpringBean
    SkalodromDao skalodromDao;

    @SpringBean
    private PrefferedWeekDayDao prefferedWeekDayDao;

    public ProfileEditPage() {

        final FileUploadField avatarFi = new FileUploadField("avatarFi", new Model<FileUpload>());

        add(new FeedbackPanel("feedback"));

        
        final Profile pe = Utils.getCurrntUser(userDao).getProfile();
        final HibernateModel<Profile> model = new HibernateModel<Profile>(pe);
        final HibernateModelList<Skalodrom> selectedScalodroms = new HibernateModelList<Skalodrom>(pe.getWhereClimb());
        final Form<Profile> form = new Form<Profile>("form", new CompoundPropertyModel<Profile>(model)){
            @Override
            protected void onSubmit() {
                final Profile p = model.getObject();
                p.setWhereClimb(new TreeSet<Skalodrom>(selectedScalodroms.getObject()));
                final FileUpload fileUpload = avatarFi.getFileUpload();
                if(fileUpload!=null){
                    p.getAvatar().setImageData(fileUpload.getBytes());
                }
                profileDao.saveOrUpdate(p);
                LOG.debug("profile saved "+model.getObject()); 
            }
        };
        form.add(new RequiredTextField("fio"));
        form.add(new RequiredTextField("email"));
        form.add(new TextField("phone"));


        RadioGroup group = new RadioGroup("sex");
        form.add(group);

        group.add(new Radio<Sex>("MALE", new Model<Sex>(Sex.MALE)));
        group.add(new Radio<Sex>("FEMALE", new Model<Sex>(Sex.FEMALE)));


        form.setMultiPart(true);

        form.add(avatarFi);

        final DynamicImageResource avatarImageResource = new AvatarImageResource(model);
        form.add(new Image("avatar", avatarImageResource));
        form.setMaxSize(Bytes.kilobytes(100));

        form.add(new TextArea("about"));
        

        form.add(new RequiredTextField<Double>("weight",Double.class));
        form.add(new DropDownChoice<ClimbLevel>("climbLevel", Arrays.asList(ClimbLevel.values()),new EnumRendererer<ClimbLevel>(ClimbLevel.class)));

        addSkalodromForm(form,selectedScalodroms );


        final HibernateModelList<WeekDay> allWeekDaysModel = new HibernateModelList<WeekDay>(prefferedWeekDayDao.findAll());
        CheckBoxMultipleChoice<WeekDay> weekDaysChoice = new CheckBoxMultipleChoice<WeekDay>("prefferedWeekDay", allWeekDaysModel, new WeekdaysRenderer());
        form.add(weekDaysChoice.setSuffix(""));

        form.add(new DatesPanel("whenClimb", new HibernateFieldDataProvider<Profile, ClimbTime>(pe,"whenClimb")));


        
        add(form);
        add(new FeedbackPanel("feedback2"));

    }

    private void addSkalodromForm(Form<Profile> form,HibernateModelList<Skalodrom> selectedScalodroms ) {
        Profile profile=Utils.getCurrntUser(userDao).getProfile();
        final HibernateModel<Profile> profileModel=new HibernateModel<Profile>(profile);

        final List<Skalodrom> skalodroms = skalodromDao.findAll();
        final HibernateModelList<Skalodrom> scalodromsModel = new HibernateModelList<Skalodrom>(skalodroms);
         final ChoiceRenderer<Skalodrom> rendererer = new ChoiceRenderer<Skalodrom>("name", "id");





        final Palette palette = new Palette("palette", selectedScalodroms,scalodromsModel,rendererer,10,false);
           form.add(palette);

    }

}
