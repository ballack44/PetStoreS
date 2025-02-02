package utils.report;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListener implements ITestListener {
    Reporter reporter = new Reporter();
    Logger consoleLogger = LogManager.getLogger(ReportingFilter.class);

    public TestListener() {}

    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);

        String currentClassname = "Test class: ".concat(result.getTestClass().getRealClass().getSimpleName());
        String methodName = result.getMethod().getMethodName();
        String[] categories = result.getMethod().getGroups();
        this.reporter.makeLeft(result.getMethod().getDescription(), currentClassname, methodName, categories);
    }

    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        consoleLogger.info("\n===============================================\n"+
                "Passed test name: " + result.getMethod().getMethodName() +
                "\n===============================================");
        this.reporter.addResult(result);
    }

    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        consoleLogger.info("\n===============================================\n"+
                "Failed test name: " + result.getMethod().getMethodName() +
                "\n===============================================");
        this.reporter.addResult(result);
    }

    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        this.reporter.addResult(result);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
        this.reporter.addResult(result);
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
        this.reporter.addResult(result);
    }

    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        context.setAttribute(Reporter.REPORTER, this.reporter);
    }

    public void onFinish(ITestContext context) {ITestListener.super.onFinish(context);}
}
