package com.example.foodexpress.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodexpress.bancodados.schema.PedidoItemSchema;
import com.example.foodexpress.bancodados.schema.PedidoSchema;
import com.example.foodexpress.bancodados.schema.ProdutoGrupoSchema;
import com.example.foodexpress.bancodados.schema.ProdutoSchema;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodExpress";
    private static int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Table Pedido
        final String CREATE_PEDIDO_TABLE = "CREATE TABLE " + PedidoSchema.TABLE_NAME
                + "("
                + PedidoSchema.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PedidoSchema.KEY_DATE_TIME_ISSUE + " DATE, "
                + PedidoSchema.KEY_STATUS_PEDIDO + " INTEGER"
                + ");";
        db.execSQL(CREATE_PEDIDO_TABLE);

        // Create Table PedidoItem
        final String CREATE_PEDIDO_ITEM_TABLE = "CREATE TABLE " + PedidoItemSchema.TABLE_NAME
                + "("
                + PedidoItemSchema.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PedidoItemSchema.KEY_PEDIDO_ID + " INTEGER, "
                + PedidoItemSchema.KEY_PRODUTO_ID + " INTEGER, "
                + PedidoItemSchema.KEY_QTDE + " FLOAT, "
                + PedidoItemSchema.KEY_VLR_UNIT + " FLOAT, "
                + "FOREIGN KEY(" + PedidoItemSchema.KEY_PEDIDO_ID + ") " + " REFERENCES " + PedidoSchema.TABLE_NAME + "(" + PedidoSchema.KEY_ID + ")"
                + ");";
        db.execSQL(CREATE_PEDIDO_ITEM_TABLE);

        // Create Table ProdutoGrupo
        final String CREATE_PRODUTO_GRUPO_TABLE = "CREATE TABLE " + ProdutoGrupoSchema.TABLE_NAME
                + "("
                + ProdutoGrupoSchema.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProdutoGrupoSchema.KEY_DESCRICAO + " TEXT "
                + ");";
        db.execSQL(CREATE_PRODUTO_GRUPO_TABLE);

        // Create Table Produto
        final String CREATE_PRODUTO_TABLE = "CREATE TABLE " + ProdutoSchema.TABLE_NAME
                + "("
                + ProdutoSchema.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProdutoSchema.KEY_PRODUTO_GRUPO_ID + " INTEGER, "
                + ProdutoSchema.KEY_DESCRICAO_PRODUTO + " TEXT, "
                + ProdutoSchema.KEY_PRECO_VENDA + " DOUBLE, "
                + ProdutoSchema.KEY_INGREDIENTES + " TEXT, "
                + "FOREIGN KEY(" + ProdutoSchema.KEY_PRODUTO_GRUPO_ID + ") " + " REFERENCES " + ProdutoGrupoSchema.TABLE_NAME + "(" + ProdutoGrupoSchema.KEY_ID + ")"
                + ");";
        db.execSQL(CREATE_PRODUTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PedidoItemSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PedidoSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProdutoSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProdutoGrupoSchema.TABLE_NAME);
        onCreate(db);
    }
}
