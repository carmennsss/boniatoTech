package com.example.serwodemoboniatotech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button botonLogin;
    ImageButton botonInfo, botonUsuariosCorreccion;
    EditText usuarioIntroducido, passwordIntroducida;
    TextView tituloApp, labelUsuario, labelPassword;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        stringsManager = StringsManager.getInstance(this);

        if (!stringsManager.isLoaded()) {
            stringsManager.loadFromFirestore(new StringsManager.OnStringsLoadedListener() {
                @Override
                public void onStringsLoaded() {
                    Log.d("MainActivity", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("MainActivity", "Error loading strings", e);
                    Toast.makeText(MainActivity.this, "Error loading strings, using defaults", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }

        botonUsuariosCorreccion = findViewById(R.id.botonUsuariosLogin);
        botonLogin = findViewById(R.id.botonLogin);
        botonInfo = findViewById(R.id.botonInfo);
        usuarioIntroducido = findViewById(R.id.editTextUser);
        passwordIntroducida = findViewById(R.id.editTextPassword);

        updateUI();

        botonLogin.setOnClickListener(v -> {
            if (usuarioIntroducido.getText().toString().equals("")
                    || passwordIntroducida.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(),
                        stringsManager.getString("login", "blank_fields", "There are blank fields"),
                        Toast.LENGTH_SHORT).show();
            } else {
                comprobarEmail(String.valueOf(usuarioIntroducido.getText()).trim());
            }
        });

        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(stringsManager.getString("login", "info_message"));
            builder.setPositiveButton(stringsManager.getString("login", "understood_button", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonUsuariosCorreccion.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(stringsManager.getString("login", "users_info"));
            builder.setPositiveButton(stringsManager.getString("login", "ok_button", "Ok"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void updateUI() {
        if (botonLogin != null) {
            botonLogin.setText(stringsManager.getString("login", "login_button", "Log in"));
        }

        if (tituloApp == null) {
            tituloApp = findViewById(R.id.tituloApp);
            labelUsuario = findViewById(R.id.labelUsuario);
            labelPassword = findViewById(R.id.labelPassword);
        }

        // Establecer textos desde Firebase
        if (tituloApp != null) {
            tituloApp.setText(stringsManager.getString("general", "app_title", "Serwo Manager"));
        }
        if (labelUsuario != null) {
            labelUsuario.setText(stringsManager.getString("login", "user_label", "User:"));
        }
        if (labelPassword != null) {
            labelPassword.setText(stringsManager.getString("login", "password_label", "Password:"));
        }
    }

    public void comprobarEmail(String usuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Usuario")
                .whereEqualTo("usuario", usuario)// optimización
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    stringsManager.getString("login", "user_not_exists", "The user doesn't exists"),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String password = document.getString("contraseña");
                            if (passwordIntroducida.getText().toString().equals(password)) {
                                String usuarioParaGuardar = usuario; // o el nombre de usuario
                                getSharedPreferences("PREFERENCIAS_APP", MODE_PRIVATE)
                                        .edit()
                                        .putString("usuario_identificado", usuarioParaGuardar)
                                        .apply();
                                Intent intent = new Intent(MainActivity.this, PantallaPrincipal.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        stringsManager.getString("login", "incorrect_password", "Incorrect password"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
