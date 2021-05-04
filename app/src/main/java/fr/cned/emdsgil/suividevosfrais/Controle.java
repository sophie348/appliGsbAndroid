package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Controle {
    //propriétés
    private static Controle instance = null;
    private static AccesDistant accesDistant;
    private static Context context;
    private Date date = new Date();
    private String nom;

/*    Calendar cal = Calendar.getInstance();
    int anneeEnCours = (cal.get(Calendar.YEAR));
    int moisEnCours = (cal.get(Calendar.MONTH));*/

    /*   String dateEnCours = anneeEnCours+"0"+moisEnCours;*/
    String dateEnCours = convertDateToString(date);

    /**
     * constructeur privé
     */
    private Controle() {
        super();
    }

    /**
     * Création de l'instance unique de Controle
     *
     * @return instance
     */
    public static final Controle getInstance(Context context) {
        if (Controle.instance == null) {
            Controle.context = context;
            Controle.instance = new Controle();
            accesDistant = new AccesDistant(context);

        }
        return Controle.instance;
    }

    /**
     * methode envoi du controleur pour envoyer les données
     *
     * @param message
     * @param jsonArray
     */
    public void envoi(String message, JSONArray jsonArray) {
        accesDistant.envoi(message, jsonArray);
    }

    /**
     * conversion des donnees enregistrees au format json
     *
     * @return
     */
    public JSONArray convertFraisToJSONArray() {
        List list = new ArrayList();
        for (Map.Entry<Integer, FraisMois> fraisDetail : Global.listFraisMois.entrySet()) {
            Integer key = fraisDetail.getKey();
            FraisMois fm = fraisDetail.getValue();
            if (key == Integer.parseInt(dateEnCours)) {
                list.add(dateEnCours);
                list.add(nom);
                list.add(fm.getEtape());
                list.add(fm.getKm());
                list.add(fm.getNuitee());
                list.add(fm.getRepas());
                for (FraisHf hf : fm.getLesFraisHf()) {
                    list.add(hf.getJour());
                    list.add(hf.getMotif());
                    list.add(hf.getMontant());

                }
            }
        }

        Log.d("liste", "***************" +list);
        return new JSONArray(list);
    }

    /**
     * convertir une date en string
     * @param uneDate
     * @return
     */
    public  String convertDateToString(Date uneDate){
        SimpleDateFormat date=new SimpleDateFormat("yyyyMM");
        return date.format(uneDate);
    }

    /**
     * setter sur le nom du visiteur
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}


