package com.example.foodexpress.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodexpress.bancodados.schema.PedidoItemSchema;
import com.example.foodexpress.bancodados.schema.PedidoSchema;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoodExpress";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Table Pedido
        String CREATE_PEDIDO_TABLE = "CREATE TABLE " + PedidoSchema.TABLE_NAME
                + "("
                + PedidoSchema.KEY_ID + " INTEGER PRIMARY KEY, "
                + PedidoSchema.KEY_DATE_TIME_ISSUE + " DATE, "
                + PedidoSchema.KEY_STATUS_PEDIDO + " INTEGER"
                + ");";
        db.execSQL(CREATE_PEDIDO_TABLE);

        // Create Table PedidoItem
        String CREATE_PEDIDO_ITEM_TABLE = "CREATE TABLE " + PedidoItemSchema.TABLE_NAME
                + "("
                + PedidoItemSchema.KEY_ID + " INTEGER PRIMARY KEY, "
                + PedidoItemSchema.KEY_PEDIDO_ID + " INTEGER, "
                + PedidoItemSchema.KEY_QTDE + " FLOAT, "
                + PedidoItemSchema.KEY_VLR_UNIT + " FLOAT, "
                + "FOREIGN KEY(" + PedidoItemSchema.KEY_PEDIDO_ID + ") " + " REFERENCES " + PedidoSchema.TABLE_NAME + "(" + PedidoSchema.KEY_ID + ")"
                + ");";
        db.execSQL(CREATE_PEDIDO_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PedidoSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PedidoItemSchema.TABLE_NAME);
        onCreate(db);
    }
}
