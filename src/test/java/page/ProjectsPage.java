package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectsPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    private By createNewProjectButton    = By.xpath("//a[@href='projects/create']");
    private By skipButton                = By.xpath("//button[@data-test='skip']");

    public ProjectsPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public SelectProjectTypePage createProject() {
        wait.until(ExpectedConditions.presenceOfElementLocated(createNewProjectButton));
        return clickCreateNewProjectButton();
    }

    public void finishProjectCreation() {
        wait.until(ExpectedConditions.presenceOfElementLocated(skipButton));
        clickSkipButton();
    }

    public SelectProjectTypePage clickCreateNewProjectButton() {
        webDriver.findElement(createNewProjectButton).click();
        return new SelectProjectTypePage(webDriver, wait);
    }

    private void clickSkipButton() {
        webDriver.findElement(skipButton).click();
    }

}
