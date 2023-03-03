package com.example.produktapi;

import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.aspectj.bridge.Version.getText;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeleniumApplicationTests {

	/*@BeforeEach
	public void beforeTests(){

		WebDriver driver = new ChromeDriver();

		driver.quit();

	}*/

	@Timeout(3)

	//APPROVED TESTs

	@Test
	public void check_websiteTitle() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		//Test if expected title matches websites title
		assertEquals("Webbutik",driver.getTitle(),"Title is not same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test
	public void check_h1Text() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		String h1Text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1")).getText();

		//Test if expected title matches websites title
		assertEquals("Testdriven utveckling - projekt",h1Text,"Title is not same as expected");

		//Closes the website window after test
		driver.quit();
	}


	@Test
	public void check_totalAmountOfProducts() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		List<WebElement> productsTotalAmount = driver.findElements(By.className("productItem"));

		//Test if expected title matches websites title
		assertEquals(20, productsTotalAmount.size(), "Amount of products not the same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test //First price check
	public void check_TotalPrice_forFjallravenBackpack_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");


		WebElement backpack = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Fin väska me plats för dator')]")));

		/* \\d matches any single digit
		 replace description with price */
		String backpackDescription = backpack.getText();
		String backpackPrice = backpackDescription.replaceAll("[^\\d.]", "");


		//Test if expected title matches websites title
		assertEquals("109.95", backpackPrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test //Second price check
	public void check_TotalPrice_forMensCasualPremiumSlimFitTShirt_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement mensTShirt = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Vilken härlig t-shirt, slim fit o casual i ett!')]")));

		String mensTShirtDescription = mensTShirt.getText();
		String mensTShirtPrice = mensTShirtDescription.replaceAll("[^\\d.]", "");


		//Test if expected title matches websites title
		assertEquals("22.3", mensTShirtPrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test //Third price check
	public void check_TotalPrice_forSolGoldPetiteMicropave_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement goldPetite = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Denna blir man glad av.')]")));

		String goldPetiteDescription = goldPetite.getText();
		String goldPetitePrice = goldPetiteDescription.replaceAll("[^\\d]", "");


		//Test if expected title matches websites title
		assertEquals("168", goldPetitePrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}


	//WELL PASSED TESTS
	@Test
	public void check_electronicsCategoryName() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		//electronics
		String electronics = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/a")).getText();


		//electronics category names matches
		assertEquals("electronics", electronics, "Category name of products not the same as expected");



		//Closes the website window after test
		driver.quit();
	}

	@Test
	public void check_jeweleryCategoryName() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");


		//jewelery
		String jewelery = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a")).getText();


		//jewelery category name matches
		assertEquals("jewelery", jewelery, "Category name of products not the same as expected");


		//Closes the website window after test
		driver.quit();
	}

	@Test
	public void check_mensClothingCategoryName() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");


		//men's clothing
		String mensClothing = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[4]/a")).getText();


		//men's clothing category name matches
		assertEquals("men's clothing", mensClothing, "Category name of products not the same as expected");



		//Closes the website window after test
		driver.quit();
	}

	@Test
	public void check_womensClothingCategoryName() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");


		//women's clothing
		String womensClothing = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[5]/a")).getText();


		//women's clothing category name matches
		assertEquals("women's clothing", womensClothing, "Category name of products not the same as expected");


		//Closes the website window after test
		driver.quit();
	}


	@Test //First price check
	public void check_totalPrice_forWomensGoldAndSilverDragonStationChainBracelet_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement womenChainBracelet = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Silver drakens återkomst. Ett måste om man vill ha den!')]")));

		String womenChainBraceletDescription = womenChainBracelet.getText();
		String womenChainBraceletPrice = womenChainBraceletDescription.replaceAll("[^\\d]", "");


		//Test if expected title matches websites title
		assertEquals("695", womenChainBraceletPrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test //Second price check
	public void check_totalPrice_forSanDiskSSDPLUS_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement sanDiskSSDPLUS = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Den här kan vara bra att ha också.')]")));

		String sanDiskSSDPLUSDescription = sanDiskSSDPLUS.getText();
		String sanDiskSSDPLUSPrice = sanDiskSSDPLUSDescription.replaceAll("[^\\d]", "");


		//Test if expected title matches websites title
		assertEquals("109", sanDiskSSDPLUSPrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}

	@Test //Third price check
	public void check_totalPrice_forGamingDrive_product() {

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement gamingDrive = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath
				("//p[contains(text(),'Är du en gamer? Ja men dåså, köp denna')]")));

		String gamingDriveDescription = gamingDrive.getText();
		String gamingDrivePrice = gamingDriveDescription.replaceAll("[^\\d]", "");


		//Test if expected title matches websites title
		assertEquals("114", gamingDrivePrice,"Price is not same as expected");

		//Closes the website window after test
		driver.quit();
	}



	@Test //First test, to check that image is visible
	public void check_forImagesVisible_acerTv(){

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement productImageAcerTv = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
			ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/81QpkIctqPL._AC_SX679_.jpg')]"
			)));



		assertTrue(productImageAcerTv.isDisplayed(), "Image does not want to load");

		//Closes the website window after test
		driver.quit();

	}

	@Test //Second test, to check that image is visible
	public void check_forImagesVisible_solGoldPetiteMicropave(){

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement productImageBracelet = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg')]"
				)));


		assertTrue(productImageBracelet.isDisplayed(), "Image does not want to load");

		//Closes the website window after test
		driver.quit();

	}

	@Test //Third test, to check that image is visible
	public void check_forImagesVisible_whiteGoldPlatedPrincess(){

		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement productImageRing = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg')]"
				)));


		assertTrue(productImageRing.isDisplayed(), "Image does not want to load");

		//Closes the website window after test
		driver.quit();

	}

	@Test //First test, to check that source of image is correct
	public void check_womensTshirtShort_sourceIsCorrect() {


			//Get the webDriver that we are going to use
			WebDriver driver = new ChromeDriver();

			//Navigate to the website that we are testing
			driver.get("https://java22.netlify.app/");

			WebElement productWomensTshirt = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg')]"
				)));

			String source = productWomensTshirt.getAttribute("src");


			assertEquals("https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg",source, "Source is not correct");

			//Closes the website window after test
			driver.quit();

		}

	@Test //Second test, to check that source of image is correct
	public void check_samsungMonitor_sourceIsCorrect() {


		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement samsungMonitor = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg')]"
				)));

		String source = samsungMonitor.getAttribute("src");


		assertEquals("https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg",source, "Source is not correct");

		//Closes the website window after test
		driver.quit();
	}

	@Test //Third test, to check that source of image is correct
	public void check_mensCottonJacket_sourceIsCorrect() {


		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();


		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement mensCottonJacket = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg')]"
				)));

		String source = mensCottonJacket.getAttribute("src");


		assertEquals("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",source, "Source is not correct");

		//Closes the website window after test
		driver.quit();
	}

	/*@Test //Third test, to check that source of image is correct      check this
	public void check_allProductNames_areCorrect() {


		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		String expectedProductName = "White Gold Plated Princess";

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		List<WebElement> productsNames = driver.findElements(By.className("card-title"));

		boolean found = false;

		for (WebElement allProductNames : productsNames){

			String actualProductName = allProductNames.getText();

			if (actualProductName.contains(expectedProductName)){
				found = true;
				return;
			}

			assertTrue(actualProductName.contains(expectedProductName), "Product name does not match!");

		}


		//Closes the website window after test
		driver.quit();
	}*/


	/*@Test //check this
	public void check_categoryClicks() {


		//Get the webDriver that we are going to use
		WebDriver driver = new ChromeDriver();

		Actions action = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Navigate to the website that we are testing
		driver.get("https://java22.netlify.app/");

		WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/a"));

		action.click(button);

		assertEquals("6", button.getSize());


		//Closes the website window after test
		driver.quit();
	}*/


	@Test
	public void wrong(){

		int y = 9;
		int u = 1;

		int result = y + u;

		assertEquals(10,result);
	}
}
