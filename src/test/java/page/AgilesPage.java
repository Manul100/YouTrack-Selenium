package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AgilesPage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    private By createActionsDropdown = By.xpath("//button[@data-test='createActionsHeaderDropdown']");
    private By createBoardButton = By.xpath("//span[@title='Доску...']");
    private By createSwimlaneButton = By.xpath("//span[@title='Свимлэйн...']");
    private By createSprintButton = By.xpath("//span[@title='Спринт...']");
    private By sprintName = By.xpath("//input[@id='boardName']");
    private By goalTextarea = By.xpath("//textarea[@id='boardGoal']");
    private By submitSprintButton = By.xpath("//button[@loader='ctrl.creatingSprint']");
    private By swimlaneSummaryTextarea = By.xpath("//textarea[@data-test='summary']");
    private By swimlaneDescriptionEditor = By.xpath("//div[@data-test='wysiwyg-editor-content']");
    private By prioritySelector     = By.xpath("//label[text()='Приоритет']");
    private By typeSelector         = By.xpath("//label[text()='Тип']");
    private By stateSelector        = By.xpath("//label[text()='Состояние']");
    private By submitSwimlaneButton    = By.xpath("//button[@data-test='submit-button']");

    private String swimlaneSelectorElementXpath = "//span[@title='%s']";

    public AgilesPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public BoardTypePage createBoard() {
        wait.until(ExpectedConditions.presenceOfElementLocated(createActionsDropdown));
        clickCreateActionsDropdown();
        return clickCreateBoardButton();
    }

    public void createSwimlane(String name, String description, String priority, String type, String state) {
        wait.until(ExpectedConditions.presenceOfElementLocated(createActionsDropdown));
        clickCreateActionsDropdown();
        clickCreateSwimlaneButton();
        wait.until(ExpectedConditions.presenceOfElementLocated(swimlaneSummaryTextarea));
        setSwimlaneName(name);
        setSwimlaneDescription(description);
        selectPriority(priority);
        selectType(type);
        selectState(state);
        wait.until(ExpectedConditions.elementToBeClickable(submitSwimlaneButton));
        clickSubmitSwimlaneButton();
    }

    public void createSprint(String name, String goal) {
        wait.until(ExpectedConditions.presenceOfElementLocated(createActionsDropdown));
        clickCreateActionsDropdown();
        clickCreateSprintButton();
        wait.until(ExpectedConditions.presenceOfElementLocated(sprintName));
        setSprintName(name);
        setSprintGoal(goal);
        wait.until(ExpectedConditions.elementToBeClickable(submitSprintButton));
        clickSubmitSprintButton();
    }

    private void clickCreateActionsDropdown() {
        webDriver.findElement(createActionsDropdown).click();
    }

    private BoardTypePage clickCreateBoardButton() {
        webDriver.findElement(createBoardButton).click();
        return new BoardTypePage(webDriver, wait);
    }

    private void clickCreateSwimlaneButton() {
        webDriver.findElement(createSwimlaneButton).click();
    }

    private void clickCreateSprintButton() {
        webDriver.findElement(createSprintButton).click();
    }

    private void setSprintName(String name) {
        webDriver.findElement(sprintName).sendKeys(name);
    }

    private void setSprintGoal(String goal) {
        webDriver.findElement(goalTextarea).sendKeys(goal);
    }

    private void clickSubmitSprintButton() {
        webDriver.findElement(submitSprintButton).click();
    }

    private void setSwimlaneName(String name) {
        webDriver.findElement(swimlaneSummaryTextarea).sendKeys(name);
    }

    private void setSwimlaneDescription(String description) {
        webDriver.findElement(swimlaneDescriptionEditor).sendKeys(description);
    }

    private void selectPriority(String priority) {
        wait.until(ExpectedConditions.presenceOfElementLocated(prioritySelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(swimlaneSelectorElementXpath.replace("%s", priority)))).click();
    }

    private void selectType(String type) {
        wait.until(ExpectedConditions.presenceOfElementLocated(typeSelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(swimlaneSelectorElementXpath.replace("%s", type)))).click();
    }

    private void selectState(String state) {
        wait.until(ExpectedConditions.presenceOfElementLocated(stateSelector)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(swimlaneSelectorElementXpath.replace("%s", state)))).click();
    }

    private void clickSubmitSwimlaneButton() {
        webDriver.findElement(submitSwimlaneButton).click();
    }
}
