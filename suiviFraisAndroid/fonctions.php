<?php
    function connexionPDO(){
        $login="userGsb";
        $mdp="secret";
        $bd="gsb2_frais";
        $serveur="localhost";
		$port="3306";
        try {
            $conn = new PDO("mysql:host=$serveur;dbname=$bd;port=$port", $login, $mdp);
            return $conn;
        } catch (PDOException $e) {
            print "Erreur de connexion PDO :".$e;
            die();
        }
    }
