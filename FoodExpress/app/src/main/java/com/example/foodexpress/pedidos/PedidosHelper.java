package com.example.foodexpress.pedidos;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodexpress.bancodados.DatabaseHelper;
import com.example.foodexpress.bancodados.schema.PedidoSchema;
import com.example.foodexpress.entidades.Pedido;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PedidosHelper {

    private Context contexto;
    private DatabaseHelper dbHelper;

    public PedidosHelper(Context contexto) {
        super();
        this.contexto = contexto;
        dbHelper = new DatabaseHelper(contexto);
    }

    public long AdicionaPedido(Pedido pedido){
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Date dataAtual =  pedido.getDataHoraEmissao(); //new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String data_emissao = df.format(dataAtual);

            ContentValues item = new ContentValues();
            item.put(PedidoSchema.KEY_DATE_TIME_ISSUE, data_emissao);
            item.put(PedidoSchema.KEY_STATUS_PEDIDO, pedido.getStatusPedido());

            return db.insert(PedidoSchema.TABLE_NAME, null, item);

        } catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível adicionar o pedido.");
            dlg.show();
            return 0;
        }
    }

    public ArrayList<Pedido> RetornaPedidos() {

        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        final String query = PedidoSchema.getQueryConsultaPedidos();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            AlertDialog.Builder dlg = new AlertDialog.Builder(contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível adicionar o item no pedido.");
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

        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
            AlertDialog.Builder dlg = new AlertDialog.Builder(contexto);
            dlg.setTitle("FoodExpress");
            dlg.setNeutralButton("Ok", null);
            dlg.setMessage("Não foi possível adicionar o item no pedido.");
            dlg.show();
        }
        finally
        {
            cursor.close();
            return pedidos;
        }
    }
}

