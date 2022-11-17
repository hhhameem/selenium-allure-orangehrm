package testrunners;

import pages.PIMPage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
    @Test(priority = 1, description = "Get employees with Status Full Time Permanent")
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

    @Test(priority = 2,description = "Get employees with Status Full Time probation")
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

    @Test(priority = 3, description = "Create First Employee")
    public void doAddFirstEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        Thread.sleep(5000);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = faker.name().username();
        String id = pimPage.inputFields.get(4).getAttribute("value");
        String password = "Str0ngP@ssword";

        pimPage.addEmployee(firstName,lastName, userName, password, password);
        Thread.sleep(10000);
        String expectedName = firstName + " " + lastName;
        List<WebElement> listH6 = driver.findElements(By.tagName("h6"));
        Utils.waitForElement(driver,listH6.get(1),50);
        String actualName = listH6.get(1).getText();
        Assert.assertTrue(actualName.contains(expectedName));
        if (listH6.get(1).isDisplayed()) {
            Utils.saveJsonList(userName, password, id);
        }
    }

    @Test(priority = 4, description = "Create Second Employee")
    public void doAddSecondEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        Thread.sleep(5000);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = faker.name().username();
        String id = pimPage.inputFields.get(4).getAttribute("value");
        String password = "Str0ngP@ssword";

        pimPage.addEmployee(firstName,lastName, userName, password, password);
        Thread.sleep(10000);
        String expectedName = firstName + " " + lastName;
        List<WebElement> listH6 = driver.findElements(By.tagName("h6"));
        Utils.waitForElement(driver,listH6.get(1),50);
        String actualName = listH6.get(1).getText();
        Assert.assertTrue(actualName.contains(expectedName));
        if (listH6.get(1).isDisplayed()) {
            Utils.saveJsonList(userName, password, id);
        }
    }

    @Test(priority = 5, description = "Create employee with existing username")
    public void doFailedAddEmployee() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        pimPage.addEmployeeLinkText.click();
        Thread.sleep(5000);
        String userName = Utils.getLastRegisteredUser();
        pimPage.checkUserName(userName);
        Assert.assertTrue(pimPage.userNameErrorMessage.isDisplayed());
    }

    @Test(priority = 6, description = "Search Employee with valid user ID")
    public void doFindLastEmployee() throws IOException, ParseException, InterruptedException {
        pimPage = new PIMPage(driver);
        SoftAssert softAssert = new SoftAssert();
        List usersList =Utils.readJSONList();
        //assert last employee
        JSONObject lastUserObj= (JSONObject) usersList.get(usersList.size()-1);
        String lastUserId = (String) lastUserObj.get("id");
        pimPage.employeeList.click();
        Thread.sleep(3000);
        pimPage.findEmployee(lastUserId);
        Thread.sleep(3000);
        WebElement table = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows =  table.findElements(By.cssSelector("[role=row]"));
        for (WebElement row: allRows) {
            List<WebElement> allCells = row.findElements(By.cssSelector("[role=cell]"));
            System.out.println("cell id" + allCells.get(1).getText());
            softAssert.assertEquals(allCells.get(1).getText(), lastUserId);
        }
        //assert second last employee
        JSONObject secondLastUserObj= (JSONObject) usersList.get(usersList.size()-2);
        String secondLastUserId = (String) secondLastUserObj.get("id");
        driver.navigate().refresh();
        Thread.sleep(3000);
        pimPage.findEmployee(secondLastUserId);
        Thread.sleep(3000);
        WebElement table2 = driver.findElement(By.className("oxd-table-body"));
        List<WebElement> allRows2 =  table2.findElements(By.cssSelector("[role=row]"));
        for (WebElement row2: allRows2) {
            List<WebElement> allCells2 = row2.findElements(By.cssSelector("[role=cell]"));
            System.out.println("cell id2" + allCells2.get(1).getText());
            softAssert.assertEquals(allCells2.get(1).getText(), secondLastUserId);
        }
        softAssert.assertAll();
    }


    @AfterTest
    public void doLogout() {
        pimPage = new PIMPage(driver);
        pimPage.profileImage.click();
        pimPage.logoutLink.click();
    }
}
