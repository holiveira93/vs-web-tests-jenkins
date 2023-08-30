package br.pe.acampos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class SauceDemoTest {

    public WebDriver acessarAplicacao() {
        System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    @DisplayName("Deve logar com sucesso")
    public void deveLogarComSucesso() {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            String message = driver.findElement(By.cssSelector(
                    "#header_container > div.header_secondary_container > span")).getText();
            Assertions.assertEquals("Products", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Nao deve logar sem username")
    public void naoDeveLogarSemUsername() {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            String message = driver.findElement(By.cssSelector(
                    "#login_button_container > div > form > div.error-message-container.error > h3")).getText();

            Assertions.assertEquals("Epic sadface: Username is required", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Nao deve logar sem passwordd")
    public void naoDeveLogarSemPassword() {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("login-button")).click();
            String message = driver.findElement(By.cssSelector(
                    "#login_button_container > div > form > div.error-message-container.error > h3")).getText();

            Assertions.assertEquals("Epic sadface: Password is required", message);
        } finally {
            driver.quit();
        }
    }
}
