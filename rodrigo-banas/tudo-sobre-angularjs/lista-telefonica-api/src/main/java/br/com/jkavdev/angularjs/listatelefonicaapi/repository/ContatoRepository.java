package br.com.jkavdev.angularjs.listatelefonicaapi.repository;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {

    @Override
    @Query("select c from Contato c join fetch c.operadora")
    List<Contato> findAll();
}
