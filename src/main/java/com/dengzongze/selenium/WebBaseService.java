package com.dengzongze.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by dengzongze on 2018/1/19.
 */
public class WebBaseService {
    private WebDriver driver;
    private String binaryPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
    private String chromeDriverPath = "C:\\Users\\dengzongze\\Downloads\\chromedriver.exe";
    private WebDriverWait wait;

    public WebBaseService() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type", "start-maximized", "no-default-browser-check");
        driver = new ChromeDriver(options);
    }

    public void startChrome() {
        wait = new WebDriverWait(driver, 3000);
        driver.get("https://kyfw.12306.cn/otn/login/init#");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("username"))));
        driver.findElement(By.id("username")).sendKeys("1029812778@qq.com");
        driver.findElement(By.id("password")).sendKeys("dzz920624");
        wait.until(ExpectedConditions.textToBe(By.id("regist_out"), "退出"));
        driver.findElement(By.xpath("//*[@id=\"selectYuding\"]/a")).click();

        wait = new WebDriverWait(driver, 5);

        ((JavascriptExecutor) driver).executeScript("$('#fromStation').val('IZQ')");
        ((JavascriptExecutor) driver).executeScript("$('#fromStationText').val('广州南')");

        ((JavascriptExecutor) driver).executeScript("$('#toStation').val('WBZ')");
        ((JavascriptExecutor) driver).executeScript("$('#toStationText').val('梧州南')");

        ((JavascriptExecutor) driver).executeScript("$('#train_date').val('2018-02-10')");

        WebElement queryTicket = driver.findElement(By.id("query_ticket"));
        while (true) {
            boolean isContinue = true;
            queryTicket.click();
            System.out.println("query ticket");
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sear-result"))));
            for (int i = 9; i < 59;i = i+2) {
                WebElement row = driver.findElement(By.xpath("//*[@id=\"queryLeftTable\"]/tr[" + i + "]"));
                String trainName = row.findElement(By.tagName("a")).getText();
                boolean yiDengZuo = driver.findElement(By.xpath("//*[@id=\"queryLeftTable\"]/tr[" + i + "]/td[3]")).getAttribute("class").equals("");
                boolean erDengZuo = driver.findElement(By.xpath("//*[@id=\"queryLeftTable\"]/tr[" + i + "]/td[4]")).getAttribute("class").equals("");
                boolean wuZuo = driver.findElement(By.xpath("//*[@id=\"queryLeftTable\"]/tr[" + i + "]/td[11]")).getAttribute("class").equals("");

                System.out.println(i);
                System.out.println("train name: " + trainName);
                System.out.println("yidengzuo: " + yiDengZuo);
                System.out.println("erdengzuo: " + erDengZuo);
                System.out.println("wuzuo: " + wuZuo);

                if (!yiDengZuo || !erDengZuo || !wuZuo) {
                    System.out.println("book ticket: " + trainName);
                    WebElement bookButton = row.findElement(By.xpath("//*[@id=\"queryLeftTable\"]/tr[" + i + "]/td[13]"));
                    bookButton.click();
                    wait.until(ExpectedConditions.invisibilityOf(bookButton));
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("submitOrder_id"))));
                    driver.findElement(By.xpath("//*[@id=\"normalPassenger_5\"]")).click();
                    driver.findElement(By.id("submitOrder_id")).click();
                    WebElement qrSubmitIdButton = driver.findElement(By.id("qr_submit_id"));
                    wait.until(ExpectedConditions.visibilityOf(qrSubmitIdButton));
                    qrSubmitIdButton.click();

                    isContinue = false;
                    break;
                }
            }
            if (!isContinue) break;
        }
    }
}
