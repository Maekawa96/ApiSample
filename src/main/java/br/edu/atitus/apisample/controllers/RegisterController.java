package br.edu.atitus.apisample.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		@PostMapping
		public ResponseEntity<RegisterEntity> createRegister(@RequestBody RegisterDTO registerDTO) throws Exception {
			// Coverter DTO2Entity	
			RegisterEntity newRegister = new RegisterEntity();
			BeanUtils.copyProperties(registerDTO, newRegister);
			// Quando  autenticação estiver funcionando, pega-se o usuario autenticado
			UserEntity user = userService.findALL().get(0); 
			newRegister.setUser(user);
			
			service.save(newRegister);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(newRegister); 
	
		}
		
		
		@GetMapping
		public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception {
			var registers = service.findALL();
			//List<RegisterEntity> registers = service.findALL();
			
			//return ResponseEntity.status(200).body(registers);
			return ResponseEntity.ok(registers);
			
			
		}
		
		public String getMethodName(@RequestParam String param) {
			return new String();
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<RegisterEntity> getOneRegister(@PathVariable UUID id) throws Exception {
			var register = service.findById(id);
			return ResponseEntity.ok(register);
		}
		
		@PutMapping("/{id}")
		public ResponseEntity<RegisterEntity> putRegister(@PathVariable UUID id, @RequestBody RegisterDTO dto) throws Exception {
			RegisterEntity register = service.findById(id);
			BeanUtils.copyProperties(dto, register);
			service.save(register);
			return ResponseEntity.ok(register);
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<String> deleteRegister(@PathVariable UUID id) throws Exception {
				service.deleteById(id);
				return ResponseEntity.ok("Registro deletado");
		}
			
		
		@ExceptionHandler(Exception.class)
		public ResponseEntity<String> handlerException(Exception ex){
		String menssage = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(menssage);
		}
		
		
}