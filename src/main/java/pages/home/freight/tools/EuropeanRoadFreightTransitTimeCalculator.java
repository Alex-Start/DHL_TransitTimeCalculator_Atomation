package pages.home.freight.tools;

import enums.Country;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Report;

import java.time.Duration;

public class EuropeanRoadFreightTransitTimeCalculator extends BasePages {
    public static final String NBSP = "\u00A0";
    private final Report logger = Report.getLogger(EuropeanRoadFreightTransitTimeCalculator.class);

    // time out duration for getting any element
    private static final Duration TIMEOUT_DURATION_DEFAULT = Duration.ofSeconds(10);
    private Duration timeOutDuration;

    private final By rootNode = By.cssSelector("div.l-grid.c-calculator.c-leadtime.js--swe-leadtime.component-wide.component-margin");
    private WebElement root;
    // Locators
    private final By originCountrySelect = By.id("origin-country");
    private final By originPostcodeInput = By.id("origin-postcode");
    private final By originPostcodeError = By.cssSelector("p.js--origin-zip-error");
    private final By destinationCountrySelect = By.id("destination-country");
    private final By destinationPostcodeInput = By.id("destination-postcode");
    private final By destinationPostcodeError = By.cssSelector("p.js--destination-zip-error");
    private final By calculateButton = By.cssSelector("button.js--freightcalc-se--input-submit");

    // Error / warning messages
    private final By generalErrorMessage = By.cssSelector(".js--freight-coutries-general-error-message p.has-rte");

    // Cookies "Accept All" button
    private final By acceptCookiesButton = By.id("onetrust-accept-btn-handler");

    private static final String URL = "https://www.dhl.com/se-en/home/freight/tools/european-road-freight-transit-time-calculator.html";

    public static final String ERROR_MESSAGE_UNABLE_TO_RETRIEVE_DATA = "Unfortunately the online tool is unable to retrieve data for the specified shipment. Please try again later or contact us for assistance.";
    public static final String ERROR_MESSAGE_CORRECT_POSTAL_CODE = "Correct postal code (e.g. no post box)*";

    public EuropeanRoadFreightTransitTimeCalculator(WebDriver driver) {
        this(driver, TIMEOUT_DURATION_DEFAULT);
    }

    public EuropeanRoadFreightTransitTimeCalculator(WebDriver driver, Duration timeOutDuration) {
        super(driver);
        this.timeOutDuration = timeOutDuration;
        //driver.manage().window().maximize();
    }

    public void open() {
        logger.info("Open: {}", URL);
        driver.get(URL);

        wait = new WebDriverWait(driver, timeOutDuration);

        waitForRootNode();

        // Handle cookies if present
        handleCookies();
    }

    private void waitForRootNode() {
        try {
            root = wait.until(ExpectedConditions.visibilityOfElementLocated(rootNode));
            scrollToElement(root);
        } catch (NoSuchElementException | TimeoutException e) {
            throw new NoSuchElementException("Root DHL calculator node not found. Test cannot continue.");
        }
    }

    private void handleCookies() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            acceptButton.click();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.info("Cookies banner not present, continue without clicking.");
        }
    }

    // Actions
    public EuropeanRoadFreightTransitTimeCalculator selectOriginCountry(Country countryCode) {
        logger.info("Select Origin Country: {}", countryCode);
        Select select = new Select(root.findElement(originCountrySelect));
        select.selectByValue(countryCode.getCode());
        return this;
    }

    public EuropeanRoadFreightTransitTimeCalculator enterOriginPostcode(String postcode) {
        logger.info("Select Origin Postcode: {}", postcode);
        WebElement input = root.findElement(originPostcodeInput);
        input.clear();
        input.sendKeys(postcode);
        return this;
    }

    public EuropeanRoadFreightTransitTimeCalculator selectDestinationCountry(Country countryCode) {
        logger.info("Select Destination Country: {}", countryCode);
        Select select = new Select(root.findElement(destinationCountrySelect));
        select.selectByValue(countryCode.getCode());
        return this;
    }

    public EuropeanRoadFreightTransitTimeCalculator enterDestinationPostcode(String postcode) {
        logger.info("Select Destination Postcode: {}", postcode);
        WebElement input = root.findElement(destinationPostcodeInput);
        input.clear();
        input.sendKeys(postcode);
        return this;
    }

    public EuropeanRoadFreightTransitTimeCalculator clickCalculate() {
        logger.info("Click [Calculate]");
        root.findElement(calculateButton).click();
        waitForCalculateButtonEnabled(timeOutDuration);
        return this;
    }

    // Error handling
    public String getGeneralErrorMessage() {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(generalErrorMessage)).getText();
        } catch (NoSuchElementException | TimeoutException e) {
            return null;
        }
    }

    // Error getters
    public String getOriginPostcodeError() {
        return getPostcodeError(originPostcodeError);
    }

    public String getDestinationPostcodeError() {
        return getPostcodeError(destinationPostcodeError);
    }

    private String getPostcodeError(By by) {
        WebElement errorElement = root.findElement(by);
        String text = errorElement.getText().trim();
        return text.isEmpty() || text.equals(NBSP) ? null : text; // null if empty or &nbsp;
    }

    /*
        before clicking:
        <button class="l-grid--center-s l-grid--w-100pc-s l-grid--w-100pc-m js--freightcalc-se--input-submit base-button c-swe-leadtime-button"> <span>Calculate</span> </button>
        after clicking and in loading process:
        <button class="l-grid--center-s l-grid--w-100pc-s l-grid--w-100pc-m js--freightcalc-se--input-submit base-button c-swe-leadtime-button is-loading" disabled=""> <span>Calculate</span> </button>
     */
    // Wait until button is enabled (disabled attribute gone)
    public void waitForCalculateButtonEnabled(Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        wait.until((ExpectedCondition<Boolean>) d -> {
            WebElement button = d.findElement(calculateButton);
            String disabled = button.getAttribute("disabled");
            return disabled == null || disabled.isEmpty();
        });
    }

}
