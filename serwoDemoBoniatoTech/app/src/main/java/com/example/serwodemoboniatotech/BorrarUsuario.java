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

public class BorrarUsuario extends AppCompatActivity {
    Button botonMenu, botonDelete;
    ImageButton botonInfo;
    TextView tituloDelete, userHeader, emailHeader;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.borrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.BorrarUsuario), (v, insets) -> {
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
                    Log.d("BorrarUsuario", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("BorrarUsuario", "Error loading strings", e);
                }
            });
        }

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(BorrarUsuario.this, RegistroUsuarios.class);
            startActivity(intent);
        });
        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(BorrarUsuario.this);
            builder.setMessage(stringsManager.getString("delete_user", "info_message"));
            builder.setPositiveButton(stringsManager.getString("delete_user", "understood", "Understood"), null);
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
        if (tituloDelete == null) {
            tituloDelete = findViewById(R.id.tituloDelete);
            userHeader = findViewById(R.id.userHeader);
            emailHeader = findViewById(R.id.emailHeader);
            botonDelete = findViewById(R.id.botonDelete);
        }

        if (tituloDelete != null) {
            tituloDelete.setText(stringsManager.getString("delete_user", "title", "Delete Users"));
        }
        if (userHeader != null)
            userHeader.setText(stringsManager.getString("delete_user", "user_header", "User"));
        if (emailHeader != null)
            emailHeader.setText(stringsManager.getString("delete_user", "email_header", "Email"));
        if (botonDelete != null)
            botonDelete.setText(stringsManager.getString("common_buttons", "delete", "Delete"));

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("delete_user", "back_button", "Back to register user"));
        }

        // Mock Data Rows
        TextView row1User = findViewById(R.id.row1User);
        TextView row1Email = findViewById(R.id.row1Email);

        TextView row2User = findViewById(R.id.row2User);
        TextView row2Email = findViewById(R.id.row2Email);

        TextView row3User = findViewById(R.id.row3User);
        TextView row3Email = findViewById(R.id.row3Email);

        if (row1User != null)
            row1User.setText(stringsManager.getString("mock_delete_user", "row1_user", "admin"));
        if (row1Email != null)
            row1Email.setText(stringsManager.getString("mock_delete_user", "row1_email", "admin@admin.com"));

        if (row2User != null)
            row2User.setText(stringsManager.getString("mock_delete_user", "row2_user", "boni"));
        if (row2Email != null)
            row2Email.setText(stringsManager.getString("mock_delete_user", "row2_email", "boni@boni.com"));

        if (row3User != null)
            row3User.setText(stringsManager.getString("mock_delete_user", "row3_user", "probador"));
        if (row3Email != null)
            row3Email.setText(stringsManager.getString("mock_delete_user", "row3_email", "pablo..."));
    }
}