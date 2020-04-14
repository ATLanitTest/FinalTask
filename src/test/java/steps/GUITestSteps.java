package steps;

import org.openqa.selenium.WebElement;
import pages.TestPageAuthForm;
import io.qameta.allure.Allure;
import io.cucumber.java.ru.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.openqa.selenium.TimeoutException;
import static entities.PagesProvider.getElementOnPage;
import java.io.IOException;


public class GUITestSteps {
    private final Logger log = LogManager.getLogger(getClass());
    private TestPageAuthForm page = new TestPageAuthForm();
    private WebElement webElement = null;

    /**
     *
     * @param title String
     */
    public void addScreenshot(String title) {
        try {
            Allure.addAttachment(title, "image/png", FileUtils.openInputStream(page.getScreenshot()), ".png");
            log.debug("Сделан скиншот на шаге {}", title);
        } catch (IOException e) {
            log.error("Ошибка записи скриншота: {}", e.getMessage());
        }
    }

    @Пусть("открыт браузер и введен адрес \"(.*)\"$")
    public void openBrowserAndFollowLink(String url) {
        page.openPage(url);
        addScreenshot("Страница сайта загружена");
    }

    @И("на {string} пользователь нажимает {string}")
    public void clickLoginFormOpenButton(String nameOfPage, String nameOfElement) {
        try {
            getElementOnPage(nameOfPage, nameOfElement).click();
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " на " + nameOfPage + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot("Вызов окна авторизации");
        }
    }

    @Когда("появилось окно Авторизации")
    public void loginFormDisplayed() {
        try {
        } catch (TimeoutException e) {
            Assert.fail("Окно авторизации не отобразилось! " + e.getMessage());
        } finally {
            addScreenshot("Отображение окна авторизации");
        }
    }

    @То("пользователь вводит логин \"([^\"]*)\"$")
    public void typeUsername(String username) {
        try {
            page.authFormUsernameField.click();
            page.authFormUsernameField.sendKeys(username);
        } catch  (TimeoutException e) {
            Assert.fail("Поле для ввода имени пользователя не доступно для ввода! " + e.getMessage());
        } finally {
            addScreenshot("Ввод логина");
        }
    }

    @И("пользователь вводит пароль \"([^\"]*)\"$")
    public void typePassword(String password) {
        try {
            page.authFormPasswordField.click();
            page.authFormPasswordField.sendKeys(password);
        } catch  (TimeoutException e) {
            Assert.fail("Поле для ввода пароля не доступно для ввода! " + e.getMessage());
        } finally {
            addScreenshot("Ввод пароля");
        }
    }

    @И("пользователь нажимает кнопку Входа")
    public void clickLoginSubmitButton() {
        try {
            page.authFormSubmitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Кнопка для авторизации пользователя не доступна для нажатия! " + e.getMessage());
        } finally {
            addScreenshot("Нажатие кнопки входа");
        }
    }

    @Тогда("появилось Сообщение об неправильных учетных данных")
    public void incorrectCredentialsAlert() {
        try {
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается сообщение об неправильных учетных данных " + e.getMessage());
        } finally {
            addScreenshot("Отображение сообщения о неправильных учетных данных");
        }
    }

    @Тогда("появилось Сообщение об незаполненных учетных данных")
    public void emptyCredentialsAlert() {
        try {
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается сообщение об незаполненных учетных данных " + e.getMessage());
        } finally {
            addScreenshot("Отображение сообщения о неполных учетных данных");
        }
    }

    @Если("отображается Пиктограмма активного пользователя")
    public void activeUserAvatarDisplayed() {
        try {
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается Пиктограмма активного пользователя " + e.getMessage());
        } finally {
            addScreenshot("Отображение пиктограммы активного пользователя");
        }
    }

    @И("пользователь нажимает на Пиктограмму активного пользователя")
    public void clickActiveUserAvatar() {
        try {
            page.userAccountFormOpenLink.click();
        } catch  (TimeoutException e) {
            Assert.fail("Пиктограмма активного пользователя не доступна для нажатия" + e.getMessage());
        } finally {
            addScreenshot("Нажатие на пиктограмму активного пользователя");
        }
    }

    @И("пользователь проверяет что в Заголовке личного кабинета отображается имя \"([^\"]*)\"$")
    public void activeUserTitleDisplayed(String username) {
        try {
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается имя активного пользователя в Заголовке личного кабинета " + e.getMessage());
        } finally {
            addScreenshot("Отображение имени пользователя в личном кабинете");
        }
    }

    @Тогда("пользователь нажимает на кнопку Выхода из учетной записи")
    public void exitActiveUserAccount() {
        try {
            page.userAccountExitButton.click();
            page.browserAlertAccept();
        } catch  (TimeoutException e) {
            Assert.fail("Кнопка выхода из учетной записи не доступна для нажатия" + e.getMessage());
        }
        addScreenshot("Подтверждение выхода из учетной записи");
    }

}
