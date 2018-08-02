import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
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

    @Test
    public void test1() {
        WDManager.setWD(WDFactory.initBrowser(Browser.IE));
        WDManager.getWD().get("https://google.com");
        System.out.println(WDManager.getWD().getTitle());
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
    }

    @Test
    public void test2() {
        WDManager.setWD(WDFactory.initBrowser(Browser.CHROME));
        WDManager.getWD().get("https://facebook.com");
        System.out.println(WDManager.getWD().getTitle());
        WDManager.getWD().findElement(By.xpath("//a[3]")).click();
        WDManager.getWD().findElement(By.xpath("//a[3]")).click();
        WDManager.getWD().findElement(By.xpath("//a[3]")).click();
    }

    @Test
    public void test3() {
        WDManager.setWD(WDFactory.initBrowser(Browser.FIREFOX));
        WDManager.getWD().get("https://youtube.com");
        System.out.println(WDManager.getWD().getTitle());
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
        WDManager.getWD().findElement(By.xpath("//a")).click();
    }

    @Test
    public void testRemote() throws MalformedURLException {
        WDManager.setWD(WDFactory.remote(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome()));
        WDManager.getWD().get("https://youtube.com");
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
