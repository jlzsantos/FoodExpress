package com.example.foodexpress.entidades;

import java.io.Serializable;

/**
 * Created by jose.santos on 09/10/2014.
 */
public class Comanda implements Serializable {

    private long idPedido;
    private int idGrupo;
    private double qtdeItem;
    private boolean iniciarPedido;
    private Produto produto;

    public Comanda(){
        this.idPedido = 0;
        this.idGrupo = 0;
        this.iniciarPedido = true;
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

    public double getQtdeItem() {
        return qtdeItem;
    }

    public void setQtdeItem(double qtdeItem) {
        this.qtdeItem = qtdeItem;
    }

    public boolean isIniciarPedido() {
        return iniciarPedido;
    }

    public void setIniciarPedido(boolean iniciarPedido) {
        this.iniciarPedido = iniciarPedido;
    }
}
