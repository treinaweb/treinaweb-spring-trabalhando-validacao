package br.com.treinaweb.twprojects.web.employees.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {

    private String name;

    private String email;

    private String phone;

    private String cpf;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate birthDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate hireDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate resignationDate;

    private AddressForm address;

    private Long positionId;
    
}
