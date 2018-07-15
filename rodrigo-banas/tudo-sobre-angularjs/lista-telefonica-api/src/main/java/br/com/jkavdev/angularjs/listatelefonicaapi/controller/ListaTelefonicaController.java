package br.com.jkavdev.angularjs.listatelefonicaapi.controller;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Contato;
import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Operadora;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.ContatoRepository;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.OperadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/contato/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Integer id) {
        Optional<Contato> contatoOptional = repository.findById(id);
        if (contatoOptional.isPresent())
            return ResponseEntity.ok().body(contatoOptional.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/contatos")
    @ResponseStatus(HttpStatus.CREATED)
    public Contato create(@RequestBody Contato contato) {
        return repository.save(contato);
    }

    @GetMapping("/operadoras")
    public List<Operadora> getAllOperadoras() {
        return operadoraRepository.findAll();
    }

}
