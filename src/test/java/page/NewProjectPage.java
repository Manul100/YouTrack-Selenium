package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewProjectPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private By projectNameField          = By.xpath("//input[@data-test='project-name']");
    private By createProjectButton       = By.xpath("//button[@data-test='create-button']");


    public NewProjectPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public ProjectsPage createNewProject(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(projectNameField));
        setProjectNameField(name);
        return clickCreateProjectButton();
    }

    private void setProjectNameField(String name) {
        webDriver.findElement(projectNameField).sendKeys(name);
    }

    private ProjectsPage clickCreateProjectButton() {
        webDriver.findElement(createProjectButton).click();
        return new ProjectsPage(webDriver, wait);
    }
}
