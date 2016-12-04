package pageobjects.employees;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeDialog {
    private By dialogLocator = By.className("employeeDialog");
    private By firstnameLocator = By.name("firstname");
    private By lastnameLocator = By.name("lastname");
    private By usernameLocator = By.name("username");
    private By saveButtonLocator = By.id("saveButton");
    private By cancelButtonLocator = By.id("cancelButton");

    private WebDriver driver;
    private WebDriverWait waitOneSec;

    public EmployeeDialog(WebDriver driver) {
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

    public void fill(String firstname, String lastname, String username) {
        setInputValue(firstnameLocator, firstname);
        setInputValue(lastnameLocator, lastname);
        setInputValue(usernameLocator, username);
    }

    public void fill(String firstname, String lastname) {
        setInputValue(firstnameLocator, firstname);
        setInputValue(lastnameLocator, lastname);
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

    public boolean isFilledWith(String firstname, String lastname, String username) {
        String firstnameInDialog = getInputValue(firstnameLocator);
        String lastnameInDialog = getInputValue(lastnameLocator);
        String usernameInDialog = getInputValue(usernameLocator);
        return firstname.equals(firstnameInDialog)
            && lastname.equals(lastnameInDialog)
            && username.equals(usernameInDialog);
    }

    private String getInputValue(By inputLocator) {
        return driver.findElement(inputLocator).getAttribute("value");
    }

    public void clickCancel() {
        driver.findElement(cancelButtonLocator).click();
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
}
