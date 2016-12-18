package pageobjects.tasks;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskDialog {
    private By dialogLocator = By.className("taskDialog");
    private By firstnameLocator = By.name("title");
    private By saveButtonLocator = By.id("saveButton");

    private WebDriver driver;
    private WebDriverWait waitOneSec;

    public TaskDialog(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
    }

    public boolean wasOpened() {
        waitOneSec.until(visibilityOfElementLocated(dialogLocator));
        return true;
    }

    public void fill(String title) {
        setInputValue(firstnameLocator, title);
    }

    public boolean isFilledWith(String title) {
        String titleInDialog = getInputValue(firstnameLocator);
        return title.equals(titleInDialog);
    }

    private String getInputValue(By inputLocator) {
        return driver.findElement(inputLocator).getAttribute("value");
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
}
