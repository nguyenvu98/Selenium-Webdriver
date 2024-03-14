package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_06_Browser_Element_Comand_03 {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmailAddress(){
        String emailAdrress = "nguyenvu" + new Random().nextInt(999999) + "@gmail.net";
        return  emailAdrress;
    }
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
    }

    @Test
    public void TC_01(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        driver.findElement(By.cssSelector("button#send2")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");

    }

    @Test
    public void TC_02(){
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys("123123123@12312312.123");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("123456");
        sleepInSecond(3);

        driver.findElement(By.cssSelector("button#send2")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div#advice-required-entry-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");
    }

    @Test
    public void TC_03(){
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        //Dky 1 tai khoan
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
        sleepInSecond(3);

        String firstName = "Nguyen Vu", lastName = "Vo", emailAddress = getEmailAddress(), password = "123456";
        String fullname = firstName +" "+ lastName;

        driver.findElement(By.cssSelector("input#firstname")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input#lastname")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input#email_address")).sendKeys(emailAddress);
        driver.findElement(By.cssSelector("input#password")).sendKeys(password);
        driver.findElement(By.cssSelector("input#confirmation")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Register']")).click();

        //veryfy trang login
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullname + "!");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullname));
        Assert.assertTrue(contactInfo.contains(emailAddress));

        //logout
        driver.findElement(By.cssSelector("a.skip-account")).click();
        sleepInSecond(2);
        driver.findElement(By.cssSelector("a[title='Log Out']")).click();
        sleepInSecond(5);

        //login
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        sleepInSecond(3);

        driver.findElement(By.cssSelector("input#email")).sendKeys(emailAddress);
        driver.findElement(By.cssSelector("input#pass")).sendKeys(password);

        driver.findElement(By.cssSelector("button#send2")).click();
        sleepInSecond(3);

        contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullname));
        Assert.assertTrue(contactInfo.contains(emailAddress));

    }

    @Test
    public void TC_04(){
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.findElement(By.xpath("//label[text()='Nombre de usuario']/parent::div/following-sibling::div//input[@name='username']")).sendKeys("Admin");
        driver.findElement(By.xpath("//label[text()='Nombre de usuario']/parent::div/following-sibling::div//input[@name='password']")).sendKeys("admin123");

        driver.findElement(By.xpath("//div[@class='oxd-sidepanel-body']//ul//li[position()='2']//a")).click();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
