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

public class GestionRoles extends AppCompatActivity {
    Button botonMenu, botonAddRole;
    ImageButton botonInfo;
    TextView tituloRoles, roleHeader, descHeader, deleteHeader, writingHeader, readingHeader, editingHeader;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gestion_roles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.GestionRoles), (v, insets) -> {
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
                    Log.d("GestionRoles", "Strings loaded successfully");
                    updateUI();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("GestionRoles", "Error loading strings", e);
                }
            });
        }

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(GestionRoles.this);
            builder.setMessage(stringsManager.getString("roles", "manage_info"));
            builder.setPositiveButton(stringsManager.getString("roles", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        botonMenu = findViewById(R.id.botonInicio);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(GestionRoles.this, PantallaAdministracion.class);
            startActivity(intent);
        });

        // Actualizar textos si los strings ya están cargados
        updateUI();
    }

    /**
     * Actualizar los textos desde Firebase
     */
    private void updateUI() {
        if (tituloRoles == null) {
            tituloRoles = findViewById(R.id.tituloRoles); // Note: XML ID is actually null in original? No, it inherited
                                                          // from previous task. Wait, in gestion_roles.xml I just
                                                          // removed text from a TextView appearing at top. It usually
                                                          // has ID... In XML line 15: <TextView ... > No ID visible in
                                                          // snippet 15-25! I missed checking for ID in XML.
            // CAUTION: The XML snippet showed <TextView ... android:text="File repository"
            // ... > without ID. I removed text. But if it has no ID, findBy won't work.
            // I should have added an ID in XML. Let me check the snippet again.
            // Line 15-25: No android:id.
            // I need to add android:id="@+id/tituloRoles" to the first TextView in XML.
        }
        // Assuming I fix XML ID in next step or concurrent.

        if (tituloRoles == null)
            tituloRoles = findViewById(R.id.tituloRoles);
        if (roleHeader == null)
            roleHeader = findViewById(R.id.roleHeader);
        if (descHeader == null)
            descHeader = findViewById(R.id.descHeader);
        if (deleteHeader == null)
            deleteHeader = findViewById(R.id.deleteHeader);
        if (writingHeader == null)
            writingHeader = findViewById(R.id.writingHeader);
        if (readingHeader == null)
            readingHeader = findViewById(R.id.readingHeader);
        if (editingHeader == null)
            editingHeader = findViewById(R.id.editingHeader);
        if (botonAddRole == null)
            botonAddRole = findViewById(R.id.botonAddRole);

        if (tituloRoles != null)
            tituloRoles.setText(stringsManager.getString("roles", "title", "Role Management"));
        if (roleHeader != null)
            roleHeader.setText(stringsManager.getString("roles", "role_header", "Role"));
        if (descHeader != null)
            descHeader.setText(stringsManager.getString("roles", "description_header", "Description"));
        if (deleteHeader != null)
            deleteHeader.setText(stringsManager.getString("roles", "perm_delete", "DELETE"));
        if (writingHeader != null)
            writingHeader.setText(stringsManager.getString("roles", "perm_writing", "WRITING"));
        if (readingHeader != null)
            readingHeader.setText(stringsManager.getString("roles", "perm_reading", "READING"));
        if (editingHeader != null)
            editingHeader.setText(stringsManager.getString("roles", "perm_editing", "EDITING"));

        if (botonAddRole != null)
            botonAddRole.setText(stringsManager.getString("roles", "add_role_button", "Add role"));

        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("roles", "menu_button", "Back"));
        }

        // Mock Data Rows
        TextView row1Role = findViewById(R.id.row1Role);
        TextView row1Desc = findViewById(R.id.row1Desc);

        TextView row2Role = findViewById(R.id.row2Role);
        TextView row2Desc = findViewById(R.id.row2Desc);

        TextView row3Role = findViewById(R.id.row3Role);
        TextView row3Desc = findViewById(R.id.row3Desc);

        if (row1Role != null)
            row1Role.setText(stringsManager.getString("mock_roles", "row1_role", "Owner"));
        if (row1Desc != null)
            row1Desc.setText(stringsManager.getString("mock_roles", "row1_desc", "Can do anything..."));

        if (row2Role != null)
            row2Role.setText(stringsManager.getString("mock_roles", "row2_role", "Admin"));
        if (row2Desc != null)
            row2Desc.setText(stringsManager.getString("mock_roles", "row2_desc", "Can do anything..."));

        if (row3Role != null)
            row3Role.setText(stringsManager.getString("mock_roles", "row3_role", "Client"));
        if (row3Desc != null)
            row3Desc.setText(stringsManager.getString("mock_roles", "row3_desc", "Must be informed"));
    }
}