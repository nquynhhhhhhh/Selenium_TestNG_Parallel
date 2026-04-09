package com.nhuquynh.handle_UploadFile;

import com.nhuquynh.Common.BaseTest;
import com.nhuquynh.drivers.DriverManager;
import com.nhuquynh.helpers.SystemHelper;
import com.nhuquynh.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class HandleUploadFile extends BaseTest {
    @Test
    public void testUploadFileWithSendKeys()  {
        //DriverManager.getDriver().get("https://the-internet.herokuapp.com/upload");
        WebUI.openURL("https://the-internet.herokuapp.com/upload");
        WebUI.waitForPageLoaded();
        WebUI.sleep(2);

        By inputFileUpload = By.xpath("//input[@id='file-upload']");
        String filepath = SystemHelper.getCurrentDir() + "src\\test\\resources\\testdata\\image2.jpg";
        WebUI.setText(inputFileUpload,filepath);
        //DriverManager.getDriver().findElement(inputFileUpload).sendKeys(System.getProperty("user.dir") + "\\datatest\\Selenium4_Upload.png");
        WebUI.sleep(2);

        WebUI.clickElement(By.xpath("//input[@id='file-submit']"));
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(By.xpath("//h3[normalize-space()='File Uploaded!']")),"Can not upload file.");
    }

    @Test
    public void testUploadFileWithRobotClass(){
        WebUI.openURL("https://files.fm/");
        WebUI.sleep(2);

        //lấy div trước mới input
        By elementFileForm = By.xpath("//div[@id='uploadifive-file_upload']");
        By inputFileUpload = By.xpath("//div[@id='file_select_button']//input[@id='file_upload']");

        String filepath = SystemHelper.getCurrentDir() + "src\\test\\resources\\testdata\\image2.jpg";

        WebUI.uploadFileWithRobotClass(elementFileForm,filepath);

        //Verify file đã upload thành oông
        By fileNameAfterUploadSuccess = By.xpath("//span[@class='filename']");
        Assert.assertTrue(WebUI.checkElementExist(fileNameAfterUploadSuccess),"Cannot upload file");
        Assert.assertEquals(WebUI.getElementText(fileNameAfterUploadSuccess),"image2.jpg","File name not match");
    }

}
