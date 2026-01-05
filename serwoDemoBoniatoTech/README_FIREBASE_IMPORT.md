# Script de Importación de Strings a Firebase Firestore

Este script automatiza la importación de todos los strings extraídos del proyecto Android a Firebase Firestore.

## 📋 Requisitos Previos

1. **Node.js** instalado (v14 o superior)
2. **Cuenta de Firebase** con un proyecto creado
3. **Firestore** habilitado en tu proyecto de Firebase
4. **Archivo de credenciales** de servicio de Firebase

---

## 🔑 Paso 1: Obtener las Credenciales de Firebase

1. Ve a **Firebase Console**: https://console.firebase.google.com
2. Selecciona tu proyecto
3. Haz clic en el ícono de configuración ⚙️ y selecciona **"Project settings"**
4. Ve a la pestaña **"Service accounts"**
5. Haz clic en **"Generate new private key"**
6. Se descargará un archivo JSON

7. **Renombra** el archivo descargado a `serviceAccountKey.json`
8. **Copia** el archivo a la carpeta raíz del proyecto:
   ```
   c:\Users\olgap\AndroidStudioProjects\serwoDemoBoniatoTech\
   ```

> [!WARNING] > **¡NUNCA subas el archivo `serviceAccountKey.json` a Git!** Este archivo contiene credenciales secretas.

---

## 📦 Paso 2: Instalar Dependencias

Abre una terminal en la carpeta del proyecto y ejecuta:

```bash
cd c:\Users\olgap\AndroidStudioProjects\serwoDemoBoniatoTech
npm install
```

Esto instalará Firebase Admin SDK y otras dependencias necesarias.

---

## 🚀 Paso 3: Ejecutar el Script

Una vez que tengas el archivo `serviceAccountKey.json` en su lugar, ejecuta:

```bash
node firebase_insert_strings.js
```

O usa el script npm:

```bash
npm run insert
```

---

## 📊 Qué Hace el Script

El script realiza las siguientes operaciones:

1. ✅ Verifica que existe el archivo `serviceAccountKey.json`
2. ✅ Inicializa Firebase Admin SDK
3. ✅ Lee el archivo `firebase_strings.json` con todos los strings
4. ✅ Crea/actualiza la colección `app_strings` en Firestore
5. ✅ Importa 12 documentos usando operaciones batch:
   - `general`
   - `login`
   - `main_screen`
   - `administration`
   - `register_users`
   - `roles`
   - `data_management`
   - `file_manager`
   - `email`
   - `whitelist`
   - `delete_user`
   - `logs`
   - `common_buttons`
6. ✅ Verifica que la importación fue exitosa
7. ✅ Muestra un resumen de los documentos creados

---

## 📁 Estructura Resultante en Firestore

```
app_strings (colección)
├── general (documento)
│   ├── app_name: "serwoDemoBoniatoTech"
│   └── app_title: "Serwo Manager"
├── login (documento)
│   ├── blank_fields: "There are blank fields"
│   ├── user_not_exists: "The user doesn't exists"
│   ├── incorrect_password: "Incorrect password"
│   └── ... (más campos)
├── main_screen (documento)
│   └── ... (campos)
└── ... (más documentos)
```

---

## 🔧 Solución de Problemas

### Error: "Cannot find module 'serviceAccountKey.json'"

**Solución**: Asegúrate de que el archivo `serviceAccountKey.json` está en la carpeta raíz del proyecto y tiene exactamente ese nombre.

### Error: "PERMISSION_DENIED"

**Solución**:

- Verifica que Firestore está habilitado en tu proyecto de Firebase
- Asegúrate de que las reglas de seguridad permiten escritura (temporalmente puedes usar reglas de prueba)
- Confirma que la cuenta de servicio tiene permisos

### Error: "Cannot find module 'firebase_strings.json'"

**Solución**: El archivo debería haberse copiado automáticamente. Si no está, cópialo manualmente desde:

```
C:\Users\olgap\.gemini\antigravity\brain\1dea55ad-223f-4aa8-a201-534060d8faaa\firebase_strings.json
```

---

## 📝 Notas Importantes

- El script usa operaciones `set()` con `{ merge: true }`, lo que significa que:
  - Si los documentos ya existen, se actualizarán
  - No se eliminarán campos existentes que no estén en el JSON
- Los strings están en inglés. Puedes crear documentos adicionales para otros idiomas siguiendo la misma estructura.

- **Total de strings importados**: 135+ campos distribuidos en 12 documentos

---

## 🎯 Próximo Paso: Integrar con Android

Después de ejecutar este script exitosamente, el siguiente paso es modificar tu aplicación Android para leer estos strings desde Firestore en lugar de tenerlos hardcodeados.

Esto requerirá:

1. Crear una clase `StringsManager` en Android
2. Implementar caché local para los strings
3. Actualizar todas las Activities para usar el StringsManager
4. Actualizar los layouts XML para usar referencias dinámicas

---

¿Necesitas ayuda con alguno de estos pasos? ¡Avísame!
