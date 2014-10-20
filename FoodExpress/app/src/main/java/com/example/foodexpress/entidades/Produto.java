package com.example.foodexpress.entidades;

import java.io.Serializable;

public class Produto implements Serializable, Comparable<Produto> {
    private long idProduto;
    private long idProdutoGrupo;
    private String descricaoProduto;
    private double precoVenda;
    private String ingredientes;

    public Produto(){
        super();
    }
    
    public Produto(long idProduto, long idGrupo, String descricao, double precoVenda, String ingredientes){
        super();
        
        this.idProduto = idProduto;
        this.idProdutoGrupo = idGrupo;
        this.descricaoProduto = descricao;
        this.precoVenda = precoVenda;
        this.ingredientes = ingredientes;
    }
    
    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }

    public long getIdProdutoGrupo() {
        return idProdutoGrupo;
    }

    public void setIdProdutoGrupo(long idProdutoGrupo) {
        this.idProdutoGrupo = idProdutoGrupo;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public int compareTo(Produto produto) {
        return (int) this.getIdProduto();
    }
}
