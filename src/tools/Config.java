package tools;

public class Config {

    /**
     * Redmine URL（★要修正）
     */
    public static final String REDMINE_URL = "http://192.168.1.6/redmine";

    /**
     * チケット作成対象プロジェクト（★要修正）
     */
    public static final String PROJECT_NAME = "test01";

    /**
     * ユーザー名（★要修正）
     */
    public static final String USERNAME = "username";

    /**
     * パスワード（★要修正）
     */
    public static final String PASSWORD = "password";

    /**
     * Chromeドライバのパス
     */
    public static final String CHROME_BINARY = "lib/chromedriver/win32_2.14/chromedriver.exe";

    /**
     * 改行置換文字列
     *
     * 「説明」の中の改行置換文字列は"\r\n"に変換する。
     */
    public static final String NEWLINE = "【改行】";
}
