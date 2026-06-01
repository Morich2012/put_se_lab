package put.selenium.pageobjects;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import put.selenium.pageobjects.repository.*;
import put.selenium.utils.ScreenshotAndQuitOnFailureRule;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AccountsPageObjectAT {

    private WebDriver driver;

    private ResetDatabase resetDatabse;
    private MainMenu mainMenu;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private LoggedInUserInformation loggedInUserInfo;

    private String hostURL;

    @Rule
    public ScreenshotAndQuitOnFailureRule screenshotOnFailureAndWebDriverQuitRule =
            new ScreenshotAndQuitOnFailureRule();


    @Before
    public void setUp() throws Exception {

        Properties properties = new Properties();
        InputStream in = getClass().getResourceAsStream("selenium.properties");
        properties.load(in);
        in.close();

        this.hostURL=properties.getProperty("host.url");

        System.setProperty("webdriver.chrome.driver", "chromedriver-win64-120.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        screenshotOnFailureAndWebDriverQuitRule.setWebDriver(driver);

        resetDatabse = new ResetDatabase(driver, this.hostURL);
        mainMenu = new MainMenu(driver);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver, this.hostURL);
        loggedInUserInfo = new LoggedInUserInformation(driver);

        resetDatabse.resetDatabase();
    }

    @Test
    public void successfulUserRegistration() throws Exception {
        // 1. Open the home page
        mainPage.openPage();

        // 2. Check we are on the home page (not logged in → shows Login form)
        assertTrue(mainPage.isOnPageNotLoggedIn());

        // 3. Click Registration in the main menu
        mainMenu.clickRegisterLink();

        // 4. Check we are on the registration page
        assertTrue(registrationPage.isOnPage());

        // 5. Register the user
        registrationPage.registerUser("user", "password", "password", "John Doe", "10 New St.");

        // 6. Check we have been redirected to the login page
        assertTrue(loginPage.isOnPage());

        // 7. Log in with the created user
        loginPage.loginUser("user", "password");

        // 8. Check the displayed user info equals "John Doe, 10 New St."
        assertEquals("John Doe, 10 New St.", loggedInUserInfo.getUserInformation());
    }


}
