package wdm;

import org.openqa.selenium.WebDriver;

/**
 * Manage WD instances
 */
public class WDManager {

    /**
     * ThreadLocal helps manage WD instances among threads
     */
    private static ThreadLocal<WebDriver> manager = new ThreadLocal<>();

    /**
     * Get WD in current thread
     *
     * @return WD in current thread
     */
    public static WebDriver getWD() {
        return manager.get();
    }


    /**
     * Set WD to current thread
     *
     * @param driver
     */
    public static void setWD(final WebDriver driver) {
        if (manager.get() != null) {
            dismissWD();
        }
        manager.set(driver);
    }

    /**
     * Close and remove WD from current thread
     */
    public static void dismissWD() {
        getWD().quit();
        manager.remove();
    }
}
