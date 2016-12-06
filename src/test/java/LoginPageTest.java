import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageobjects.login.LoginPage;

public class LoginPageTest {

    private static final String[] VALID_CREDENTIALS = {"userone", "userone"};
    private static final String[] INVALID_CREDENTIALS = {"hello", "world"};

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
//        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
//        driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testMenuButtonNotPresentBeforeLogin() throws Exception {
        assertTrue(loginPage.menuButtonNotPresent());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        loginPage.loginWith(VALID_CREDENTIALS[0], VALID_CREDENTIALS[1]);
        assertTrue(loginPage.mainPageHeaderIsSet());
    }

    @Test
    public void testMenuButtonPresentAfterLogin() throws Exception {
        loginPage.loginWith(VALID_CREDENTIALS[0], VALID_CREDENTIALS[1]);
        assertTrue(loginPage.menuButtonPresent());
    }

    @Test
    public void testLoginFailedIfWrongCredentials() throws Exception {
        loginPage.loginWith(INVALID_CREDENTIALS[0], INVALID_CREDENTIALS[1]);
        assertTrue(loginPage.errorMessagePresent());
        assertEquals("Неправильный логин или пароль", loginPage.getErrorMessage());
    }
}
