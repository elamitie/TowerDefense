package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnimationTests.class, JSONReaderTests.class, LayerTests.class, LevelLayoutTests.class, UnitTests.class,
		Utility.class })
public class AllTests {

}
