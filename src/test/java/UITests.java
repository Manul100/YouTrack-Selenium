import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import page.*;
import utils.AllureTestListener;
import utils.CsvDataProvider;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Listeners({AllureTestListener.class})
public class UITests {

    private WebDriver webDriver;
    private WebDriverWait wait;
    private static final List<String> taskId = new ArrayList<>();
    private static String projectName;

    @BeforeClass
    public void setUp(ITestContext context) throws IOException {
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver-win64\\chromedriver.exe");
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        context.setAttribute("driver", webDriver);

        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        String username = props.getProperty("username");
        String password = props.getProperty("password");

        webDriver.get("http://193.233.193.42:9091/");
        LoginPage loginPage = new LoginPage(webDriver, wait);
        loginPage.login(username, password);
        wait.until(ExpectedConditions.urlContains("http://193.233.193.42:9091/dashboard"));
    }

    @AfterClass
    public void quit() {
        if(webDriver != null) {
            webDriver.quit();
        }
    }

    @AfterMethod
    public void attachScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && webDriver != null) {
            byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Скриншот при падении теста",
                    new ByteArrayInputStream(screenshot));
        }
    }

    @DataProvider(name = "taskDataProvider")
    public Object[][] taskDataProvider() {
        return CsvDataProvider.readCsvData("src/test/java/testdata/taskData.csv");
    }

    @Test(dataProvider = "taskDataProvider", dependsOnMethods = "createProjectTest")
    public void createTaskTest(String name, String priority, String type, String state) {
        webDriver.get("http://193.233.193.42:9091/issues");
        try {
            IssuesPage issuesPage = new IssuesPage(webDriver, wait);
            issuesPage.createTask(name, priority, type, state);

            Assert.assertEquals(wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(String.format("//a[contains(text(), '%s')]", name)))).getText(), name);

            taskId.add(issuesPage.getTaskIdByTaskName(name));
        }
        catch (Exception e) {
            AllureTestListener.takeScreenshot(webDriver);
            throw e;
        }
    }

    @Test(dependsOnMethods = "createTaskTest")
    public void editTaskTest() {
        webDriver.get("http://193.233.193.42:9091/issues");
        String input = String.valueOf(Math.random());

        IssuesPage issuesPage = new IssuesPage(webDriver, wait);
        SelectedIssuePage selectedIssuePage = issuesPage.clickIssueLink(taskId.getFirst());
        selectedIssuePage.editTask(input);

        Assert.assertTrue(wait.until(ExpectedConditions.attributeToBe(
                By.xpath("//h1[@data-test='ticket-summary']"),
                "innerText", input)));
    }

    @Test(dependsOnMethods = "createTaskTest")
    public void addTimeTest() {
        webDriver.get("http://193.233.193.42:9091/issues");
        String time = "16";
        String timeSpentOption = "Разработка";

        IssuesPage issuesPage = new IssuesPage(webDriver, wait);
        SelectedIssuePage selectedIssuePage = issuesPage.clickIssueLink(taskId.getFirst());
        selectedIssuePage.addTime(time, timeSpentOption);

        WebElement span = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@data-test='hours']")));

        Assert.assertEquals(span.getText(), "16ч");
    }

    @Test(dependsOnMethods = "createTaskTest")
    public void addCommentaryTest() {
        webDriver.get("http://193.233.193.42:9091/issues");
        String text = "c7cRjH13pcQarViLzGp1gpFdIzO1B36K";

        IssuesPage issuesPage = new IssuesPage(webDriver, wait);
        SelectedIssuePage selectedIssuePage = issuesPage.clickIssueLink(taskId.getFirst());
        selectedIssuePage.addCommentary(text);

        WebElement comment = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format("//div[@data-test='comment-content']//p[text()='%s']", text))));
        Assert.assertEquals(comment.getText(), text);
    }

    @DataProvider(name = "deleteTaskProvider")
    public Object[] deleteTaskProvider() {
        return taskId.toArray();
    }

    @Test(dependsOnMethods = {"addTimeTest", "addCommentaryTest", "editTaskTest"}, dataProvider = "deleteTaskProvider")
    public void deleteTaskTest(String taskId) {
        webDriver.get("http://193.233.193.42:9091/issues");

        IssuesPage issuesPage = new IssuesPage(webDriver, wait);
        SelectedIssuePage selectedIssuePage = issuesPage.clickIssueLink(taskId);
        selectedIssuePage.deleteTask();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format("//span[text()='Задача %s удалена']", taskId))));
        webDriver.get("http://193.233.193.42:9091/issues");

        List<WebElement> list = webDriver.findElements(By.xpath(String.format("//a[contains(text(), '%s')]", taskId)));
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void createProjectTest() {
        webDriver.get("http://193.233.193.42:9091/projects");
        String name = "K1dDoymwre";
        String type = "Scrum";
        projectName = name;

        ProjectsPage projectsPage = new ProjectsPage(webDriver, wait);
        SelectProjectTypePage selectProjectTypePage = projectsPage.createProject();
        NewProjectPage newProjectPage = selectProjectTypePage.selectProjectType(type);
        projectsPage = newProjectPage.createNewProject(name);
        projectsPage.finishProjectCreation();

        WebElement title = webDriver.findElement(By.xpath("//h1[@data-test='project-heading']"));
        Assert.assertEquals(name, title.getText());
    }

    @Test(dependsOnMethods = "createProjectTest")
    public void createBoardTest() {
        webDriver.get("http://193.233.193.42:9091/agiles");
        String name = "TestBoard";
        String type = "Scrum";

        AgilesPage agilesPage = new AgilesPage(webDriver, wait);
        BoardTypePage boardTypePage = agilesPage.createBoard();
        BoardCreationPage boardCreationPage = boardTypePage.selectBoardType(type);
        agilesPage = boardCreationPage.createBoard(name, projectName);

        WebElement selectedBoardSpan = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@class='c_entityName__c4e']")));

        Assert.assertEquals(selectedBoardSpan.getText(), name);
    }

    @Test()
    public void createSwimlaneTest() {
        webDriver.get("http://193.233.193.42:9091/agiles");
        String name = "TestSwimlane";
        String description = "sf1FgbAN4y";
        String priority = "Критическая";
        String type = "Веха";
        String state = "В обработке";

        AgilesPage agilesPage = new AgilesPage(webDriver, wait);
        agilesPage.createSwimlane(name, description, priority, type, state);

        WebElement swimlaneSpan = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(String.format("//span[text()='%s']", name))));

        Assert.assertEquals(swimlaneSpan.getText(), name);
    }

    @Test()
    public void createSprintTest() {
        webDriver.get("http://193.233.193.42:9091/agiles");
        String name = "TestSprint";
        String goal = "Sf2gN1sgl";

        AgilesPage agilesPage = new AgilesPage(webDriver, wait);
        agilesPage.createSprint(name, goal);

        WebElement sprintDiv = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='yt-agile-board__toolbar__sprint__select__text']")));

        Assert.assertEquals(sprintDiv.getText(), name);
    }
}
