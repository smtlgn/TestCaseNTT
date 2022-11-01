package NttTestCase;

import com.google.common.io.Files;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import static org.openqa.selenium.Keys.ENTER;

public class nttTest {
    @Test
    public void abc() throws IOException, CsvValidationException, InterruptedException {

        CSVReader reader = new CSVReader(new FileReader("src/test/resources/data.csv"));
        String[] cell;

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://demoqa.com/");
        String expectURL = "https://demoqa.com/";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(expectURL, actualURL);

        driver.findElement(By.xpath("//*[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        while ((cell = reader.readNext()) != null) {

            for (int i = 0; i < 1; i++) {
                String name = cell[i];
                String email = cell[i + 1];
                String adres = cell[i + 2];

                driver.findElement(By.id("firstName")).sendKeys(name);

                driver.findElement(By.id("userEmail")).sendKeys(email);

                driver.findElement(By.id("currentAddress")).sendKeys(adres);
            }

            driver.findElement(By.id("lastName")).sendKeys("Olgun");
            driver.findElement(By.id("userNumber")).sendKeys("1234567890");
            driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click();
            driver.findElement(By.cssSelector("label[for='hobbies-checkbox-1']")).click();

            WebElement product =  driver.findElement(By.xpath("//input[@id='subjectsInput']"));
            product.sendKeys("English");
            product.sendKeys(ENTER);

            driver.findElement(By.id("dateOfBirthInput")).click();
            driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']")).click();
            driver.findElement(By.xpath("//option[@value='2']")).click();

            driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']")).click();
            driver.findElement(By.xpath("//option[@value='1994']")).click();

            driver.findElement(By.xpath("//div[@class='react-datepicker__day react-datepicker__day--006 react-datepicker__day--weekend']")).click();

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("button#submit")) );
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector("button#submit")));

            Thread.sleep(5000);

            TakesScreenshot screenshot = (TakesScreenshot)driver;
            File image = screenshot.getScreenshotAs(OutputType.FILE);
            Files.move(image,new File("screenshots/ekran.png"));

            driver.findElement(By.id("closeLargeModal")).click();
            driver.quit();
        }
    }
}
