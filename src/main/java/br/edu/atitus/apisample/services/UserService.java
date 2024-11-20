package br.edu.atitus.apisample.services;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

// Anotação para indicar que essa classe é gerenciada pelo Spring
@Service
public class UserService {
    
    private final UserRepository repository;
    
    // Construtor para injeção de dependências
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public UserEntity save(UserEntity newUser) throws Exception {
        // Validação de regras de negócio
        if (newUser == null)
            throw new Exception("Objeto nulo!");
        
        if (newUser.getName() == null || newUser.getName().isEmpty())
            throw new Exception("Nome inválido!");
        newUser.setName(newUser.getName().trim());
        
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
            throw new Exception("Email inválido!");
        
        // Validar o email com regex
        if (!isValidEmail(newUser.getEmail()))
            throw new Exception("Email inválido!");
        
        newUser.setEmail(newUser.getEmail().trim());
        
        if (repository.existsByEmail(newUser.getEmail()))
        	throw new Exception("Esse email já possui uma conta");
        
        // Salvar o usuário no repositório
        this.repository.save(newUser);
        
        return newUser;
    }
    
    public List<UserEntity> findALL() throws Exception{
    	return repository.findAll();
    }
}
