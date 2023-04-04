package br.pe.acampos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class IteraLoginTest {

    public WebDriver acessarAplicacao() {
        System.setProperty("webdriver.chrome.driver","driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://itera-qa.azurewebsites.net/Login");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    @DisplayName("Deve logar com sucesso")
    public void deveLogarComSucesso() {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("Username")).sendKeys("alycampos");
            driver.findElement(By.id("Password")).sendKeys("123");
            driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody > tr:nth-child(7) >" +
                    " td:nth-child(2) > input.btn.btn-primary")).click();

            String message = driver.findElement(By.cssSelector("body > div > div > h3")).getText();
            Assertions.assertEquals("Welcome alycampos", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Nao deve logar sem username")
    public void naoDeveLogarSemUsername() {
        WebDriver driver = acessarAplicacao();

        try {

            driver.findElement(By.id("Password")).sendKeys("123");
            driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody > tr:nth-child(7) >" +
                    " td:nth-child(2) > input.btn.btn-primary")).click();
            String message1 = driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody > " +
                    "tr:nth-child(3) > td > span")).getText();
            String message2 = driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody >" +
                    " tr:nth-child(5) > td > label")).getText();

            Assertions.assertEquals("Please enter username", message1);
            Assertions.assertEquals("Wrong username or passwordd", message2);
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Nao deve logar sem password")
    public void naoDeveLogarSemPassword() {
        WebDriver driver = acessarAplicacao();

        try {
            driver.findElement(By.id("Username")).sendKeys("alycampos");
            driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody > tr:nth-child(7) >" +
                    " td:nth-child(2) > input.btn.btn-primary")).click();
            String message1 = driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody > " +
                    "tr:nth-child(6) > td > span")).getText();
            String message2 = driver.findElement(By.cssSelector("body > div > div:nth-child(4) > form > table > tbody >" +
                    " tr:nth-child(5) > td > label")).getText();

            Assertions.assertEquals("Please enter password", message1);
            Assertions.assertEquals("Wrong username or password", message2);
        } finally {
            driver.quit();
        }
    }
}
