package demo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_14_Actions {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    Actions actions;
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
        actions = new Actions(driver);
    }

    @Test
    public void TC_01_Tooltips(){
        driver.get("https://automationfc.github.io/jquery-tooltip/");
        WebElement ageTextbox = driver.findElement(By.cssSelector("input#age"));

        actions.moveToElement(ageTextbox).perform();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.xpath("div[@role='tooltip']//div")).getText(),
                "We ask for your age only for statistical purposes.");

    }

    @Test
    public void TC_02_Hover_Element(){
        driver.get("https://www.myntra.com/");
        actions.moveToElement(driver.findElement(By.xpath("//a[text()='Kids' and @class='desktop-main']"))).perform();
        sleepInSecond(3);

        actions.moveToElement(driver.findElement(By.xpath("//a[text()='Home & Bath' and @class='desktop-categoryName']"))).perform();
        actions.click(driver.findElement(By.xpath("//a[text()='Home & Bath' and @class='desktop-categoryName']"))).perform();
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
