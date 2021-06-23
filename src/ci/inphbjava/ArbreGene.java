package ci.inphbjava;

import sun.applet.Main;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class ArbreGene implements Serializable {
	 public  LinkedList<Personne> arbre= new LinkedList<Personne>();


	/* Fonction pour créer une personne sans parent (la racine de l'arbre ou le premier ancêtre)
	 et l'ajouter à l'arbre généalogique */


	public void CreerAncetre() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nom d'un ancêtre");
		String nom = sc.nextLine();
		System.out.println("Entrez son prénom ");
		String Prenoms = sc.nextLine();
		System.out.println("Entrez son sexe (M ou F)");
		String sexe = sc.nextLine();
		System.out.println("Entrez sa date de naissance (jour/Mois/Annee)");
		String dateNaiss = sc.nextLine();
			while (!dateNaiss.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) { // forcer l'utilisateur à entrer le bon format de l'âge
				System.out.println("Erreur de format, veuillez saisir le format (jour/mois/année)");
				dateNaiss = sc.nextLine();
			}
		Personne ancetre = new Personne(nom, Prenoms, sexe, dateNaiss);
		arbre.add(ancetre);
		System.out.println(ancetre.getNom()+" "+ancetre.getPrenoms()+" créé(e) avec succès\n");
		System.out.println("la personne "+ancetre.getNom()+" "+ancetre.getPrenoms()+" a-t-elle un enfant? O/N");
		String tes = sc.nextLine();
		while (tes.equalsIgnoreCase("o")) {
			CreerEnfant(ancetre);
			System.out.println("la personne "+ancetre.getNom()+" "+ancetre.getPrenoms()+" a-t-elle un autre enfant? O/N");
			tes = sc.nextLine();
		}
			ancetre.setEnfants(ancetre.getEnfants());
		    int index;
		    for (Personne enf: ancetre.getEnf()){
				index =ancetre.getEnf().indexOf(enf)+1;
				enf.setId(ancetre.getId()+""+index); }
		System.out.println(" \n");
		serialisationArbre();
	}




	// Fonction pour créer un enfant et l'ajouter à l'arbre généalogique

	public void CreerEnfant(Personne parent) {
			Scanner sc = new Scanner(System.in);
			String nom = parent.getNom();
			System.out.println("Entrez son prénom ");
			String Prenoms = sc.nextLine();
			System.out.println("Entrez son sexe (M ou F)");
			String sexe = sc.nextLine();
			System.out.println("Entrez sa date de naissance (jour/Mois/Annee)");
			String dateNaiss = sc.nextLine();

	/*forcer l'utilisateur à entrer le bon format de la date de naissance afin que l'age
	 de l'enfant soit inférieur à celui du père

	 */


			while (!dateNaiss.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
				System.out.println("Erreur format, entrez l'âge sous le bon format (jj/mm/aaaa ");
				dateNaiss = sc.nextLine();

			}
			while (parent.getAge(parent.getdateNaissance())< parent.getAge(dateNaiss)) {
				System.out.println("Impossible qu'un enfant soit plus agé que son parent, entrez une date adéquate");
				dateNaiss = sc.nextLine();

			}

			Personne enfant = new Personne(nom, Prenoms, sexe, dateNaiss, parent);
			arbre.add(enfant);
			System.out.println(enfant.getNom()+" "+enfant.getPrenoms()+" créé(e) avec succès");
			//serialisationArbre();

	}



	// Fonction pour retrouver une personne dans l'arbre généalogique


	public  Personne RetrouverPersonne() {
		this.affiche();
		System.out.println("\n Entrez l'id correspondant à la personne ");
		Scanner sc = new Scanner(System.in);
		String id1 = sc.nextLine();
		Personne p1 = null;
		for (Personne pers : arbre) {
			if (id1.equals( pers.getId())) {
				p1 = pers;
			}
		}
		if (p1 == null) {
			System.out.println("\n Mauvaise entrée \n");
			RetrouverPersonne();
		}
		return p1;
	}

