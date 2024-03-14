package demo;

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

public class Topic_08_Button_Checkbox_RadioButton {
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
    public void TC_01(){
        driver.get("https://egov.danang.gov.vn/reg");
        WebElement registerButton = driver.findElement(By.cssSelector("input.egov-button"));
        //verify button disable
        Assert.assertFalse(registerButton.isEnabled());

        driver.findElement(By.cssSelector("input#chinhsach")).click();
        Assert.assertTrue(registerButton.isEnabled());

        //Lay ra ma mau button
        String buttonColorRGB = registerButton.getCssValue("background-color");

        //Convert kieu string qua kieu Colors
        Color buttonBackgroundColor = Color.fromString(buttonColorRGB);

        //Convert qua kieu hexa
        String registerButtonHexa = buttonBackgroundColor.asHex();
        Assert.assertEquals(registerButtonHexa,"#ef5a00");

    }

    @Test
    public void TC_02_Fahasa(){
        driver.get("https://www.fahasa.com/customer/account/create");
        driver.findElement(By.cssSelector("li.popup-login-tab-login a")).click();

        //Tim element button roi verify disable
        WebElement loginButton = driver.findElement(By.cssSelector("div.fhs-btn-box>button.fhs-btn-login"));
        Assert.assertFalse(loginButton.isEnabled());

        //Verify background color button
        String buttonColorRgb = loginButton.getCssValue("background-color");
        Color buttonBackgroundColor = Color.fromString(buttonColorRgb);
        String buttonColorHexa = buttonBackgroundColor.asHex();
        Assert.assertEquals(buttonColorHexa,"#000000");

        //Nhap email password
        driver.findElement(By.cssSelector("div.fhs-input-group>input#login_username")).sendKeys("nguyenvu@yahoo.com");
        driver.findElement(By.cssSelector("div.fhs-input-group>input#login_password")).sendKeys("123123123");
        sleepInSecond(3);

        Assert.assertTrue(loginButton.isEnabled());
        Assert.assertEquals(Color.fromString(loginButton.getCssValue("background-color")).asHex().toLowerCase(),"#C92127;");

    }

    @Test
    public void TC_03_Default_Checkbox(){
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        sleepInSecond(10);
        By dualZoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");

        checkToElement(dualZoneCheckbox);
        Assert.assertTrue(driver.findElement(dualZoneCheckbox).isSelected());
        //Bo chon checkbox

        unCheckToElement(dualZoneCheckbox);
        Assert.assertFalse(driver.findElement(dualZoneCheckbox).isSelected());

    }

    public void TC_04_Default_Radio(){
        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        //driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input")).click();
        By two_petrol = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");
        By two_diesel = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");

        checkToElement(two_petrol);
        Assert.assertTrue(driver.findElement(two_petrol).isSelected());
        Assert.assertFalse(driver.findElement(two_diesel).isSelected());
    }

    @Test
    public void TC_05_Select_AllCheckBox(){
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> allElementCheckbox = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

        for (WebElement elementCheckbox : allElementCheckbox){
            if (!elementCheckbox.isSelected())
            elementCheckbox.click();
        }
//      Verify tat ca element duoc chon
        for (WebElement elementCheckbox : allElementCheckbox){
            Assert.assertTrue(elementCheckbox.isSelected());
        }
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        allElementCheckbox = driver.findElements(By.cssSelector("div.form-single-column input[type='checkbox']"));

//      Chon 1 checkbox trong so checkbox duoc chon
        for (WebElement elementCheckbox : allElementCheckbox){
            if (elementCheckbox.getAttribute("value").equals("Gout") && !elementCheckbox.isSelected()){
                elementCheckbox.click();
            }
        }
    }

    @Test
    public void TC_06_Custom_Checkbox(){

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
