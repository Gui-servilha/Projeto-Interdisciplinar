package br.com.consultaagendamentos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConsultaController {

    private BancoHelper bancoHelper;

    public ConsultaController(Context context) {
        bancoHelper = new BancoHelper(context);
    }

    // Consulta por CPF
    public String consultarPessoaPorCPF(String cpf) {
        SQLiteDatabase db = bancoHelper.getReadableDatabase();
        Cursor cursor = null;
        String resultado;

        try {
            cursor = db.rawQuery("SELECT * FROM pessoas WHERE cpf = ?", new String[]{cpf});

            if (cursor != null && cursor.moveToFirst()) {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));
                String tipoAtendimento = cursor.getString(cursor.getColumnIndexOrThrow("tipo_atendimento"));

                resultado = "CPF: " + cpf + "\n" +
                        "Nome: " + nome + "\n" +
                        "Data: " + data + "\n" +
                        "Hora: " + hora + "\n" +
                        "Telefone: " + telefone + "\n" +
                        "Tipo de Atendimento: " + tipoAtendimento;
            } else {
                resultado = "Pessoa não encontrada para o CPF informado.";
            }
        } catch (Exception e) {
            resultado = "Erro na consulta por CPF: " + e.getMessage();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return resultado;
    }

    // Consulta por Data
    public String consultarPessoasPorData(String data) {
        SQLiteDatabase db = bancoHelper.getReadableDatabase();
        Cursor cursor = null;
        StringBuilder resultado = new StringBuilder();

        // Converter data para o formato ISO antes da consulta
        String dataFormatada = formatarData(data);

        if (dataFormatada == null) {
            return "Erro: A data fornecida não é válida. Use o formato dd-MM-yyyy.";
        }

        try {
            // Consulta ao banco de dados
            cursor = db.rawQuery("SELECT * FROM pessoas WHERE data = ?", new String[]{dataFormatada});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                    String cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"));
                    String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                    String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));
                    String tipoAtendimento = cursor.getString(cursor.getColumnIndexOrThrow("tipo_atendimento"));

                    resultado.append("Nome: ").append(nome).append("\n")
                            .append("CPF: ").append(cpf).append("\n")
                            .append("Hora: ").append(hora).append("\n")
                            .append("Telefone: ").append(telefone).append("\n")
                            .append("Tipo de Atendimento: ").append(tipoAtendimento).append("\n\n");
                } while (cursor.moveToNext());
            } else {
                resultado.append("Nenhuma pessoa encontrada para a data informada.");
            }
        } catch (Exception e) {
            resultado.append("Erro na consulta por data: ").append(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return resultado.toString();
    }

    // Método para converter data para formato ISO (yyyy-MM-dd)
    private String formatarData(String data) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return formatoSaida.format(formatoEntrada.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Retorna nulo caso a conversão falhe
        }
    }
}



