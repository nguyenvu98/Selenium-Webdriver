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

public class Topic_16_Shadow_DOM {
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
    public void TC_01_SHadow_Dom(){
        driver.get("https://automationfc.github.io/shadow-dom/");
        WebElement shadowHostElement = driver.findElement(By.cssSelector("div#shadow_host"));
        SearchContext shadowRootContext = shadowHostElement.getShadowRoot();

        //shadowRootContext dai dien cho shadowDom
        //Phai goi thong qua Class SearchContext de tim trong shadowdom

        shadowRootContext.findElement(By.cssSelector("span#shadow_content>span")).getText();
        String nestText = driver.findElement((By.cssSelector("div#nested_shadow_content>div"))).getText();


        WebElement nestedShadowHost = shadowRootContext.findElement(By.cssSelector("div#nested_shadow_host"));
    }

    @Test
    public void TC_02_Shadow_DOM_Shoppee(){
        driver.get("https://shopee.vn/");
        WebElement shadowHostElement = driver.findElement(By.cssSelector("shopee-banner-popup-stateful"));
        SearchContext shadowRootElement = shadowHostElement.getShadowRoot();

        //check popup hien thi
        if (shadowRootElement.findElements(By.cssSelector("div.home-popup__context")).size()>0
                && shadowRootElement.findElement(By.cssSelector("div.home-popup__context")).isDisplayed()){
            shadowRootElement.findElement(By.cssSelector("div.shopee-popup__close-button")).click();
            sleepInSecond(3);
        }else {
            driver.findElement(By.cssSelector("input.shopee-searchbar-input__input")).sendKeys("exciter 150");
        }
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
