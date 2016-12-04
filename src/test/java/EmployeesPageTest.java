import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageobjects.employees.DeleteDialog;
import pageobjects.employees.EmployeeDialog;
import pageobjects.employees.EmployeesPage;
import pageobjects.login.LoginPage;

public class EmployeesPageTest {

    private WebDriver driver;
    private EmployeesPage page;
    private EmployeeDialog dialog;
    private DeleteDialog deleteDialog;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWith("userone", "userone");
        assertTrue(loginPage.mainPageHeaderIsSet());
        page = new EmployeesPage(driver);
        dialog = new EmployeeDialog(driver);
        deleteDialog = new DeleteDialog(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void employeeFlow() {
        String firstname = "Michael";
        String lastname = "Scott";
        String username = "mscott";
        createEmployee(firstname, lastname, username);

        String newFirstname = "Mike";
        String newLastname = "Scotty";
        update(firstname, lastname, username, newFirstname, newLastname);

        deleteLastEmployee();
    }

    private void update(String firstname, String lastname, String username,
                        String newFirstname, String newLastname) {
        page.moveToLastTableRow();
        page.clickEditButton();
        assertTrue(dialog.wasOpened());
        assertTrue(dialog.isFilledWith(firstname, lastname, username));
        dialog.fill(newFirstname, newLastname);
        dialog.clickSave();
        assertTrue(dialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Сотрудник сохранен", page.getSnackbarMessage());
        assertTrue(page.lastTableRowContains(newFirstname, newLastname));
    }

    private void createEmployee(String firstname, String lastname, String username) {
        page.clickNewEmployeeButton();
        assertTrue(dialog.wasOpened());
        dialog.fill(firstname, lastname, username);
        dialog.clickSave();
        assertTrue(dialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Сотрудник сохранен", page.getSnackbarMessage());
        assertTrue(page.lastTableRowContains(firstname, lastname));
    }

    private void deleteLastEmployee() {
        page.moveToLastTableRow();
        page.clickDeleteButton();
        assertTrue(deleteDialog.wasOpened());
        deleteDialog.clickConfirmDeleteButton();
        assertTrue(deleteDialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Сотрудник удален", page.getSnackbarMessage());
    }

    @Test
    public void requiredFieldErrors() {
        page.clickNewEmployeeButton();
        dialog.wasOpened();
        dialog.fill("Michael", "", "");
        dialog.clickSave();
        dialog.isOpen();
        assertEquals("Обязательное поле", dialog.getFieldError("lastname"));
        assertEquals("Обязательное поле", dialog.getFieldError("username"));
    }

    @Test
    public void usernameTooShort() {
        page.clickNewEmployeeButton();
        dialog.wasOpened();
        dialog.fill("Michael", "Scott", "ms");
        dialog.clickSave();
        dialog.isOpen();
        assertEquals("Минимум 3 символа, максимум 20", dialog.getFieldError("username"));
    }

    @Test
    public void usernameWrongCharacters() {
        page.clickNewEmployeeButton();
        dialog.wasOpened();
        dialog.fill("Michael", "Scott", "1234");
        dialog.clickSave();
        dialog.isOpen();
        assertEquals("Только латинские буквы", dialog.getFieldError("username"));
    }

    @Test
    public void usernameAlreadyUsed() {
        String firstname = "Michael";
        String lastname = "Scott";
        String username = "mscott";
        createEmployee(firstname, lastname, username);

        String newFirstname = "Mike";
        String newLastname = "Scotty";
        page.clickTableHeader(); // to hide snackbar which prevents fab clicking
        page.clickNewEmployeeButton();
        dialog.wasOpened();
        dialog.fill(newFirstname, newLastname, "mscott");
        dialog.clickSave();
        dialog.isOpen();
        assertEquals("Такой логин уже используется", dialog.getFieldError("username"));

        dialog.clickCancel();
        dialog.wasClosed();

        deleteLastEmployee();
    }
}
