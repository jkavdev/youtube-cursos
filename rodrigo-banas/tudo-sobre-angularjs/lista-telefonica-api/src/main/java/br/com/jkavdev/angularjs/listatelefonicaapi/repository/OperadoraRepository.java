package br.com.jkavdev.angularjs.listatelefonicaapi.repository;

import br.com.jkavdev.angularjs.listatelefonicaapi.domain.Operadora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadoraRepository extends JpaRepository<Operadora, Integer> {
}
