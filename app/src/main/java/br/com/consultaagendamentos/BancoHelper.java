package br.com.consultaagendamentos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    private static final int VERSAO_BANCO = 2; // Atualize para a versão mais recente

    public BancoHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela
        String criarTabela = "CREATE TABLE pessoas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cpf TEXT, " +
                "nome TEXT, " +
                "data DATE, " +
                "hora TEXT, " +
                "telefone TEXT, " +
                "tipo_atendimento TEXT)";  // Defina todos os campos necessários
        db.execSQL(criarTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização da versão do banco de dados
        if (oldVersion < 2) {
            // Adiciona a coluna tipo_atendimento, caso a versão antiga seja inferior à versão 2
            db.execSQL("ALTER TABLE pessoas ADD COLUMN tipo_atendimento TEXT");
        }
    }

    // Método para excluir o banco de dados (caso precise recriar completamente)
    public void deleteDatabase(Context context) {
        context.deleteDatabase(NOME_BANCO);  // Exclui o banco de dados
    }
}


