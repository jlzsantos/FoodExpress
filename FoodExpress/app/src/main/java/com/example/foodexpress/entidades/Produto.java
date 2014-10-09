package com.example.foodexpress.entidades;

import java.io.Serializable;

public class Produto implements Serializable {
    private int idProduto;
    private int idCategoriaProduto;
    private String descricaoProduto;
    private float precoVenda;
    private String ingredientes;

    public Produto(){
        super();
    }
    
    public Produto(int idProduto, String descricao, float precoVenda, String ingredientes){
        super();
        
        this.idProduto = idProduto;
        this.descricaoProduto = descricao;
        this.precoVenda = precoVenda;
        this.ingredientes = ingredientes;
    }
    
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdCategoriaProduto() {
        return idCategoriaProduto;
    }

    public void setIdCategoriaProduto(int idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }

    public String getdescricaoProduto() {
        return descricaoProduto;
    }

    public void setdescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
}
