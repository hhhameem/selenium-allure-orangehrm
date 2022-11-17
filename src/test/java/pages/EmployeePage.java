package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EmployeePage {
    @FindBy(className = "oxd-text--h6")
    public WebElement dashboard;

    @FindBy(className = "oxd-userdropdown-img")
    public WebElement profileImage;

    @FindBy(partialLinkText = "Logout")
    public WebElement logoutLink;

    @FindBy(className = "oxd-main-menu-item--name")
    public List<WebElement> menuItems;

    @FindBy(className = "oxd-input")
    public List<WebElement> inputFields;

    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> selectButtons;

    @FindBy(css = "[type=submit]")
    public List<WebElement> submitButtons;

    public EmployeePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void selectNationality () {
        WebElement nationalityButton =  selectButtons.get(0);
        nationalityButton.sendKeys("b");
        nationalityButton.sendKeys(Keys.ARROW_DOWN);
        nationalityButton.sendKeys(Keys.ARROW_DOWN);
        nationalityButton.sendKeys(Keys.ENTER);
        submitButtons.get(0).click();
    }

    public void selectBloodGroup() {
        WebElement bloodGroupButton = selectButtons.get(2);
        bloodGroupButton.sendKeys(Keys.ARROW_DOWN);
        bloodGroupButton.sendKeys(Keys.ENTER);
        submitButtons.get(1).click();
    }
}
