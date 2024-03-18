package demo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_13_Alert {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    WebDriverWait explicitWait;


    @BeforeClass
    public void beforeClass() {

        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\src\\browserDriver\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/src/browserDriver/geckodriver.exe");
        }

//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new FirefoxDriver();
//      driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Accept(){
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = driver.switchTo().alert();

    }

    @Test
    public void TC_02_Authentication_Alert(){
        //Cach 1: Su dung tach chuoi roi cong lai ra url https://admin:admin@the-internet.herokuapp.com/basic-auth
        String username = "admdin";
        String password = "admdin";

        driver.get("https://the-internet.herokuapp.com/basic-auth");
        String authUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        String[] authUrlArray = authUrl.split("//");
        driver.get(authUrlArray[0] + "//" + username + ":" + password + "@" +authUrlArray[1]);
        //Cach 2: Su dung Slenium version 4
        
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
