package pageobjects.employees;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ilshat on 04.12.16.
 */
public class DeleteDialog {
    private By dialogLocator = By.className("deleteDialog");
    private By confirmDeleteButtonLocator = By.id("confirmDeleteButton");

    private WebDriver driver;
    private WebDriverWait waitOneSec;

    public DeleteDialog(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
    }

    public boolean wasOpened() {
        waitOneSec.until(visibilityOfElementLocated(dialogLocator));
        return true;
    }

    public void clickConfirmDeleteButton() {
        driver.findElement(confirmDeleteButtonLocator).click();
    }

    public boolean wasClosed() {
        waitOneSec.until(invisibilityOfElementLocated(dialogLocator));
        return true;
    }
}
