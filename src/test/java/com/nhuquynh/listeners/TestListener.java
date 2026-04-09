package com.nhuquynh.listeners;
import com.aventstack.extentreports.Status;
import com.nhuquynh.helpers.CaptureHelper;
import com.nhuquynh.helpers.PropertiesHelper;
import com.nhuquynh.reports.AllureManager;
import com.nhuquynh.reports.ExtentReportManager;
import com.nhuquynh.reports.ExtentTestManager;
import com.nhuquynh.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static int test_total;
    private static int test_passed_total;
    private static int test_failed_total;
    private static int test_skipped_total;

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Setup môi trường onStart: " + result.getStartDate());
        //CaptureHelper.startRecord("VideoSuite01");
        //Load file Properties cofig
        PropertiesHelper.loadAllFiles();

        //Có thể kết nối Database trước để lấy data test
        //Call API bên t3 để xác thực 1 cái gì đó khi cần
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Kết thúc bộ test: " + result.getEndDate());
        LogUtils.info("Test Total: " +test_total);
        LogUtils.info("Test Passed Total: " +test_passed_total);
        LogUtils.info("Test Failed Total: " +test_failed_total);
        LogUtils.info("Test Skipped Total: " +test_skipped_total);
        //CaptureHelper.stopRecord(1); //chỉ record được TC cuối
        //Gửi mail
        //Xuất report
        //Kết thúc và thực thi Extents Report
        ExtentReportManager.getExtentReports().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        //ghi vào logs Files
        //ghi vào report chi tiết từng bước
        LogUtils.info("➡\uFE0FBắt đầu chạy test case: " + result.getName());
        test_total++;
        CaptureHelper.startRecord(result.getName());
        //Bắt đầu ghi 1 TCs mới vào Extent Report
        ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("✅Test case " + result.getName() + " is passed.");
        test_passed_total++;
        CaptureHelper.stopRecord(1);
        //Extent Report
        ExtentTestManager.logMessage(Status.PASS, result.getName() + " is passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("❌Test case " + result.getName() + " is failed.");
        LogUtils.error(result.getThrowable());

        //Allure Report
        //AllureManager.saveTextLog("❌Test case " + result.getName() + " is failed."); => nó tự đính kèm lỗi, kh cần cái này
        AllureManager.saveScreenshotPNG();

        //Extent Report
        ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString()); //đính kèm chi tiết lỗi trước
        ExtentTestManager.addScreenshot(result.getName()); //đính kèm hình ảnh sau
        ExtentTestManager.logMessage(Status.FAIL, "❌Test case " + result.getName() + " is failed.");

        test_failed_total++;
        CaptureHelper.captureScreenshot(result.getName());
        CaptureHelper.stopRecord(1);

        //Tạo ticket Jira
        //Gửi evidence và logs lên Slack/MSTeam...

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("\uD83E\uDD98Test case " + result.getName() + " is skipped.");
        LogUtils.warn(result.getThrowable());

        //Extent Report
        ExtentTestManager.logMessage(Status.SKIP,"\uD83E\uDD98Test case " + result.getName() + " is skipped.");
        ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());

        test_skipped_total++;
        CaptureHelper.stopRecord(1);

    }
}
