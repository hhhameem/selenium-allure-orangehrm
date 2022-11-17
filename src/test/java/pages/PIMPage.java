package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PIMPage {
    @FindBy(className = "oxd-userdropdown-img")
    public WebElement profileImage;

    @FindBy(partialLinkText = "Logout")
    public WebElement logoutLink;

    @FindBy(className = "oxd-select-text-input")
    List<WebElement> dropdowns;

    @FindBy(css = "[type=submit]")
    WebElement submitBtn;

    @FindBy(partialLinkText = "Add Employee")
    public WebElement addEmployeeLinkText;

    @FindBy(partialLinkText = "Employee List")
    public WebElement employeeList;

    @FindBy(className = "oxd-switch-input")
    WebElement checkBox;

    @FindBy(className = "oxd-input")
    public List<WebElement> inputFields;

    @FindBy(className = "orangehrm-main-title")
    public WebElement title;

    @FindBy(className = "oxd-input-field-error-message")
    public WebElement userNameErrorMessage;

    public PIMPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void selectEmploymentStatus(WebDriver driver, int position) {
        dropdowns.get(0).click();
        for (int i = 0; i < position; i++) {
            dropdowns.get(0).sendKeys(Keys.ARROW_DOWN);
        }
        dropdowns.get(0).sendKeys(Keys.ENTER);
        submitBtn.click();
    }

    public void checkUserName(String userName) {
        checkBox.click();
        inputFields.get(5).sendKeys(userName);
    }

    public void addEmployee(String firstName, String lastName, String userName, String pass, String confirmPass){
        checkBox.click();
        inputFields.get(1).sendKeys(firstName);
        inputFields.get(3).sendKeys(lastName);
        inputFields.get(5).sendKeys(userName);
        inputFields.get(6).sendKeys(pass);
        inputFields.get(7).sendKeys(confirmPass);
        submitBtn.click();
    }

    public void findEmployee(String id) {
        inputFields.get(1).sendKeys(id);
        submitBtn.click();
    }
}
