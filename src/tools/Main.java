package tools;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.TestException;
import tools.core.TicketMaker;

/**
 * Redmineチケット作成ツール
 *
 * 実行例
 *
 * java -jar RedmineTicketMaker.jar -test -user USER -password PASSWORD --data
 * data/sampleData.txt
 *
 * @author T.Noguchi
 *
 */
public class Main {

    public static void main(String[] args) {
	System.out.println("Redmineチケット作成ツール 起動...");
	CommandLine cl = null;
	try {
	    cl = checkOptions(args);
	} catch (ParseException e) {
	    System.exit(1);
	}

	boolean flagTestMode = false;
	String userName = cl.getOptionValue("user");
	String password = cl.getOptionValue("password");
	String fileName = cl.getOptionValue("data");
	if (cl.hasOption("-test")) {
	    flagTestMode = true;
	}

	try {
	    TicketMaker maker = new TicketMaker();
	    maker.exec(fileName, userName, password, flagTestMode);
	    System.out.println("Redmineチケット作成ツール 正常終了");
	} catch (TestException e) {
	    e.printStackTrace();
	    System.err.println("▲エラーが発生しました。[" + e.getMessage() + "]");
	    System.out.println("Redmineチケット作成ツール 異常終了");
	    System.exit(1);
	}
    }

    private static CommandLine checkOptions(String[] args) throws ParseException {
	Options opts = new Options();
	Option optionTest = new Option("test", false, "テストモード: チケット作成を行わずに項目の入力確認のみを行う場合にセットします。");
	Option optionUser = new Option("user", true, "ユーザー名（必須）");
	optionUser.setRequired(true);
	Option optionData = new Option("data", true, "チケットデータファイル（必須）");
	optionData.setRequired(true);
	Option optionPassword = new Option("password", true, "パスワード（必須）");
	optionPassword.setRequired(true);
	opts.addOption(optionUser);
	opts.addOption(optionPassword);
	opts.addOption(optionTest);
	opts.addOption(optionData);
	CommandLineParser parser = new BasicParser();

	HelpFormatter help = new HelpFormatter();
	CommandLine cl = null;
	try {
	    cl = parser.parse(opts, args);
	} catch (ParseException e) {
	    help.printHelp("RedmineTicketMaker", opts);
	    throw e;
	}
	return cl;
    }
}
