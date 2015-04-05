# RedmineTicketMaker
簡易的Redmineチケット作成ツール（ベータ版）

Seleniumによるブラウザ操作を利用したRedmineチケット作成ツール。

動作確認環境は以下：

* Redmineサーバー
 - Redmine 3.0.1 (CenOS 6.5?)
* チケット登録クライアント
 - Windows 7 64bit 
 - Oracle Java version 1.8.0_20

###特徴  
- Seleniumでブラウザの操作をしているため機能修正・拡張を行いやすい。 
- Redmineのバージョンアップに伴うREST APIの仕様変更の影響を受けない。
UI変更の影響は受けるが修正は容易。

##セットアップ
1. 必要に応じてJavaをインストールします。
2. 適当な場所にgit cloneします。  
git clone https://github.com/tnog2014/RedmineTicketMaker.git

##設定ファイルの修正
config.propertiesファイルを修正します。  

1. RedmineのURLとプロジェクト名を変更してください。  
プロジェクト名は、プロジェクトのURLにアクセスした際、URLの末尾に表示される文字列です。  
例）http://192.168.1.6/redmine/projects/**test01**  
2. 必要であれば、Chromeドライバの実行ファイルパスと、改行置換文字列を変更します。  
※改行置換文字列とは、チケットデータの「説明」フィールドで、改行文字（\r\n）に置換される文字列です。

##チケットデータファイル作成
1. サンプルExcelファイル（data/sampleData.xlsx）の記述に従って、チケットデータを作成します。
2. 水色の領域に対応する部分をコピー＆ペーストして、テキストファイルを作成してください。  
※Excelのセル内では改行しないでください。
チケットの「説明」欄で改行したい場合には、「説明」フィールドで、改行の代わりに改行置換文字列（通常は、【改行】）を記述してください。

##プログラム実行
1. git cloneで作成した、RedmineTicketMakerフォルダに移動します。
2. 以下の引数を指定してコマンドを実行してください。  
java -jar RedmineTicketMaker.jar -user （ユーザー） -password （パスワード） --data （チケットデータファイル）  
例）チケット作成  
java -jar RedmineTicketMaker.jar -test -user USER -password PASSWORD --data data/sampleData.txt  
尚、チケット情報入力画面に入力は行うが、チケット作成を実行したくない場合には、テストモードで実行する。javaの引数として、-testを加えます。  
例）テストモード実行  
java -jar RedmineTicketMaker.jar -test -user USER ...

以上。


