package br.com.lucas.maia.recaptcha.service;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import com.anti_captcha.api.NoCaptchaProxyless;
import com.anti_captcha.helper.DebugHelper;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

@Service
public class RepatchaService {

	private RemoteWebDriver driver;

	public void inicio() throws Exception {

		setUp();

		recaptcha();
	}

	private void setUp() throws Exception {

		WebDriverManager.chromedriver().setup();

		HashMap<String, Object> chromePref = new HashMap<String, Object>();

		ChromeOptions options = new ChromeOptions();
		options.setHeadless(false);
		options.setExperimentalOption("prefs", chromePref);

		driver = new ChromeDriver(options);
	}

	private void recaptcha() throws InterruptedException, IOException, AWTException {

        DebugHelper.setVerboseMode(true);
        NoCaptchaProxyless api = new NoCaptchaProxyless();
        api.setClientKey("3e7851ded010dc4c1e0bcee497304de6");
        api.setWebsiteUrl(new URL("https://www.detran.mg.gov.br/veiculos/situacao-do-veiculo/consulta-situacao-do-veiculo"));
        api.setWebsiteKey("6LfVpnIUAAAAAHkISk6Z6juZcsUx6hbyJGwfnfPL");
        if (!api.createTask()) {
            DebugHelper.out("API v2 send failed. " + api.getErrorMessage(), DebugHelper.Type.ERROR);

        } else if (!api.waitForResult()) {
            DebugHelper.out("Could not solve the captcha.", DebugHelper.Type.ERROR);

        } else {        	
            DebugHelper.out("Result: " + api.getTaskSolution().getGRecaptchaResponse(), DebugHelper.Type.SUCCESS);

    		String link = ("https://www.detran.mg.gov.br/veiculos/situacao-do-veiculo/consulta-situacao-do-veiculo");
    		driver.get(link);

    		String placa = "HOC-9880";
    		String chassi = "9C6KE1500B0001618";

    		System.out.println("Começado preenchimento de dados");

    		WebElement searchBoxPlaca = driver.findElement(By.id("ConsultarSituacaoVeiculoPlaca"));
    		searchBoxPlaca.sendKeys(placa);

    		WebElement searchBoxChassi = driver.findElement(By.id("ConsultarSituacaoVeiculoChassi"));
    		searchBoxChassi.sendKeys(chassi);

    		JavascriptExecutor js = (JavascriptExecutor) driver;
    		js.executeScript("document.getElementById('g-recaptcha-response').value='" + api.getTaskSolution().getGRecaptchaResponse() + "'");
    		
    		WebElement buttonPesquisar = driver.findElement(By.cssSelector(".formulario > .uma_coluna > button"));
    		buttonPesquisar.click();

    		System.out.println("Finalizado");

            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "jpg", new File("printscreen.jpg"));
        }

	}

}
