package br.com.treinaweb.twprojects.web.employees.controllers;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojects.core.exceptions.EmployeeNotFoundException;
import br.com.treinaweb.twprojects.core.repositories.EmployeeRepository;
import br.com.treinaweb.twprojects.core.repositories.PositionRepository;
import br.com.treinaweb.twprojects.web.employees.dtos.EmployeeForm;
import br.com.treinaweb.twprojects.web.employees.mappers.EmployeeMapper;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @GetMapping
    public ModelAndView index() {
        var employees = employeeRepository.findAll()
            .stream()
            .map(employeeMapper::toEmployeeListItem)
            .toList();
        var model = Map.of("employees", employees);
        return new ModelAndView("employees/index", model);
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        var employee = employeeRepository.findById(id)
            .map(employeeMapper::toEmployeeDetails)
            .orElseThrow(EmployeeNotFoundException::new);
        var model = Map.of("employee", employee);
        return new ModelAndView("employees/details", model);
    }

    @GetMapping("/create")
    public ModelAndView create() {
        var positions = positionRepository.findAll();
        var model = Map.of(
            "pageTitle", "Cadastro de Funcionário",
            "employeeForm", new EmployeeForm(),
            "positions", positions
        );
        return new ModelAndView("employees/form", model);
    }

    @PostMapping("/create")
    public String create(EmployeeForm employeeForm) {
        var employee = employeeMapper.toEmployee(employeeForm);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var employeeForm = employeeRepository.findById(id)
            .map(employeeMapper::toEmployeeForm)
            .orElseThrow(EmployeeNotFoundException::new);
        var positions = positionRepository.findAll();
        var model = Map.of(
            "pageTitle", "Edição de Funcionário",
            "employeeForm", employeeForm,
            "positions", positions
        );
        return new ModelAndView("employees/form", model);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, EmployeeForm employeeForm) {
        var employee = employeeRepository.findById(id)
            .orElseThrow(EmployeeNotFoundException::new);
        var employeeData = employeeMapper.toEmployee(employeeForm);
        BeanUtils.copyProperties(employeeData, employee, "id");
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        var employee = employeeRepository.findById(id)
            .orElseThrow(EmployeeNotFoundException::new);
        employeeRepository.delete(employee);
        return "redirect:/employees";
    }
    
}
