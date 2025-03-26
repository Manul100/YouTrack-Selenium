package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoardTypePage {

    private WebDriver webDriver;
    private WebDriverWait wait;

    private By scrumBoardButton     = By.xpath("//button[@data-test='createScrumBoard']");
    private By kanbanBoardButton    = By.xpath("//button[@data-test='createKanbanBoard']");
    private By versionBoardButton   = By.xpath("//button[@data-test='createVersionBoard']");
    private By customBoardButton    = By.xpath("//button[@data-test='createCustomBoard']");
    private By personalBoardButton  = By.xpath("//button[@data-test='createPersonalBoard']");

    public BoardTypePage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public BoardCreationPage selectBoardType(String type) {
        By buttonLocator = switch (type.toLowerCase()) {
            case "scrum" -> scrumBoardButton;
            case "kanban" -> kanbanBoardButton;
            case "version" -> versionBoardButton;
            case "custom" -> customBoardButton;
            case "personal" -> personalBoardButton;
            default -> throw new IllegalArgumentException("Unknown board type: " + type);
        };

        wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator)).click();
        return new BoardCreationPage(webDriver, wait);
    }
}
