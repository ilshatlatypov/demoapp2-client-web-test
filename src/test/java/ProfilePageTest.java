import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageobjects.login.LoginPage;
import pageobjects.profile.ChangePasswordDialog;
import pageobjects.profile.ProfilePage;

public class ProfilePageTest {
    private static final String LOGIN = "userone";
    private static final String PASSWORD = "userone";
    private static final String NEW_PASSWORD = "newpassword";
    private static final String WRONG_CURRENT_PASSWORD = "wrongpassword";
    private static final String BLANK = "";
    private static final String NEW_PASSWORD_TOO_SHORT = "ns";

    private WebDriver driver;
    private ProfilePage page;
    private ChangePasswordDialog dialog;

    @Before
    public void setUp() {
//        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
//        driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWith(LOGIN, PASSWORD);
        assertTrue(loginPage.mainPageHeaderIsSet());
        page = new ProfilePage(driver);
        dialog = new ChangePasswordDialog(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void changePassword() throws Exception {
        changePassword(PASSWORD, NEW_PASSWORD);
        changePassword(NEW_PASSWORD, PASSWORD); // rollback password change
    }

    @Test
    public void changePasswordFailsIfCurrentValueIsWrong() throws Exception {
        openChangePasswordDialogFillAndClickSave(WRONG_CURRENT_PASSWORD, NEW_PASSWORD, NEW_PASSWORD);
        assertTrue(dialog.isOpen());
        assertEquals("Неправильный пароль", dialog.getFieldErrorWithWait("currentValue"));
    }

    @Test
    public void changePasswordFailsIfCurrentValueIsBlank() throws Exception {
        openChangePasswordDialogFillAndClickSave(BLANK, NEW_PASSWORD, NEW_PASSWORD);
        assertTrue(dialog.isOpen());
        assertEquals("Обязательное поле", dialog.getFieldError("currentValue"));
    }

    @Test
    public void changePasswordFailsIfNewPasswordValueIsBlank() throws Exception {
        openChangePasswordDialogFillAndClickSave(PASSWORD, BLANK, BLANK);
        assertTrue(dialog.isOpen());
        assertEquals("Обязательное поле", dialog.getFieldError("newValue"));
        assertEquals("Обязательное поле", dialog.getFieldError("newValueConfirmation"));
    }

    @Test
    public void changePasswordFailsIfNewPasswordValueIsTooShort() throws Exception {
        openChangePasswordDialogFillAndClickSave(PASSWORD, NEW_PASSWORD_TOO_SHORT, NEW_PASSWORD_TOO_SHORT);
        assertTrue(dialog.isOpen());
        assertEquals("Минимум 3 символа, максимум 20", dialog.getFieldError("newValue"));
    }

    @Test
    public void changePasswordFailsIfNewValueConfirmationIsDifferent() throws Exception {
        openChangePasswordDialogFillAndClickSave(PASSWORD, NEW_PASSWORD, "newpassworddifferent");
        assertTrue(dialog.isOpen());
        assertEquals("Должно совпадать с новым паролем", dialog.getFieldError("newValueConfirmation"));
    }

    private void changePassword(String currentPassword, String newPassword) {
        openChangePasswordDialogFillAndClickSave(currentPassword, newPassword, newPassword);
        assertTrue(dialog.wasClosed());
        assertTrue(page.snackbarDisplayed());
        assertEquals("Пароль изменен", page.getSnackbarMessage());
    }

    private void openChangePasswordDialogFillAndClickSave(String currentPassword, String newPassword,
                                                          String newPasswordConfirmation) {
        page.clickChangePasswordButton();
        assertTrue(dialog.wasOpened());
        dialog.fill(currentPassword, newPassword, newPasswordConfirmation);
        dialog.clickSave();
    }
}
