package br.com.consultaagendamentos;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class BancoController {

    private SQLiteDatabase db;
    private BancoHelper bancoHelper;

    public BancoController(Context context) {
        bancoHelper = new BancoHelper(context);
    }

    // Método para converter data para formato ISO (yyyy-MM-dd)
    private String formatarData(String data) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return formatoSaida.format(formatoEntrada.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Retorna nulo caso ocorra erro na conversão
        }
    }

    // Método para inserir pessoa
    public void inserirPessoa(String cpf, String nome, String data, String hora, String telefone, String tipoAtendimento) {
        db = bancoHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cpf", cpf);
        valores.put("nome", nome);
        valores.put("data", formatarData(data));
        valores.put("hora", hora);
        valores.put("telefone", telefone);
        valores.put("tipo_atendimento", tipoAtendimento);

        db.insert("pessoas", null, valores);
        db.close();
    }


    // Inserir dados fictícios
    public void inserirDadosFicticios() {
        db = bancoHelper.getWritableDatabase();

        inserirPessoa("12345678901", "João Silva", "01-12-2024", "10:30", "(11) 98765-4321", "Limpeza de pele");
        inserirPessoa("98765432100", "Maria Oliveira", "02-12-2024", "11:45", "(21) 91234-5678", "Exame");
        inserirPessoa("45678912345", "Carlos Santos", "03-12-2024", "14:00", "(31) 93456-7890", "Vacinação");
        inserirPessoa("32165498765", "Ana Costa", "04-12-2024", "16:15", "(41) 99876-5432", "Consulta");
        inserirPessoa("65432198700", "Lucas Lima", "05-12-2024", "18:20", "(51) 98765-4321", "Consulta de rotina");
        inserirPessoa("65432198700", "Lucas 1", "05-12-2024", "18:30", "(51) 98765-4321", "Consulta de rotina");
        inserirPessoa("65432198700", "Lucas 2", "05-12-2024", "19:00", "(51) 98765-4321", "Consulta de rotina1");
        inserirPessoa("65432198700", "Lucas 3", "05-12-2024", "19:30", "(51) 98765-4321", "Consulta de rotina2");
        db.close();
    }
}

