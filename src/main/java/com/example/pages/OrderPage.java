package com.example.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class OrderPage {
    private WebDriverWait wait;
    // Поле «Имя»
    private By name = By.cssSelector("input[placeholder='* Имя']");
    // Поле «Фамилия»
    private By surname = By.cssSelector("input[placeholder='* Фамилия']");
    // Поле адреса доставки
    private By address = By.cssSelector("input[placeholder*='Адрес']");
    // Поле выбора станции метро
    private By metro = By.cssSelector("input[placeholder*='метро']");
    // Поле номера телефона
    private By phone = By.cssSelector("input[placeholder*='Телефон']");
    // Кнопка «Далее»
    private By next = By.xpath("//button[normalize-space()='Далее']");
    // Поле даты доставки
    private By date = By.cssSelector("input[placeholder*='Когда привезти']");
    // Список сроков аренды
    private By duration = By.className("Dropdown-control");
    // Поле комментария для курьера
    private By comment = By.cssSelector("input[placeholder*='Комментарий']");
    // Кнопка «Заказать» в форме аренды
    private By submit = By.xpath("//div[contains(@class,'Order_Buttons')]//button[normalize-space()='Заказать']");
    // Кнопка «Да» в окне подтверждения
    private By confirm = By.xpath("//button[normalize-space()='Да']");
    // Заголовок «Заказ оформлен»
    private By success = By.xpath("//div[contains(@class,'Order_ModalHeader') and contains(.,'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement field(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void fillRecipient(String firstName, String lastName, String street, String station, String telephone) {
        field(name).sendKeys(firstName);
        field(surname).sendKeys(lastName);
        field(address).sendKeys(street);
        field(metro).sendKeys(station);
        // Станция метро в списке подсказок
        field(By.xpath("//li[.//div[normalize-space()='" + station + "']]")).click();
        field(phone).sendKeys(telephone);
        field(next).click();
    }

    public void fillRental(LocalDate delivery, String period, String colorId, String note) {
        String day = delivery.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        field(date).sendKeys(day, Keys.ENTER);
        field(duration).click();
        // Вариант срока аренды в списке
        field(By.xpath("//div[@class='Dropdown-option' and normalize-space()='" + period + "']")).click();
        // Флажок выбранного цвета самоката
        field(By.id(colorId)).click();
        field(comment).sendKeys(note);
    }

    public void submitAndConfirm() {
        field(submit).click();
        field(confirm).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(confirm));
    }

    public String readConfirmation() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(success)).getText();
    }
}
