package pageobjects.profile;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait waitOneSec;
    private By changePasswordButtonLocator = By.id("changePasswordButton");
    private By snackbarLocator = By.id("snackbar");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
        driver.get("http://localhost:3000/profile");
        By pageHeaderLocator = By.tagName("h1");
        waitOneSec.until(textToBe(pageHeaderLocator, "Профиль"));
    }

    public void clickChangePasswordButton() {
        driver.findElement(changePasswordButtonLocator).click();
    }

    public boolean snackbarDisplayed() {
        waitOneSec.until(visibilityOfElementLocated(snackbarLocator));
        return true;
    }

    public String getSnackbarMessage() {
        return driver.findElement(snackbarLocator).getText();
    }
}
