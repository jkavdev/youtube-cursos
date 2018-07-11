package br.com.jkavdev.angularjs.listatelefonicaapi.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String serial;

    private String nome;

    private String telefone;

    private LocalDateTime data;

    private String cor;

    private Contato() {
    }

    public Contato(String nome, String telefone, Operadora operadora) {
        this.nome = nome;
        this.telefone = telefone;
        this.operadora = operadora;
        this.data = LocalDateTime.now();
        cor = "red";
    }

    @ManyToOne
    @JoinColumn(name = "operadora_id")
    private Operadora operadora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cor='" + cor + '\'' +
                ", data='" + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + '\'' +
                ", " + operadora +
                '}';
    }
}
