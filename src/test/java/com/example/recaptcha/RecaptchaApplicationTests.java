package com.example.recaptcha;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RecaptchaApplicationTests {
	
	private RemoteWebDriver driver;
	
	@BeforeAll
	public void setUp() {
		
		System.out.println("passei aqui"); 
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();

		driver = new ChromeDriver(options);

	}

 
    @Test
    public void getSearchPage() {
    	
    	
		String link = ("https://www.detran.mg.gov.br/veiculos/transferencia-de-propriedade/transferencia-de-propriedade");
		driver.get(link);
    }

}