package tools.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TopPage extends Base {

    public TopPage(WebDriver driver) {
	super(driver);
    }

    public LogIn clickログイン() {
	WebElement we = driver.findElement(By.className("login"));
	we.click();
	return new LogIn(driver);
    }
}
