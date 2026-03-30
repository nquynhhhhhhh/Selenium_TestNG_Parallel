package com.nhuquynh.Bai34_AllureReport.testcases;

import com.nhuquynh.Bai34_AllureReport.pages.LoginPage;
import com.nhuquynh.Common.BaseTest;
import com.nhuquynh.dataproviders.DataProviderFactory;
import com.nhuquynh.helpers.CaptureHelper;
import com.nhuquynh.keywords.WebUI;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Hashtable;

public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @Epic("Regression")
    @Feature("DMS")
    @Story("Login")
    @Owner("Nhu Quynh")
    @Severity(SeverityLevel.NORMAL)
    @Link("https://anhtester.com/dms/873")
    @Issue("https://jira.com/anhtester/dms/issue")
    @Description("This test attempts to log into the website using a login and a password. Fails if any error happens.\n\nNote that this test does not test 2-Factor Authentication.")
    @Test(priority = 1, dataProvider = "data_provider_login_success", dataProviderClass = DataProviderFactory.class)
    public void loginSuccess(String email, String password) throws AWTException {
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cũng sẽ mang giá trị
        loginPage.loginCRM(email,password);
        WebUI.sleep(1);
        loginPage.verifyLoginSuccess();
    }

    @Epic("Regression")
    @Feature("DMS")
    @Story("Login")
    @Owner("Nhu Quynh")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1, dataProvider = "data_provider_login_excel", dataProviderClass = DataProviderFactory.class)
    public void loginSuccessFromDataProvider(String email, String password){
        CaptureHelper.startRecord("loginSuccessRecord");
        loginPage = new LoginPage();
        loginPage.loginCRM(email,password);
        loginPage.verifyLoginSuccess();
    }

    @Feature("Smoke")
    @Owner("Uyen")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1, dataProvider = "data_provider_login_excel_hashtable", dataProviderClass = DataProviderFactory.class)
    public void loginSuccessFromDataProviderHashTable(Hashtable< String, String > data){
        loginPage = new LoginPage();
        loginPage.loginCRM(data.get("Email"), data.get("Password") );
        loginPage.verifyLoginSuccess();
    }

    @Epic("Regression")
    @Feature("Inventory")
    @Story("Login")
    @Owner("Dung")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginFailWithEmailInvalid(){
        CaptureHelper.startRecord("loginFailWithEmailInvalid");
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cng sẽ mang giá trị
        loginPage.loginCRM("admin123@example.com","123456");
        loginPage.verifyLoginFail("Invalid email or password");
    }

    @Feature("Smoke")
    @Owner("Hai")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 3)
    public void loginFailWithPassInvalid(){
        CaptureHelper.startRecord("loginFailWithPassInvalid");
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cng sẽ mang giá trị
        loginPage.loginCRM("admin@example.com","111");
        loginPage.verifyLoginFail();
    }

    @Feature("Smoke")
    @Owner("Nhu")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4)
    public void loginFailWithEmailNull(){
        CaptureHelper.startRecord("loginFailWithEmailNull");
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cng sẽ mang giá trị
        loginPage.loginCRM("","123456");
        loginPage.verifyLoginFail("The Email Address field is required.");
    }

    @Feature("Regression")
    @Owner("Nhu")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 5)
    public void loginFailWithPassNull(){
        CaptureHelper.startRecord("loginFailWithPassNull");
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cng sẽ mang giá trị
        loginPage.loginCRM("admin@example.com","");
        loginPage.verifyLoginFail("The Password field is required.");
    }

    @Feature("Regression")
    @Owner("My")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 6)
    public void loginFailWithNullFields(){
        CaptureHelper.startRecord("loginFailWithNullFields");
        loginPage = new LoginPage(); //khởi tạo browser ở BaseTest trước rồi nên driver đã có giá trị => chỗ này cng sẽ mang giá trị
        loginPage.loginCRM("","");
        loginPage.verifyLoginFailWithNullFields();
        //loginPage.verifyLoginFailWithNullFields_ArrayList(2);
    }
}
