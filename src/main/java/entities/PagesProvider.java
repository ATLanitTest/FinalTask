package entities;

import org.openqa.selenium.WebElement;
import pages.TestPageAuthForm;

public class PagesProvider {
    private TestPageAuthForm pageAuthForm = new TestPageAuthForm();
//    private TestPageThemes testPageThemes = new TestPageThemes();


    public WebElement getElementOnPage(String nameOfPage, String nameOfElement) {
        WebElement webElement = null;
        webElement = pageAuthForm.get(nameOfElement);
//        switch (nameOfPage) {
//            case "Страница_авторизации":
//            case "главная_страница":
//                webElement = pageAuthForm.get(nameOfElement);
//                break;
//            case "Страница_Темы":
//                webElement = testPageThemes.get(nameOfElement);
//                break;
//            default:
//                webElement = null;
//        }
        return webElement;
    }
}