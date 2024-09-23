package test;

import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import data.RegistrationByCardInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    RegistrationByCardInfo cardData = DataGenerator.generateByCard();
    String date3 = DataGenerator.generateDate(3);
    String date5 = DataGenerator.generateDate(5);


    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void sendTheCorrectCardForm() {
        $("[data-test-id='city'] input").setValue(cardData.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date3);
        $("[data-test-id='name'] input").setValue(cardData.getName());
        $("[data-test-id='phone'] input").setValue(cardData.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + date3));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date5);
        $$("button").find(exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + date5));

    }

    @Test
    void shouldBeDoneWithoutACity() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date3);
        $("[data-test-id='name'] input").setValue(cardData.getName());
        $("[data-test-id='phone'] input").setValue(cardData.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldBeDoneWithDate() {
        $("[data-test-id='city'] input").setValue(cardData.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue(cardData.getName());
        $("[data-test-id='phone'] input").setValue(cardData.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldBeDoneWithName() {
        $("[data-test-id='city'] input").setValue(cardData.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date3);
        $("[data-test-id='phone'] input").setValue(cardData.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void ShouldBeDoneWithPhone() {
        $("[data-test-id='city'] input").setValue(cardData.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date3);
        $("[data-test-id='name'] input").setValue(cardData.getName());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldBeDoneWithAgreement() {
            $("[data-test-id='city'] input").setValue(cardData.getCity());
            $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
            $("[data-test-id='date'] input").setValue(date3);
            $("[data-test-id='name'] input").setValue(cardData.getName());
            $("[data-test-id='phone'] input").setValue(cardData.getNumber());
            $$("button").find(exactText("Запланировать")).click();
            $(".input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        }
    }





