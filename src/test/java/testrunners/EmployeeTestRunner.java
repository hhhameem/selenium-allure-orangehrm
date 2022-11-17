package testrunners;

import pages.EmployeePage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class EmployeeTestRunner extends Setup {
    LoginPage loginPage;
    EmployeePage employeePage;

    @BeforeTest
    public void doLogin() throws InterruptedException, IOException, ParseException {
        loginPage = new LoginPage(driver);
        employeePage = new EmployeePage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com");
        List usersList = Utils.readJSONList();
        JSONObject userObj= (JSONObject) usersList.get(usersList.size()-1);
        String userName = (String) userObj.get("userName");
        String password = (String) userObj.get("password");
        loginPage.doLogin(userName, password);
        Assert.assertTrue(employeePage.dashboard.isDisplayed());
    }

    @Test(priority = 1, description = "Update employee Nationality & Driving license expiry Information")
    public void doUpdateNationality() throws InterruptedException {
        employeePage = new EmployeePage(driver);
        employeePage.menuItems.get(2).click();
        Thread.sleep(3000);
        employeePage.inputFields.get(8).sendKeys("1997-04-21");
        employeePage.selectNationality();
        Thread.sleep(3000);
        String actualNationality = "Bangladeshi";
        String expectedNationality = employeePage.selectButtons.get(0).getText();
        Assert.assertEquals(actualNationality, expectedNationality);
    }

    @Test(priority = 2, description = "Update employee Blood group Information")
    public void doUpdateBloodGroup() throws InterruptedException {
        employeePage = new EmployeePage(driver);
        Utils.scrollDown(driver);
        employeePage.selectBloodGroup();
        Thread.sleep(3000);
        String actualBloodGroup = "A+";
        String expectedBloodGroup = employeePage.selectButtons.get(2).getText();
        Assert.assertEquals(actualBloodGroup, expectedBloodGroup);
    }
    @AfterTest
    public void doLogout() {
        employeePage = new EmployeePage(driver);
        employeePage.profileImage.click();
        employeePage.logoutLink.click();
    }

}