/*
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le nom de la personne ");
		String nom = sc.nextLine();
		System.out.println("Entrez son prénom ");
		String Prenoms = sc.nextLine();
		System.out.println("Entrez son sexe (M ou F)");
		String sexe = sc.nextLine();
		System.out.println("Entrez sa date de naissance (jour/Mois/Annee)");
		String dateNaiss = sc.nextLine();

			while (!dateNaiss.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
				System.out.println("Erreur format");
				dateNaiss = sc.nextLine();

			}
			Personne person = null;
			for( Personne p : arbre) {
				if (p.getNom().equalsIgnoreCase(nom) && p.getPrenoms().equalsIgnoreCase(Prenoms) &&
						p.getSexe().equalsIgnoreCase(sexe) && p.getdateNaissance().equalsIgnoreCase(dateNaiss)) {
					person= p;
					break;
				}
			}
			return person;

	} */

// Fonction pour sérialiser un objet arbre

 void serialisationArbre() {
	try {
		FileOutputStream fos = new FileOutputStream("personne.dat");
		ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
	}catch(IOException e){
			System.out.println( "impossible d'écrire dans le fichier");
		}
	}

// Fonction pour afficher tous les membres d'un arbre généalogique
    void affiche () {
		for (Personne p : arbre) {
			System.out.println(p);
		}
	}

// Fonction qui affiche les elements d'une liste de personnes

	//public void parcoursListe(LinkedList<Personne> l) {
	//	for (Personne p : l){

			public  void parcoursProfondeur( Personne p )
	{
		for (int i=0;i<p.nombreAscendants();i++){
			System.out.print("`\t\t");}
		System.out.println(p);
		for (Personne p1: p.getEnf()){
			parcoursProfondeur(p1);
		}
	}






			//for (Personne j )
		//	System.out.println(p);
		//}

	//}


	/* ********************** LES FONCTIONS DES MENUS ******************************/


