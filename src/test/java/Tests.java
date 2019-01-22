import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Class running all tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UnitTests.class, IntegrationTests.class})
public class Tests {
}
