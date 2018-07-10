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

    private String cor;

    private Contato() { }

    public Contato(String nome, String telefone, Operadora operadora) {
        this.nome = nome;
        this.telefone = telefone;
        this.operadora = operadora;
        cor = "red";
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cor='" + cor + '\'' +
                ", " + operadora +
                '}';
    }
}
