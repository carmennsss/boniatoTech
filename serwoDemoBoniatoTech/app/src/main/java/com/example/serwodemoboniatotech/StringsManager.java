package com.example.serwodemoboniatotech;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.Map;

/**
 * StringsManager - Para gestionar strings desde Firebase Firestore
 */
public class StringsManager {
    private static final String TAG = "StringsManager";
    private static final String PREFS_NAME = "FirebaseStringsCache";
    private static final String COLLECTION_NAME = "app_strings";

    private static StringsManager instance;
    private Context context;
    private FirebaseFirestore db;
    private SharedPreferences prefs;

    // Cache en memoria: category -> (key -> value)
    private Map<String, Map<String, String>> stringsCache;

    // Listeners para notificar cambios
    private Map<String, ListenerRegistration> firestoreListeners;
    private OnStringsLoadedListener loadedListener;

    // Estado de carga
    private boolean isLoaded = false;
    private boolean isLoading = false;

    /**
     * Interface para notificar cuando los strings están cargados
     */
    public interface OnStringsLoadedListener {
        void onStringsLoaded();

        void onStringsError(Exception e);
    }

    /**
     * Constructor privado (Singleton)
     */
    private StringsManager(Context context) {
        this.context = context.getApplicationContext();
        this.db = FirebaseFirestore.getInstance();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.stringsCache = new HashMap<>();
        this.firestoreListeners = new HashMap<>();

        // Cargar desde caché local inmediatamente
        loadFromLocalCache();

        // Siempre configurar listeners para asegurar datos actualizados
        setupRealtimeListeners();
    }

    /**
     * Obtener instancia del singleton
     */
    public static synchronized StringsManager getInstance(Context context) {
        if (instance == null) {
            instance = new StringsManager(context);
        }
        return instance;
    }

    /**
     * Cargar strings desde Firestore
     */
    public void loadFromFirestore(OnStringsLoadedListener listener) {
        if (isLoading) {
            Log.d(TAG, "Already loading strings...");
            return;
        }

        this.loadedListener = listener;
        isLoading = true;

        Log.d(TAG, "Loading strings from Firestore...");

        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    int documentsLoaded = 0;

                    querySnapshot.forEach(document -> {
                        String category = document.getId();
                        Map<String, Object> data = document.getData();

                        Map<String, String> categoryStrings = new HashMap<>();
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            categoryStrings.put(entry.getKey(), String.valueOf(entry.getValue()));
                        }

                        stringsCache.put(category, categoryStrings);
                        saveToLocalCache(category, categoryStrings);
                    });

                    documentsLoaded = querySnapshot.size();
                    isLoaded = true;
                    isLoading = false;

                    Log.d(TAG, "Successfully loaded " + documentsLoaded + " string documents from Firestore");

                    if (loadedListener != null) {
                        loadedListener.onStringsLoaded();
                    }

                    // Configurar listeners para actualizaciones en tiempo real
                    setupRealtimeListeners();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading strings from Firestore", e);
                    isLoading = false;

                    // Si hay strings en caché local, usar esos
                    if (!stringsCache.isEmpty()) {
                        Log.d(TAG, "Using local cache due to Firestore error");
                        isLoaded = true;
                        if (loadedListener != null) {
                            loadedListener.onStringsLoaded();
                        }
                    } else {
                        if (loadedListener != null) {
                            loadedListener.onStringsError(e);
                        }
                    }
                });
    }

    /**
     * Cargar strings desde caché local (SharedPreferences)
     */
    private void loadFromLocalCache() {
        Map<String, ?> allPrefs = prefs.getAll();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("category_")) {
                String category = key.substring(9); // Remover "category_"
                String json = (String) entry.getValue();

                try {
                    Map<String, String> categoryStrings = parseJsonToMap(json);
                    stringsCache.put(category, categoryStrings);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing cached strings for category: " + category, e);
                }
            }
        }

        if (!stringsCache.isEmpty()) {
            Log.d(TAG, "Loaded " + stringsCache.size() + " categories from local cache");
            isLoaded = true;
        }
    }

    /**
     * Guardar categoría en caché local
     */
    private void saveToLocalCache(String category, Map<String, String> strings) {
        String json = mapToJson(strings);
        prefs.edit()
                .putString("category_" + category, json)
                .apply();
    }

    /**
     * Configurar listeners en tiempo real para actualizaciones
     */
    private boolean isListening = false;

    /**
     * Configurar listeners en tiempo real para actualizaciones
     */
    private void setupRealtimeListeners() {
        if (isListening)
            return;
        isListening = true;

        db.collection(COLLECTION_NAME)
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Log.e(TAG, "Error listening to Firestore changes", error);
                        return;
                    }

                    if (querySnapshot != null) {
                        querySnapshot.getDocumentChanges().forEach(change -> {
                            String category = change.getDocument().getId();
                            Map<String, Object> data = change.getDocument().getData();

                            Map<String, String> categoryStrings = new HashMap<>();
                            for (Map.Entry<String, Object> entry : data.entrySet()) {
                                categoryStrings.put(entry.getKey(), String.valueOf(entry.getValue()));
                            }

                            stringsCache.put(category, categoryStrings);
                            saveToLocalCache(category, categoryStrings);

                            Log.d(TAG, "Updated category: " + category);
                        });
                    }
                });
    }

    /**
     * Obtener un string por categoría y clave
     * 
     * @param category Categoría (ej: "login", "main_screen")
     * @param key      Clave del string (ej: "blank_fields", "user_label")
     * @return El string o un fallback si no existe
     */
    public String getString(String category, String key) {
        if (stringsCache.containsKey(category)) {
            Map<String, String> categoryStrings = stringsCache.get(category);
            if (categoryStrings != null && categoryStrings.containsKey(key)) {
                return categoryStrings.get(key);
            }
        }

        // Fallback: retornar una clave descriptiva
        String fallback = "[" + category + "." + key + "]";
        Log.w(TAG, "String not found: " + fallback);
        return fallback;
    }

    /**
     * Obtener un string con fallback personalizado
     */
    public String getString(String category, String key, String fallback) {
        if (stringsCache.containsKey(category)) {
            Map<String, String> categoryStrings = stringsCache.get(category);
            if (categoryStrings != null && categoryStrings.containsKey(key)) {
                return categoryStrings.get(key);
            }
        }
        return fallback;
    }

    /**
     * Verificar si los strings están cargados
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Limpiar caché (útil para testing)
     */
    public void clearCache() {
        stringsCache.clear();
        prefs.edit().clear().apply();
        isLoaded = false;
    }

    // Helper methods para JSON simple (sin librería externa)

    private String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first)
                json.append(",");
            json.append("\"").append(escapeJson(entry.getKey())).append("\":");
            json.append("\"").append(escapeJson(entry.getValue())).append("\"");
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    private Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();

        // Parser JSON simple (solo para nuestro caso específico)
        json = json.trim();
        if (json.startsWith("{"))
            json = json.substring(1);
        if (json.endsWith("}"))
            json = json.substring(0, json.length() - 1);

        String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("^\"|\"$", "");
                String value = keyValue[1].trim().replaceAll("^\"|\"$", "");
                map.put(unescapeJson(key), unescapeJson(value));
            }
        }

        return map;
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String unescapeJson(String s) {
        return s.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }
}
