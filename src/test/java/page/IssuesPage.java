package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class IssuesPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private By newIssueButton       = By.xpath("//a[@href='newIssue']");
    private By issueSummaryTextarea = By.xpath("//textarea[@data-test='summary']");
    private By submitIssueButton    = By.xpath("//button[@data-test='submit-button']");
    private By prioritySelector     = By.xpath("//label[text()='Приоритет']");
    private By typeSelector         = By.xpath("//label[text()='Тип']");
    private By stateSelector        = By.xpath("//label[text()='Состояние']");

    private String issueSelectorElementXpath = "//span[@title='%s']";
    private String issueLinkXpath = "//a[contains(text(), '%s')]";


    public IssuesPage(WebDriver driver, WebDriverWait wait) {
        this.webDriver = driver;
        this.wait = wait;
    }

    public void createTask(String taskName, String priority, String type, String state) {
        wait.until(ExpectedConditions.presenceOfElementLocated(newIssueButton));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        clickNewIssueButton();
        wait.until(ExpectedConditions.presenceOfElementLocated(issueSummaryTextarea));
        setIssueSummaryTextarea(taskName);
        selectPriority(priority);
        selectType(type);
        selectState(state);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));
        clickSubmitIssueButton();
    }

    private void clickNewIssueButton() {
        webDriver.findElement(newIssueButton).click();
    }

    private void setIssueSummaryTextarea(String taskName) {
        webDriver.findElement(issueSummaryTextarea).sendKeys(taskName);
    }

    private void selectPriority(String priority) {
        wait.until(ExpectedConditions.presenceOfElementLocated(prioritySelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(issueSelectorElementXpath.replace("%s", priority)))).click();
    }

    private void selectType(String type) {
        wait.until(ExpectedConditions.presenceOfElementLocated(typeSelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(issueSelectorElementXpath.replace("%s", type)))).click();
    }

    private void selectState(String state) {
        wait.until(ExpectedConditions.presenceOfElementLocated(stateSelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(issueSelectorElementXpath.replace("%s", state)))).click();
    }

    private void clickSubmitIssueButton() {
        webDriver.findElement(submitIssueButton).click();
    }

    public String getTaskIdByTaskName(String taskName) {
        return webDriver.findElement(By.xpath(String.format("//a[contains(text(), '%s')]", taskName)))
                .findElement(By.xpath("./ancestor::tr"))
                .findElement(By.xpath(".//td[@data-test='ring-table-cell id']")).getText();
    }

    public SelectedIssuePage clickIssueLink(String taskId) {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(issueLinkXpath.replace("%s", taskId)))).click();
        return new SelectedIssuePage(webDriver, wait);
    }
}
