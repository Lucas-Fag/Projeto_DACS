package br.univille.dacs2022.dto;

import javax.validation.constraints.NotBlank;

public class CidadeDTO {

    private long id;
    @NotBlank(message = "O campo nome não pode ser deixado em branco.")
    private String nome;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}