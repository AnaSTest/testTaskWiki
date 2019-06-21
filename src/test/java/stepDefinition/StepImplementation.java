package stepDefinition;

import com.codeborne.selenide.Condition;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class StepImplementation {

    private final static Logger log = LoggerFactory.getLogger(StepImplementation.class);

    @Тогда("^открывается .* \"([^\"]*)\"$")
    public void check(String element) throws Throwable {
        $(By.xpath("//a[text()='" + element + "']")).shouldBe(Condition.exist).shouldBe(Condition.visible);
        log.info("открывается " + element);
    }

    @Когда("^пользователь (?:выбирает|нажимает) (?:раздел|подраздел|кнопку) \"([^\"]*)\".*$")
    public void chooseOption(String element) throws Throwable {
        $(By.xpath("//a[text()='" + element + "']")).shouldBe(Condition.exist).click();
        log.info("пользователь выбирает|нажимает раздел|подраздел|кнопку " + element);
    }

    @Тогда("^открывается страница с любым заголовком и нажатие на ссылку в определении$")
    public void clickHrefCheckTitle() throws Throwable {
        int a = 1;
        while (!Thread.interrupted()) {
            /*
             * Проверка наличия Философия в заголовке
             */
            By by = By.xpath("//h1[@id=\"firstHeading\"]");
            if ($(by).is(Condition.exist) && getWebDriver().getTitle().contains("Философия")) {
                log.info("Чтобы попасть " + getWebDriver().getTitle() + " необходимо сделать " + a + " переходов");
                break;
            }
            log.info("Step" + a + " " + getWebDriver().getTitle() + " " + getWebDriver().getCurrentUrl());
            /*
             * текст первого параграфа
             */
            String text = $(By.xpath("//*[@id=\"mw-content-text\"]/div/p[1]")).getText();
            /*
             * listHref - ссылки первого параграфа
             */
            List<WebElement> listHref = $(By.xpath("//*[@id=\"mw-content-text\"]/div/p[1]"))
                    .findElements(By.cssSelector("a"));
            if (listHref.size() == 0) {
                log.info("Ссылок в определении не найдено");
                break;
            }
            WebElement result = null;
            /*
             * поскольку нужно взять первую ссылку после - , т.е. определение, дефис как опорный элемент
             */
            {
                int borderIndex = text.indexOf("—");
                int index = 0;
                for (WebElement webElement : listHref) {

                    int i = text.indexOf(webElement.getText(), index);
                    if (i > borderIndex) {
                        result = webElement;
                        break;
                    }
                    index = i + webElement.getText().length();
                }
            }
            result.click();
            a++;
        }
    }
}