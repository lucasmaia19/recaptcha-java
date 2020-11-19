package br.com.lucas.maia.recaptcha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucas.maia.recaptcha.service.RepatchaService;

@RestController
@RequestMapping("detran")
public class RecaptchaController {
	
	@Autowired
	RepatchaService repatchaService;

	@PostMapping("consulta")
	public void testeRepatcha() throws Exception {
		
		repatchaService.inicio();

	}

}
