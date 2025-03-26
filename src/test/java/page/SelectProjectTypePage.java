package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectProjectTypePage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private By acceptTemplateButton = By.xpath("//button[@data-test='accept-button']");

    private String projectTypeHeaderXpath = "//h3[text()='%s']";

    public SelectProjectTypePage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public NewProjectPage selectProjectType(String type) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                projectTypeHeaderXpath.replace("%s", type))));
        clickProjectTypeHeader(type);
        wait.until(ExpectedConditions.presenceOfElementLocated(acceptTemplateButton));
        return clickAcceptTemplateButton();
    }

    private void clickProjectTypeHeader(String type) {
        webDriver.findElement(By.xpath(projectTypeHeaderXpath.replace("%s", type))).click();
    }

    private NewProjectPage clickAcceptTemplateButton() {
        webDriver.findElement(acceptTemplateButton).click();
        return new NewProjectPage(webDriver, wait);
    }
}
