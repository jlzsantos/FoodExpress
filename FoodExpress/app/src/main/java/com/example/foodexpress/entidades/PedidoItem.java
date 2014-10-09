package com.example.foodexpress.entidades;


import java.io.Serializable;
import java.util.UUID;

public class PedidoItem implements Serializable {
    private long idPedidoItem;
    private long idPedido;
    private float qtde;
    private float vlrUnit;
    private float vlrTotal;
    private Produto produto;

    public PedidoItem(){
        super();
    }

    public PedidoItem(long idPedido, long idPedidoItem, float qtde, float vlrUnit, float vlrTotal, Produto produto){
        super();

        UUID guid;
        guid = UUID.randomUUID();

        //this.idPedidoItem = guid.toString();
        this.idPedido = idPedido;
        this.idPedidoItem = idPedidoItem;
        this.qtde = qtde;
        this.vlrUnit = vlrUnit;
        this.vlrTotal = (qtde * vlrUnit);
        this.produto = produto;
    }

    public long getIdPedidoItem() {
        return idPedidoItem;
    }

    public void setIdPedidoItem(long idPedidoItem) {
        this.idPedidoItem = idPedidoItem;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public float getQtde() {
        return qtde;
    }

    public void setQtde(float qtde) {
        this.qtde = qtde;
    }

    public float getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(float vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public float getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(float vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}