package tools.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import tools.page.NewIssue;
import tools.page.TopPage;

/**
 * 簡易Redmineチケット作成ツール
 *
 * @author T.Noguchi
 *
 */
public class TicketMaker {

    private static final String CONFIG_FILE = "config.properties";

    private Properties conf = null;

    public TicketMaker() throws TestException {
	try {
	    loadPropertyFile();
	} catch (IOException e) {
	    throw new TestException("プロパティーファイルの読み込みに失敗しました。[" + CONFIG_FILE + "]", e);
	}
    }

    private void loadPropertyFile() throws IOException {
	conf = new Properties();
	FileInputStream fis = new FileInputStream(new File(CONFIG_FILE));
	InputStreamReader isr = new InputStreamReader(fis);
	conf.load(isr);
    }

    private WebDriver getWebDriver() {
	System.setProperty("webdriver.chrome.driver", conf.getProperty("CHROME_BINARY"));
	WebDriver driver = new ChromeDriver();
	return driver;
    }

    private String replaceNewLineString(String input) {
	String newline = conf.getProperty("NEWLINE");
	String ret = input;
	if (newline != null) {
	    ret = ret.replaceAll(newline, "\r\n");
	}
	return ret;
    }

    public void exec(String fileName, String userName, String password, boolean flagTestMode) throws TestException {
	String redmineUrl = conf.getProperty("REDMINE_URL");
	String projectName = conf.getProperty("PROJECT_NAME");

	System.out.println("ユーザ:         [" + userName + "]");
	System.out.println("チケットデータ: [" + fileName + "]");
	System.out.println("テストモード:   [" + flagTestMode + "]");
	System.out.println("Redmine URL:    [" + redmineUrl + "]");
	System.out.println("プロジェクト名: [" + projectName + "]");

	// --------------
	// ブラウザ起動
	// --------------

	WebDriver driver = null;
	try {

	    // ------------------
	    // データ読み込み
	    // ------------------
	    List<Issue> issues = new ArrayList<Issue>();
	    try {
		List<String> lines = FileUtils.readLines(new File(fileName), "utf-8");
		for (String line : lines) {
		    // トリム後空行、もしくは、#で始まる行は処理しない。
		    if (line.trim().length() == 0 || line.startsWith("#")) {
			continue;
		    }
		    // タブが不足しないように補う。
		    line = line + StringUtils.repeat("\t", 8);
		    String[] items = line.split("\t", -1);
		    Issue issue = new Issue();
		    issue.setトラッカー(items[0]);
		    issue.set題名(items[1]);
		    issue.set説明(items[2]);
		    issue.setステータス(items[3]);
		    issue.set優先度(items[4]);
		    issue.set担当者(items[5]);
		    issue.set開始日(items[6]);
		    issue.set期限(items[7]);
		    issue.set予定時間(items[8]);
		    issues.add(issue);
		}
		System.out.println("========== 作成するチケット ==========");
		System.out.println("チケット数: " + issues.size());
		for (int i = 0; i < issues.size(); i++) {
		    System.out.println(String.format("(%03d/%03d) :%s", i + 1, issues.size(), issues.get(i)));
		}
		System.out.println("======================================");
	    } catch (IOException e1) {
		throw new TestAbortException("ファイルの読み込みに失敗しました。[" + fileName + "]", e1);
	    }
	    try {
		driver = getWebDriver();
		driver.get(redmineUrl);

		// --------------
		// ログイン
		// --------------
		TopPage topPage = new TopPage(driver);
		topPage.clickログイン()
			.inputユーザー(userName)
			.inputパスワード(password)
			.clickログイン();

		// ------------------
		// チケット作成
		// ------------------

		String newIssueUrl = String.format("%s/projects/%s/issues/new", redmineUrl, projectName);
		int total = issues.size();
		driver.get(newIssueUrl);
		for (int num = 0; num < total; num++) {

		    Issue issue = issues.get(num);

		    System.out.println(String.format("作成中... (%03d/%03d) :%s", num + 1, total, issue));
		    NewIssue newIssue = new NewIssue(driver);

		    // トラッカー（必須）
		    if (StringUtils.isNotBlank(issue.getトラッカー())) {
			newIssue.inputトラッカー(issue.getトラッカー());
		    }

		    // 題名（必須）
		    if (StringUtils.isNotBlank(issue.get題名())) {
			newIssue.input題名(issue.get題名());
		    }
		    if (StringUtils.isNotBlank(issue.get説明())) {
			String desc = issue.get説明();
			desc = replaceNewLineString(desc);
			newIssue.input説明(desc);
		    }

		    // ステータス（必須）
		    if (StringUtils.isNotBlank(issue.getステータス())) {
			newIssue.inputステータス(issue.getステータス());
		    }

		    // 優先度（必須）
		    if (StringUtils.isNotBlank(issue.get優先度())) {
			newIssue.input優先度(issue.get優先度());
		    }
		    if (StringUtils.isNotBlank(issue.get担当者())) {
			newIssue.select担当者(issue.get担当者());
		    }
		    if (StringUtils.isNotBlank(issue.get開始日())) {
			newIssue.input開始日(issue.get開始日());
		    }
		    if (StringUtils.isNotBlank(issue.get期限())) {
			newIssue.input期限(issue.get期限());
		    }
		    if (StringUtils.isNotBlank(issue.get予定時間())) {
			newIssue.input予定時間(issue.get予定時間());
		    }

		    if (!flagTestMode) {
			newIssue.click作成();
		    }
		    sleep(5000);
		    try {
			driver.get(newIssueUrl);
			// ページ移動ダイアログを表示させて、画面遷移を許可する。
			WebElement we = driver.findElement(By.id("issue_tracker_id"));
		    } catch (UnhandledAlertException e) {
			driver.switchTo().alert().accept();
		    }
		}
		sleep(3000);
	    } catch (Exception e) {
		throw new TestAbortException("ブラウザ操作時にエラーが発生しました。", e);
	    }
	} finally {
	    // ------------
	    // 終了処理
	    // ------------
	    if (driver != null) {
		driver.quit();
	    }
	    System.out.println("Redmineチケット作成ツール 終了");
	}

    }

    private void sleep(int millisec) {
	try {
	    Thread.sleep(millisec);
	} catch (InterruptedException e) {
	    // e.printStackTrace();
	}
    }
}
