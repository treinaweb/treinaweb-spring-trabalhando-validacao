package br.com.treinaweb.twprojects.web.employees.dtos;

import br.com.treinaweb.twprojects.core.enums.AddressState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressForm {

    private String city;
    
    private AddressState state;
    
    private String street;
    
    private String number;
    
    private String complement;
    
    private String zipCode;
    
    private String neighborhood;
    
}
