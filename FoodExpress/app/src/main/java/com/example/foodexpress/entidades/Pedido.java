package com.example.foodexpress.entidades;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

public class Pedido implements Serializable, Comparable<Pedido> {
    private long idPedido;
    private Date dataHoraEmissao;
    private int statusPedido;
    private String descricaoStatus;
    private ArrayList<PedidoItem> itensPedido;
    //private float vlrTotalPedido;

    public Pedido(Pedido pedido){
        super();
    }

    public Pedido(long idPedido, Date dataHoraEmissao, int statusPedido, ArrayList<PedidoItem> itens){
        super();

        UUID guid;
        guid = UUID.randomUUID();

        //this.idPedido = guid.toString();
        this.idPedido = idPedido;
        this.dataHoraEmissao = dataHoraEmissao;
        this.statusPedido = statusPedido;
        this.itensPedido = itens;
    }

    public Pedido(long idPedido, Date dataHoraEmissao, int statusPedido){
        super();

        UUID guid;
        guid = UUID.randomUUID();

        //this.idPedido = guid.toString();
        this.idPedido = idPedido;
        this.dataHoraEmissao = dataHoraEmissao;
        this.statusPedido = statusPedido;
    }

    public Pedido(long idPedido, Date dataHoraEmissao, int statusPedido, String statusDesc){
        super();
        this.idPedido = idPedido;
        this.dataHoraEmissao = dataHoraEmissao;
        this.statusPedido = statusPedido;
        this.descricaoStatus = statusDesc;
    }

    public Pedido(){
        super();
    }

    private float getValorTotalPedido(){

        ArrayList<PedidoItem> itens = getItensPedido();
        float total = 0;

        if (itens != null) {
            for (int i = 0; i < itens.size(); i++) {
                total += itens.get(i).getVlrTotal();
            }
        }

        return total;
    }

    public long getIdPedido()
    {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {

        this.idPedido = idPedido;
    }

    public Date getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(Date dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public int getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(int statusPedido) {
        this.statusPedido = statusPedido;
    }

    @Override
    public int compareTo(Pedido pedido) {
        return (int) this.getIdPedido();
    }

    public float getVlrTotalPedido() {
        //return vlrTotalPedido;
        return getValorTotalPedido();
    }
/*
    public void setVlrTotalPedido(float vlrTotalPedido) {
        this.vlrTotalPedido = vlrTotalPedido;
    }
*/
    public ArrayList<PedidoItem> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(ArrayList<PedidoItem> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public String getDescricaoStatus() {
        return descricaoStatus;
    }

    public void setDescricaoStatus(String descricaoStatus) {
        this.descricaoStatus = descricaoStatus;
    }

    public String getDataHoraEmissaoString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(dataHoraEmissao);
    }
}