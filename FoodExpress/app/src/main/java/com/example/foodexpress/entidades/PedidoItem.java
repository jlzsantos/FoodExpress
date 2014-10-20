package com.example.foodexpress.entidades;


import java.io.Serializable;
import java.util.UUID;

public class PedidoItem implements Serializable, Comparable<PedidoItem> {
    private long idPedidoItem;
    private long idPedido;
    private long idProduto;
    private double qtde;
    private double vlrUnit;
    private double vlrTotal;
    private Produto produto;

    public PedidoItem(){
        super();
    }

    public PedidoItem(long idPedido, long idPedidoItem, long idProduto, double qtde, double vlrUnit, Produto produto){
        super();

        UUID guid;
        guid = UUID.randomUUID();

        //this.idPedidoItem = guid.toString();
        this.idPedido = idPedido;
        this.idPedidoItem = idPedidoItem;
        this.idProduto = idProduto;
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

    public double getQtde() {
        return qtde;
    }

    public void setQtde(double qtde) {
        this.qtde = qtde;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public double getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(double vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int compareTo(PedidoItem item) {
        return (int) this.getIdPedido();
    }

    public long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(long idProduto) {
        this.idProduto = idProduto;
    }
}