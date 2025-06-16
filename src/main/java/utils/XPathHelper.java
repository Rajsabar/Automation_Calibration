package utils;

public class XPathHelper {
	 // Salesforce Login Page
    public static final String USERNAME_INPUT = "//input[@class='username']";
    public static final String PASSWORD_INPUT = "//input[@class='password']";
    public static final String LOGIN_BUTTON = "//input[@id='Login']";
    public static final String CANCEL_BUTTON = "//button[@id='cancel-button']";

    // Salesforce Home Page
    public static final String SERVICES_DELIVERY_CONSOLE = "//span[@title='Services Delivery Console']";
    // Search Asset XPaths
    public static final String SEARCH_BUTTON = "//button[@class='slds-button slds-button_neutral search-button slds-truncate']";
    public static final String SEARCH_BOX_1 = "//div[@type='search']//input[@placeholder='Search...']";
    public static final String SEARCH_BOX_2 = "//div[@type='search']//input[@placeholder='Search']";

    // Open Work Order XPaths
    public static final String ASSET_OPEN_WORKORDER = "//span[contains(text(),'Asset Open WorkOrder')]";

    // Product Support Strategy XPaths
    public static final String PRODUCT_SUPPORT_PARENT_PRODUCT = "//article[@class='slds-card slds-card_boundary slds-card_related-list-fix']//span[@title='Product Support Strategy for Parent Product']";
    public static final String VIEW_ALL_PRODUCT_SUPPORT = "//span[@class='view-all-label'][contains(.,'View AllProduct Support Strategy for Parent Product')]";
    public static final String SERVICE_TABLE_DATA = "//section[@aria-expanded='true']//section[@aria-expanded='true']//table//tbody//lightning-primitive-cell-factory[@data-label='Service']//*[text()]";

    // Create Work Order XPaths
    public static final String CREATE_WORKORDER_BUTTON = "//button[contains(@name,'WorkOrder')]";
    public static final String NEW_WORKORDER_DIALOG = "//h4[contains(.,'New Work Order')]";
    public static final String SERVICE_BUNDLE_BUTTON = "//label[text()='Service Bundle']//parent::*//div//button";
    public static final String SERVICE_BUNDLE_SEARCH = "//label[text()='Service Bundle']//parent::*//div//input[@placeholder='Search...']";
    public static final String SERVICE_BUNDLE_ITEM = "//li[@data-name='Calibration Only']";
    public static final String RECEIVING_METHOD_BUTTON = "//button[@name='Receiving_Method__c']";
    public static final String EXPECTED_RECEIPT_DATE = "//legend[text()='Expected Receipt Date/Time']//parent::*//child::input";
    public static final String TRADE_BUTTON = "//input[@value='Trade']";
    public static final String SAVE_BUTTON = "//button[@title='Save' or text()='Save']";
    public static final String WORK_ORDER_SERVICES ="//span[@title='Work Order Services']";
}
