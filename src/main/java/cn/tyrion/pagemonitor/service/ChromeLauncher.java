package cn.tyrion.pagemonitor.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class ChromeLauncher {

    private static Logger log = LogManager.getLogger(ChromeLauncher.class.getName());

    private static String DRIVER_PATH = "C:\\Users\\tyrion.lv\\Downloads\\chromedriver.exe";
    private static String PRODUCT_PAGE_LINK = "http://qibao.gyyx.cn/Buy/Order?ItemCode=45512113";
    private static String ELEMENT_XPATH = "/html/body/div[5]/div[4]/div/p[2]/strong";
    private static double PREVIOUS_PRICE = 0;
    private static int COUNT = 1;

    public static void main(String[] args) {

        log.info("价格监控开始，日期：{}", LocalDate.now());

        ChromeLauncher launcher = new ChromeLauncher();


        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.get(PRODUCT_PAGE_LINK);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                driver.navigate().refresh();
                WebElement priceElement = ((ChromeDriver) driver).findElementByXPath(ELEMENT_XPATH);

                String priceStr = priceElement.getText();
                double price = launcher.getPrice(priceStr);

                log.info("监控第{}次价格: {}", COUNT, price);
                if (price < PREVIOUS_PRICE) {
                    // TODO Integrate Wechat.
                    // sendWeChatMessage();
                    PREVIOUS_PRICE = price;
                }
                COUNT ++;
                log.info(COUNT);

                if (COUNT > 5) {
                    driver.quit();
                    timer.cancel();
                    log.info("价格监控结束，总共监控{}次", COUNT);
                }


            }
        }, 1000*2, 1000*3);

    }

    private double getPrice(String priceStr) {
        return Double.parseDouble(priceStr.replace("￥", ""));
    }
}
