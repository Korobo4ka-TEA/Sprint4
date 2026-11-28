package com.example.tests;

import com.example.pages.MainPage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

public abstract class BrowserTest {
    protected WebDriver driver;
    protected MainPage mainPage;

    @Before
    public void openHomePage() throws IOException {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                Path profiles = Path.of(System.getProperty("user.home"), "Library", "Caches",
                        "Sprint4-Scooter", "firefox-profiles");
                Files.createDirectories(profiles);
                GeckoDriverService service = new GeckoDriverService.Builder()
                        .withProfileRoot(profiles.toFile())
                        .withLogFile(Path.of("target", "geckodriver.log").toAbsolutePath().toFile())
                        .build();
                driver = new FirefoxDriver(service);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
        driver.manage().window().setSize(new Dimension(1280, 900));
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
