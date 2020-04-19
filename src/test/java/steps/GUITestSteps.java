package steps;

import entities.PagesProvider;
import entities.Storage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.TestPageAuthForm;

import io.qameta.allure.Allure;
import io.cucumber.java.ru.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import webdriver.DriverFactory;
import webdriver.SharedDriver;
import java.io.IOException;

import static entities.Enums.PAGE_MAIN;


public class GUITestSteps {
    private final Logger log = LogManager.getLogger(getClass());
    private TestPageAuthForm page = new TestPageAuthForm();
    // private TestPageThemes pageThemes = new TestPageThemes();
    PagesProvider pagesProvider = new PagesProvider();
    String currentURL = null;

    Storage storage = new Storage();

    /**
     * @param driver
     */
    public GUITestSteps(SharedDriver driver) {
    }

    /**
     * @param title String
     */
    public void addScreenshot(String title) {
        try {
            Allure.addAttachment(title, "image/png", FileUtils.openInputStream(page.getScreenshot()), ".png");
            log.debug("Сделан скриншот на шаге {}", title);
        } catch (IOException e) {
            log.error("Ошибка записи скриншота: {}", e.getMessage());
        }
    }

    @Пусть("я открыл браузер и загрузил страницу {string}")
    public void openBrowserAndLoadSite(String nameOfElement) {
        try {
            page.openPage(nameOfElement);
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @И("на {string} я нажал на элемент {string}")
    public void clickElement(String nameOfPage, String nameOfElement) {
        try {
            new WebDriverWait(DriverFactory.getDriver(), 30).until(ExpectedConditions.elementToBeClickable(pagesProvider.getElementOnPage(nameOfPage, nameOfElement)));
            pagesProvider.getElementOnPage(nameOfPage, nameOfElement).click();
            storage.putStorageData(PAGE_MAIN.toString(), pagesProvider.getElementOnPage(nameOfPage, nameOfElement));
        } catch (TimeoutException | IOException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @Когда("отобразился элемент {string}")
    public void loginFormDisplayed(String nameOfElement) {
        try {
            page.authFormUsernameField.isDisplayed();
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @Тогда("на {string} я ввел значение {string} в поле {string}")
    public void enterValue(String nameOfPage, String data, String nameOfElement) {
        try {
            pagesProvider.getElementOnPage(nameOfPage, nameOfElement).click();
            page.get(nameOfElement).sendKeys(data);
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @Тогда("на {string} отобразился элемент {string}")
    public void elementFound(String nameOfPage, String nameOfElement) {
        try {
            pagesProvider.getElementOnPage(nameOfPage, nameOfElement);
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @И("на {string} элемент {string} отображается {string}")
    public void elementFoundWithLogin(String nameOfPage, String nameOfElement, String login) {
        try {
            String avatarName = page.get(nameOfElement).getText();
            Assert.assertEquals(avatarName, login);
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @Тогда("я нажал на элемент {string}")
    public void clickElementExit(String nameOfElement) {
        try {
            page.get(nameOfElement).click();
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        }
    }

    @И("появилось информационное окно")
    public void elementFoundInformationWindow() {
        try {
            Assert.assertEquals(DriverFactory.getDriver().switchTo().alert().getText(), "Вы уверены что хотите выйти?");
        } catch (TimeoutException e) {
            Assert.fail("Информационное окно не отобразилось! " + e.getMessage());
        }
    }

    @Тогда("я нажал кнопку {string}")
    public void clickElementOk(String nameOfElement) {
        try {
            page.browserAlertAccept();
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @И("элемент {string} не отображается")
    public void elementNotFound(String nameOfElement) {
        try {
            page.get(nameOfElement).isEnabled();
        } catch (TimeoutException e) {
            Assert.fail(nameOfElement + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfElement);
        }
    }

    @И("я перешел на страницу {string}")
    public void goToPage(String nameOfPage) {
        try {
            page.openPage(nameOfPage);
        } catch (TimeoutException e) {
            Assert.fail(nameOfPage + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfPage);
        }
    }

    @Пусть("открыть браузер, загрузить {string} и авторизоваться с {string} и {string}")
    public void openBrowserAuth(String url, String login, String password) {
        try {
            page.openPage(url);
            page.authFormOpenButton.click();
            new WebDriverWait(DriverFactory.getDriver(), 2)
                    .until(ExpectedConditions.elementToBeClickable(page.authFormUsernameField));
            page.authFormUsernameField.click();
            page.authFormUsernameField.sendKeys(login);
            page.authFormPasswordField.click();
            page.authFormPasswordField.sendKeys(password);
            page.authFormSubmitButton.click();
        } catch (TimeoutException e) {
            Assert.fail(url + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(url);
        }
    }

    @Тогда("на {string} отобразились элементы {string}, {string}, {string}")
    public void elementsDisplayed(String nameOfPage, String arg1, String arg2, String arg3) {
        try {
            new WebDriverWait(DriverFactory.getDriver(), 3)
                    .until(ExpectedConditions.visibilityOf(page.themeCreateTitle)).isEnabled();
            page.themeCreateDescription.isEnabled();
            page.themeCreateCancel.isEnabled();
        } catch (TimeoutException e) {
            Assert.fail(nameOfPage + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(nameOfPage);
        }
    }

    @Тогда("выйти из профиля")
    public void step() {
        try {
            page.userAvatar.click();
            page.userAccountExitButton.click();
            page.browserAlertAccept();
        } catch (TimeoutException e) {
            Assert.fail("Шаг 2 не доступен! " + e.getMessage());
        }
    }

    @Затем("на странице опубликованной темы я нажал на элемент {string}")
    public void clickTheme(String nameOfElement) {
        try {
            new WebDriverWait(DriverFactory.getDriver(), 3)
                    .until(ExpectedConditions.visibilityOf(page.themeName));
            currentURL = DriverFactory.getDriver().getCurrentUrl();
            System.out.println(currentURL);
            page.openPage(nameOfElement);
        } catch (TimeoutException e) {
            Assert.fail("Страница темы не доступна! " + e.getMessage());
        } finally {
            addScreenshot("Страница темы");
        }
    }

    @И("на {string} отобразился элемент {string} с текстом {string}")
    public void elementWithText(String nameOfPage, String h1, String titleData) {
        try {
            String pageUrl = currentURL.substring(22);
            String avatarName = new WebDriverWait(DriverFactory.getDriver(), 3)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='" + pageUrl + "']"))).getText();
            Assert.assertEquals(avatarName, titleData);
        } catch (TimeoutException e) {
            Assert.fail(titleData + " не доступна! " + e.getMessage());
        } finally {
            addScreenshot(titleData);
        }
    }

}
