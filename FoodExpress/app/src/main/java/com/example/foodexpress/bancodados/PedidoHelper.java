package com.example.foodexpress.bancodados;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodexpress.bancodados.DatabaseHelper;
import com.example.foodexpress.bancodados.schema.PedidoItemSchema;
import com.example.foodexpress.bancodados.schema.PedidoSchema;
import com.example.foodexpress.entidades.Pedido;
import com.example.foodexpress.entidades.PedidoItem;
import com.example.foodexpress.entidades.Produto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PedidoHelper {

    private Context _contexto;
    private DatabaseHelper _dbHelper;
    private ProdutoHelper _produtoHelper;

    public PedidoHelper(Context contexto) {
        super();
        this._contexto = contexto;
        this._dbHelper = new DatabaseHelper(contexto);
        this._produtoHelper = new ProdutoHelper(contexto);
    }

    public long AdicionaPedido(Pedido pedido){
        try {
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            Date dataAtual =  pedido.getDataHoraEmissao(); //new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String data_emissao = df.format(dataAtual);

            ContentValues item = new ContentValues();
            item.put(PedidoSchema.KEY_DATE_TIME_ISSUE, data_emissao);
            item.put(PedidoSchema.KEY_STATUS_PEDIDO, pedido.getStatusPedido());

            return db.insert(PedidoSchema.TABLE_NAME, null, item);

        } catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível adicionar o pedido.");
            dlg.show();
            return 0;
        }
    }

    public void AdicionaPedidoItem(PedidoItem item){
        try {
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            ContentValues value = new ContentValues();
            value.put(PedidoItemSchema.KEY_PEDIDO_ID, item.getIdPedido());
            value.put(PedidoItemSchema.KEY_PRODUTO_ID, item.getIdProduto());
            value.put(PedidoItemSchema.KEY_QTDE, item.getQtde());
            value.put(PedidoItemSchema.KEY_VLR_UNIT, item.getVlrUnit());

            db.insert(PedidoItemSchema.TABLE_NAME, null, value);

        } catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível adicionar o item do pedido.");
            dlg.show();
        }
    }

    public void RemovePedidoItemPorIdPedido(long id){
        try{
            SQLiteDatabase db = _dbHelper.getWritableDatabase();
            db.delete(PedidoItemSchema.TABLE_NAME, PedidoItemSchema.KEY_PEDIDO_ID + " = ?", new String[] { String.valueOf(id) });
            db.close();
        } catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível excluir os itens do pedido.");
            dlg.show();
        }
    }

    public ArrayList<Pedido> RetornaPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        final String query = PedidoSchema.getQueryConsultaPedidos();

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
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
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível efetuar a pesquisa.");
            dlg.show();
        }
        finally
        {
            cursor.close();
            return pedidos;
        }
    }

    public ArrayList<Pedido> RetornaPedidosPorStatus(int status) {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        final String query = PedidoSchema.getQueryConsultaPedidosPorStatus(status);

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
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
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível efetuar a pesquisa.");
            dlg.show();
        }
        finally
        {
            cursor.close();
            return pedidos;
        }
    }

    public ArrayList<PedidoItem> RetornaPedidoItensPorIdPedido(long id){
        ArrayList<PedidoItem> pedidoItens = new ArrayList<PedidoItem>();

        final String query = PedidoItemSchema.getQueryConsultaPedidoItemPorIdPedido(id);

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            if (cursor.moveToFirst()) {
                do {
                    long idItem = Long.parseLong(cursor.getString(0));
                    long idPedido = Long.parseLong(cursor.getString(1));
                    long idProduto = Long.parseLong(cursor.getString(2));
                    float qtde = Float.parseFloat(cursor.getString(3));
                    float vlrUnit = Float.parseFloat(cursor.getString(4));

                    //Produto produto = new Produto(1, 1, "Maguerita", 32.00f, "(Maguerita) Farinha de trigo, fermento biológico seco, açúcar, sal, azeite, ovos, molho de tomate, mussarela de búfala e manjericão");
                    Produto produto = _produtoHelper.RetornaProdutoPorId(idProduto);

                    PedidoItem item = new PedidoItem(idItem, idPedido, idProduto, qtde, vlrUnit, produto);
                    pedidoItens.add(item);

                } while (cursor.moveToNext());
            }

            Collections.sort(pedidoItens);
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(_contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível efetuar a pesquisa.");
            dlg.show();
        }
        finally
        {
            cursor.close();
            return pedidoItens;
        }
    }
}
