package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Topic_07_Dropdown {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    WebDriverWait explicitWait;
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
    String firstName = "Nguyen Vu",lastName ="Vo", emaillAddress = getEmailAddress(), companyName="Nguyen Vu Pro FC", password="123123";
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
        driver.get("https://demo.nopcommerce.com/");
        driver.findElement(By.xpath("//a[@class='ico-register']")).click();
        sleepInSecond(3);

        Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
        day.selectByVisibleText("15");

        //verify dropdown la single
        Assert.assertFalse(day.isMultiple());

        //verify so luong item dropdown
        List<WebElement> dayOption = day.getOptions();
        Assert.assertEquals(dayOption.size(),32);
        //Assert.assertEquals(day.getOptions().size(),32);



        new Select(driver.findElement(By.name("DateOfBirthMonth"))).selectByVisibleText("May");
        new Select(driver.findElement(By.name("DateOfBirthYear"))).selectByVisibleText("2024");

        driver.findElement(By.cssSelector("input#FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        driver.findElement(By.id("Email")).sendKeys(emaillAddress);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("Company")).sendKeys(companyName);

        driver.findElement(By.id("register-button")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(),"Your registration completed");

    }

    @Test
    public void TC_02_Custom_Dropdown_Jquery(){
        driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
//        1.  Click vao 1 the cho no so het gia tri dropdown
        driver.findElement(By.cssSelector("span#number-button")).click();

//        2.1 No se so ra het gia tri
//        2.2 No se so ra 1 phan nhung can thoi gian load them
        selectItemInDropdown("span#number-button","ul#number-menu div","5");
        sleepInSecond(3);

        driver.navigate().refresh();
        selectItemInDropdown("span#number-button","ul#number-menu div","12");

        //verify
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),"12");


    }

    @Test
    public void TC_03_Custom_Dropdown_React(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        selectItemInDropdown("i.dropdown.icon","div.item>span.text","Matt");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Matt");
    }


    @Test
    public void TC_04_Custom_Dropdown_Editable(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        selectItemEditableDropdown("input.search","div.item span","Austria");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Austria");
    }

    public void selectItemEditableDropdown(String parentCss, String childCss, String ItemExpected){
        driver.findElement(By.cssSelector("input.search")).clear();
        driver.findElement(By.cssSelector("input.search")).sendKeys(ItemExpected);
        List<WebElement> allItem = explicitWait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector(childCss)));
        for(WebElement item : allItem){
            String textItem = item.getText();
            if (textItem.equals(ItemExpected)){
                item.click();
                break;
            }
        }
    }

    public void selectItemInDropdown(String parentCss, String childCss,String itemTextExpected){
        driver.findElement(By.cssSelector(parentCss)).click();
        //vua wait vua tim element
        List<WebElement> allItem = explicitWait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector(childCss)));
        for(WebElement item : allItem){
            String textItem = item.getText();
            if (textItem.equals(itemTextExpected)){
                item.click();
                break;
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
