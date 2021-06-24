<?php
include "fonctions.php";

// contrôle de réception de paramètre
if(isset($_POST["operation"])){
	//demande de connexion a la base de données
	if($_REQUEST["operation"]=="connexion"){
		
		try{
			
			//recuperation des donnees en post
			$lesdonnees=$_REQUEST["lesdonnees"];
			$donnee=json_decode($lesdonnees);
			$login2=$donnee[0];
			$mdp2=$donnee[1];
			
			//recherche dans la table visiteur
			print("connexion%");
			$cnx=connexionPDO();
			
			$larequete="select mdp from visiteur where login=:unLogin";
			//print($larequete);
			$req=$cnx->prepare($larequete);
			$req->bindParam(':unLogin', $login2, PDO::PARAM_STR);
			$req->execute();
			$passe=$req->fetch();
			//print(json_encode($passe));
			
		
					
			if($passe['mdp']==$mdp2 && !empty($passe)){
				print("connexionreussie");
				$cnx=null;
			}else{
				print("erreurlogin");
				
			}
			
			}catch(PDOException $e){
			print "Erreur !%".$e->getMessage();
			die();
			}
			
			
			
		}elseif($_REQUEST["operation"]=="enreg"){
			try{
				
			//récupération des données en post
			$lesdonnees = $_REQUEST["lesdonnees"];
			$donnee = json_decode($lesdonnees);
			
			$date=$donnee[0];
			$loginVisiteur=$donnee[1];
			$etape=$donnee[2];
			$km=$donnee[3];
			$nuitee=$donnee[4];
			$repas=$donnee[5];
			$donneeHf=array();
			
			
			if(count($donnee)>6){
				
				$donneeHf=array_slice($donnee,6);
				$donneeHf=array_chunk($donneeHf,3);
				
				
			}
			
			print("enreg%");

			
			
			
			$cnx=connexionPDO();
				
			$requete="select id from visiteur  where login='$loginVisiteur'";
				
			$req= $cnx->prepare($requete);
				
			$req->execute();
			
			$ligne=$req->fetch();
		
			$id=$ligne['id'];
			
			
			$requete0="INSERT into fichefrais (idvisiteur,mois,nbjustificatifs,montantvalide,datemodif,idetat) VALUES ('$id','$date',0,0,NOW(),'CR')";
			$requete1="INSERT into lignefraisforfait(idvisiteur,mois,idfraisforfait,quantite) VALUES ('$id','$date','ETP',$etape)";
			$requete2="INSERT into lignefraisforfait(idvisiteur,mois,idfraisforfait,quantite) VALUES ('$id','$date','KM',$km)";
			$requete3="INSERT into lignefraisforfait(idvisiteur,mois,idfraisforfait,quantite) VALUES ('$id','$date','NUI',$nuitee)";
			$requete4="INSERT into lignefraisforfait(idvisiteur,mois,idfraisforfait,quantite) VALUES ('$id','$date','REP',$repas)";
			
			foreach($donneeHf as $key=>$value){
				
				$requeteHf="INSERT  into lignefraishorsforfait(idvisiteur,mois,libelle,date,montant) VALUES('$id','$date','$value[1]','$value[0]','$value[2]')";
	
				$req=$cnx->prepare($requeteHf);
				$req->execute();
			}
			
			
			
			$req0 = $cnx->prepare($requete0);
			$req0->execute();
			$req1 = $cnx->prepare($requete1);
     		$req1->execute();
			$req2 = $cnx->prepare($requete2);
			$req2->execute();
			$req3 = $cnx->prepare($requete3);
			$req3->execute();
			$req4 = $cnx->prepare($requete4);
			$req4->execute();
			
			
			
			
			
	
			
			
		}catch(PDOException $e){
			print "Erreur !%".$e->getMessage();
			die();
		}
	}
	

}