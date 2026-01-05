const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');

// Colores para la consola
const colors = {
    reset: '\x1b[0m',
    green: '\x1b[32m',
    red: '\x1b[31m',
    yellow: '\x1b[33m',
    blue: '\x1b[34m',
    cyan: '\x1b[36m'
};

function log(message, color = colors.reset) {
    console.log(`${color}${message}${colors.reset}`);
}

async function main() {
    try {
        log('\n🚀 Iniciando importación de strings a Firebase Firestore...', colors.cyan);

        // 1. Verificar que existe el archivo de credenciales
        const serviceAccountPath = path.join(__dirname, 'serviceAccountKey.json');
        if (!fs.existsSync(serviceAccountPath)) {
            log('\n❌ ERROR: No se encontró el archivo serviceAccountKey.json', colors.red);
            log('\nPara obtener este archivo:', colors.yellow);
            log('  1. Ve a Firebase Console: https://console.firebase.google.com', colors.yellow);
            log('  2. Selecciona tu proyecto', colors.yellow);
            log('  3. Ve a Project Settings (⚙️) > Service Accounts', colors.yellow);
            log('  4. Haz clic en "Generate new private key"', colors.yellow);
            log('  5. Guarda el archivo como "serviceAccountKey.json" en este directorio\n', colors.yellow);
            process.exit(1);
        }

        log('✓ Archivo de credenciales encontrado', colors.green);

        // 2. Inicializar Firebase Admin
        const serviceAccount = require(serviceAccountPath);

        admin.initializeApp({
            credential: admin.credential.cert(serviceAccount)
        });

        const db = admin.firestore();
        log('✓ Firebase Admin SDK inicializado', colors.green);

        // 3. Leer el archivo JSON con los strings
        const jsonPath = path.join(__dirname, 'firebase_strings.json');
        if (!fs.existsSync(jsonPath)) {
            log(`\n❌ ERROR: No se encontró el archivo firebase_strings.json`, colors.red);
            log(`   Buscado en: ${jsonPath}\n`, colors.yellow);
            process.exit(1);
        }

        const stringsData = JSON.parse(fs.readFileSync(jsonPath, 'utf8'));
        log('✓ Archivo firebase_strings.json leído correctamente', colors.green);

        // 4. Preparar batch writes
        const appStrings = stringsData.app_strings;
        const documentKeys = Object.keys(appStrings);

        log(`\n📦 Preparando ${documentKeys.length} documentos para importación...`, colors.blue);

        // 5. Importar documentos en batch (máximo 500 operaciones por batch)
        const batchSize = 500;
        let totalDocuments = 0;

        for (let i = 0; i < documentKeys.length; i += batchSize) {
            const batch = db.batch();
            const batchKeys = documentKeys.slice(i, i + batchSize);

            for (const key of batchKeys) {
                const docRef = db.collection('app_strings').doc(key);
                batch.set(docRef, appStrings[key], { merge: true });
                totalDocuments++;
            }

            await batch.commit();
            log(`  ✓ Batch ${Math.floor(i / batchSize) + 1} completado (${batchKeys.length} documentos)`, colors.green);
        }

        // 6. Verificar la importación
        log('\n🔍 Verificando importación...', colors.cyan);
        const snapshot = await db.collection('app_strings').get();

        log(`\n✅ Importación completada exitosamente!`, colors.green);
        log(`   • Total de documentos importados: ${totalDocuments}`, colors.green);
        log(`   • Total de documentos en Firestore: ${snapshot.size}`, colors.green);

        // 7. Mostrar resumen de documentos
        log('\n📋 Documentos creados:', colors.blue);
        snapshot.forEach(doc => {
            const data = doc.data();
            const fieldCount = Object.keys(data).length;
            log(`   • ${doc.id} (${fieldCount} campos)`, colors.cyan);
        });

        log('\n🎉 Proceso completado con éxito!\n', colors.green);

        process.exit(0);

    } catch (error) {
        log('\n❌ ERROR durante la importación:', colors.red);
        log(error.message, colors.red);

        if (error.code === 'PERMISSION_DENIED') {
            log('\n💡 Verifica que:', colors.yellow);
            log('  • Las credenciales de Firebase son correctas', colors.yellow);
            log('  • El proyecto de Firebase tiene Firestore habilitado', colors.yellow);
            log('  • La cuenta de servicio tiene permisos de escritura en Firestore\n', colors.yellow);
        }

        process.exit(1);
    }
}

// Ejecutar el script
main();
