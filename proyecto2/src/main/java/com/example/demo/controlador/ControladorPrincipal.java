package com.example.demo.controlador;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ClienteService;

@RestController
public class ControladorPrincipal {
	private ClienteService clienteService;

	public ControladorPrincipal(ClienteService cs) {
		this.clienteService = cs;
	}
	
	@GetMapping("/consultaDirectorio")
	public List<File> consulta() {

	}

}
