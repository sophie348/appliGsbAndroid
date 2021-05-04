package fr.cned.emdsgil.suividevosfrais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class Connexion extends AppCompatActivity {
    private EditText txtLogin;
    private EditText txtMdp;
    private Button btnRetour;
    private Button btnConnexion;
    private Controle controle;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        setTitle("GSB : Connexion");

        init();

    }

    /**
     * initialisation de l'activité
     */
    private void init(){
        //recupération des objets graphiques
        txtLogin=(EditText)findViewById(R.id.txtLogin);
        txtMdp=(EditText)findViewById(R.id.txtMdp);
        btnRetour=(Button)findViewById(R.id.btnRetour);
        btnConnexion=(Button)findViewById(R.id.btnConnexion);
        this.controle=Controle.getInstance(this);

        imgReturn_clic();
        connexion_clic();
    }


    /**
     * Sur la selection du bouton : retour au menu principal
     */
    private void imgReturn_clic() {
        btnRetour.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(Connexion.this, MainActivity.class) ;
        startActivity(intent) ;
    }


    private void connexion_clic(){
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controle.envoi("connexion",convertToJSONArrayMdp());
                controle.setNom(txtLogin.getText().toString());
            }
        });
   }

    /**
     * conversion  des login et mot de passe au format json
     * @return
     */
    public JSONArray convertToJSONArrayMdp(){
        List list=new ArrayList();
        list.add(txtLogin.getText().toString());
        list.add(txtMdp.getText().toString());

        return new JSONArray(list);
    }





}