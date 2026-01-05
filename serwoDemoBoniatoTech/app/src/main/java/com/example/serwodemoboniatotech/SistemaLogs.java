package com.example.serwodemoboniatotech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SistemaLogs extends AppCompatActivity {
    Button botonMenu, botonExport, botonOperacion, botonUsuario, botonFecha, botonResultado;
    ImageButton botonInfo;
    TextView tituloLogs, opHeader, userHeader, dateHeader, resultHeader;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sistema_logs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SistemaLogs), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar StringsManager
        stringsManager = StringsManager.getInstance(this);

        // Si los strings no están cargados, cargarlos desde Firestore
        if (!stringsManager.isLoaded()) {
            stringsManager.loadFromFirestore(new StringsManager.OnStringsLoadedListener() {
                @Override
                public void onStringsLoaded() {
                    Log.d("SistemaLogs", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("SistemaLogs", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SistemaLogs.this);
            builder.setMessage(stringsManager.getString("logs", "info_message"));
            builder.setPositiveButton(stringsManager.getString("logs", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonMenu = findViewById(R.id.botonInicio);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(SistemaLogs.this, PantallaAdministracion.class);
            startActivity(intent);
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (tituloLogs == null) {
            tituloLogs = findViewById(R.id.tituloLogs);
            opHeader = findViewById(R.id.opHeader);
            userHeader = findViewById(R.id.userHeader);
            dateHeader = findViewById(R.id.dateHeader);
            resultHeader = findViewById(R.id.resultHeader);
            botonOperacion = findViewById(R.id.botonOperacion);
            botonUsuario = findViewById(R.id.botonUsuario);
            botonFecha = findViewById(R.id.botonFecha);
            botonResultado = findViewById(R.id.botonResultado);
            botonExport = findViewById(R.id.botonExport);
        }

        if (tituloLogs != null)
            tituloLogs.setText(stringsManager.getString("logs", "title", "System Logs"));
        if (opHeader != null)
            opHeader.setText(stringsManager.getString("logs", "operation_label", "Operation"));
        if (userHeader != null)
            userHeader.setText(stringsManager.getString("logs", "user_label", "User"));
        if (dateHeader != null)
            dateHeader.setText(stringsManager.getString("logs", "date_label", "Date"));
        if (resultHeader != null)
            resultHeader.setText(stringsManager.getString("logs", "result_label", "Result"));

        if (botonOperacion != null)
            botonOperacion.setText(stringsManager.getString("logs", "operation_label", "Operation"));
        if (botonUsuario != null)
            botonUsuario.setText(stringsManager.getString("logs", "user_label", "User"));
        if (botonFecha != null)
            botonFecha.setText(stringsManager.getString("logs", "date_label", "Date"));
        if (botonResultado != null)
            botonResultado.setText(stringsManager.getString("logs", "result_label", "Result"));

        if (botonExport != null)
            botonExport.setText(stringsManager.getString("logs", "export_csv_button", "Export CSV"));

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("logs", "back_button", "Back"));
        }

        // Mock Data Rows
        TextView row1Op = findViewById(R.id.row1Op);
        TextView row1User = findViewById(R.id.row1User);
        TextView row1Date = findViewById(R.id.row1Date);
        TextView row1Result = findViewById(R.id.row1Result);

        TextView row2Op = findViewById(R.id.row2Op);
        TextView row2User = findViewById(R.id.row2User);
        TextView row2Date = findViewById(R.id.row2Date);
        TextView row2Result = findViewById(R.id.row2Result);

        TextView row3Op = findViewById(R.id.row3Op);
        TextView row3User = findViewById(R.id.row3User);
        TextView row3Date = findViewById(R.id.row3Date);
        TextView row3Result = findViewById(R.id.row3Result);

        TextView row4Op = findViewById(R.id.row4Op);
        TextView row4User = findViewById(R.id.row4User);
        TextView row4Date = findViewById(R.id.row4Date);
        TextView row4Result = findViewById(R.id.row4Result);

        TextView row5Op = findViewById(R.id.row5Op);
        TextView row5User = findViewById(R.id.row5User);
        TextView row5Date = findViewById(R.id.row5Date);
        TextView row5Result = findViewById(R.id.row5Result);

        TextView row6Op = findViewById(R.id.row6Op);
        TextView row6User = findViewById(R.id.row6User);
        TextView row6Date = findViewById(R.id.row6Date);
        TextView row6Result = findViewById(R.id.row6Result);

        if (row1Op != null)
            row1Op.setText(stringsManager.getString("mock_logs", "row1_op", "MAIL_SEND"));
        if (row1User != null)
            row1User.setText(stringsManager.getString("mock_logs", "row1_user", "pablo..."));
        if (row1Date != null)
            row1Date.setText(stringsManager.getString("mock_logs", "row1_date", "2025-12-18"));
        if (row1Result != null)
            row1Result.setText(stringsManager.getString("mock_logs", "row1_result", "success"));

        if (row2Op != null)
            row2Op.setText(stringsManager.getString("mock_logs", "row2_op", "login"));
        if (row2User != null)
            row2User.setText(stringsManager.getString("mock_logs", "row2_user", "admin@admin"));
        if (row2Date != null)
            row2Date.setText(stringsManager.getString("mock_logs", "row2_date", "2025-12-18"));
        if (row2Result != null)
            row2Result.setText(stringsManager.getString("mock_logs", "row2_result", "success"));

        if (row3Op != null)
            row3Op.setText(stringsManager.getString("mock_logs", "row3_op", "login"));
        if (row3User != null)
            row3User.setText(stringsManager.getString("mock_logs", "row3_user", "admin@admin"));
        if (row3Date != null)
            row3Date.setText(stringsManager.getString("mock_logs", "row3_date", "2025-12-18"));
        if (row3Result != null)
            row3Result.setText(stringsManager.getString("mock_logs", "row3_result", "success"));

        if (row4Op != null)
            row4Op.setText(stringsManager.getString("mock_logs", "row4_op", "login"));
        if (row4User != null)
            row4User.setText(stringsManager.getString("mock_logs", "row4_user", "admin@admin"));
        if (row4Date != null)
            row4Date.setText(stringsManager.getString("mock_logs", "row4_date", "2025-12-18"));
        if (row4Result != null)
            row4Result.setText(stringsManager.getString("mock_logs", "row4_result", "error"));

        if (row5Op != null)
            row5Op.setText(stringsManager.getString("mock_logs", "row5_op", "login"));
        if (row5User != null)
            row5User.setText(stringsManager.getString("mock_logs", "row5_user", "admin@admin"));
        if (row5Date != null)
            row5Date.setText(stringsManager.getString("mock_logs", "row5_date", "2025-12-18"));
        if (row5Result != null)
            row5Result.setText(stringsManager.getString("mock_logs", "row5_result", "success"));

        if (row6Op != null)
            row6Op.setText(stringsManager.getString("mock_logs", "row6_op", "login"));
        if (row6User != null)
            row6User.setText(stringsManager.getString("mock_logs", "row6_user", "admin@admin"));
        if (row6Date != null)
            row6Date.setText(stringsManager.getString("mock_logs", "row6_date", "2025-12-18"));
        if (row6Result != null)
            row6Result.setText(stringsManager.getString("mock_logs", "row6_result", "success"));
    }
}
