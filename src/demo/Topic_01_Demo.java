package demo;

import java.awt.event.FocusEvent.Cause;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_01_Demo {
    /*
     * Selenium version: 1.x/ 2.x/ 3.x/ 4.x 8 loai Locator Selenium Locator = HTML
     * Attribute: Id/ Class/ Name = Trûng vs 3 attribute cúa HTML LinkText/ Partial
     * LinkText: HTML Link (the a) Tagname: HTML Tagname Css/ XPath
     *
     * Selenium version 4. - GUI (Graphic User Interface) Class - Relative Locator:
     * above/ bellow/ near/ leftof / rightof
     */
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\src\\browserDriver\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/src/browserDriver/chromedriver.exe");
        }

//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver(co);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
    }

    @Test
    public void TC_01_abc() {
        driver.get("http://live.techpanda.org/index.php/customer/account/login/");
        // Click My Account tren duoi footer
        // Element khong la duy nhat nen phai lay tu doi cha
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
    }

    @Test
    public void TC_02_getTextElemnt() {
        driver.get("http://live.techpanda.org/index.php/checkout/cart/");
        String successMessageText = driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText();
        Assert.assertEquals(successMessageText, "IPhone was added to your shopping cart.");
    }

    @Test
    public void TC_03_Relative_Element() {
        //span[start-with(text(),'Countinue'] dung trong truong hop phan dau cua attribute khong thay doi
        //button[contain(@name,'update-cart']
    }

    @Test
    public void TC_04_Handle_Text() {
        driver.get("https://automationfc.github.io/basic-form/");

        //1. Truyen text vao trong locator de check hien thi (isDisplayed)
        //Nen su dung vi no tuyet doi
        driver.findElement(By.xpath("//h1[text()='Selenium Webdriver']")).isDisplayed();
        //2. Get text ra roi verify sau
        String textVerify = driver.findElement(By.xpath("//p[contain(text(),'Mail Personal or']")).getText();
        Assert.assertTrue(textVerify.contains("String can test"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

}
