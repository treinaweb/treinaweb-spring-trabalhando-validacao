package br.com.treinaweb.twprojects.core.validators;

import br.com.treinaweb.twprojects.core.repositories.ClientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientEmailUniqueValidator implements ConstraintValidator<ClientEmailUnique, String> {

    private final ClientRepository clientRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return clientRepository.findByEmail(value).isEmpty();
    }
    
}
