package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.*;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static Report logger = Report.getLogger(DriverFactory.class);

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            logger.info("Init WebDriver");
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36";

            ChromeOptions options = new ChromeOptions();
            // Set User-Agent to a real Chrome browser
            options.addArguments("--user-agent="+ userAgent);
            //options.addArguments("profile-directory=Default");
            options.addArguments("--disable-blink-features=AutomationControlled");

            options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);

            ChromeDriver chromeDriver = new ChromeDriver(options);

            driverThread.set(chromeDriver);
        }
        return driverThread.get();
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            logger.info("Quit WebDriver");
            driverThread.get().quit();
            driverThread.remove();
        }
    }

}
