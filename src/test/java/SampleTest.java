import io.github.bonigarcia.wdm.Architecture;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import report.api.ExecutionListener;
import wdm.Browser;
import wdm.WDFactory;
import wdm.WDManager;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners(ExecutionListener.class)
public class SampleTest {

    public void waitForElement(By by) {
        WDManager.getWait().until(ExpectedConditions.elementToBeClickable(by));
    }

    public void type(By by, String txt) {
        waitForElement(by);
        WDManager.getWD().findElement(by).sendKeys(txt);
    }

    public void click(By by) {
        waitForElement(by);
        WDManager.getWD().findElement(by).click();
    }

    @Test(description = "Test Google")
    public void testGoogle() {
        WDFactory.getConfig().setArchitecture(Architecture.X32);
        WDManager.setWD(WDFactory.initBrowser(Browser.IE));
        WDManager.getWD().get("https://google.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("lst-ib"), "aaaaaaaa");
        click(By.name("btnK"));
        click(By.xpath("//a[text() = 'Video']"));
    }

    @Test(description = "Test Facebook")
    public void testFacebook() {
        WDManager.setWD(WDFactory.initBrowser(Browser.CHROME));
        WDManager.getWD().get("https://facebook.com");
        System.out.println(WDManager.getWD().getTitle());
        click(By.xpath("//div[@id='reg_pages_msg']/a"));
        click(By.xpath("//button"));
        type(By.xpath("//input[@type='text']"), "aaaaaaa");
        type(By.xpath("//input[@type='password']"), "111111111111");
        click(By.xpath("//button"));

    }

    @Test(description = "Test Youtube")
    public void testYoutube() {
        WDManager.setWD(WDFactory.initBrowser(Browser.FIREFOX));
        WDManager.getWD().get("https://youtube.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("search"), "aaaaaa");
        click(By.id("search-icon-legacy"));
        click(By.cssSelector("a#video-title"));
    }

    @Test(description = "Test remote 1")
    public void testRemote() throws MalformedURLException {
        WDManager.setWD(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
        WDManager.getWD().get("https://youtube.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("search"), "aaaaaa");
        click(By.id("search-icon-legacy"));
        click(By.cssSelector("a#video-title"));
    }

    @Test(description = "Test remote 2")
    public void testRemote2() throws MalformedURLException {
        WDManager.setWD(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
        WDManager.getWD().get("https://youtube.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("search"), "aaaaaa");
        click(By.id("search-icon-legacy"));
        click(By.cssSelector("a#video-title"));
    }

    @AfterMethod
    public void after() {
        WDManager.dismissWD();
    }
}