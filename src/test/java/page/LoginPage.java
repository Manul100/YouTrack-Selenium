package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    private final By usernameField  = By.id("username");
    private final By passwordField  = By.id("password");
    private final By signInButton   = By.xpath("//button[@data-test = 'login-button']");


    public LoginPage(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public void login(String username, String password) {
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
        setUsername(username);
        setPassword(password);
        clickSignInButton();
    }

    public void setUsername(String username) {
        webDriver.findElement(usernameField).sendKeys(username);
    }

    public void setPassword(String password) {
        webDriver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignInButton() {
        webDriver.findElement(signInButton).click();
    }
}
