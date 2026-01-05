package com.example.serwodemoboniatotech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnvioCorreos extends AppCompatActivity {
    Button botonMenu, botonAttach;
    ImageButton botonInfo;
    TextView labelFor, labelSubject, labelMessage, textMessageBody;
    EditText editFor, editSubject;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.envio_correos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.EnvioCorreos), (v, insets) -> {
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
                    Log.d("EnvioCorreos", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("EnvioCorreos", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EnvioCorreos.this);
            builder.setMessage(stringsManager.getString("email", "send_info"));
            builder.setPositiveButton(stringsManager.getString("email", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(EnvioCorreos.this, Inbox.class);
            startActivity(intent);
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (labelFor == null) {
            labelFor = findViewById(R.id.labelFor);
            editFor = findViewById(R.id.editFor);
            labelSubject = findViewById(R.id.labelSubject);
            editSubject = findViewById(R.id.editSubject);
            labelMessage = findViewById(R.id.labelMessage);
            textMessageBody = findViewById(R.id.textMessageBody);
            botonAttach = findViewById(R.id.botonAttach);
        }

        if (labelFor != null)
            labelFor.setText(stringsManager.getString("email", "for_label", "For:"));
        if (editFor != null)
            editFor.setHint(stringsManager.getString("email", "to_hint", "admin@admin.com"));
        if (labelSubject != null)
            labelSubject.setText(stringsManager.getString("email", "subject_label", "Subject:"));
        if (editSubject != null)
            editSubject.setHint(stringsManager.getString("email", "subject_hint", "Meeting with BoniatoTech"));
        if (labelMessage != null)
            labelMessage.setText(stringsManager.getString("email", "message_label", "Message:"));
        if (textMessageBody != null)
            textMessageBody.setText(
                    "I would like to appoint a meeting with the client.\n Please let me know when are you available.");

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("email", "send_button", "Send"));
        }
        if (botonAttach != null) {
            botonAttach.setText(stringsManager.getString("email", "attach_button", "Attach"));
        }
    }
}
