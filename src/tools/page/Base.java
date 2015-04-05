package tools.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.TestException;

public class Base {

    protected WebDriver driver;

    public Base(WebDriver driver) {
	this.driver = driver;
    }

    protected void wait(int millisec) {
	try {
	    Thread.sleep(millisec);
	} catch (InterruptedException e) {
	    // e.printStackTrace();
	}
    }

    /**
     * 要素が操作可能になるまで待機する。
     *
     * @param locator
     * @param millisec
     * @throws TestException
     */
    // TODO: 未実装。
    protected void waitElement(By locator,
	    int millisec) throws TestException {
	wait(millisec);
	// WebDriverWait wait = new WebDriverWait(driver, waitTimeInMillisec);
	// WebElement element =
	// wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	// if (element == null) {
	// throw new TestException("要素がない");
	// }
    }

    protected void input(By by, String input) {
	waitElement(by, waitTimeInMillisec);
	WebElement we = driver.findElement(by);
	we.sendKeys(input);
    }

    protected void clear(By by) {
	waitElement(by, waitTimeInMillisec);
	WebElement we = driver.findElement(by);
	we.clear();
    }

    protected void clearAndInput(By by, String input) {
	waitElement(by, waitTimeInMillisec);
	clear(by);
	input(by, input);
    }

    private int waitTimeInMillisec = 500;

    protected void select(By by, String input) throws TestException {
	waitElement(by, waitTimeInMillisec);
	WebElement we = driver.findElement(by);
	Select element = new Select(we);
	element.selectByVisibleText(input);
    }

    protected void click(By by) {
	waitElement(by, waitTimeInMillisec);
	WebElement we = driver.findElement(by);
	we.click();
    }

}
