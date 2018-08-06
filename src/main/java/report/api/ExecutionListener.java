package report.api;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

public class ExecutionListener implements ITestListener {
    private TestResult result;

    public void onTestStart(final ITestResult iTestResult) {
        result = new TestResult();
    }

    public void onTestSuccess(final ITestResult iTestResult) {
        setTestDataOnTestEnd(iTestResult, Status.PASS);
    }

    public void onTestFailure(final ITestResult iTestResult) {
        setTestDataOnTestEnd(iTestResult, Status.FAIL);
    }

    public void onTestSkipped(final ITestResult iTestResult) {
        setTestDataOnTestEnd(iTestResult, Status.SKIPPED);
    }

    public void onTestFailedButWithinSuccessPercentage(final ITestResult iTestResult) {
        //skip
    }

    public void onStart(final ITestContext iTestContext) {
        ResultSenderUtil.setVersionInfo();
    }

    public void onFinish(final ITestContext iTestContext) {
        //skip
    }


    private void setTestDataOnTestEnd(final ITestResult itr, final Status stt) {
        result.setTestName(itr.getMethod().getDescription());
        result.setGroups(itr.getMethod().getGroups());
        result.setStatus(stt);
        result.setExecutedBy(System.getProperty("user.name"));
        setTestDuration(itr);
        if (stt != Status.PASS) {
            result.setErrorMsg(itr.getThrowable().toString());
        }
        result.setExecutionDate(ISODateTimeFormat.dateTime().print(DateTime.now()));
        ResultSenderUtil.sendTestResult(result);
    }

    private void setTestDuration(final ITestResult itr) {
        final long durInMillis = itr.getEndMillis() - itr.getStartMillis();
        result.setDuration(String.format("%d min %d sec",
                TimeUnit.MILLISECONDS.toMinutes(durInMillis),
                TimeUnit.MILLISECONDS.toSeconds(durInMillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durInMillis))));
    }
}
