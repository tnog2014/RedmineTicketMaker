package tools.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import tools.Config;
import tools.page.NewIssue;
import tools.page.TopPage;

/**
 * 簡易Redmineチケット作成ツール
 *
 * @author T.Noguchi
 *
 */
public class TicketMaker {

    public void exec(String fileName) throws TestException {
	// --------------
	// ブラウザ起動
	// --------------
	String REDMINE_SITE = Config.REDMINE_URL;
	System.setProperty("webdriver.chrome.driver", Config.CHROME_BINARY);
	WebDriver driver = null;
	try {
	    driver = new ChromeDriver();
	    driver.get(REDMINE_SITE);

	    // --------------
	    // ログイン
	    // --------------
	    TopPage topPage = new TopPage(driver);
	    topPage.clickログイン()
		    .inputユーザー(Config.USERNAME)
		    .inputパスワード(Config.PASSWORD)
		    .clickログイン();

	    // ------------------
	    // データ読み込み
	    // ------------------
	    List<Issue> issues = new ArrayList<Issue>();
	    try {
		List<String> lines = FileUtils.readLines(new File(fileName));
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
		    System.out.println(issue);
		    issues.add(issue);
		}
	    } catch (IOException e1) {
		e1.printStackTrace();
		throw new TestAbortException("ファイルの読み込みに失敗しました。[" + fileName + "]");
	    }

	    // ------------------
	    // チケット作成
	    // ------------------
	    String newIssueUrl = REDMINE_SITE + "/projects/" + Config.PROJECT_NAME + "/issues/new";
	    int total = issues.size();
	    for (int num = 0; num < total; num++) {
		try {
		    driver.get(newIssueUrl);

		    Issue issue = issues.get(num);

		    System.out.println(num + "/" + total + ":" + issue);
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
			desc = desc.replaceAll(Config.NEWLINE, "\r\n");
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

		    newIssue.click作成();
		    sleep(5000);
		} catch (UnhandledAlertException e) {
		    driver.switchTo().alert().accept();
		}
	    }
	    sleep(3000);
	} finally {
	    // ------------
	    // 終了処理
	    // ------------
	    if (driver != null) {
		driver.quit();
	    }
	}

    }

    private void sleep(int millisec) {
	try {
	    Thread.sleep(millisec);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
