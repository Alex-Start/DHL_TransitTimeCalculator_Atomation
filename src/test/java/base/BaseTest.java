package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.DriverFactory;
import utils.Report;

public class BaseTest {
    protected WebDriver driver;
    protected final Report logger = Report.getLogger(this.getClass());

    @BeforeClass
    public void setUp() {
        logger.info("Starting browser");
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        logger.info("Closing browser");
        DriverFactory.quitDriver();
    }

    public Report getLogger() {
        return logger;
    }
}
