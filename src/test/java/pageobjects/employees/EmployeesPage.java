package pageobjects.employees;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeesPage {
    private By fabLocator = By.className("fab");
    private By snackbarLocator = By.id("snackbar");

    private By lastTableRowLocator = By.xpath("//tbody/tr[last()]");
    private By tableHeaderRowLocator = By.xpath("//thead/tr");
    private By firstnameColumnLocator = By.className("firstnameColumn");
    private By lastnameColumnLocator = By.className("lastnameColumn");

    private By deleteButtonLocator = By.className("deleteButton");
    private By editButtonLocator = By.className("editButton");

    private WebDriver driver;
    private WebDriverWait waitOneSec;

    public EmployeesPage(WebDriver driver) {
        this.driver = driver;
        this.waitOneSec = new WebDriverWait(driver, 1);
        driver.get("http://localhost:3000/employees");
        By pageHeaderLocator = By.tagName("h1");
        waitOneSec.until(textToBe(pageHeaderLocator, "Сотрудники"));
        By tableLocator = By.tagName("table");
        assertTrue(driver.findElement(tableLocator).isDisplayed());
        assertTrue(driver.findElement(fabLocator).isDisplayed());
    }

    public void clickNewEmployeeButton() {
        driver.findElement(fabLocator).click();
    }

    public boolean snackbarDisplayed() {
        waitOneSec.until(visibilityOfElementLocated(snackbarLocator));
        return true;
    }

    public String getSnackbarMessage() {
        return driver.findElement(snackbarLocator).getText();
    }

    public boolean lastTableRowContains(String firstname, String lastname) {
        WebElement lastRow = driver.findElement(lastTableRowLocator);
        String lastRowFirstname = lastRow.findElement(firstnameColumnLocator).getText();
        String lastRowLastname = lastRow.findElement(lastnameColumnLocator).getText();
        return firstname.equals(lastRowFirstname) && lastname.equals(lastRowLastname);
    }

    public void moveToLastTableRow() {
        Actions actions = new Actions(driver);
        WebElement header = driver.findElement(tableHeaderRowLocator);
        WebElement lastRow = driver.findElement(lastTableRowLocator);
        actions.moveToElement(header)
            .moveToElement(lastRow)
            .build().perform();
    }

    public void clickDeleteButton() {
        waitOneSec.until(presenceOfElementLocated(deleteButtonLocator)).click();
    }

    public void clickEditButton() {
        waitOneSec.until(presenceOfElementLocated(editButtonLocator)).click();
    }

    public void clickTableHeader() {
        driver.findElement(tableHeaderRowLocator).click();
    }
}
