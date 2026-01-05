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

public class PantallaAdministracion extends AppCompatActivity {
    Button botonMenu, botonAdminUsuario, botonAdminRol, botonAsignarRol, botonWhiteList, botonLogs;
    ImageButton botonInfo;
    TextView tituloAdministracion;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_administracion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.PantallaAdministracion), (v, insets) -> {
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
                    Log.d("PantallaAdministracion", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("PantallaAdministracion", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PantallaAdministracion.this);
            builder.setMessage(stringsManager.getString("administration", "info_message"));
            builder.setPositiveButton(stringsManager.getString("administration", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        botonMenu = findViewById(R.id.botonInicio);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, PantallaPrincipal.class);
            startActivity(intent);
        });

        botonAdminUsuario = findViewById(R.id.botonAdminUsuario);
        botonAdminUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, RegistroUsuarios.class);
            startActivity(intent);
        });

        botonAdminRol = findViewById(R.id.botonGestionRoles);
        botonAdminRol.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, GestionRoles.class);
            startActivity(intent);
        });

        botonAsignarRol = findViewById(R.id.AsignarRol);
        botonAsignarRol.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, AsignarRol.class);
            startActivity(intent);
        });

        botonWhiteList = findViewById(R.id.botonAdministrarWhiteList);
        botonWhiteList.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, GestionWhiteList.class);
            startActivity(intent);
        });

        botonLogs = findViewById(R.id.botonLogs);
        botonLogs.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaAdministracion.this, SistemaLogs.class);
            startActivity(intent);
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (tituloAdministracion == null) {
            tituloAdministracion = findViewById(R.id.tituloAdministracion);
        }

        if (tituloAdministracion != null) {
            tituloAdministracion.setText(stringsManager.getString("administration", "title", "Administration"));
        }
        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("administration", "menu_button", "Main menu"));
        }
        if (botonAdminUsuario != null) {
            botonAdminUsuario
                    .setText(stringsManager.getString("administration", "user_admin_button", "User Administration"));
        }
        if (botonAdminRol != null) {
            botonAdminRol
                    .setText(stringsManager.getString("administration", "role_management_button", "Role Management"));
        }
        if (botonAsignarRol != null) {
            botonAsignarRol.setText(stringsManager.getString("administration", "assign_role_button", "Assign Roles"));
        }
        if (botonWhiteList != null) {
            botonWhiteList
                    .setText(stringsManager.getString("administration", "whitelist_button", "Whitelist Management"));
        }
        if (botonLogs != null) {
            botonLogs.setText(stringsManager.getString("administration", "logs_button", "System Logs"));
        }
    }
}