package com.nhuquynh.Bai26_ParallelExecutionPOM.pages;

import com.nhuquynh.drivers.DriverManager;
import com.nhuquynh.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

    public By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    public By menuCustomers = By.xpath("//span[normalize-space()='Customers']");
    public By menuTasks = By.xpath("//span[normalize-space()='Tasks']");
    public By menuProjects = By.xpath("//span[normalize-space()='Projects']");
    public By menuSales = By.xpath("//span[@class='menu-text' and normalize-space()='Sales']");
    public By menuProposals = By.xpath("//span[normalize-space()='Proposals']");
    public By iconProfile = By.xpath("//li[@class='icon header-user-profile']");
    public By optionLogout = By.xpath("//a[text()='Logout']");

    //Các hàm xử lý chung cho tất cả các trang
    public void logoutSystem() {
        WebUI.clickElement(iconProfile);
        WebUI.clickElement(optionLogout);
    }

    public CustomerPage clickMenuCustomer(){
        WebUI.waitForElementVisible(menuCustomers);
        WebUI.clickElement(menuCustomers);

        return new CustomerPage();
    }

    public void clickMenuDashboard() {
        WebUI.clickElement(menuDashboard);
    }

    public ProjectPage clickMenuProject() {
        WebUI.clickElement(menuProjects);
        return new ProjectPage();
    }

    public void clickMenuTask() {
        WebUI.clickElement(menuTasks);
    }



}
