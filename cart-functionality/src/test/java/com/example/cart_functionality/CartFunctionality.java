package com.example.cart_functionality;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class CartFunctionality {
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void open() {
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get("https://periplus.com");
		
//		Direct to Sign in page
		WebElement signIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-signin-text")));
		signIn.click();
		
//		Insert email
		WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
		emailField.sendKeys("ywilanata@gmail.com");
//		Insert password
		driver.findElement(By.id("ps")).sendKeys("Test123!");
		driver.findElement(By.id("button-login")).click();
		
		
		
//		First product
		WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name")));
		searchBar.sendKeys("The Burnout");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		WebElement searchButton = driver.findElement(By.xpath("//button[@class='btnn']"));
		searchButton.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		WebElement product = driver.findElement(By.xpath("//img[@class='hover-img'][1]"));
		product.click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		WebElement addToCart = driver.findElement(By.xpath("//button[@class='btn btn-add-to-cart']"));		
		addToCart.click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notification-modal-header")));
		
//		Second product
		searchBar = driver.findElement(By.id("filter_name"));
		searchBar.clear();
		searchBar.sendKeys("Dog Man: Big Jim");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		searchButton = driver.findElement(By.xpath("//button[@class='btnn']"));
		searchButton.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		product = driver.findElement(By.xpath("//img[@class='hover-img'][1]"));
		product.click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		addToCart = driver.findElement(By.xpath("//button[@class='btn btn-add-to-cart']"));		
		addToCart.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notification-modal-header")));
		
//		Third product
		searchBar = driver.findElement(By.id("filter_name"));
		searchBar.clear();
		searchBar.sendKeys("The Alchemist");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		searchButton = driver.findElement(By.xpath("//button[@class='btnn']"));
		searchButton.click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		product = driver.findElement(By.xpath("//img[@class='hover-img'][1]"));
		product.click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		addToCart = driver.findElement(By.xpath("//button[@class='btn btn-add-to-cart']"));		
		addToCart.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("notification-modal-header")));
		
		WebElement cartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-your-cart")));
		cartButton.click();
	}
	
	@Test(priority = 1)
	public void editQty() {
//		Add Quantity
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		String before = driver.findElement(By.xpath("//input[@class='input-number text-center'][1]")).getAttribute("value");
		driver.findElement(By.xpath("//div[@class='button plus'][1]")).click();
		String afterAdd = driver.findElement(By.xpath("//input[@class='input-number text-center'][1]")).getAttribute("value");
		Assert.assertTrue("Qty doesn't increase", Integer.parseInt(before) < Integer.parseInt(afterAdd));
		
//		Subtract quantity
		driver.findElement(By.xpath("//div[@class='button minus'][1]")).click();
		String afterMinus = driver.findElement(By.xpath("//input[@class='input-number text-center'][1]")).getAttribute("value");
		Assert.assertTrue("Qty doesn't decrease", Integer.parseInt(afterMinus) < Integer.parseInt(afterAdd));
		
//		Change quantity
		WebElement qty = driver.findElement(By.xpath("//input[@class='input-number text-center'][1]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '3';", qty);
		driver.findElement(By.xpath("//input[@class='btn bg-transparent text-dark' and @value='Update']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		String afterEdit = driver.findElement(By.xpath("//input[@class='input-number text-center'][1]")).getAttribute("value");
		Assert.assertTrue("Qty doesn't change", afterEdit.equals("3"));
	}
	
	@Test(priority = 2)
	public void removeBook() {		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		List<WebElement> booksBefore = driver.findElements(By.xpath("//div[@class='row row-cart-product']"));
		driver.findElement(By.xpath("//a[@class='btn btn-cart-remove'][1]")).click();
		List<WebElement> booksAfter = driver.findElements(By.xpath("//div[@class='row row-cart-product']"));
		Assert.assertTrue("Book qty is the same.", booksAfter.size() < booksBefore.size());
	}
	
	@Test(priority = 3)
	public void saveForLater() {
//		Move to "save for later" section
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='btn btn-cart-save'][1]")));
		driver.findElement(By.xpath("//a[@class='btn btn-cart-save'][1]")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		String msg = driver.findElement(By.tagName("h3")).getText();
		Assert.assertTrue("Saved for later is not seen", msg.equals("Saved for Later"));
		
//		Move back to cart section
		driver.findElement(By.xpath("//a[@class='btn btn-move-cart']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
	}
	
	@Test(priority = 4)
	public void removeSaveForLater() {
//		Move to "saved for later" section
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='btn btn-cart-save'][1]")));
		driver.findElement(By.xpath("//a[@class='btn btn-cart-save'][1]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		String msg = driver.findElement(By.tagName("h3")).getText();
		Assert.assertTrue("Save for later is not seen", msg.equals("Saved for Later"));
		
//		Remove from "saved for later"
		driver.findElement(By.xpath("//a[@class='btn btn-save-remove']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
	}
	
	@Test(priority = 5)
	public void checkOut() {
		List<WebElement> books = driver.findElements(By.xpath("//div[@class='row row-cart-product']"));
		if (!books.isEmpty()) {
			driver.findElement(By.xpath("//a[contains(@class, 'btn') and @onclick='beginCheckout()']")).click();
			WebElement addressBook = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='address_book']")));
			Assert.assertTrue("Not directed to checkout page!", addressBook.isDisplayed());
			
//			Return to cart
			driver.navigate().back();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
		}
	}
	
	@Test(priority = 6)
	public void homePage() {
		List<WebElement> books = driver.findElements(By.xpath("//div[@class='row row-cart-product']"));
		
		if (books.isEmpty()) {
			driver.findElement(By.xpath("//a[@class='buton']")).click();
		} else {
			driver.findElement(By.xpath("//a[@class='btn text-white']")).click();
		}
		
		WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name")));
		Assert.assertTrue("Not directed to home page!", searchBar.isDisplayed());
	}
	
	@AfterClass
	public void close() {
		driver.close();
	}
	
}
