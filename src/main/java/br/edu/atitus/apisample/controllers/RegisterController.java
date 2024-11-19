package br.edu.atitus.apisample.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.RegisterDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.RegisterService;
import br.edu.atitus.apisample.services.UserService;

@RestController

@RequestMapping("/registers")
public class RegisterController {

		private final RegisterService service;
		private final UserService userService;

		public RegisterController(RegisterService service, UserService userService) {
			super();
			this.service = service;
			this.userService = userService;
		}
		
		public ResponseEntity<RegisterEntity> createRegister(RegisterDTO registerDTO) throws Exception {
			// Coverter DTO2Entity	
			RegisterEntity newRegister = new RegisterEntity();
			BeanUtils.copyProperties(registerDTO, newRegister);
			// Quando  autenticação estiver funcionando, pega-se o usuario autenticado
			UserEntity user = userService.findALL().get(0); 
	  //TODO FALTA CHAMAR METODO CAMADA SERVICE
		}
		
		
		@GetMapping
		public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception {
			var registers = service.findALL();
			//List<RegisterEntity> registers = service.findALL();
			
			//return ResponseEntity.status(200).body(registers);
			return ResponseEntity.ok(registers);
		}
		
}
