package Project;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonAddCartValidation {

	public static void main(String[] args) {

		System.setProperty("Webdriver.chrome.driver", "D:\\Selenium Workspace\\Chrome Driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Navigate to Amazon site
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();

		// Select Electronics from All dropdown
		WebElement dropdown = driver.findElement(By.id("searchDropdownBox"));
		Select select = new Select(dropdown);
		select.selectByVisibleText("Electronics");

		// Search for a product
		WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search Amazon.in']"));
		searchBox.sendKeys("PS5");
		driver.findElement(By.id("nav-search-submit-button")).click();

		// Scroll Down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");

		// Select Sony checkbox
		WebElement checkbox = driver.findElement(By.xpath("//*[@id='p_123/237204']/span/a/div/label/i"));
		if (!checkbox.isSelected()) {
			checkbox.click();
		}

		// Click the product
		js.executeScript("window.scrollBy(0,250)");
		WebElement product = driver.findElement(By.partialLinkText("Sony PlayStation®5 Console (slim)"));
		product.click();

		// Get window handles
		Set<String> windowHandles = driver.getWindowHandles();
		String firstWindow = (String) windowHandles.toArray()[0];
		String secondWindow = (String) windowHandles.toArray()[1];
		driver.switchTo().window(secondWindow);

		// Check the product page displayed correctly
		WebElement checkProductPage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='platformInformation_feature_div']")));
		if (checkProductPage.getText().contains("PlayStation 5")) {
			driver.findElement(By.id("add-to-cart-button")).click();
		}
		
		// Close the current tab
		driver.close();

		// Switch to default window
		driver.switchTo().window(firstWindow);
		js.executeScript("window.scrollBy(0,-250)");

		// Navigate to home page
		WebElement homePage = driver.findElement(By.id("nav-logo-sprites"));
		homePage.click();

		// Select Computer & Accessories from dropdown
		dropdown = driver.findElement(By.id("searchDropdownBox"));
		select = new Select(dropdown);
		select.selectByVisibleText("Computers & Accessories");

		// Search for second product
		searchBox = driver.findElement(By.xpath("//input[@placeholder='Search Amazon.in']"));
		searchBox.sendKeys("hard drive 1 tb");
		driver.findElement(By.id("nav-search-submit-button")).click();

		// Scroll Down
		js.executeScript("window.scrollBy(0,300)");

		// Select Seagate checkbox
		checkbox = driver.findElement(By.xpath("//*[@id='p_123/224943']/span/a/div/label/i"));
		if (!checkbox.isSelected()) {
			checkbox.click();
		}

		// Click the product
		js.executeScript("window.scrollBy(0,250)");
		product = driver.findElement(By.partialLinkText(
				"Seagate One Touch 1TB External HDD with Password Protection Light Blue, for Windows and Mac, with 3 Yr Data Recovery Services, and 6 Months Mylio Create Plan and Dropbox Backup Plan (STKY1000402)"));
		product.click();

		// Switch Tab
		windowHandles = driver.getWindowHandles();
		firstWindow = (String) windowHandles.toArray()[0];
		secondWindow = (String) windowHandles.toArray()[1];
		driver.switchTo().window(secondWindow);

		// Check the product page displayed correctly
		checkProductPage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='bylineInfo']")));
		if (checkProductPage.getText().contains("Visit the Seagate Store")) {
			driver.findElement(By.id("add-to-cart-button")).click();
		}
		
		// Navigating to Cart Page
		WebElement cartPage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='attach-sidesheet-view-cart-button']/span/input")));
		cartPage.click();
		
		// Confirm if navigated to cart page
		WebElement cartPageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='sc-active-cart']/div/div[1]/div/h2")));
		String confirmText = cartPageElement.getText();
		
		if(confirmText.contains("Shopping Cart")) {
			
			// Close or quit browser
			driver.quit();
		}

	}

}
