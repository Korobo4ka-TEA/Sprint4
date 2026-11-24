package com.example.tests;

import com.example.pages.OrderPage;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest extends BrowserTest {
    private static class OrderData {
        private final String name;
        private final String surname;
        private final String address;
        private final String metro;
        private final String phone;
        private final int daysUntilDelivery;
        private final String period;
        private final String color;
        private final String note;

        OrderData(String name, String surname, String address, String metro, String phone,
                  int daysUntilDelivery, String period, String color, String note) {
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.metro = metro;
            this.phone = phone;
            this.daysUntilDelivery = daysUntilDelivery;
            this.period = period;
            this.color = color;
            this.note = note;
        }
    }

    @Parameterized.Parameters(name = "Заказ через кнопку {0}")
    public static Object[][] orders() {
        return new Object[][] {
            {"вверху", new OrderData("Анна", "Соколова", "Москва, улица Мира, 7", "Сокол",
                    "79990000001", 2, "сутки", "black", "Позвонить у подъезда")},
            {"внизу", new OrderData("Иван", "Лапин", "Москва, улица Лесная, 8", "Сокол",
                    "79990000002", 5, "трое суток", "grey", "Встречу возле дома")}
        };
    }

    private String entry;
    private OrderData order;

    public OrderTest(String entry, OrderData order) {
        this.entry = entry;
        this.order = order;
    }

    @Test
    public void customerCanOrderScooter() {
        if (entry.equals("вверху")) {
            mainPage.orderFromTop();
        } else {
            mainPage.orderFromBottom();
        }
        OrderPage page = new OrderPage(driver);
        page.fillRecipient(order.name, order.surname, order.address, order.metro, order.phone);
        page.fillRental(LocalDate.now().plusDays(order.daysUntilDelivery), order.period, order.color, order.note);
        page.submitAndConfirm();
        assertTrue(page.readConfirmation().contains("Заказ оформлен"));
    }
}
