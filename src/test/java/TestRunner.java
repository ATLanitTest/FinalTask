import org.testng.annotations.DataProvider;
import webdriver.Properties;
import org.apache.logging.log4j.*;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = {"steps"}
)

/**
 * Класс для запуска API-тестов
 */
public class TestRunner extends AbstractTestNGCucumberTests {

    private static Logger log;
    static {
        Properties.setProperties();
        log = LogManager.getLogger(TestRunner.class);
        log.debug("Настройки приложения установлены");
    }

    @BeforeClass
    public void beforeClass() {
        log.info("Запуск тестового класса");
    }

    @AfterClass
    public void afterClass() {
        log.info("класс капут");
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}