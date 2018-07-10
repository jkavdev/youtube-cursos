package br.com.jkavdev.angularjs.listatelefonicaapi.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Contato {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    private String telefone;

    private LocalDateTime data;

    private Contato() { }

    public Contato(String nome, String telefone, Operadora operadora) {
        this.nome = nome;
        this.telefone = telefone;
        this.operadora = operadora;
    }

    @ManyToOne
    @JoinColumn(name = "operadora_id")
    private Operadora operadora;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", " + operadora +
                '}';
    }
}
