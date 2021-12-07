package Firebase;

import Tools.FileHelper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Conexion {

    private static FileHelper fhelper;
    private static Firestore bd;

    public void conectar() throws IOException {
        fhelper = new FileHelper("Firebase conexion");
        
        FileInputStream serviceAccount
                = new FileInputStream("serviceAccountKey.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);

        //Creamos instancia
        bd = FirestoreClient.getFirestore();
        fhelper.escribir("Conexion con Firebase iniciada");
    }

    public boolean add(
            String coleccion,
            String documento,
            Map<String, Object> data) {
        
        try {
            DocumentReference docRef = bd.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time: " + result.get().getUpdateTime());
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            fhelper.escribir(e.getMessage());
        } catch (ExecutionException ex) {
            ex.printStackTrace();
            fhelper.escribir(ex.getMessage());
        }
        return false;
    }

}
