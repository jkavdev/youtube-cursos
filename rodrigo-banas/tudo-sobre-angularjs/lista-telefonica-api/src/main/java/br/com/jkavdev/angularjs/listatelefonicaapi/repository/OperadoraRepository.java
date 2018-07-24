package br.com.jkavdev.angularjs.listatelefonicaapi.repository;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Operadora;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperadoraRepository extends JpaRepository<Operadora, Integer> {

    @Override
    @Cacheable("operadoras.todas")
    List<Operadora> findAll();

    @Override
    @Cacheable(value = "operadoras.porId")
    Optional<Operadora> findById(Integer integer);
}
