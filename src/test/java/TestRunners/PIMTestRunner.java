package TestRunners;

import Pages.PIMPage;
import Pages.LoginPage;
import Setup.Setup;
import Utils.Utils;
import com.github.javafaker.Faker;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class PIMTestRunner extends Setup {
    PIMPage pimPage;
    LoginPage loginPage;

    @BeforeTest
    public void doLogin() throws InterruptedException {
        loginPage = new LoginPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage.doLogin("Admin", "admin123");
        Thread.sleep(5000);
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "dashboard";
        Assert.assertTrue(actualUrl.contains(expectedUrl));
        List<WebElement> menus = driver.findElements(By.className("oxd-main-menu-item--name"));
        menus.get(1).click();
    }
    @Test(priority = 1)
    public void getEmployeeStatusFullTimePermanent() throws InterruptedException {
        pimPage = new PIMPage(driver);
        Thread.sleep(5000);
        pimPage.selectEmploymentStatus(driver, 3);
        Utils.scrollDown(driver);
        WebElement table = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows =  table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row: allRows) {
            List<WebElement> allCells = row.findElements(By.cssSelector("[role=cell]"));
            Assert.assertTrue(allCells.get(5).getText().contains("Permanent"));
        }
    }

    @Test(priority = 2)
    public void getEmployeeStatusFullTimeProbation() throws InterruptedException {
        pimPage = new PIMPage(driver);
        Utils.scrollUp(driver);
        pimPage.selectEmploymentStatus(driver, 4);
        Thread.sleep(2000);
        Utils.scrollDown(driver);
        WebElement table = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows =  table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row: allRows) {
            List<WebElement> allCells = row.findElements(By.cssSelector("[role=cell]"));
            Assert.assertTrue(allCells.get(5).getText().contains("Full-Time Probation"));
        }
    }

    @Test(priority = 3)
    public void doAddEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        Thread.sleep(5000);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = faker.name().username();
        String password = "Str0ngP@ssword";

        pimPage.addEmployee(firstName,lastName, userName, password, password);
        Thread.sleep(10000);
        String expectedName = firstName + " " + lastName;
        List<WebElement> listH6 = driver.findElements(By.tagName("h6"));
        Utils.waitForElement(driver,listH6.get(1),50);
        String actualName = listH6.get(1).getText();
        Assert.assertTrue(actualName.contains(expectedName));
        if (listH6.get(1).isDisplayed()) {
            Utils.saveJsonList(userName, password);
        }
    }

    @Test(priority = 4)
    public void doFailedAddEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        Thread.sleep(5000);
        String userName = Utils.getLastRegisteredUser();
        pimPage.checkUserName(userName);
        Assert.assertTrue(pimPage.userNameErrorMessage.isDisplayed());
    }

    @AfterTest
    public void doLogout() {
        pimPage = new PIMPage(driver);
        pimPage.profileImage.click();
        pimPage.logoutLink.click();
    }
}
