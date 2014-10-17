package com.example.foodexpress.bancodados.schema;

/**
 * Created by jose.santos on 15/10/2014.
 */
public class ProdutoSchema {

    // Estrutura
    public static final String TABLE_NAME = "produto";
    public static final String KEY_ID = "id";
    public static final String KEY_PRODUTO_GRUPO_ID = "produto_grupo_id";
    public static final String KEY_DESCRICAO_PRODUTO = "descricao_produto";
    public static final String KEY_PRECO_VENDA = "preco_venda";
    public static final String KEY_INGREDIENTES = "ingredientes";

    // Querys
    public static String getQueryConsultaProduto(){
        String query = "SELECT "
                + KEY_ID + ", "
                + KEY_PRODUTO_GRUPO_ID + ", "
                + KEY_DESCRICAO_PRODUTO + ", "
                + KEY_PRECO_VENDA + ", "
                + KEY_INGREDIENTES
                + " FROM "
                + TABLE_NAME;

        return query;
    }

    public static String getQueryConsultaProdutoPorId(long id){
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_PRODUTO_GRUPO_ID + ", "
                + KEY_DESCRICAO_PRODUTO + ", "
                + KEY_PRECO_VENDA + ", "
                + KEY_INGREDIENTES
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }

    public static String getQueryConsultaProdutoPorGrupoId(long id){
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_PRODUTO_GRUPO_ID + ", "
                + KEY_DESCRICAO_PRODUTO + ", "
                + KEY_PRECO_VENDA + ", "
                + KEY_INGREDIENTES
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_PRODUTO_GRUPO_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }

    public static String getQueryTruncateTable(){
        final String query = "DELETE FROM " + TABLE_NAME;
        return query;
    }
}
