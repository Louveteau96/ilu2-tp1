package histoire;


import villagegaulois.*;
import villagegaulois.Village.VillageSansChefException;
import personnages.*;

public class ScenarioCasDegrade {

    public static void main(String[] args) {
        Etal etal = new Etal();
        Village village = new Village("Village", 10, 10);
        
        //afficher village non valide
        try {
            String villageois = village.afficherVillageois();
            System.out.println(villageois);
        } catch (VillageSansChefException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
        
        // acheter produit non valide
        Gaulois acheteur = null;
        try {
            etal.acheterProduit(0, acheteur);
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        try {
            etal.acheterProduit(-1, new Gaulois("Obélix", 5));
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        try {
            etal.acheterProduit(1, new Gaulois("Obélix", 5));
        } catch (IllegalStateException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        System.out.println("Fin du test");
    }

}