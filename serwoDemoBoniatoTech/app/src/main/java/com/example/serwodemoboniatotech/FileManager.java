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

public class FileManager extends AppCompatActivity {
    Button botonMenu, botonUpload, botonDownload, botonDelete, botonNewFolder, botonDeleteFolder, botonRename,
            botonAtras;
    ImageButton botonInfo, botonInfoArchivos;
    TextView tituloFileManager, subtituloFileManager, fileListHeader1, fileListHeader2, actionsLabel;
    StringsManager stringsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.file_manager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fileManager), (v, insets) -> {
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
                    Log.d("FileManager", "Strings loaded successfully");
                    updateButtonTexts();
                }

                @Override
                public void onStringsError(Exception e) {
                    Log.e("FileManager", "Error loading strings", e);
                }
            });
        }

        // Inicializar TextViews
        tituloFileManager = findViewById(R.id.tituloFileManager);
        subtituloFileManager = findViewById(R.id.subtituloFileManager);
        fileListHeader1 = findViewById(R.id.fileListHeader1);
        fileListHeader2 = findViewById(R.id.fileListHeader2);
        actionsLabel = findViewById(R.id.actionsLabel);

        botonMenu = findViewById(R.id.botonVueltaMenu);
        botonMenu.setOnClickListener(v -> {
            Intent intent = new Intent(FileManager.this, PantallaPrincipal.class);
            startActivity(intent);
        });

        botonAtras = findViewById(R.id.botonAtras);
        botonAtras.setOnClickListener(v -> {
            finish();
        });

        botonInfo = findViewById(R.id.botonInfo);
        botonInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "info_message"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonUpload = findViewById(R.id.botonUpload);
        botonUpload.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "upload_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonDownload = findViewById(R.id.botonDownload);
        botonDownload.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "download_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonDelete = findViewById(R.id.botonDelete);
        botonDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "delete_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonNewFolder = findViewById(R.id.botonNewFolder);
        botonNewFolder.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "new_folder_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonDeleteFolder = findViewById(R.id.botonDeleteFolder);
        botonDeleteFolder.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "delete_folder_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonInfoArchivos = findViewById(R.id.botonInfoArchivos);
        botonInfoArchivos.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "file_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        botonRename = findViewById(R.id.botonRename);
        botonRename.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FileManager.this);
            builder.setMessage(stringsManager.getString("file_manager", "rename_info"));
            builder.setPositiveButton(stringsManager.getString("file_manager", "understood", "Understood"), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // Actualizar textos de los botones si los strings ya están cargados
        updateButtonTexts();
    }

    /**
     * Actualizar los textos de los botones y labels desde Firebase
     */
    private void updateButtonTexts() {
        // TextViews
        if (tituloFileManager != null) {
            tituloFileManager.setText(stringsManager.getString("file_manager", "title", "File repository"));
        }
        if (subtituloFileManager != null) {
            subtituloFileManager.setText(
                    stringsManager.getString("file_manager", "subtitle", "Manage your server files efficiently"));
        }
        if (fileListHeader1 != null) {
            fileListHeader1
                    .setText(stringsManager.getString("file_manager", "file_list_header1", "drwxr-xr-x 1 ftp ftp"));
        }
        if (fileListHeader2 != null) {
            fileListHeader2
                    .setText(stringsManager.getString("file_manager", "file_list_header2", "0 Dec 15 10:29 hello"));
        }
        if (actionsLabel != null) {
            actionsLabel.setText(stringsManager.getString("file_manager", "actions_label", "Actions"));
        }

        // Buttons
        if (botonMenu != null) {
            botonMenu.setText(stringsManager.getString("file_manager", "menu_button", "Main menu"));
        }
        if (botonAtras != null) {
            botonAtras.setText(stringsManager.getString("file_manager", "back_button", "Back"));
        }
        if (botonUpload != null) {
            botonUpload.setText(stringsManager.getString("file_manager", "upload_button", "Upload"));
        }
        if (botonDownload != null) {
            botonDownload.setText(stringsManager.getString("file_manager", "download_button", "Download"));
        }
        if (botonDelete != null) {
            botonDelete.setText(stringsManager.getString("file_manager", "delete_button", "Delete"));
        }
        if (botonNewFolder != null) {
            botonNewFolder.setText(stringsManager.getString("file_manager", "new_folder_button", "New Folder"));
        }
        if (botonDeleteFolder != null) {
            botonDeleteFolder
                    .setText(stringsManager.getString("file_manager", "delete_folder_button", "Delete Folder"));
        }
        if (botonRename != null) {
            botonRename.setText(stringsManager.getString("file_manager", "rename_button", "Rename"));
        }
    }
}