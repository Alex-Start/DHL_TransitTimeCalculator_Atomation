package pages.home.freight.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class TransitTimeResultValidator extends BasePages {
    // time out duration for getting any element
    private final Duration timeOutDuration = Duration.ofSeconds(10);

    // locator for Transit Time Result
    private final By transitTimeResult = By.xpath("//h3[text()='2/2 Transit Time Result']");

    // Locator for all product boxes
    private static final By PRODUCT_BOXES = By.cssSelector("div.c-leadtime--options-container.js--swe-leadtime--options-container");
    private WebElement root;

    // Locator for all product boxes
    private static final By productBoxesLocator = By.cssSelector("div.c-leadtime--product-box");
    private List<WebElement> boxes;
    private TransitTimeTable transitTimeTable = new TransitTimeTable();

    public TransitTimeResultValidator(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, timeOutDuration);
        try {
            waitForRootNode();
            // Find all product boxes
            boxes = root.findElements(productBoxesLocator);
            parseBoxes(boxes);
        } catch (NoSuchElementException e) {
            // No TransitTimeResult - check it in verification method
        }
    }

    private void waitForRootNode() {
        try {
            root = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_BOXES));
            scrollToElement(root);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Root DHL node not found. Test cannot continue.");
        }
    }

    public boolean isTransitTimeResultDisplayed() {
        if (root == null) {
            return false;
        }
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(transitTimeResult));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void parseBoxes(List<WebElement> boxes) {
        boolean isFindFeatures = false;

        for (WebElement box : boxes) {
            if (!isFindFeatures) {
                try {
                    // Locate the parent div for a single product box info
                    WebElement productBoxInfo = wait.until(driver -> box.findElement(By.cssSelector("div.c-swe-leadtime--product-box-info")));
                    transitTimeTable.addFeatures(parseFeature(productBoxInfo));
                    isFindFeatures = true;
                } catch (NoSuchElementException | TimeoutException e) {
                    // the first box is not features!
                }
            } else {
                parseProductBox(box, transitTimeTable);
            }
        }
    }

    private List<String> parseFeature(WebElement productBoxInfo) {
        // Get all feature <li> texts
        List<WebElement> featureElements = productBoxInfo.findElements(By.cssSelector("div.list li span"));
        List<String> features = new ArrayList<>();
        for (WebElement f : featureElements) {
            features.add(f.getText());
        }
        return features;
    }

    private void parseProductBox(WebElement box, TransitTimeTable transitTimeTable) {

        // 1. Option text
        String optionText = box.findElement(By.cssSelector("div.c-swe-leadtime--product-box-top > span.c-swe-leadtime--option-visible-mobile > strong")).getText();

        // 2. Product name
        String productName = box.findElement(By.cssSelector("div.c-swe-leadtime--product-box-top > span.color-red-500:nth-child(2) > strong")).getText();

        // 3. Product description
        String description = box.findElement(By.cssSelector("div.c-swe-leadtime--product-box-top > p")).getText();

        // 4. Estimated delivery date
        String deliveryDate = box.findElement(By.cssSelector("strong.js--leadtime-product-box-deliverydate")).getText();

        TransitTimeTable.ProductBuilder productBuilder = transitTimeTable.addProduct()
                //.addOption(optionText)
                .addProductName(productName)
                .addDescription(description)
                .addDeliveryDate(deliveryDate);

        for(String feature : transitTimeTable.getFeatures()) {
            String state = box.findElement(By.xpath(".//p[contains(text(),'"+ feature +"')]/following-sibling::p[1]")).getAttribute("title");
            productBuilder.addFeatureState(feature, state);
        }
        productBuilder.build();
    }

    public TransitTimeTable getTransitTimeTable() {
        return transitTimeTable;
    }

}
