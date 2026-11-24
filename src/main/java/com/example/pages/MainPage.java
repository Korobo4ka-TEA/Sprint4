package com.example.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;
    // Кнопка согласия на использование cookie
    private By cookies = By.id("rcc-confirm-button");
    // Верхняя кнопка «Заказать»
    private By topOrder = By.xpath("//header//button[normalize-space()='Заказать'] | //div[contains(@class,'Header_Header')]//button[normalize-space()='Заказать']");
    // Нижняя кнопка «Заказать»
    private By bottomOrder = By.xpath("//div[contains(@class,'Home_FinishButton')]//button[normalize-space()='Заказать']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        if (!driver.findElements(cookies).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(cookies)).click();
        }
    }

    // Общий блок вопроса и ответа. Числовой id меняется при обновлении.
    private String faqItem(String question) {
        return "//div[@class='accordion__item'][.//*[@data-accordion-component='AccordionItemButton' and contains(normalize-space(.), '" + question + "')]]";
    }

    public void openQuestion(String question) {
        // Кнопка вопроса в разделе «Вопросы о важном»
        By button = By.xpath(faqItem(question) + "//*[@data-accordion-component='AccordionItemButton']");
        scrollAndClick(button);
    }

    public String readAnswer(String question) {
        // Текст ответа на выбранный вопрос
        By answer = By.xpath(faqItem(question) + "//*[@data-accordion-component='AccordionItemPanel']/p");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }

    public void orderFromTop() {
        scrollAndClick(topOrder);
    }

    public void orderFromBottom() {
        scrollAndClick(bottomOrder);
    }

    private void scrollAndClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
