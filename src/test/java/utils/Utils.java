package utils;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Utils {

    public static void scrollDown(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
    }
    public static void scrollUp(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
    }
    public static void waitForElement(WebDriver driver, WebElement element, int TIME_UNIT_SECONDS){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(TIME_UNIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void saveJsonList(String username, String password, String id) throws IOException, ParseException, IOException, ParseException {
        String fileName="./src/test/resources/Users.json";
        JSONParser parser=new JSONParser();
        Object obj= parser.parse(new FileReader(fileName));
        JSONArray jsonArray= (JSONArray) obj;

        JSONObject userObject=new JSONObject();
        userObject.put("userName",username);
        userObject.put("password",password);
        userObject.put("id",id);
        jsonArray.add(userObject);

        FileWriter file=new FileWriter(fileName);
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();
    }

    public static JSONArray readJSONList() throws IOException, ParseException {
        String fileName="./src/test/resources/Users.json";
        JSONParser parser=new JSONParser();
        Object object= parser.parse(new FileReader(fileName));
        JSONArray jsonArray= (JSONArray) object;
        return jsonArray;
    }
    public static String getLastRegisteredUser() throws IOException, ParseException {
        List usersList = readJSONList();
        JSONObject userObj= (JSONObject) usersList.get(usersList.size()-1);
        String userName = (String) userObj.get("userName");
        return userName;
    }
}
