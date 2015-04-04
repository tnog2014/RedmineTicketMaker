package tools.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogIn extends Base {

    public LogIn(WebDriver driver) {
	super(driver);
    }

    public LogIn inputユーザー(String username) {
	WebElement we = driver.findElement(By.id("username"));
	we.sendKeys(username);
	return this;
    }

    public LogIn inputパスワード(String password) {
	WebElement we = driver.findElement(By.id("password"));
	we.sendKeys(password);
	return this;
    }

    public void clickログイン() {
	WebElement we = driver.findElement(By.name("login"));
	we.click();

    }

}
