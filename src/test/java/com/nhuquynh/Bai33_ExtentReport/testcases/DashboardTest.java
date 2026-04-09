package com.nhuquynh.Bai33_ExtentReport.testcases;

import com.nhuquynh.Bai33_ExtentReport.pages.DashboardPage;
import com.nhuquynh.Bai33_ExtentReport.pages.LoginPage;
import com.nhuquynh.Common.BaseTest;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest{
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @Test
    public void checkDashboardTotal(){
        loginPage = new LoginPage();
//        loginPage.loginCRM(); //chỉ login
//        dashboardPage = new DashboardPage(driver);

        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyInvoicesAwaitingPayment("1 / 3");

    }
}
