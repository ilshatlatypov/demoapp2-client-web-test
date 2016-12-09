package pageobjects.profile;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfNestedElementLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordDialog {
    private final WebDriver driver;
    private final WebDriverWait waitOneSec;

    private By dialogLocator = By.className("changePasswordDialog");
    private By currentValueLocator = By.name("currentValue");
    private By newValueLocator = By.name("newValue");
    private By newValueConfirmationLocator = By.name("newValueConfirmation");
    private By saveButtonLocator = By.id("saveButton");
    private By cancelButtonLocator = By.id("cancelButton");

    public ChangePasswordDialog(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
    }

    public boolean wasOpened() {
        waitOneSec.until(visibilityOfElementLocated(dialogLocator));
        return true;
    }

    public boolean isOpen() {
        return driver.findElement(dialogLocator).isDisplayed();
    }

    public void fill(String currentValue, String newValue, String newValueConfirmation) {
        setInputValue(currentValueLocator, currentValue);
        setInputValue(newValueLocator, newValue);
        setInputValue(newValueConfirmationLocator, newValueConfirmation);
    }

    private void setInputValue(By inputLocator, String firstname) {
        driver.findElement(inputLocator).clear();
        driver.findElement(inputLocator).sendKeys(firstname);
    }

    public void clickSave() {
        driver.findElement(saveButtonLocator).click();
    }

    public boolean wasClosed() {
        waitOneSec.until(invisibilityOfElementLocated(dialogLocator));
        return true;
    }

    public String getFieldError(String fieldName) {
        WebElement textFieldElement = driver.findElement(By.className(fieldName));
        List<WebElement> divs = textFieldElement.findElements(By.tagName("div"));

        boolean errorDivExists = divs.size() == 2;
        String error = null;
        if (errorDivExists) {
            WebElement errorDiv = divs.get(1);
            error = errorDiv.getText();
        }
        return error;
    }

    public String getFieldErrorWithWait(String fieldName) {
        WebElement textFieldElement = driver.findElement(By.className(fieldName));
        waitOneSec.until(presenceOfNestedElementLocatedBy(textFieldElement, By.xpath("./div[2]")));
        WebElement errorDiv = textFieldElement.findElement(By.xpath("./div[2]"));
        return errorDiv.getText();
    }
}
