package com.nhuquynh.handle_Table;

import com.nhuquynh.Bai34_AllureReport.pages.CustomerPage;
import com.nhuquynh.Bai34_AllureReport.pages.DashboardPage;
import com.nhuquynh.Bai34_AllureReport.pages.LoginPage;
import com.nhuquynh.Common.BaseTest;
import com.nhuquynh.keywords.WebUI;
import com.nhuquynh.utils.LogUtils;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class HandleTable extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomerPage customerPage;

    @BeforeMethod
    public void initPageObject() {
        loginPage = new LoginPage();
        dashboardPage = new DashboardPage();
        customerPage = new CustomerPage();
    }

    @Test
    public void testSearchDataInTable(){
        loginPage.loginCRM("admin@example.com","123456");
        dashboardPage.clickMenuCustomer();
        customerPage.searchAndCheckDataInTable(3, "Công ty", "Company");

    }

    @Test
    public void testCheckPaginationOnTable() {
        loginPage.loginCRM("admin@example.com","123456");
        dashboardPage.clickMenuCustomer();

        //Data search read from Properties

        WebUI.waitForPageLoaded();

        String searchValue = "Công ty";

        //Get item on One Page
        //B1: Chọn hiển thị 10 items
        Select select = new Select(WebUI.getWebElement(By.xpath("//select[@name='clients_length']")));
        select.selectByVisibleText("10");
        WebUI.sleep(2);
        //get kết quả của nó ra xem có phải 10 kh
        Select select2 = new Select(WebUI.getWebElement(By.xpath("//select[@name='clients_length']")));
        LogUtils.info(select2.getFirstSelectedOption().getText());

        int itemTotalOnePage = Integer.parseInt(select.getFirstSelectedOption().getText());
        LogUtils.info("Tổng số item / trang: " + itemTotalOnePage);

        //Set Text on Search input
        customerPage.searchDataCustomer(searchValue);
        WebUI.waitForPageLoaded();
        WebUI.sleep(2);

        //Get total item
        String strTotal = WebUI.getElementText(By.xpath("//div[@id='clients_info']"));
        ArrayList<String> list = new ArrayList<String>();

        //Tách chuỗi theo khoảng trắng, sau đó cho vào ArrayList
        for (String strItem : strTotal.split("\\s")) {
            list.add(strItem);
        }

        LogUtils.info(list);

        //Lấy phần tử thứ 6
        int itemTotal = Integer.parseInt(list.get(5));
        LogUtils.info("Tổng số item: " + itemTotal);
        int pageTotal = itemTotal / itemTotalOnePage;
        int sodu = itemTotal % itemTotalOnePage;
        LogUtils.info("Tổng số nguyên: " + pageTotal);
        LogUtils.info("Tổng số dư: " + sodu);

        if (sodu > 0) {
            pageTotal = pageTotal + 1;
        }

        LogUtils.info("Tổng số Page: " + pageTotal);

        for (int i = 1; i <= pageTotal; i++) {
            WebUI.checkDataInTableByColumn_Contains(3, searchValue,"Company");

            //Nhấn nút Next để đến trang tiếp theo
            if (i < pageTotal) {
                WebUI.clickElement(By.xpath("//a[normalize-space()='Next']"));
                WebUI.sleep(1);
            }
        }

    }

}
