package com.example.foodexpress.entidades;

import java.lang.String;
import java.util.UUID;

public class ProdutoGrupo{

    private long idProdutoGrupo;
    private String descricaoGrupo;

    public ProdutoGrupo(){
        super();
    }

    public ProdutoGrupo(long idGrupo, String descricaoGrupo){
        super();

        UUID guid;
        guid = UUID.randomUUID();

        //this.idProdutoGrupo = guid.toString();
        this.idProdutoGrupo = idGrupo;
        this.descricaoGrupo = descricaoGrupo;
    }

    public long getIdProdutoGrupo() {
        return idProdutoGrupo;
    }

    public void setIdProdutoGrupo(long idProdutoGrupo) {
        this.idProdutoGrupo = idProdutoGrupo;
    }

    public String getDescricaoGrupo() {
        return descricaoGrupo;
    }

    public void setDescricaoGrupo(String descricaoGrupo) {
        this.descricaoGrupo = descricaoGrupo;
    }
}