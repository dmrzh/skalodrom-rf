package ru.skalodrom_rf;

import junit.framework.TestCase;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.skalodrom_rf.dao.ScalodromDao;
import ru.skalodrom_rf.web.pages.IndexPage;

import javax.annotation.Resource;

/**
 * Simple test using the WicketTester
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestHomePage extends TestCase{

    @Resource
    ScalodromDao scalodromDao;
	private WicketTester tester;

	@Override @Before
	public void setUp()
	{
		tester = new WicketTester(new WebApplication(){
            @Override
            public Class<? extends Page> getHomePage() {
                return IndexPage.class;
            }
        });
	}
    @Ignore
     @Test  @Transactional
	public void testRenderMyPage()
	{
		//start and render the test page
        tester.startPage(new IndexPage(scalodromDao));

		//assert rendered page class
		tester.assertRenderedPage(IndexPage.class);

		//assert rendered label component
		//tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
