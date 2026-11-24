package com.example.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FaqTest extends BrowserTest {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Object[][] answers() {
        return new Object[][] {
            {"Сколько это стоит", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
            {"Хочу сразу несколько самокатов", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
            {"рассчитывается время аренды", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
            {"сегодня", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
            {"продлить заказ", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
            {"зарядку", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
            {"отменить заказ", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
            {"МКАД", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    private String question;
    private String expectedAnswer;

    public FaqTest(String question, String expectedAnswer) {
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    @Test
    public void questionOpensItsAnswer() {
        mainPage.openQuestion(question);
        assertEquals(expectedAnswer, mainPage.readAnswer(question));
    }
}
