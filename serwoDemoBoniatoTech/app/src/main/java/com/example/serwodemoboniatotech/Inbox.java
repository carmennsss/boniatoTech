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

public class Inbox extends AppCompatActivity {
    Button botonMenu, botonRefresh, botonCompose;
    ImageButton botonInfo;
    TextView tituloInbox, subjectHeader, fromHeader, dateHeader;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Inbox), (v, insets) -> {
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
                    Log.d("Inbox", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("Inbox", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Inbox.this);
            builder.setMessage(stringsManager.getString("email", "inbox_info"));
            builder.setPositiveButton(stringsManager.getString("email", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonMenu = findViewById(R.id.botonInicio);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(Inbox.this, PantallaPrincipal.class);
            startActivity(intent);
        });

        botonCompose = findViewById(R.id.botonCompose);
        botonCompose.setOnClickListener(v -> {
            Intent intent = new Intent(Inbox.this, EnvioCorreos.class);
            startActivity(intent);
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (tituloInbox == null) {
            tituloInbox = findViewById(R.id.tituloInbox);
            subjectHeader = findViewById(R.id.subjectHeader);
            fromHeader = findViewById(R.id.fromHeader);
            dateHeader = findViewById(R.id.dateHeader);
            botonRefresh = findViewById(R.id.botonRefresh);
        }

        if (tituloInbox != null) {
            String userEmail = "pablo.pruebas.mail@gmail.com "; // Placeholder/Real logic usually fetches user. Keeping
                                                                // simple.
            tituloInbox.setText(userEmail + stringsManager.getString("email", "inbox_title", "Inbox"));
        }
        if (subjectHeader != null)
            subjectHeader.setText(stringsManager.getString("email", "subject_header", "Subject"));
        if (fromHeader != null)
            fromHeader.setText(stringsManager.getString("email", "from_header", "From"));
        if (dateHeader != null)
            dateHeader.setText(stringsManager.getString("email", "date_header", "Date"));

        if (botonMenu != null)
            botonMenu.setText(stringsManager.getString("email", "back_button", "Back to Inbox"));
        if (botonRefresh != null)
            botonRefresh.setText(stringsManager.getString("email", "refresh_button", "Refresh"));
        botonCompose.setText(stringsManager.getString("email", "compose_button", "Compose"));

        // Mock Data Rows
        TextView row1Subject = findViewById(R.id.row1Subject);
        TextView row1From = findViewById(R.id.row1From);
        TextView row1Date = findViewById(R.id.row1Date);

        TextView row2Subject = findViewById(R.id.row2Subject);
        TextView row2From = findViewById(R.id.row2From);
        TextView row2Date = findViewById(R.id.row2Date);

        if (row1Subject != null)
            row1Subject.setText(stringsManager.getString("mock_inbox", "row1_subject", "hola miguel"));
        if (row1From != null)
            row1From.setText(stringsManager.getString("mock_inbox", "row1_from", "pablito..."));
        if (row1Date != null)
            row1Date.setText(stringsManager.getString("mock_inbox", "row1_date", "2025-12-18"));

        if (row2Subject != null)
            row2Subject.setText(stringsManager.getString("mock_inbox", "row2_subject", "Reunion..."));
        if (row2From != null)
            row2From.setText(stringsManager.getString("mock_inbox", "row2_from", "admin@admin"));
        if (row2Date != null)
            row2Date.setText(stringsManager.getString("mock_inbox", "row2_date", "2025-12-18"));
    }
}
