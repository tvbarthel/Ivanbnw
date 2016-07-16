package fr.tvbarthel.ivanbnw.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.tvbarthel.ivanbnw.en.EnMainActivityTest;
import fr.tvbarthel.ivanbnw.fr.FrMainActivityTest;

/**
 * Suite that run every test on every locale.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        FrMainActivityTest.class,
        EnMainActivityTest.class
})
public class InstrumentationTestSuite {
}
