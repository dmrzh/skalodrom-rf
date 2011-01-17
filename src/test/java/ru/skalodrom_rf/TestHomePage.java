package ru.skalodrom_rf;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import ru.skalodrom_rf.web.WicketApplication;
import ru.skalodrom_rf.web.pages.IndexPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(IndexPage.class);

		//assert rendered page class
		tester.assertRenderedPage(IndexPage.class);

		//assert rendered label component
		//tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
