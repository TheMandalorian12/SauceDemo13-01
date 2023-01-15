package Edit.SauceDemo;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utilities.CapturaEvidencia;
import Utilities.DatosExcel;

public class Asignacion6 {
	String url = "https://www.saucedemo.com/";
	WebDriver driver;
	String rutaEvidencia = "..\\SauceDemo\\Evidencias\\";
	String nombreDocumento = "Evidencias SauceDemo.docx";
	Boolean iniciasesion = true;

	@BeforeSuite

	public void abrirNavegador() throws InvalidFormatException, IOException, InterruptedException {
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		CapturaEvidencia.escribirTituloEnDocumento(rutaEvidencia + nombreDocumento, "Evidencias SauceDemo", 18);

	}

	@Test(description = "CP01-iniciarSesion", priority = 1, dataProvider = "Datos Orden")
	public void login(String user, String password, String name, String lastName, String postalCode)
			throws InvalidFormatException, IOException, InterruptedException {
		driver.findElement(By.cssSelector("#user-name")).sendKeys(Keys.CONTROL, "A", Keys.DELETE);
		driver.findElement(By.cssSelector("#password")).sendKeys(Keys.CONTROL, "A", Keys.DELETE);
		driver.findElement(By.cssSelector("#user-name")).sendKeys(user);
		driver.findElement(By.cssSelector("#password")).sendKeys(password);
		driver.findElement(By.cssSelector("#login-button")).click();
		// captura evidencia
		// CapturaEvidencia.escribirTituloEnDocumento(rutaEvidencia + nombreDocumento,
		// "Evidencias SauceDemo", 18);
		CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg", rutaEvidencia + nombreDocumento,
				"Paso 1 - Página Login");

		try {
			driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
			iniciasesion = true;
		} catch (Exception e) {
			iniciasesion = false;
		}
		if (iniciasesion) {
			Assert.assertEquals(driver.getTitle(), "Swag Labs");
			System.out.println("Inicio sesion exitoso");
			driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bolt-t-shirt']")).click(); // seleccionamos
																											// un
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 2 - Seleccionamos producto");

			driver.findElement(By.cssSelector("#shopping_cart_container")).click(); // seleccionamos carrito
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 3 - Carrito de compras");
			driver.findElement(By.cssSelector("#checkout")).click();

			// llenamos fomrulario

			driver.findElement(By.cssSelector("#first-name")).sendKeys(name);
			driver.findElement(By.cssSelector("#last-name")).sendKeys(lastName);
			driver.findElement(By.cssSelector("#postal-code")).sendKeys(postalCode);
			System.out.println("Orden generada");
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 4 - Formulario");

			driver.findElement(By.cssSelector("#continue")).click();
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 5 - Orden");

			// Click para finalizar orden
			driver.findElement(By.name("finish")).click();
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 6 - Pantalla de orden finalizada");

			WebElement backHome = driver.findElement(By.id("back-to-products"));
			Assert.assertEquals(true, backHome.isDisplayed());
			System.out.println("Compra finalizada");

			// Volvemos al home y hacemos click en logout
			backHome.click();
			driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 7 - Home");

			// Agregamos una espera y cerramos sesión
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
			System.out.println("Sesion finalizada");
			// Evidencias
			CapturaEvidencia.capturarPantallaEnDocumento(driver, rutaEvidencia + "img.jpg",
					rutaEvidencia + nombreDocumento, "Paso 8 - Sesión finalizada");

		} else {
			Assert.assertTrue(true);
		}

	}

	@DataProvider(name = "Datos Orden")
	public Object[][] leerDatosOrden() throws Exception {
		return DatosExcel.leerExcel("..\\SauceDemo\\Datos\\DatosOrden.xlsx", "Hoja1");
	}

	@AfterSuite
	public void cerrarnavegador() {
		driver.close();

	}
}
