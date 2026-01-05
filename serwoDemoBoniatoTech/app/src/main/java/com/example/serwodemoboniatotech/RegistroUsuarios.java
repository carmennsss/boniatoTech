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

public class RegistroUsuarios extends AppCompatActivity {
    Button botonMenu, botonBorrar, botonRegistrar;
    ImageButton botonInfo;
    TextView tituloRegistro, labelName, labelAddress, labelAddressKey, labelPassword, labelConfirmPassword;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RegistroUsuarios), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stringsManager = StringsManager.getInstance(this);

        // Si los strings no están cargados, cargarlos desde Firestore
        if (!stringsManager.isLoaded()) {
            stringsManager.loadFromFirestore(new StringsManager.OnStringsLoadedListener() {
                @Override
                public void onStringsLoaded() {
                    Log.d("RegistroUsuarios", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("RegistroUsuarios", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroUsuarios.this);
            builder.setMessage(stringsManager.getString("register_users", "info_message"));
            builder.setPositiveButton(stringsManager.getString("register_users", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUsuarios.this, PantallaAdministracion.class);
            startActivity(intent);
        });

        botonBorrar = findViewById(R.id.botonBorrar);
        botonBorrar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUsuarios.this, BorrarUsuario.class);
            startActivity(intent);
        });

        botonRegistrar = findViewById(R.id.botonRegistrar);
        tituloRegistro = findViewById(R.id.tituloRegistro);
        labelName = findViewById(R.id.labelName);
        labelAddress = findViewById(R.id.labelAddress);
        labelAddressKey = findViewById(R.id.labelAddressKey);
        labelPassword = findViewById(R.id.labelPassword);
        labelConfirmPassword = findViewById(R.id.labelConfirmPassword);

        updateUI();
    }

    private void updateUI() {
        if (tituloRegistro != null) {
            tituloRegistro.setText(stringsManager.getString("register_users", "title", "Register user"));
        }
        if (labelName != null) {
            labelName.setText(stringsManager.getString("register_users", "name_label", "Name:"));
        }
        if (labelAddress != null) {
            labelAddress.setText(stringsManager.getString("register_users", "address_label", "Address:"));
        }
        if (labelAddressKey != null) {
            labelAddressKey.setText(stringsManager.getString("register_users", "address_key_label", "Address key:"));
        }
        if (labelPassword != null) {
            labelPassword.setText(stringsManager.getString("register_users", "password_label", "Password:"));
        }
        if (labelConfirmPassword != null) {
            labelConfirmPassword
                    .setText(stringsManager.getString("register_users", "confirm_password_label", "Confirm password:"));
        }
        if (botonRegistrar != null) {
            botonRegistrar.setText(stringsManager.getString("register_users", "register_button", "Register user"));
        }
        if (botonBorrar != null) {
            botonBorrar.setText(stringsManager.getString("register_users", "delete_button", "Delete"));
        }
        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("common_buttons", "back_button", "Back"));
        }
    }
}