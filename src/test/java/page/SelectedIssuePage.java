package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectedIssuePage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private By issueActionMenu      = By.xpath("//span[@aria-label='Показать больше']//div");
    private By deleteTaskElement    = By.xpath("//span[text()='Удалить задачу']");
    private By issueConfirmButton   = By.xpath("//button[@data-test='confirm-ok-button']");
    private By addTimeElement       = By.xpath("//span[text()='Добавить затраченное время...']");
    private By durationField        = By.xpath("//input[@data-test='duration']");
    private By timeOptionSelector   = By.xpath("//span[text()='Select an option']");
    private By timeSaveButton       = By.xpath("//button[@data-test='save']");
    private By commentaryField      = By.xpath("//div[@data-test='wysiwyg-editor-content']");
    private By postCommentButton    = By.xpath("//button[@data-test='post-comment']");
    private By editIssueButton      = By.xpath("//button[@data-test='edit-issue-button']");
    private By saveIssueButton      = By.xpath("//button[@data-test='save-button']");
    private By issueSummaryTextarea = By.xpath("//textarea[@data-test='summary']");

    private String timeOptionElementXpath = "//span[text()='%s']";

    public SelectedIssuePage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public IssuesPage deleteTask() {
        wait.until(ExpectedConditions.presenceOfElementLocated(issueActionMenu));
        clickIssueActionMenu();
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteTaskElement));
        clickDeleteTaskElement();
        wait.until(ExpectedConditions.presenceOfElementLocated(issueConfirmButton));
        clickIssueConfirmButton();
        return new IssuesPage(webDriver, wait);
    }

    public void addTime(String time, String timeSpentOption) {
        wait.until(ExpectedConditions.presenceOfElementLocated(issueActionMenu));
        clickIssueActionMenu();
        wait.until(ExpectedConditions.presenceOfElementLocated(addTimeElement));
        clickAddTimeElement();
        wait.until(ExpectedConditions.presenceOfElementLocated(durationField));
        setDurationField(time);
        clickTimeOptionsSelector();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(timeOptionElementXpath.replace("'%s'", timeSpentOption))));
        clickTimeSpentOption(timeSpentOption);
        wait.until(ExpectedConditions.elementToBeClickable(timeSaveButton));
        clickTimeSaveButton();
    }

    public void addCommentary(String commentary) {
        wait.until(ExpectedConditions.presenceOfElementLocated(commentaryField));
        setCommentaryField(commentary);
        wait.until(ExpectedConditions.elementToBeClickable(postCommentButton));
        clickPostCommentaryButton();
    }

    public void editTask(String taskName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(editIssueButton));
        clickEditIssueButton();
        WebElement textarea = wait.until(ExpectedConditions.presenceOfElementLocated(issueSummaryTextarea));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value = '';", textarea);
        setIssueSummaryTextarea(taskName);
        clickSaveIssueButton();
    }

    private void clickIssueActionMenu() {
        webDriver.findElement(issueActionMenu).click();
    }

    private void clickDeleteTaskElement() {
        webDriver.findElement(deleteTaskElement).click();
    }

    private void clickIssueConfirmButton() {
        webDriver.findElement(issueConfirmButton).click();
    }

    private void clickAddTimeElement() {
        webDriver.findElement(addTimeElement).click();
    }

    private void setDurationField(String time) {
        webDriver.findElement(durationField).sendKeys(time);
    }

    private void clickTimeOptionsSelector() {
        webDriver.findElement(timeOptionSelector).click();
    }

    private void clickTimeSpentOption(String timeSpentOption) {
        webDriver.findElement(
                By.xpath(timeOptionElementXpath.replace("'%s'", timeSpentOption))).click();
    }

    private void clickTimeSaveButton() {
        webDriver.findElement(timeSaveButton).click();
    }

    private void setCommentaryField(String commentary) {
        webDriver.findElement(commentaryField).sendKeys(commentary);
    }

    private void clickPostCommentaryButton() {
        webDriver.findElement(postCommentButton).click();
    }

    private void clickEditIssueButton() {
        webDriver.findElement(editIssueButton).click();
    }

    private void setIssueSummaryTextarea(String taskName) {
        webDriver.findElement(issueSummaryTextarea).sendKeys(taskName);
    }

    private void clickSaveIssueButton() {
        webDriver.findElement(saveIssueButton).click();
    }
}
