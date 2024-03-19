package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_15_Popups {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    Keys keys;


    Actions actions;
    WebDriverWait explicitWait;


    @BeforeClass
    public void beforeClass() {

        if (osName.contains("Windows")) {
//            System.setProperty("webdriver.gecko.driver", projectPath + "\\src\\browserDriver\\geckodriver.exe");
            keys = Keys.CONTROL;
        } else {
//            System.setProperty("webdriver.gecko.driver", projectPath + "/src/browserDriver/geckodriver.exe");
            keys = Keys.COMMAND;
        }

        Keys cmdCtrl = Platform.getCurrent().is(Platform.WINDOWS) ? Keys.CONTROL : Keys.COMMAND;

//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new FirefoxDriver();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(new FirefoxProfile());
        firefoxOptions.addPreference("dom.webnotifications.enabled",false);
        driver = new FirefoxDriver(firefoxOptions);

//      driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        actions = new Actions(driver);
    }

    @Test
    public void TC_01_Fixed_In_Dom(){
        driver.get("https://ngoaingu24h.vn/");
        By loginPopup = By.cssSelector("div#modal-login-v1 div.modal-content");

        //Verify popup is undisplayed
        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

        //Click button login
        driver.findElement(By.xpath("//div[@id='button-login-dialog']//button[@class='login_ icon-before']")).click();

        //Verify popup is displayed
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        //login
        driver.findElement(By.xpath("//div[@class='modal fade in']//input[@id='account-input']")).sendKeys("automationfc");
        driver.findElement(By.xpath("//div[@class='modal fade in']//input[@id='password-input']")).sendKeys("automationfc");

        driver.findElement(By.xpath("//div[@class='modal fade in']//button[@class='btn-v1 btn-login-v1 buttonLoading']")).click();
    }


    @Test
    public void TC_02_Not_Fixed_In_DOM(){
//        driver.get("https://tiki.vn/");
//        driver.findElement(By.xpath("//span[text()='Tài khoản']")).click();
    }
    public void checkToElement(By byXpath){
        if (!driver.findElement(byXpath).isSelected()){
            driver.findElement(byXpath).click();
            sleepInSecond(3);
        }
    }
    public void unCheckToElement(By byXpath) {
        if (driver.findElement(byXpath).isSelected()) {
            driver.findElement(byXpath).click();
            sleepInSecond(3);
        }
    }
    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
