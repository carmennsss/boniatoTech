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

public class GestionDatos extends AppCompatActivity {
    Button botonEspecies, botonRecintos, botonCuidadores, botonAnimales, botonMenu, botonTraslados,
            botonEspeciesRecintos, botonNew;
    ImageButton botonInfo;
    TextView instructionText, headerId, headerName, headerAddress;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gestion_datos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gestionDatos), (v, insets) -> {
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
                    Log.d("GestionDatos", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("GestionDatos", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "info_message"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonEspecies = findViewById(R.id.botonEspecies);
        botonEspecies.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "species_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonRecintos = findViewById(R.id.botonRecinto);
        botonRecintos.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "enclosure_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonCuidadores = findViewById(R.id.botonCuidador);
        botonCuidadores.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "keeper_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonAnimales = findViewById(R.id.botonAnimales);
        botonAnimales.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "animals_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonMenu = findViewById(R.id.botonInicio);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(GestionDatos.this, PantallaPrincipal.class);
            startActivity(intent);
        });

        botonTraslados = findViewById(R.id.botonTraslados);
        botonTraslados.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "transfers_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonEspeciesRecintos = findViewById(R.id.botonEspeciesRecintos);
        botonEspeciesRecintos.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionDatos.this);
            builder.setMessage(stringsManager.getString("data_management", "species_enclosures_info"));
            builder.setPositiveButton(stringsManager.getString("data_management", "understood", "Understood"), null);
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
        if (instructionText == null) {
            instructionText = findViewById(R.id.instructionText);
        }
        if (botonNew == null) {
            botonNew = findViewById(R.id.botonNew);
        }

        if (instructionText != null) {
            instructionText.setText(stringsManager.getString("data_management", "instruction_text",
                    "Double click on a record to update or delete it"));
        }

        if (headerId == null)
            headerId = findViewById(R.id.headerId);
        if (headerName == null)
            headerName = findViewById(R.id.headerName);
        if (headerAddress == null)
            headerAddress = findViewById(R.id.headerAddress);

        if (headerId != null)
            headerId.setText(stringsManager.getString("data_management", "zookeper_id_header", "zookeper_id"));
        if (headerName != null)
            headerName.setText(stringsManager.getString("data_management", "name_zookeper_header", "name_zookeper"));
        if (headerAddress != null)
            headerAddress.setText(
                    stringsManager.getString("data_management", "address_zookeper_header", "address_zookeper"));

        if (botonEspecies != null) {
            botonEspecies.setText(stringsManager.getString("data_management", "species_button", "Species"));
        }
        if (botonRecintos != null) {
            botonRecintos.setText(stringsManager.getString("data_management", "enclosure_button", "Enclosure"));
        }
        if (botonCuidadores != null) {
            botonCuidadores.setText(stringsManager.getString("data_management", "zookeepers_button", "Zookeepers"));
        }
        if (botonAnimales != null) {
            botonAnimales.setText(stringsManager.getString("data_management", "animals_button", "Animals"));
        }
        if (botonTraslados != null) {
            botonTraslados.setText(stringsManager.getString("data_management", "transfer_button", "Transfer"));
        }
        if (botonEspeciesRecintos != null) {
            botonEspeciesRecintos.setText(
                    stringsManager.getString("data_management", "species_enclosure_button", "Species_Enclosure"));
        }
        if (botonNew != null) {
            botonNew.setText(stringsManager.getString("data_management", "new_button", "New"));
        }
        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("data_management", "menu_button", "Main menu"));
        }

        // Mock Data Rows
        TextView row1Id = findViewById(R.id.row1Id);
        TextView row1Name = findViewById(R.id.row1Name);
        TextView row1Address = findViewById(R.id.row1Address);

        TextView row2Id = findViewById(R.id.row2Id);
        TextView row2Name = findViewById(R.id.row2Name);
        TextView row2Address = findViewById(R.id.row2Address);

        TextView row3Id = findViewById(R.id.row3Id);
        TextView row3Name = findViewById(R.id.row3Name);
        TextView row3Address = findViewById(R.id.row3Address);

        TextView row4Id = findViewById(R.id.row4Id);
        TextView row4Name = findViewById(R.id.row4Name);
        TextView row4Address = findViewById(R.id.row4Address);

        TextView row5Id = findViewById(R.id.row5Id);
        TextView row5Name = findViewById(R.id.row5Name);
        TextView row5Address = findViewById(R.id.row5Address);

        TextView row6Id = findViewById(R.id.row6Id);
        TextView row6Name = findViewById(R.id.row6Name);
        TextView row6Address = findViewById(R.id.row6Address);

        if (row1Id != null)
            row1Id.setText(stringsManager.getString("mock_data", "row1_id", "1"));
        if (row1Name != null)
            row1Name.setText(stringsManager.getString("mock_data", "row1_name", "Alex Johnson"));
        if (row1Address != null)
            row1Address.setText(stringsManager.getString("mock_data", "row1_address", "123 Main St"));

        if (row2Id != null)
            row2Id.setText(stringsManager.getString("mock_data", "row2_id", "2"));
        if (row2Name != null)
            row2Name.setText(stringsManager.getString("mock_data", "row2_name", "Brenda Lee"));
        if (row2Address != null)
            row2Address.setText(stringsManager.getString("mock_data", "row2_address", "456 Oak Ave"));

        if (row3Id != null)
            row3Id.setText(stringsManager.getString("mock_data", "row3_id", "3"));
        if (row3Name != null)
            row3Name.setText(stringsManager.getString("mock_data", "row3_name", "Chris Evans"));
        if (row3Address != null)
            row3Address.setText(stringsManager.getString("mock_data", "row3_address", "789 Pine Ln"));

        if (row4Id != null)
            row4Id.setText(stringsManager.getString("mock_data", "row4_id", "4"));
        if (row4Name != null)
            row4Name.setText(stringsManager.getString("mock_data", "row4_name", "Diana Ross"));
        if (row4Address != null)
            row4Address.setText(stringsManager.getString("mock_data", "row4_address", "101 Cedar Rd"));

        if (row5Id != null)
            row5Id.setText(stringsManager.getString("mock_data", "row5_id", "5"));
        if (row5Name != null)
            row5Name.setText(stringsManager.getString("mock_data", "row5_name", "Ethan Hunt"));
        if (row5Address != null)
            row5Address.setText(stringsManager.getString("mock_data", "row5_address", "202 Birch Blvd"));

        if (row6Id != null)
            row6Id.setText(stringsManager.getString("mock_data", "row6_id", "6"));
        if (row6Name != null)
            row6Name.setText(stringsManager.getString("mock_data", "row6_name", "Fiona Glenn"));
        if (row6Address != null)
            row6Address.setText(stringsManager.getString("mock_data", "row6_address", "303 Willow Wy"));
    }
}
