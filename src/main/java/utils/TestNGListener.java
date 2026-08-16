package utils;

import manager.AppManager;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener {
    Logger logger = LoggerFactory.getLogger(TestNGListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("START {}#{}", testClassName(result), result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("PASS  {}#{} in {} ms", testClassName(result), result.getName(), duration(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("SKIP  {}#{} in {} ms{}", testClassName(result), result.getName(), duration(result), reasonSuffix(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("FAIL  {}#{} in {} ms{}", testClassName(result), result.getName(), duration(result), reasonSuffix(result));
        WebDriver driver = ((AppManager) result.getInstance()).getDriver();
        TakeScreenShot.takeScreenShot((TakesScreenshot) driver);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.error("TIMEOUT {}#{} in {} ms{}", testClassName(result), result.getName(), duration(result), reasonSuffix(result));
    }

    private String testClassName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName();
    }

    private long duration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    // appends " - ExceptionType: message" when the result carries a failure cause, e.g. skip due to a failed dependency
    private String reasonSuffix(ITestResult result) {
        Throwable throwable = result.getThrowable();
        if (throwable == null) {
            return "";
        }
        return " - " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
    }
}
