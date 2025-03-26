package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoardCreationPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private By nameInput            = By.xpath("//input[@name='name']");
    private By projectSelector      = By.xpath("//input[@placeholder='Добавить проект']");
    private By createBoardButton    = By.xpath("//button[@data-test='saveNewBoard']");

    private String projectNameElementXpath = "//span[@title='%s']";

    public BoardCreationPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public AgilesPage createBoard(String name, String projectName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameInput));
        setNameInput(name);
        setProjectName(projectName);
        wait.until(ExpectedConditions.elementToBeClickable(createBoardButton));
        return clickCreateBoardButton();
    }

    private void setNameInput(String name) {
        webDriver.findElement(nameInput).sendKeys(name);
    }

    private void setProjectName(String projectName) {
        webDriver.findElement(projectSelector).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(projectNameElementXpath.replace("%s", projectName))))
                .click();
    }

    private AgilesPage clickCreateBoardButton() {
        webDriver.findElement(createBoardButton).click();
        return new AgilesPage(webDriver, wait);
    }
}
