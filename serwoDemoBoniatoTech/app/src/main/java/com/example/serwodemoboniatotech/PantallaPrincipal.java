package com.example.serwodemoboniatotech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class PantallaPrincipal extends AppCompatActivity {
    Button botonLogout, botonArchivos, botonDatos, botonAdministrador, botonCorreos;
    ImageButton botonInfo;
    TextView tituloMain, subtituloMain;
    String usuarioActual;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pantallaPrincipal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stringsManager = StringsManager.getInstance(this);

        usuarioActual = getSharedPreferences("PREFERENCIAS_APP", MODE_PRIVATE)
                .getString("usuario_identificado", null);
        if (usuarioActual == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPrincipal.this);
            builder.setMessage(stringsManager.getString("main_screen", "info_message"));
            builder.setPositiveButton(stringsManager.getString("main_screen", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonLogout = findViewById(R.id.botonLogout);
        botonArchivos = findViewById(R.id.botonGestionArchivos);
        botonDatos = findViewById(R.id.gestionDatos);
        botonAdministrador = findViewById(R.id.administracion);
        botonCorreos = findViewById(R.id.botonCorreo);
        tituloMain = findViewById(R.id.tituloMain);
        subtituloMain = findViewById(R.id.subtituloMain);

        updateUI();

        botonLogout.setOnClickListener(v -> {
            getSharedPreferences("PREFERENCIAS_APP", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
            Intent intent = new Intent(PantallaPrincipal.this, MainActivity.class);
            startActivity(intent);
        });

        botonArchivos.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaPrincipal.this, FileManager.class);
            startActivity(intent);
        });

        botonDatos.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaPrincipal.this, GestionDatos.class);
            startActivity(intent);
        });

        botonAdministrador.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Usuario")
                    .whereEqualTo("usuario", usuarioActual)
                    .whereEqualTo("admin", true)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(getApplicationContext(),
                                        stringsManager.getString("main_screen", "no_admin_permits",
                                                "You don't have administrator permits"),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(PantallaPrincipal.this, PantallaAdministracion.class);
                                startActivity(intent);
                            }
                        }
                    });
        });

        botonCorreos.setOnClickListener(v -> {
            Intent intent = new Intent(PantallaPrincipal.this, Inbox.class);
            startActivity(intent);
        });
    }

    private void updateUI() {
        if (tituloMain != null) {
            tituloMain.setText(stringsManager.getString("main_screen", "title", "Main page"));
        }
        if (subtituloMain != null) {
            subtituloMain.setText(stringsManager.getString("main_screen", "subtitle", "Select an option"));
        }
        if (botonDatos != null) {
            botonDatos.setText(stringsManager.getString("main_screen", "manage_data_button", "Manage Data"));
        }
        if (botonArchivos != null) {
            botonArchivos.setText(stringsManager.getString("main_screen", "file_manager_button", "File Manager"));
        }
        if (botonCorreos != null) {
            botonCorreos.setText(stringsManager.getString("main_screen", "mail_button", "Mail Controller"));
        }
        if (botonAdministrador != null) {
            botonAdministrador
                    .setText(stringsManager.getString("main_screen", "administration_button", "Administration"));
        }
        if (botonLogout != null) {
            botonLogout.setText(stringsManager.getString("main_screen", "logout_button", "Log out"));
        }
    }
}