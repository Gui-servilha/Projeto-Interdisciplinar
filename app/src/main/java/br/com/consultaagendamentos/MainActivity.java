package br.com.consultaagendamentos;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editCpf;
    private EditText editData; // Novo campo para a data
    private Button buttonConsultar;
    private Button buttonConsultarData; // Novo botão para consultar por data
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BancoHelper bancoHelper = new BancoHelper(this);
        bancoHelper.deleteDatabase(this);

        // Inicializa os componentes da UI
        editCpf = findViewById(R.id.editCpf);
        editData = findViewById(R.id.editData); // Inicializa o campo de data
        buttonConsultar = findViewById(R.id.buttonConsultar);
        buttonConsultarData = findViewById(R.id.buttonConsultarData); // Inicializa o botão de consulta por data
        textResultado = findViewById(R.id.textResultado);

        // Instancia o controlador do banco de dados
        BancoController bancoController = new BancoController(this);

        // Verifica e insere os dados fictícios, caso necessário
        verificarEInserirDadosFicticios(bancoController);

        // Configura o botão para realizar consultas por CPF
        buttonConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editCpf.getText().toString();
                if (!cpf.isEmpty()) {
                    ConsultaController consultaController = new ConsultaController(MainActivity.this);
                    String resultado = consultaController.consultarPessoaPorCPF(cpf);
                    textResultado.setText(resultado);
                } else {
                    textResultado.setText("Por favor, insira um CPF válido.");
                }
            }
        });

        // Configura o botão para realizar consultas por data
        buttonConsultarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editData.getText().toString();
                if (!data.isEmpty()) {
                    ConsultaController consultaController = new ConsultaController(MainActivity.this);
                    String resultado = consultaController.consultarPessoasPorData(data);
                    textResultado.setText(resultado);
                } else {
                    textResultado.setText("Por favor, insira uma data válida.");
                }
            }
        });
    }

    // Método para verificar e inserir dados fictícios, se necessário
    private void verificarEInserirDadosFicticios(BancoController bancoController) {
        BancoHelper bancoHelper = new BancoHelper(this);
        SQLiteDatabase db = bancoHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM pessoas", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count == 0) {
                bancoController.inserirDadosFicticios();
            }
        }
        cursor.close();
        db.close();

    }
}
