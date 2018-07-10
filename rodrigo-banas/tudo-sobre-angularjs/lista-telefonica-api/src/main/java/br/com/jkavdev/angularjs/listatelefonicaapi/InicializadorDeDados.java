package br.com.jkavdev.angularjs.listatelefonicaapi;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Contato;
import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Operadora;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.ContatoRepository;
import br.com.jkavdev.angularjs.listatelefonicaapi.repository.OperadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InicializadorDeDados implements CommandLineRunner {

    @Autowired
    private OperadoraRepository operadoraRepository;
    @Autowired
    private ContatoRepository contatoRepository;

    @Override
    public void run(String... args) throws Exception {

        Operadora vivo = new Operadora("Vivo", 13, "Celular");
        Operadora tim = new Operadora("Tim", 14, "Celular");
        Operadora claro = new Operadora("Claro", 15, "Celular");
        Operadora gvt = new Operadora("GVT", 15, "Fixo");
        Operadora embratel = new Operadora("Embratel", 15, "Fixo");

        Arrays.asList(vivo, tim, claro, gvt, embratel)
                .forEach(o -> operadoraRepository.save(o));

        Contato jhonatan = new Contato("Jhonatan", "9999-3214", vivo);
        Contato eilane = new Contato("Eilane", "9999-3214", tim);
        Contato douglas = new Contato("Douglas", "9999-3214", claro);
        Contato maria = new Contato("Maria", "9999-3214", gvt);
        Contato lucas = new Contato("Lucas", "9999-3214", embratel);

        Arrays.asList(jhonatan, eilane, douglas, maria, lucas)
                .forEach(c -> contatoRepository.save(c));

        contatoRepository.findAll()
            .forEach(System.out::println);

        System.out.printf("Pronto.....................................................................................");


    }
}
