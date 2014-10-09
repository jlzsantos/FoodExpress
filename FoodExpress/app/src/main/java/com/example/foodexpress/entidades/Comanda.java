package com.example.foodexpress.entidades;

import java.io.Serializable;

/**
 * Created by jose.santos on 09/10/2014.
 */
public class Comanda implements Serializable {

    private long idPedido;
    private int idGrupo;
    private float qtdeItem;
    private Produto produto;

    public Comanda(){
        this.idPedido = 0;
        this.idGrupo = 0;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public float getQtdeItem() {
        return qtdeItem;
    }

    public void setQtdeItem(float qtdeItem) {
        this.qtdeItem = qtdeItem;
    }
}
