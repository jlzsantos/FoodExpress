package com.example.foodexpress.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodexpress.bancodados.schema.PedidoItemSchema;
import com.example.foodexpress.bancodados.schema.PedidoSchema;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.utils.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PedidoHelper {

    private DatabaseHelper _dbHelper;
    private ProdutoHelper _produtoHelper;
    private Util _util;

    public PedidoHelper(Context contexto) {
        super();
        this._dbHelper = new DatabaseHelper(contexto);
        this._produtoHelper = new ProdutoHelper(contexto);
        this._util = new Util(contexto);
    }

    public long AdicionaPedido(Pedido pedido){
        long idPedido = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            Date dataAtual =  pedido.getDataHoraEmissao(); //new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String data_emissao = df.format(dataAtual);

            ContentValues item = new ContentValues();
            //item.put(PedidoSchema.KEY_DATE_TIME_ISSUE, data_emissao);
            item.put(PedidoSchema.KEY_STATUS_PEDIDO, pedido.getStatusPedido());

            idPedido = db.insert(PedidoSchema.TABLE_NAME, null, item);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível adicionar o pedido.");
        } finally {
            db.close();
            return idPedido;
        }
    }

    public void AlteraPedidoStatus(long idPedido, int idStatus){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            ContentValues item = new ContentValues();
            item.put(PedidoSchema.KEY_STATUS_PEDIDO, idStatus);

            db.update(PedidoSchema.TABLE_NAME, item, PedidoSchema.KEY_ID + " = ?",
                    new String[] { String.valueOf(idPedido) });

        } catch (Exception ex) {
            _util.showMensagem("Não foi alterar o status do pedido.");
        } finally {
            db.close();
        }
    }

    public void AdicionaPedidoItem(PedidoItem item){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            ContentValues value = new ContentValues();
            value.put(PedidoItemSchema.KEY_PEDIDO_ID, item.getIdPedido());
            value.put(PedidoItemSchema.KEY_PRODUTO_ID, item.getIdProduto());
            value.put(PedidoItemSchema.KEY_QTDE, item.getQtde());
            value.put(PedidoItemSchema.KEY_VLR_UNIT, item.getVlrUnit());

            db.insert(PedidoItemSchema.TABLE_NAME, null, value);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível adicionar o item do pedido.");
        } finally {
            db.close();
        }
    }

    public void RemovePedidoItemPorIdPedido(long id){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try{
            db.delete(PedidoItemSchema.TABLE_NAME, PedidoItemSchema.KEY_PEDIDO_ID + " = ?",
                    new String[] { String.valueOf(id) });

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível excluir os itens do pedido.");
        } finally {
            db.close();
        }
    }

    public ArrayList<Pedido> RetornaPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        final String query = PedidoSchema.getQueryConsultaPedidos();

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst()) {
                do {
                    long idPedido = Long.parseLong(cursor.getString(0));

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date emissao = df.parse(cursor.getString(1));

                    int status_pedido = cursor.getInt(2);
                    String descricao_status = cursor.getString(3);

                    Pedido pedido = new Pedido(idPedido, emissao, status_pedido, descricao_status);
                    pedidos.add(pedido);

                } while (cursor.moveToNext());
            }

            Collections.sort(pedidos);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível efetuar a pesquisa.");
        } finally {
            cursor.close();
            return pedidos;
        }
    }

    public ArrayList<Pedido> RetornaPedidosPorStatus(int status) {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        final String query = PedidoSchema.getQueryConsultaPedidosPorStatus(status);

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long idPedido = Long.parseLong(cursor.getString(0));
                    Date emissao = new Date(cursor.getLong(1));
                    int status_pedido = cursor.getInt(2);
                    String descricao_status = cursor.getString(3);

                    Pedido pedido = new Pedido(idPedido, emissao, status_pedido, descricao_status);
                    pedidos.add(pedido);

                } while (cursor.moveToNext());
            }

            Collections.sort(pedidos);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível efetuar a pesquisa.");
        } finally {
            cursor.close();
            return pedidos;
        }
    }

    public Pedido RetornaPedidoPorId(long id) {
        Pedido pedido = new Pedido();
        ArrayList<PedidoItem> itens = new ArrayList<PedidoItem>();

        final String query = PedidoSchema.getQueryConsultaPedidosPorId(id);

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long idPedido = Long.parseLong(cursor.getString(0));
                    Date emissao = new Date(cursor.getLong(1));
                    int status_pedido = cursor.getInt(2);
                    String descricao_status = cursor.getString(3);

                    itens = this.RetornaPedidoItensPorIdPedido(idPedido);

                    pedido.setIdPedido(idPedido);
                    pedido.setDataHoraEmissao(emissao);
                    pedido.setStatusPedido(status_pedido);
                    pedido.setDescricaoStatus(descricao_status);
                    pedido.setItensPedido(itens);

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            _util.showMensagem("Não foi possível efetuar a pesquisa.");
        } finally {
            cursor.close();
            return pedido;
        }
    }

    public ArrayList<PedidoItem> RetornaPedidoItensPorIdPedido(long id){
        ArrayList<PedidoItem> pedidoItens = new ArrayList<PedidoItem>();

        final String query = PedidoItemSchema.getQueryConsultaPedidoItemPorIdPedido(id);

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long idItem = Long.parseLong(cursor.getString(0));
                    long idPedido = Long.parseLong(cursor.getString(1));
                    long idProduto = Long.parseLong(cursor.getString(2));
                    float qtde = Float.parseFloat(cursor.getString(3));
                    float vlrUnit = Float.parseFloat(cursor.getString(4));

                    Produto produto = _produtoHelper.RetornaProdutoPorId(idProduto);

                    PedidoItem item = new PedidoItem(idItem, idPedido, idProduto, qtde, vlrUnit, produto);
                    pedidoItens.add(item);

                } while (cursor.moveToNext());
            }

            Collections.sort(pedidoItens);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível efetuar a pesquisa.");
        } finally {
            cursor.close();
            return pedidoItens;
        }
    }
}