// -------------------------menu 1-----------------------------------------------
	public int menu1() {



        System.out.println(" \n********************* MENU PRINCIPAL ******************************\n");



        System.out.println("1 - Créer un arbre");
        System.out.println("2 - afficher un arbre");
        System.out.println("3 - Liens de parenté");
        System.out.println("4 - Quitter");
		System.out.println("\n-------------------------\n");
		System.out.println("Choisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
        return selection;
    }





    // -----------------------------menu 2-----------------------------------

	public int menu2() {



        System.out.println("************************* CREATION D'ARBRE **************************");


        System.out.println("1 - Ajouter un ancêtre");
        System.out.println("2 - Attribuer un enfant à une personne");
        System.out.println("3 - Revenir au menu principal");
        System.out.println("4 - Quitter");
		System.out.println("\n-------------------------\n");
		System.out.println("Choisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();

        switch (selection) {
        case 1:
            // Ajouter un ancêtre
			this.CreerAncetre();
			lienDesMenus();
            break;
        case 2:
           // Attribuer un enfant à une personne

					System.out.println("Voici la liste des personnes de l'arbre\n");
					Personne pers = RetrouverPersonne();
					CreerEnfant(pers);
					pers.setEnfants(pers.getEnfants());
					int index;
					for (Personne enf: pers.getEnf()){
						index = pers.getEnf().indexOf(enf)+1;
						enf.setId(pers.getId()+""+index);

					}
					serialisationArbre();
					lienDesMenus();

            break;
        case 3:
        	lienDesMenus();
            break;
        case 4:
            System.exit(0);
            break;
        default:
            lienDesMenus();
    }



        return selection;
    }




     // --------------------------------------menu 3--------------------------------------------
	public int menu3() {



         System.out.println("************************* AFFICHAGE D'ARBRE **************************");


        System.out.println("1 - Afficher l'arbre généalogique d'une personne");
        System.out.println("2 - Afficher le parent d'une personne");
        System.out.println("3 - Afficher les enfants d'une personne");
        System.out.println("4 - Afficher les frères et soeurs d'une personne");
        System.out.println("5 - Afficher le frère aîné d'une personne");
        System.out.println("6 - Afficher tous les ascendants d'une personne");
        System.out.println("7 - Afficher les cousins d'une personne");
        System.out.println("8 - Afficher les onles et tantes d'une personne");
        System.out.println("9 - Afficher les neveux et nièces d'une personne");

        System.out.println("10- Revenir au menu principal");
        System.out.println("11- Quitter");

		System.out.println("\n-------------------------\n");
		System.out.println("Choisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();

         switch (selection) {
        case 1:
            // Afficher l'arbre généalogique d'une personne
			System.out.println("Entrez les infos de la personne dont vous voir l'arbre");
					Personne person = RetrouverPersonne();
					if (person == null)
							System.out.println("Cette personne n'existe pas dans l'arbre");
					else {
						parcoursProfondeur( person);
					}

			lienDesMenus();
            break;
        case 2:
           // Afficher le parent d'une personne;
			System.out.println("Entrez les infos de la personne dont vous voir les enfants");
					Personne pers1 = this.RetrouverPersonne();
					if (pers1 == null)
							System.out.println("Cette personne n'existe pas dans l'arbre");
					else {
						System.out.println(pers1.getParent());
					}
			lienDesMenus();
            break;
         case 3:
            // Afficher les enfants d'une personne
			 System.out.println("Entrez les infos de la personne dont vous voir les enfants");
					Personne pers2 = this.RetrouverPersonne();
					if (pers2 == null)
							System.out.println("Cette personne n'existe pas dans l'arbre");
					else {
						System.out.println(pers2.getEnf());
					}

			 lienDesMenus();
            break;
        case 4:
           // Afficher les frères et soeurs d'une personne;
			System.out.println("Entrez les infos de la personne dont vous voir les enfants");
					Personne pers3 = RetrouverPersonne();

						System.out.println(pers3.getFraternels());
			lienDesMenus();
            break;
		case 5:
            // Afficher le frère aîné d'une personne
			System.out.println("Entrez l'id de la personne dont vous voulez voir l'ainé'");
			Personne pers4 = RetrouverPersonne();
			System.out.println(pers4.getAine());
			lienDesMenus();
            break;
        case 6:
           // Afficher tous les ascendants d'une personne
			System.out.println("Entrez les infos de la personne dont vous voir les ascendants");
			Personne pers5 = RetrouverPersonne();
			System.out.println(pers5.getAscendants());
			lienDesMenus();
            break;
         case 7:
            // Afficher tous les cousins d'une personne
			 System.out.println("Entrez les infos de la personne dont vous voir les cousins");
					Personne pers6 = this.RetrouverPersonne();
					if (pers6 == null)
							System.out.println("Cette personne n'existe pas dans l'arbre");
					else {
						System.out.println(pers6.getCousins());
					}
			 lienDesMenus();
            break;
        case 8:
           // Afficher les onles et tantes d'une personne
			System.out.println("Entrez les infos de la personne dont vous voir les enfants");
			Personne pers7 = RetrouverPersonne();
			System.out.println(pers7.getOncles());
			lienDesMenus();
			break;
		case 9:
           // Afficher les neveux et nièces d'une personne
			System.out.println("Entrez les infos de la personne dont vous voir les enfants");
			Personne pers = RetrouverPersonne();
			System.out.println(pers.getNeveux());
			lienDesMenus();
            break;
        case 10:
             lienDesMenus();
            break;
        case 11:
            System.exit(0);
            break;
        default:
            lienDesMenus();
    }
        return selection;
    }







 // ----------------------------------- menu 4 --------------------------------------------------
	public int menu4() {


       System.out.println("************************* LIENS DE PARENTE **************************");


        System.out.println("1 - Voir les liens de parenté entre deux personnes");
        System.out.println("2 - Vérifier si deux personnes ont un lien de parenté précis");
        System.out.println("3 - Revenir au menu principal");
        System.out.println("4 - Quitter");
		System.out.println("\n-------------------------\n");
		System.out.println("Choisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
        switch (selection) {
        case 1:
            // Voir les liens de parenté entre deux personnes
			System.out.println("Entrez les infos de la personne 1");
			Personne pers1 = this.RetrouverPersonne();

			if (pers1 == null )
				System.out.println("La personne 1 n'existe pas dans l'arbre");

			else {
				System.out.println("Entrez les infos de la personne 2");
				Personne pers2 = this.RetrouverPersonne();
				if ( pers2 == null)
				System.out.println("La personne 2 n'existe pas dans l'arbre");

				else {
					pers1.aQuelLienAvec(pers2);
					lienDesMenus();
					} }

			lienDesMenus();
            break;
        case 2:
           // Vérifier si deux personnes ont un lien de parenté précis
			lienDesMenus();
            break;
        case 3:
             lienDesMenus();
            break;
        case 4:
            System.exit(0);
            break;
        default:
            lienDesMenus();
    }
        return selection;
    }




 // ------------------------------- menu 5 ------------------------------------------------
	public int menu5() {



        System.out.println("************************* LIENS DE PARENTE PRECIS **************************");


        System.out.println("1 - parent-enfant");
        System.out.println("2 - cousins");
        System.out.println("3 - oncle/tante - neveux/nièces");
        System.out.println("4 - ascendant-descendant");
        System.out.println("5 - Revenir au menu principal");
        System.out.println("6 - Quitter");
		System.out.println("\n-------------------------\n");
		System.out.println("Choisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
         switch (selection) {
        case 1:
            // parent-enfant
			lienDesMenus();
            break;
        case 2:
           // cousins
			lienDesMenus();
            break;
         case 3:
            // oncle/tante - neveux/nièces
			 lienDesMenus();
            break;
        case 4:
           // ascendant-descendant
			lienDesMenus();
            break;
        case 5:
             lienDesMenus();
            break;
        case 6:
            System.exit(0);
            break;
        default:
            lienDesMenus();
    }
        return selection;
    }




// ----------------------------------- Gestion des liens entre les différents menus--------------------------------------------------

public void lienDesMenus() {

	int choix;
	choix = menu1();

	switch (choix) {
		case 1:
			menu2();
			break;
		case 2:
			menu3();
			break;
		case 3:
			menu4();
			break;
		case 4:
			System.exit(0);
			break;
		default:
			lienDesMenus();
	}

}




    public static void main(String[] args) {
//arb.CreerAncetre();
		//arb.affiche();
// désérialisation

		ArbreGene arb = new ArbreGene();
	try {
		FileInputStream fis = new FileInputStream("personne.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		arb = (ArbreGene)ois.readObject();
	}catch(IOException | ClassNotFoundException e){
			System.out.println( "impossible de lire le fichier");
		}

	// menu


			System.out.println("**********************Arbre Généalogique****************************");
			System.out.println("*                 By SOUMAHORO & DOSSO & KODIA                     *");
			System.out.println("********************************************************************");






	arb.affiche();
	arb.lienDesMenus();

// revoir la fonction créer enfant pour transformer les 2 boucles while en 1 seule boucle











	}




































/* 1 créer un arbre
		creer un ancêtre
		créer un enfant
 (voulez vous ajouter un autre enfant ?o/n)

2 afficher un arbre
	Afficher l'arbre généalogique d'une personne
	Afficher les enfants d'une personne
	Afficher les frères et soeurs d'une personne
	Afficher le frère aîné d'une personne
	Afficher tous les ascendants d'une personne
	Afficher tous les descendants d'une personne
	Afficher les onles et tantes d'une personne
	Aficher les neveux et nièces d'une persone
3 LIENS DE PARENTE

	Voir les liens de parenté entre deux personnes
	Vérifier si deux personnes ont un lien de parenté précis
		1 - parent-enfant
		2- cousins
		3 - oncle/tante-neveux/nièces
		4 - ascendant-descendant



*/
	}



