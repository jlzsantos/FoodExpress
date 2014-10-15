package com.example.foodexpress.bancodados.schema;

/**
 * Created by jose.santos on 15/10/2014.
 */
public class ProdutoGrupoSchema {

    // Estrutura
    public static final String TABLE_NAME = "produto_grupo";
    public static final String KEY_ID = "id";
    public static final String KEY_DESCRICAO = "descricao_grupo";

    // Querys
    public static String getQueryConsultaProdutoGrupo(){
        String query = "SELECT "
                + KEY_ID + ", "
                + KEY_DESCRICAO
                + " FROM "
                + TABLE_NAME;

        return query;
    }

    public static String getQueryConsultaProdutoGrupoPorId(long id){
        String str = "SELECT "
                + KEY_ID + ", "
                + KEY_DESCRICAO
                + " FROM "
                + TABLE_NAME
                + " WHERE "
                + KEY_ID + " = %d";

        final String query = String.format(str, id);
        return query;
    }
}
