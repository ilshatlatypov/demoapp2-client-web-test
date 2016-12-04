package pageobjects.login;

import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private final WebDriverWait waitOneSec;

    private By usernameLocator  = By.name("login");
    private By passwordLocator  = By.name("password");
    private By submitButton     = By.tagName("button");
    private By errorMessageLocator = By.className("formError");
    private By menuButtonLocator = By.id("menuButton");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
        driver.get("http://localhost:3000/login");
        By loginFormLocator = By.tagName("form");
        assertTrue(driver.findElement(loginFormLocator).isDisplayed());
    }

    public void loginWith(String username, String password) {
        driver.findElement(usernameLocator).sendKeys(username);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean mainPageHeaderIsSet() {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), "Главная"));
        return true;
    }

    public boolean errorMessagePresent() {
        waitOneSec.until(ExpectedConditions.presenceOfElementLocated(errorMessageLocator));
        return driver.findElement(errorMessageLocator).isDisplayed();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessageLocator).getText();
    }

    public boolean menuButtonNotPresent() {
        return driver.findElements(menuButtonLocator).isEmpty();
    }

    public boolean menuButtonPresent() {
        waitOneSec.until(ExpectedConditions.presenceOfElementLocated(menuButtonLocator));
        return driver.findElement(menuButtonLocator).isDisplayed();
    }
}
