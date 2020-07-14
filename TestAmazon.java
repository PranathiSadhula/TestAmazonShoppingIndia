package testcases;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class TestAmazon {
	WebDriverWait wait;
	RemoteWebDriver driver;

	@Test
	public void TC001_testAmazonLoginSuccess() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS);
		// driver.manage().window().maximize();
		driver.get("https://www.amazon.in");
		wait.until(ExpectedConditions.titleContains("Online Shopping site in India"));

		String parentWindow = driver.getWindowHandle();
		System.out.println(parentWindow);

		List<WebElement> frames = driver.findElements(By.xpath("//iframe[contains(@name,'Gateway')]"));
		System.out.println("no. of frames available :" + frames.size());

		// for (WebElement iframe : frames) {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frames.get(0)));

		} catch (StaleElementReferenceException e) {
			System.out.println("ignoring StaleElementReferenceException");
		}
		System.out.println("frames switched for first frame:" + driver.getWindowHandles());
		// break;
		// }
		driver.findElementByTagName("html").click();

		Set<String> windowHandlesfor1stFrame = driver.getWindowHandles();
		System.out.println("1st frame===>" + windowHandlesfor1stFrame);
		for (String window : windowHandlesfor1stFrame) {
			driver.switchTo().window(window);
			System.out.println("switching to window :" + window);
		}
		System.out.println("u are in window :" + driver.getWindowHandle());
		String currentWindow = driver.getWindowHandle();
		wait.until(ExpectedConditions.titleContains("Laptops - Amazon.in"));
		System.out.println(driver.getTitle());
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@alt='travel']")));
		driver.findElement(By.xpath("//*[@alt='travel']")).click();

		wait.until(ExpectedConditions.titleContains("Multitasking"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='a']")));
		driver.findElement(By.xpath("//img[@alt='a']")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("s-result-count")));
		String searchResultCount = driver.findElement(By.id("s-result-count")).getText();// .replaceAll("\\D", "");
		System.out.println("No. of results found :" + searchResultCount);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sort")));
		Select sortBy = new Select(driver.findElement(By.id("sort")));
		List<WebElement> sortByOptions = sortBy.getOptions();
		for (WebElement options : sortByOptions) {
			// wait.until(ExpectedConditions.elementToBeSelected(options));
			if (options.getText().contains("Avg.")) {
				options.click();
				break;
			}
		}

		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//*[@data-component-type='s-product-image']/parent::div")));
		List<WebElement> results = driver
				.findElements(By.xpath("//*[@data-component-type='s-product-image']/parent::div"));// .click();
		System.out.println(results.size());
		results.get(0).click();
		Set<String> windowsAddTocart = driver.getWindowHandles();
		for (String window : windowsAddTocart) {
			driver.switchTo().window(window);
		}

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
		driver.findElement(By.id("add-to-cart-button")).click();

		driver.close(); // closing add to cart window

		driver.switchTo().window(currentWindow); // switching to laptops window
		driver.close(); // closing laptops window
		driver.switchTo().window(parentWindow);
		System.out.println("u are in window parent:" + parentWindow);
		System.out.println("no. of frames available :" + frames.size());
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frames.get(1)));

		} catch (StaleElementReferenceException e) {
			System.out.println("ignoring StaleElementReferenceException");
		}
		System.out.println("frames switched for 2nd frame:" + driver.getWindowHandles());
		driver.findElementByTagName("html").click();

		Set<String> windowHandlesfor2ndFrame = driver.getWindowHandles();
		System.out.println("2nd frame===>" + windowHandlesfor2ndFrame);
		for (String window : windowHandlesfor2ndFrame) {
			driver.switchTo().window(window);
		}

		System.out.println(driver.getTitle());

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@alt='Indoor lighting']")));
		driver.findElement(By.xpath("//*[@alt='Indoor lighting']")).click();
		
		wait.until(ExpectedConditions.titleContains("indoor lights"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,150)", "");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@alt='Ceiling lights']")));
		driver.findElement(By.xpath("//*[@alt='Ceiling lights']"))
				.click();

		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[contains(text(),'Recommended for you')]/../following-sibling::div/ul/li[1]")));
		driver.findElement(By.xpath("//*[contains(text(),'Recommended for you')]/../following-sibling::div/ul/li[1]"))
				.click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
		driver.findElement(By.id("add-to-cart-button")).click();

		driver.close(); // closing Home Products window
		driver.switchTo().window(parentWindow);

		driver.navigate().refresh();

		wait.until(ExpectedConditions.titleContains("Online Shopping site in India"));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-cart")));
		driver.findElement(By.id("nav-cart")).click();
		
		wait.until(ExpectedConditions.titleIs("Amazon.in Shopping Cart"));
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@data-name='Subtotals']/*[@id='sc-subtotal-label-buybox']")));
		System.out.println("No. of Items selected and added to your cart :"+driver.findElement(By.xpath("//*[@data-name='Subtotals']/*[@id='sc-subtotal-label-buybox']")).getText());
		System.out.println("Total Amount of your selected items in your cart :"+driver.findElement(By.xpath("//*[@data-name='Subtotals']/*[@id='sc-subtotal-amount-buybox']")).getText());
		
	
	}

	public void switchToFrame(List<WebElement> frames, int noOfFramesToSwitch) {

		for (int index = 1; index < frames.size(); index++) {
			try {
				if (index == noOfFramesToSwitch) {
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frames.get(index)));
				} else {
					System.out.println("frames not switched");
				}

			} catch (StaleElementReferenceException e) {
				System.out.println("ignoring StaleElementReferenceException");
			}
		}

	}

}
