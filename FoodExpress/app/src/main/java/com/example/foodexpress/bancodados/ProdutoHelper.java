package com.example.foodexpress.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
        this._util = new Util(contexto);
    }

    public long AdicionaProdutoGrupo(ProdutoGrupo grupo){
        try{
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            ContentValues itemAdd = new ContentValues();
            itemAdd.put(ProdutoGrupoSchema.KEY_DESCRICAO, grupo.getDescricaoGrupo());

            return db.insert(ProdutoGrupoSchema.TABLE_NAME, null, itemAdd);

        } catch (Exception ex) {
            _util.RetornaSimpleDialog("Não foi possível adicionar o grupo de produtos: " + ex.getMessage()).show();
            return 0;
        }
    }

    public void AdicionaProdutoGrupos(ArrayList<ProdutoGrupo> grupos){
        final String queryInsert = ProdutoGrupoSchema.getQueryInsertProdutoGrupo();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try{

            db.beginTransaction();
            SQLiteStatement stmt = db.compileStatement(queryInsert);

            for(int i = 0; i < grupos.size(); i++){
                stmt.clearBindings();
                stmt.bindString(1, grupos.get(i).getDescricaoGrupo());
                stmt.execute();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (Exception ex) {
            _util.RetornaSimpleDialog("Não foi possível adicionar o grupo de produtos: " + ex.getMessage()).show();
        } finally {
            db.close();
        }
    }

    public void insertNormal(ArrayList<ProdutoGrupo> grupos){
        try{
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            for(int i = 0; i < grupos.size(); i++){
                ContentValues values = new ContentValues();
                values.put(ProdutoGrupoSchema.KEY_DESCRICAO, grupos.get(i).getDescricaoGrupo());

                db.insert(ProdutoGrupoSchema.TABLE_NAME, null, values);
            }

            db.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public long AdicionaProduto(Produto produto){
        try{
            SQLiteDatabase db = _dbHelper.getWritableDatabase();

            ContentValues itemAdd = new ContentValues();
            itemAdd.put(ProdutoSchema.KEY_PRODUTO_GRUPO_ID, produto.getIdProdutoGrupo());
            itemAdd.put(ProdutoSchema.KEY_DESCRICAO_PRODUTO, produto.getDescricaoProduto());
            itemAdd.put(ProdutoSchema.KEY_PRECO_VENDA, produto.getPrecoVenda());
            itemAdd.put(ProdutoSchema.KEY_PRECO_VENDA, produto.getPrecoVenda());

            return db.insert(ProdutoSchema.TABLE_NAME, null, itemAdd);

        } catch (Exception ex) {
            _util.RetornaSimpleDialog("Não foi possível adicionar o produto: " + ex.getMessage()).show();
            return 0;
        }
    }

    public ArrayList<ProdutoGrupo> RetornaProdutoGrupos(){
        ArrayList<ProdutoGrupo> grupos = new ArrayList<ProdutoGrupo>();

        final String query = ProdutoGrupoSchema.getQueryConsultaProdutoGrupo();
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                long idGrupo = cursor.getLong(0);
                String descricao = cursor.getString(1);

                ProdutoGrupo grupo = new ProdutoGrupo(idGrupo, descricao);
                grupos.add(grupo);

            } while (cursor.moveToNext());

            Collections.sort(grupos);

        } catch (Exception ex) {
            _util.RetornaSimpleDialog(_msgErroPesquisa + ex.getMessage()).show();
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

                produto.setIdProduto(cursor.getLong(0));
                produto.setIdProdutoGrupo(cursor.getLong(1));
                produto.setDescricaoProduto(cursor.getString(2));
                produto.setPrecoVenda(cursor.getFloat(3));
                produto.setIngredientes(cursor.getString(4));

            } while (cursor.moveToNext());

        } catch (Exception ex) {
            _util.RetornaSimpleDialog(_msgErroPesquisa + ex.getMessage()).show();
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
                long idProduto = cursor.getLong(0);
                long idGrupo = cursor.getLong(1);
                String descricao = cursor.getString(2);
                float precoVenda = cursor.getFloat(3);
                String ingredientes = cursor.getString(4);

                Produto produto = new Produto(idProduto, idGrupo, descricao, precoVenda, ingredientes);
                produtos.add(produto);

            } while (cursor.moveToNext());

            Collections.sort(produtos);

        } catch (Exception ex) {
            _util.RetornaSimpleDialog(_msgErroPesquisa + ex.getMessage()).show();
        } finally {
            cursor.close();
            return produtos;
        }
    }

    public void RemoveGruposAll(){
        final String query = ProdutoGrupoSchema.getQueryTruncateTable();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void RemoveProdutosAll(){
        final String query = ProdutoSchema.getQueryTruncateTable();
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}