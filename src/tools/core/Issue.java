package tools.core;

public class Issue {
    private String strトラッカー;
    private String str題名;
    private String str説明;
    private String strステータス;
    private String str優先度;
    private String str担当者;
    private String str開始日;
    private String str期限;
    private String str予定時間;

    public String getトラッカー() {
	return strトラッカー;
    }

    public void setトラッカー(String strトラッカー) {
	this.strトラッカー = strトラッカー;
    }

    public String get題名() {
	return str題名;
    }

    public void set題名(String str題名) {
	this.str題名 = str題名;
    }

    public String get説明() {
	return str説明;
    }

    public void set説明(String str説明) {
	this.str説明 = str説明;
    }

    public String getステータス() {
	return strステータス;
    }

    public void setステータス(String strステータス) {
	this.strステータス = strステータス;
    }

    public String get優先度() {
	return str優先度;
    }

    public void set優先度(String str優先度) {
	this.str優先度 = str優先度;
    }

    public String get担当者() {
	return str担当者;
    }

    public void set担当者(String str担当者) {
	this.str担当者 = str担当者;
    }

    public String get開始日() {
	return str開始日;
    }

    public void set開始日(String str開始日) {
	this.str開始日 = str開始日;
    }

    public String get期限() {
	return str期限;
    }

    public void set期限(String str期限) {
	this.str期限 = str期限;
    }

    public String get予定時間() {
	return str予定時間;
    }

    public void set予定時間(String str予定時間) {
	this.str予定時間 = str予定時間;
    }
}
