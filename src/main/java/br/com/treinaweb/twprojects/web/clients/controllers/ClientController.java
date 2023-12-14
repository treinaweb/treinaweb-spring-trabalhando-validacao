package br.com.treinaweb.twprojects.web.clients.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojects.core.exceptions.ClientNotFoundException;
import br.com.treinaweb.twprojects.core.repositories.ClientRepository;
import br.com.treinaweb.twprojects.web.clients.dtos.ClientForm;
import br.com.treinaweb.twprojects.web.clients.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @GetMapping
    public ModelAndView index() {
        var clients = clientRepository.findAll()
            .stream()
            .map(clientMapper::toClientListItem)
            .toList();
        var model = Map.of("clients", clients);
        return new ModelAndView("clients/index", model);
    }

    @GetMapping("/create")
    public ModelAndView create() {
        var model = Map.of(
            "clientForm", new ClientForm(),
            "pageTitle", "Cadastro de Cliente"
        );
        return new ModelAndView("clients/form", model);
    }

    @PostMapping("/create")
    public String create(ClientForm clientForm) {
        var client = clientMapper.toClient(clientForm);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var clientForm = clientRepository.findById(id)
            .map(clientMapper::toClientForm)
            .orElseThrow(ClientNotFoundException::new);
        var model = Map.of(
            "clientForm", clientForm,
            "pageTitle", "Edição de Cliente"
        );
        return new ModelAndView("clients/form", model);
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, ClientForm clientForm) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        var client = clientMapper.toClient(clientForm);
        client.setId(id);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
    
}
