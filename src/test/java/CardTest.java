import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;

class CardTest {
    private String setDate(String pattern) {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSucceed() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ставрополь");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(BACK_SPACE);
        String date = setDate("dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на " + date));

    }

}