package br.com.jkavdev.angularjs.listatelefonicaapi.controller;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Contato;
import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Operadora;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.ContatoRepository;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.OperadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ListaTelefonicaController {

    @Autowired
    private ContatoRepository repository;
    @Autowired
    private OperadoraRepository operadoraRepository;

    @GetMapping("/contatos")
    public List<Contato> getAllContatos() {
        return repository.findAll();
    }

    @GetMapping("/operadoras")
    public List<Operadora> getAllOperadoras() {
        return operadoraRepository.findAll();
    }

}
