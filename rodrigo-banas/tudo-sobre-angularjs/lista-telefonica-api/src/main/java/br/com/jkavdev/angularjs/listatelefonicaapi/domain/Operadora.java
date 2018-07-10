package br.com.jkavdev.angularjs.listatelefonicaapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Operadora {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    private Integer codigo;

    private String categoria;

    private Integer preco;

    private Operadora() { }

    public Operadora(String nome, Integer codigo, String categoria, Integer preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.categoria = categoria;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Operadora{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo=" + codigo +
                ", categoria='" + categoria + '\'' +
                ", preco='" + preco + '\'' +
                '}';
    }
}
