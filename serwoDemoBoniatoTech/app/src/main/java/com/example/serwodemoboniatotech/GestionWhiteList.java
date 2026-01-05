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

public class GestionWhiteList extends AppCompatActivity {
    Button botonMenu, botonAniadir, botonEliminar;
    ImageButton botonInfo;
    TextView tituloWhitelist, emailHeader, nameHeader, dateHeader;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gestion_whitelist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.GestionWhiteList), (v, insets) -> {
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
                    Log.d("GestionWhiteList", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("GestionWhiteList", "Error loading strings", e);
                }
            });
        }

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(GestionWhiteList.this, PantallaAdministracion.class);
            startActivity(intent);
        });
        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionWhiteList.this);
            builder.setMessage(stringsManager.getString("whitelist", "info_message"));
            builder.setPositiveButton(stringsManager.getString("whitelist", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonAniadir = findViewById(R.id.botonAniadir);
        botonAniadir.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionWhiteList.this);
            builder.setMessage(stringsManager.getString("whitelist", "add_info"));
            builder.setPositiveButton(stringsManager.getString("whitelist", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonEliminar = findViewById(R.id.botonEliminar);
        botonEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionWhiteList.this);
            builder.setMessage(stringsManager.getString("whitelist", "remove_info"));
            builder.setPositiveButton(stringsManager.getString("whitelist", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (tituloWhitelist == null) {
            tituloWhitelist = findViewById(R.id.tituloWhitelist);
            emailHeader = findViewById(R.id.emailHeader);
            nameHeader = findViewById(R.id.nameHeader);
            dateHeader = findViewById(R.id.dateHeader);
        }

        if (tituloWhitelist != null)
            tituloWhitelist.setText(stringsManager.getString("whitelist", "title", "Whitelist Management"));
        if (emailHeader != null)
            emailHeader.setText(stringsManager.getString("whitelist", "email_header", "Email"));
        if (nameHeader != null)
            nameHeader.setText(stringsManager.getString("whitelist", "name_header", "Name"));
        if (dateHeader != null)
            dateHeader.setText(stringsManager.getString("whitelist", "date_header", "Registration Date"));

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("whitelist", "menu_button", "Back"));
        }
        if (botonAniadir != null) {
            botonAniadir.setText(stringsManager.getString("whitelist", "add_button", "Add"));
        }
        if (botonEliminar != null) {
            botonEliminar.setText(stringsManager.getString("whitelist", "remove_button", "Remove"));
        }

        // Mock Data Rows
        TextView row1Email = findViewById(R.id.row1Email);
        TextView row1Name = findViewById(R.id.row1Name);
        TextView row1Date = findViewById(R.id.row1Date);

        TextView row2Email = findViewById(R.id.row2Email);
        TextView row2Name = findViewById(R.id.row2Name);
        TextView row2Date = findViewById(R.id.row2Date);

        if (row1Email != null)
            row1Email.setText(stringsManager.getString("mock_whitelist", "row1_email", "ea@gmail.com"));
        if (row1Name != null)
            row1Name.setText(stringsManager.getString("mock_whitelist", "row1_name", "carmen"));
        if (row1Date != null)
            row1Date.setText(stringsManager.getString("mock_whitelist", "row1_date", "2025-12-18..."));

        if (row2Email != null)
            row2Email.setText(stringsManager.getString("mock_whitelist", "row2_email", "ae@gmail.com"));
        if (row2Name != null)
            row2Name.setText(stringsManager.getString("mock_whitelist", "row2_name", "olga"));
        if (row2Date != null)
            row2Date.setText(stringsManager.getString("mock_whitelist", "row2_date", "2025-12-18..."));
    }
}