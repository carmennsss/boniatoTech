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

public class AsignarRol extends AppCompatActivity {
    Button botonMenu, botonAsignar, botonBorrarRol;
    ImageButton botonInfo;
    TextView tituloAsignarRol, userHeader, addressHeader, rolesHeader, labelRole;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.asignar_rol);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AsignarRol), (v, insets) -> {
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
                    Log.d("AsignarRol", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("AsignarRol", "Error loading strings", e);
                }
            });
        }

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(AsignarRol.this, PantallaAdministracion.class);
            startActivity(intent);
        });
        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AsignarRol.this);
            builder.setMessage(stringsManager.getString("roles", "assign_info"));
            builder.setPositiveButton(stringsManager.getString("roles", "understood", "Understood"), null);
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
        if (tituloAsignarRol == null) {
            tituloAsignarRol = findViewById(R.id.tituloAsignarRol);
            userHeader = findViewById(R.id.userHeader);
            addressHeader = findViewById(R.id.addressHeader);
            rolesHeader = findViewById(R.id.rolesHeader);
            labelRole = findViewById(R.id.labelRole);
            botonAsignar = findViewById(R.id.botonAsignar);
            botonBorrarRol = findViewById(R.id.botonBorrarRol);
        }

        if (tituloAsignarRol != null) {
            tituloAsignarRol.setText(stringsManager.getString("roles", "assign_title", "Assign roles to users"));
        }
        if (userHeader != null)
            userHeader.setText(stringsManager.getString("roles", "user_header", "User"));
        if (addressHeader != null)
            addressHeader.setText(stringsManager.getString("roles", "address_header", "Address"));
        if (rolesHeader != null)
            rolesHeader.setText(stringsManager.getString("roles", "roles_header", "Roles"));
        if (labelRole != null)
            labelRole.setText(stringsManager.getString("roles", "role_label", "Role:"));

        if (botonAsignar != null)
            botonAsignar.setText(stringsManager.getString("roles", "assign_button", "Assign"));
        if (botonBorrarRol != null)
            botonBorrarRol.setText(stringsManager.getString("common_buttons", "delete", "Delete"));

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("roles", "back_button", "Back"));
        }

        // Mock Data Rows
        TextView row1User = findViewById(R.id.row1User);
        TextView row1Address = findViewById(R.id.row1Address);
        TextView row1Role = findViewById(R.id.row1Role);

        TextView row2User = findViewById(R.id.row2User);
        TextView row2Address = findViewById(R.id.row2Address);
        TextView row2Role = findViewById(R.id.row2Role);

        TextView row3User = findViewById(R.id.row3User);
        TextView row3Address = findViewById(R.id.row3Address);
        TextView row3Role = findViewById(R.id.row3Role);

        if (row1User != null)
            row1User.setText(stringsManager.getString("mock_assign_role", "row1_user", "boni"));
        if (row1Address != null)
            row1Address.setText(stringsManager.getString("mock_assign_role", "row1_address", "boni@boni.com"));
        if (row1Role != null)
            row1Role.setText(stringsManager.getString("mock_assign_role", "row1_role", "Reader"));

        if (row2User != null)
            row2User.setText(stringsManager.getString("mock_assign_role", "row2_user", "boniato1"));
        if (row2Address != null)
            row2Address.setText(stringsManager.getString("mock_assign_role", "row2_address", "boniato@boniato.com"));
        if (row2Role != null)
            row2Role.setText(stringsManager.getString("mock_assign_role", "row2_role", "It doesn't have roles"));

        if (row3User != null)
            row3User.setText(stringsManager.getString("mock_assign_role", "row3_user", "Boniato2"));
        if (row3Address != null)
            row3Address.setText(stringsManager.getString("mock_assign_role", "row3_address", "boniato2@boniato2.com"));
        if (row3Role != null)
            row3Role.setText(stringsManager.getString("mock_assign_role", "row3_role", "Reader"));
    }
}