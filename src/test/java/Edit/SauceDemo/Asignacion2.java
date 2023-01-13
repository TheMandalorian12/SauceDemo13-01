package Edit.SauceDemo;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Asignacion2 {

	String url = "https://www.saucedemo.com/";

	@Test
	public void loguearseEnSauceDemoChrome() {
		WebDriver navegador = new ChromeDriver();
		navegador.get(url);
//ingresar un usuario
		WebElement txtUsername = navegador.findElement(By.cssSelector("#user-name"));
		txtUsername.sendKeys("standard_user");
//ingresar una contraseña
		WebElement txtPassword = navegador.findElement(By.id("password"));
		txtPassword.sendKeys("secret_sauce");
//Hacer click en botón login
		WebElement txtLoginButton = navegador.findElement(By.name("login-button"));
		txtLoginButton.click();

	}

	@Test
	public void loguearseEnSauceDemoFirefox() {
		WebDriver navegador = new FirefoxDriver();
		navegador.get(url);
		// usuario
		WebElement txtUsername = navegador.findElement(By.cssSelector("#user-name"));
		txtUsername.sendKeys("standard_user");
		// contraseña
		WebElement txtPassword = navegador.findElement(By.id("password"));
		txtPassword.sendKeys("secret_sauce");
		// click
		WebElement txtLoginButton = navegador.findElement(By.name("login-button"));
		txtLoginButton.click();
	}
}