package tools.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewIssue extends Base {

    public NewIssue(WebDriver driver) {
	super(driver);
    }

    public NewIssue inputトラッカー(String input) {
	input(By.id("issue_tracker_id"), input);
	return this;
    }

    public NewIssue input題名(String input) {
	input(By.id("issue_subject"), input);
	return this;
    }

    public NewIssue input説明(String input) {
	input(By.id("issue_description"), input);
	return this;
    }

    public NewIssue input優先度(String input) {
	input(By.id("issue_priority_id"), input);
	return this;
    }

    public NewIssue select担当者(String input) {
	wait(1000);
	select(By.id("issue_assigned_to_id"), input);
	return this;
    }

    public NewIssue input開始日(String input) {
	clearAndInput(By.id("issue_start_date"), input);
	return this;
    }

    public NewIssue input期限(String input) {
	clearAndInput(By.id("issue_due_date"), input);
	return this;
    }

    public NewIssue input予定時間(String input) {
	clearAndInput(By.id("issue_estimated_hours"), input);
	return this;
    }

    public NewIssue inputステータス(String input) {
	input(By.id("issue_status_id"), input);
	return this;
    }

    public void click作成() {
	click(By.name("commit"));
    }

}
