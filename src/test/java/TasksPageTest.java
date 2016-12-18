import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageobjects.employees.DeleteDialog;
import pageobjects.login.LoginPage;
import pageobjects.tasks.TaskDialog;
import pageobjects.tasks.TasksPage;

/**
 * Created by ilshat on 17.12.16.
 */
public class TasksPageTest {
    private WebDriver driver;
    private TasksPage page;
    private TaskDialog dialog;
    private DeleteDialog deleteDialog;

    @Before
    public void setUp() {
//        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
//        driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWith("userone", "userone");
        assertTrue(loginPage.mainPageHeaderIsSet());
        page = new TasksPage(driver);
        dialog = new TaskDialog(driver);
        deleteDialog = new DeleteDialog(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void taskFlow() {
        String title = "Task title";
        createTask(title);
        String newTitle = "New task title";
        update(title, newTitle);
        deleteLastTask();
    }

    private void createTask(String title) {
        page.clickNewTaskButton();
        assertTrue(dialog.wasOpened());
        dialog.fill(title);
        dialog.clickSave();
        assertTrue(dialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Задача сохранена", page.getSnackbarMessage());
        assertTrue(page.lastTableRowContains(title));
    }

    private void update(String title, String newTitle) {
        page.moveToLastTableRow();
        page.clickEditButton();
        assertTrue(dialog.wasOpened());
        assertTrue(dialog.isFilledWith(title));
        dialog.fill(newTitle);
        dialog.clickSave();
        assertTrue(dialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Задача сохранена", page.getSnackbarMessage());
        assertTrue(page.lastTableRowContains(newTitle));
    }


    private void deleteLastTask() {
        page.moveToLastTableRow();
        page.clickDeleteButton();
        assertTrue(deleteDialog.wasOpened());
        deleteDialog.clickConfirmDeleteButton();
        assertTrue(deleteDialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Задача удалена", page.getSnackbarMessage());
    }
}
