package fr.cned.emdsgil.suividevosfrais;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.cned.emdsgil.suividevosfrais.AccesHTTP;
import fr.cned.emdsgil.suividevosfrais.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import android.widget.Toast;



public class AccesDistant implements AsyncResponse{
    //propriétés
    private static final String SERVERADDR = "http://192.168.1.42/suiviFraisAndroid/serveurSuiviFrais.php";
    private Controle controle;
    private Context context;


    /**
     * constructeur
     */
    public AccesDistant(Context context) {

        controle=Controle.getInstance(null);
        this.context=context;

    }

    @Override
    /**
     * retour du serveur HTTP
     * param output
     */
    public void processFinish(String output) {

        Log.d("serveur", "*************" + output);
        //decouper le message
        String[] message = output.split("%");
        //imprimer ce que contient le message dans la console

        if (message.length > 1) {
            if (message[0].equals("connexion")) {
                Log.d("connexion", "******************" + message[1]);
                if (message[1].equals("connexionreussie")) {
                    Toast.makeText(context, "connexion reussie", Toast.LENGTH_SHORT).show();

                    controle.envoi("enreg", controle.convertFraisToJSONArray());
                } else {
                    if (message[1].equals("erreurlogin")) {

                        Toast.makeText(context, "erreur de login", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (message[0].equals(("enreg"))) {
                    Log.d("enreg", "******************" + message[1]);
                }
            }

        } else {
            if (message[0].equals("erreur")) {
                Log.d("erreur", "******************" + message[1]);
            }
        }

    }






    public void envoi(String operation, JSONArray lesDonneesJson){
        // lien avec AccesHTTP pour permettre à delegate d'appeler la méthode processFinish
        // au retour du serveur
        AccesHTTP accesDonnees=new AccesHTTP();
        accesDonnees.delegate=this;
        // ajout de paramètres dans l'enveloppe HTTP
        accesDonnees.addParam("operation",operation);
        accesDonnees.addParam("lesdonnees",lesDonneesJson.toString());
        // envoi en post des paramètres, à l'adresse SERVERADDR
        accesDonnees.execute(SERVERADDR);


    }

}
