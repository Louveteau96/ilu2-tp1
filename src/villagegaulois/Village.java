package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbrEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbrEtals);
	}

	public String getNom() {
		return nom;
	}

	/*Le chef*/
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	public class VillageSansChefException extends Exception{
		public VillageSansChefException(String erreur) {
			super(erreur);
		}
	}

	
	/*TP 1 */
	
	private static class Marche{
		private Etal[] etals;
		
		/*Constructeur*/
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i]=new Etal();
			}
		}
		
		/*Fonction Utiliser l'étal*/
		public void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int indiceEtal = 0;
			for (Etal etal : etals) {
				if (!etal.isEtalOccupe()) {
					return indiceEtal;
				}
				indiceEtal+=1;
			}
			return -1;
			}
		
		public Etal[] trouverEtals(String produit) {
			Etal[] venteProduit = new Etal[etals.length];
			int nbrVenteProduit = 0;
			for(Etal etal : etals) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					venteProduit[nbrVenteProduit]=etal;
					nbrVenteProduit+=1;
				}
			}
			return venteProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(Etal etal : etals) {
				if(etal.getVendeur()==gaulois) {
					return etal;
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for(Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					chaine.append("     - " + etal.afficherEtal());
				}
				else {
					nbEtalVide+=1;
				}
			}
			chaine.append("Il reste "+ nbEtalVide +" étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
		
		}
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		int numEtal = marche.trouverEtalLibre();
		if (numEtal == -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + " n'a pas pu trouver d'étal de libre.\n");
		}
		else {
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + numEtal);
			marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeursProduit = marche.trouverEtals(produit);
		if(etalsVendeursProduit.length==0) {
			chaine.append("Aucun vendeurs ne vend de " + produit + ".\n");
			return chaine.toString();
		}
		chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");
		for (Etal etal : etalsVendeursProduit) {
			if (etal != null && etal.isEtalOccupe()) {
				chaine.append("   - " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalVendeur=rechercherEtal(vendeur);
		chaine.append(etalVendeur.libererEtal());
		return chaine.toString();
		
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int etalsOccupes = marche.etals.length;
		if (etalsOccupes==0) {
			chaine.append("Il n'y a aucun vendeur au village " + getNom()+".\n");
			return chaine.toString();
		}
		chaine.append("Le marché du village "+ getNom() + " possède plusieurs étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();

	}
	
	
	
	
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(chef==null) {
			throw new VillageSansChefException("Il n'y a pas de chef dans le village");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}