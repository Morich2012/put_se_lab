package put.selenium.linear;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import put.selenium.utils.ScreenshotAndQuitOnFailureRule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class AccountsLinearScriptAT {

    private WebDriver driver;

    @Rule
    public ScreenshotAndQuitOnFailureRule screenshotOnFailureAndWebDriverQuitRule =
            new ScreenshotAndQuitOnFailureRule();


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64-120.exe");
        this.driver = new ChromeDriver();
        screenshotOnFailureAndWebDriverQuitRule.setWebDriver(driver);
        driver.get("http://localhost:8080/accounts/controller?action=db_reset");
    }

    @Test
    public void successfulUserRegistration() throws Exception {
        driver.get("http://localhost:8080/accounts/controller");
        driver.findElement(By.linkText("Register")).click();

        assertThat(driver.findElement(By.xpath("//h3")).getText(), is("Register"));

        driver.findElement(By.name("username")).sendKeys("user");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.name("repeat_password")).sendKeys("password");
        driver.findElement(By.name("name")).sendKeys("John Doe");
        driver.findElement(By.name("addressData")).sendKeys("10 New St.");
        driver.findElement(By.name("submit")).click();

        assertThat(driver.findElement(By.xpath("//h3")).getText(), is("Login"));
    }


}
