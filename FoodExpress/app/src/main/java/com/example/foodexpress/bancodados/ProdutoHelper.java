package com.example.foodexpress.bancodados;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import com.example.foodexpress.bancodados.schema.PedidoItemSchema;
import com.example.foodexpress.bancodados.schema.ProdutoGrupoSchema;
import com.example.foodexpress.bancodados.schema.ProdutoSchema;
import com.example.foodexpress.entidades.Produto;
import com.example.foodexpress.entidades.ProdutoGrupo;
import com.example.foodexpress.utils.Util;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jose.santos on 15/10/2014.
 */
public class ProdutoHelper {

    private Context _contexto;
    private DatabaseHelper _dbHelper;
    private Util _util;
    private final String _msgErroPesquisa = "Não foi possível efetuar a pesquisa: ";

    public ProdutoHelper(Context contexto){
        super();
        this._contexto = contexto;
        this._dbHelper = new DatabaseHelper(contexto);
    }

    public void AdicionaProdutoGrupos(ArrayList<ProdutoGrupo> grupos){
        final String queryInsert = ProdutoGrupoSchema.getQueryInsertProdutoGrupo();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {

            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(queryInsert);

            for(ProdutoGrupo grup : grupos){
                stmt.clearBindings();
                stmt.bindString(1, grup.getDescricaoGrupo());
                stmt.execute();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (SQLiteException ex) {
            _util.showMensagem("Não foi possível adicionar o grupo: " + ex.getMessage());
        } finally {
            db.close();
        }
    }

    public long AdicionaProduto(Produto produto){
        try {
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            ContentValues itemAdd = new ContentValues();
            itemAdd.put(ProdutoSchema.KEY_PRODUTO_GRUPO_ID, produto.getIdProdutoGrupo());
            itemAdd.put(ProdutoSchema.KEY_DESCRICAO_PRODUTO, produto.getDescricaoProduto());
            itemAdd.put(ProdutoSchema.KEY_PRECO_VENDA, produto.getPrecoVenda());
            itemAdd.put(ProdutoSchema.KEY_INGREDIENTES, produto.getIngredientes());

            return db.insert(ProdutoSchema.TABLE_NAME, null, itemAdd);

        } catch (Exception ex) {
            _util.showMensagem("Não foi possível adicionar o produto: " + ex.getMessage());
            return 0;
        }
    }

    public void AdicionaProdutos(ArrayList<Produto> produtos){
        final String queryInsert = ProdutoSchema.getQueryInsertProduto();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {

            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(queryInsert);

            for(Produto prod : produtos){
                stmt.clearBindings();
                stmt.bindLong(1, prod.getIdProdutoGrupo());
                stmt.bindString(2, prod.getDescricaoProduto());
                stmt.bindDouble(3, prod.getPrecoVenda());
                stmt.bindString(4, prod.getIngredientes());
                stmt.execute();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (SQLiteException ex) {
            _util.showMensagem("Não foi possível adicionar a lista de produtos: " + ex.getMessage());
        } finally {
            db.close();
        }
    }

    public ArrayList<ProdutoGrupo> RetornaProdutoGrupos(){
        ArrayList<ProdutoGrupo> grupos = new ArrayList<ProdutoGrupo>();

        final String query = ProdutoGrupoSchema.getQueryConsultaProdutoGrupo();
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long idGrupo = cursor.getLong(0);
                    String descricao = cursor.getString(1);

                    ProdutoGrupo grupo = new ProdutoGrupo(idGrupo, descricao);
                    grupos.add(grupo);
                } while (cursor.moveToNext());
            }

            Collections.sort(grupos);

        } catch (Exception ex) {
            _util.showMensagem(_msgErroPesquisa + ex.getMessage());
        } finally {
            cursor.close();
            return grupos;
        }
    }

    public Produto RetornaProdutoPorId(long id){
        Produto produto = new Produto();

        final String query = ProdutoSchema.getQueryConsultaProdutoPorId(id);
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    produto.setIdProduto(cursor.getLong(0));
                    produto.setIdProdutoGrupo(cursor.getLong(1));
                    produto.setDescricaoProduto(cursor.getString(2));
                    produto.setPrecoVenda(cursor.getFloat(3));
                    produto.setIngredientes(cursor.getString(4));
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            _util.showMensagem(_msgErroPesquisa + ex.getMessage());
        } finally {
            cursor.close();
            return produto;
        }
    }

    public ArrayList<Produto> RetornaProdutoPorGrupoId(long idProdGrupo){
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        final String query = ProdutoSchema.getQueryConsultaProdutoPorGrupoId(idProdGrupo);
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    long idProduto = cursor.getLong(0);
                    long idGrupo = cursor.getLong(1);
                    String descricao = cursor.getString(2);
                    float precoVenda = cursor.getFloat(3);
                    String ingredientes = cursor.getString(4);

                    Produto produto = new Produto(idProduto, idGrupo, descricao, precoVenda, ingredientes);
                    produtos.add(produto);
                } while (cursor.moveToNext());
            }

            Collections.sort(produtos);

        } catch (Exception ex) {
            _util.showMensagem(_msgErroPesquisa + ex.getMessage());
        } finally {
            cursor.close();
            return produtos;
        }
    }

    public void RemovePedidoItensPorIdPedido(long idPedido){
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            db.delete(PedidoItemSchema.TABLE_NAME, PedidoItemSchema.KEY_PEDIDO_ID + " = ?",
                    new String[] { String.valueOf(idPedido) });

        } catch (SQLiteException ex) {
            _util.showMensagem("Não foi possível remover os itens do pedido: " + ex.getMessage());
        } finally {
            db.close();
        }
    }

    public void RemoveGruposAll(){
        final String query = ProdutoGrupoSchema.getQueryTruncateTable();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL(query);
        } catch (SQLiteException ex) {
            _util.showMensagem("Não foi possível remover os grupos de produtos: " + ex.getMessage());
        } finally {
            db.close();
        }
    }

    public void RemoveProdutosAll(){
        final String query = ProdutoSchema.getQueryTruncateTable();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            db.execSQL(query);
        } catch (SQLiteException ex) {
            _util.showMensagem("Não foi possível remover os produtos: " + ex.getMessage());
        } finally {
            db.close();
        }
    }
}