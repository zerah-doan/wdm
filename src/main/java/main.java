import io.github.bonigarcia.wdm.Architecture;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wdm.Browser;
import wdm.WDFactory;
import wdm.WDManager;

import java.net.MalformedURLException;
import java.net.URL;

public class main {
//    public static void main(String[] args) {
//        WDFactory.getConfig().setArchitecture(Architecture.X32);
//        WDFactory.getConfig().setDriverVersion("3.12.0");
//        WebDriver wd = WDFactory.initBrowser(Browser.IE);
//        wd.get("https://github.com/bonigarcia/webdrivermanager");
//        wd.quit();
//    }

    WebDriverWait wait;

    public void waitForElement(By by) {
        if (wait == null) {
            wait = new WebDriverWait(WDManager.getWD(), 20);
        }
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void type(By by, String txt) {
        waitForElement(by);
        WDManager.getWD().findElement(by).sendKeys(txt);
    }

    public void click(By by) {
        waitForElement(by);
        WDManager.getWD().findElement(by).click();
    }

    @Test
    public void testGoogle() {
        WDFactory.getConfig().setArchitecture(Architecture.X32);
        WDManager.setWD(WDFactory.initBrowser(Browser.IE));
        WDManager.getWD().get("https://google.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("lst-ib"), "aaaaaaaa");
        click(By.name("btnK"));
        click(By.xpath("//a[text() = 'Video']"));
    }

    @Test
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

    @Test
    public void testYoutube() {
        WDManager.setWD(WDFactory.initBrowser(Browser.FIREFOX));
        WDManager.getWD().get("https://youtube.com");
        System.out.println(WDManager.getWD().getTitle());
        type(By.id("search"), "aaaaaa");
        click(By.id("search-icon-legacy"));
        click(By.cssSelector("a#video-title"));
    }

    @Test
    public void testRemote() throws MalformedURLException {
        WDManager.setWD(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
        WDManager.getWD().get("https://yahoo.com");
        System.out.println(WDManager.getWD().getTitle());
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
    }

    @Test
    public void testRemote2() throws MalformedURLException {
        WDManager.setWD(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
        WDManager.getWD().get("https://yahoo.com");
        System.out.println(WDManager.getWD().getTitle());
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
    }

    @AfterMethod
    public void after() {
        WDManager.dismissWD();
    }
}
