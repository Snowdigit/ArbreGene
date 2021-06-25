package ci.inphbjava;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class ArbreGene implements Serializable {
	 public  LinkedList<Personne> arbre= new LinkedList<Personne>();


	/* Fonction pour créer une personne sans parent (la racine de l'arbre ou le premier ancêtre)
	 et l'ajouter à l'arbre généalogique */


	public void CreerAncetre() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEntrez le nom d'un ancêtre\n");
		String nom = sc.nextLine();
		System.out.println("\nEntrez son prénom \n");
		String Prenoms = sc.nextLine();
		System.out.println("\nEntrez son sexe (M ou F)\n");
		String sexe = sc.nextLine();
			//forcer l'utilisateur à entrer le bon format du sexe (M ou F)

		while (!sexe.equalsIgnoreCase("m") && !sexe.equalsIgnoreCase("f")) {
			System.out.println("\nErreur de saisie, entrez 'm' pour masculin et 'f' pour feminin\n");
			sexe = sc.nextLine();
		}
		System.out.println("\nEntrez sa date de naissance (jour/Mois/Annee)\n");
		String dateNaiss = sc.nextLine();
		// forcer l'utilisateur à entrer le bon format de la date de naissance
		while (!dateNaiss.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
			System.out.println("\nErreur de format, veuillez saisir le format (jour/mois/année)\n");
			dateNaiss = sc.nextLine();
		}
		Personne ancetre = new Personne(nom, Prenoms, sexe, dateNaiss);
		arbre.add(ancetre);
		System.out.println(ancetre.getNom()+" "+ancetre.getPrenoms()+" créé(e) avec succès\n");
		System.out.println("\nla personne "+ancetre.getNom()+" "+ancetre.getPrenoms()+" a-t-elle un enfant? O/N\n");
		String tes = sc.nextLine();
		while (tes.equalsIgnoreCase("o")) {
			CreerEnfant(ancetre);
			System.out.println("\nla personne "+ancetre.getNom()+" "+ancetre.getPrenoms()+" a-t-elle un autre enfant? O/N\n");
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
		System.out.println("\nEntrez son prénom\n ");
		String Prenoms = sc.nextLine();
		System.out.println("\nEntrez son sexe (M ou F)\n");
		String sexe = sc.nextLine();

		//forcer l'utilisateur à entrer le bon format du sexe (M ou F)

		while (!sexe.equalsIgnoreCase("m") && !sexe.equalsIgnoreCase("f")) {
			System.out.println("\nErreur de saisie, entrez 'm' pour masculin et 'f' pour feminin\n");
			sexe = sc.nextLine();
		}

		System.out.println("\nEntrez sa date de naissance (jour/Mois/Annee)\n");
		String dateNaiss = sc.nextLine();
		/*forcer l'utilisateur à entrer le bon format de la date de naissance afin que l'age
	 	de l'enfant soit inférieur à celui du père
	 	*/
		while (!dateNaiss.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
			System.out.println("\nErreur format, entrez l'âge sous le bon format (jj/mm/aaaa) \n");
			dateNaiss = sc.nextLine();

		}
		while (parent.getAge(parent.getdateNaissance())<= parent.getAge(dateNaiss)) {
			System.out.println("\nImpossible qu'un enfant ait le même âge ou soit plus agé que son parent, entrez une date convenable !\n");
			dateNaiss = sc.nextLine();

		}

		Personne enfant = new Personne(nom, Prenoms, sexe, dateNaiss, parent);
		arbre.add(enfant);
		parent.setEnfants(parent.getEnfants());
		System.out.println(enfant.getNom()+" "+enfant.getPrenoms()+" \ncréé(e) avec succès\n");
		//serialisationArbre();

	}



	// Fonction pour retrouver une personne dans l'arbre généalogique


	public  Personne RetrouverPersonne() {
		this.affiche();
		System.out.println("\n Entrez l'id correspondant à la personne\n ");
		Scanner sc = new Scanner(System.in);
		String id1 = sc.nextLine();
		Personne p1 = null;
		for (Personne pers : arbre) {
			if (id1.equals( pers.getId())) {
				p1 = pers;
			}
		}

		return p1;
	}

	/* Fonction permettant de changer le prénom d'une personne
	Elle nous permettra de changer le prénom de l'aîné
	*/
	public void changerPrenom(Personne p) {
		System.out.println("\n Entrez les prénoms désirés\n");
		Scanner sc = new Scanner(System.in);
		String prenom = sc.nextLine();
		p.setPrenoms(prenom);
	}



// Fonction pour sérialiser un objet arbre

 void serialisationArbre() {
	try {
		FileOutputStream fos = new FileOutputStream("personne.dat");
		ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
	}catch(IOException e){
			System.out.println( "\nimpossible d'écrire dans le fichier\n");
		}
	}

// Fonction pour afficher tous les membres d'un arbre généalogique
    void affiche () {
		for (Personne p : arbre) {
			System.out.println(p);
		}
	}

// Fonction qui affiche un arbre suivant un parcours en profondeur

	public  void parcoursProfondeur( Personne p ) {
		for (int i=0;i<p.nombreAscendants();i++){ System.out.print("*  ");
		}
		System.out.println(p);
		for (Personne pers: p.getEnf()){
			parcoursProfondeur(pers);
		}
	}



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
		System.out.println("\nChoisissez une option\n");
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

			System.out.println("\nVoici la liste des personnes de l'arbre\n");
			Personne pers = RetrouverPersonne();
			CreerEnfant(pers);
			pers.setEnfants(pers.getEnfants());
			int index;
			for (Personne enf: pers.getEnf()){
				// pour que les enfants aient des id relatifs à celui de leur parent
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



		System.out.println("\n************************* AFFICHAGE D'ARBRE **************************\n");
        System.out.println("1 - Afficher l'arbre généalogique d'une personne");
        System.out.println("2 - Afficher le parent d'une personne");
        System.out.println("3 - Afficher les enfants d'une personne");
        System.out.println("4 - Afficher les frères et soeurs d'une personne");
        System.out.println("5 - Afficher le frère aîné d'une personne");
        System.out.println("6 - Afficher tous les ascendants d'une personne");
        System.out.println("7 - Afficher tous les descendants d'une personne");
        System.out.println("8 - Afficher les cousins d'une personne");
        System.out.println("9 - Afficher les onles et tantes d'une personne");
        System.out.println("10- Afficher les neveux et nièces d'une personne");
        System.out.println("11- Afficher l'ancêtre d'une personne");
        System.out.println("12- Revenir au menu principal");
        System.out.println("13- Quitter");
		System.out.println("\n-------------------------\n");
		System.out.println("\nChoisissez une option\n");
		int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
        switch (selection) {
        case 1:
            // Afficher l'arbre généalogique d'une personne
			System.out.println("\nChoisissez la personne à partir de laquelle vous désirez voir l'arbre\n");
			Personne person = RetrouverPersonne();
			while (person == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				person = this.RetrouverPersonne();
			}
			parcoursProfondeur(person);
			lienDesMenus();
            break;
        case 2:
           // Afficher le parent d'une personne;
			System.out.println("\n Choisissez la personne dont vous voulez voir le parent\n");
			Personne pers1 = this.RetrouverPersonne();
			while (pers1 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers1 = this.RetrouverPersonne();
			}
			System.out.println(pers1.getParent());
			lienDesMenus();
            break;
         case 3:
            // Afficher les enfants d'une personne
			 System.out.println("\n Choisissez la personne dont vous voulez voir les enfants\n");
			 Personne pers2 = this.RetrouverPersonne();
			 while (pers2 == null){
			 	System.out.println("\nCette personne n'existe pas dans l'arbre\n");
			 	pers2 = this.RetrouverPersonne();}
			 System.out.println(pers2.getEnf());
			 lienDesMenus();
            break;
        case 4:
           // Afficher les frères et soeurs d'une personne;
			System.out.println("\n Choisissez la personne dont vous voulez voir les frères et soeurs\n");
			Personne pers3 = RetrouverPersonne();
			while (pers3 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers3 = RetrouverPersonne();}
			System.out.println(pers3.getFraternels());
			lienDesMenus();
            break;
		case 5:
            // Afficher le frère aîné d'une personne
			System.out.println("\n Choisissez la personne dont vous voulez voir l'ainé\n");
			Personne pers4 = RetrouverPersonne();
			while (pers4 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers4 = this.RetrouverPersonne();
			}
			Personne aine =pers4.getAine();
			System.out.println(aine);
			// Possibilité de Changement du prenom de l'ainé d'une personne
			Scanner sc = new Scanner(System.in);
			System.out.println( "\nVoulez vous changer les prénoms de cet ainé ? (O/N)\n");
			String reponse = sc.nextLine();
			if (reponse.equalsIgnoreCase("o")) {
			changerPrenom(aine);
			System.out.println("\nChangement éffectué\n");
			lienDesMenus();
		}	else
				lienDesMenus();
            break;
        case 6:
           // Afficher tous les ascendants d'une personne
			System.out.println("\n Choisissez la personne dont vous voulez voir les ascendants\n");
			Personne pers5 = RetrouverPersonne();
			while (pers5 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers5 = this.RetrouverPersonne();
			}
			System.out.println(pers5.getAscendants());
			lienDesMenus();
            break;
        case 7:
           // Afficher tous les descendants d'une personne
			System.out.println("\n Choisissez la personne dont vous voulez voir les descendants\n");
			Personne pers6 = RetrouverPersonne();
			while (pers6 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers6 = this.RetrouverPersonne();
			}
			System.out.println(pers6.getDescendants());
			lienDesMenus();
            break;
         case 8:
            // Afficher tous les cousins d'une personne
			 System.out.println("\n Choisissez la personne dont vous voulez voir les cousins\n");
			 Personne pers7 = this.RetrouverPersonne();
			 while (pers7 == null){
			 	System.out.println("\nCette personne n'existe pas dans l'arbre\n");
			 	pers7 = this.RetrouverPersonne();
			 }
			 System.out.println(pers7.getCousins());
			 lienDesMenus();
            break;
        case 9:
           // Afficher les onles et tantes d'une personne
			System.out.println("\n Choisissez la personne dont vous voulez voir les oncles et tantes\n");
			Personne pers8 = RetrouverPersonne();
			while (pers8 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers8 = this.RetrouverPersonne();
			}
			System.out.println(pers8.getOncles());
			lienDesMenus();
			break;
		case 10:
           // Afficher les neveux et nièces d'une personne
			System.out.println("\n Choisissez la personne dont vous voulez voir les neveux et nièces\n");
			Personne pers9 = RetrouverPersonne();
			while (pers9 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers9 = this.RetrouverPersonne();
			}
			System.out.println(pers9.getNeveux());
			lienDesMenus();
            break;
        case 11:
            // Afficher l'ancêtre d'une personne
			 System.out.println("\n Choisissez la personne dont vous voulez voir l'ancêtre\n");
			 Personne pers10 = this.RetrouverPersonne();
			 while (pers10 == null){
			 	System.out.println("\nCette personne n'existe pas dans l'arbre\n");
			 	pers10 = this.RetrouverPersonne();
			 }
			 System.out.println(pers10.getAncetre());
			 lienDesMenus();
            break;
        case 12:
        	lienDesMenus();
            break;
        case 13:
            System.exit(0);
            break;
        default:
            lienDesMenus();
    }
        return selection;
    }







 // ----------------------------------- menu 4 --------------------------------------------------
	public int menu4() {


       System.out.println("\n************************* LIENS DE PARENTE **************************\n");


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
			System.out.println("\n Choisissez la personne 1\n");
			Personne pers1 = this.RetrouverPersonne();
			while (pers1 == null){
				System.out.println("Cette personne n'existe pas dans l'arbre\n");
				pers1 = this.RetrouverPersonne();
			}
			System.out.println("\n Choisissez la personne 2\n");
			Personne pers2 = this.RetrouverPersonne();
			while (pers2 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers2 = this.RetrouverPersonne();
			}
			pers1.aQuelLienAvec(pers2);
			lienDesMenus();
            break;
        case 2:
           // Vérifier si deux personnes ont un lien de parenté précis
			menu5();
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



        System.out.println("\n************************* LIENS DE PARENTE PRECIS **************************\n");


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
			System.out.println("\nEntrez l'id' du parent\n");
			Personne pers1 = this.RetrouverPersonne();
			while (pers1 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers1 = this.RetrouverPersonne();
			}
			System.out.println("\n Entrez l'id de l'enfant");
			Personne pers2 = this.RetrouverPersonne();
			while (pers2 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers2 = this.RetrouverPersonne();
			}
			System.out.println(pers1.estParentde(pers2));
			lienDesMenus();
            break;
        case 2:
           // cousins
			System.out.println("\nEntrez l'id de la personne 1\n");
			Personne pers3 = this.RetrouverPersonne();
			while (pers3 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers3 = this.RetrouverPersonne();
			}
			System.out.println("\n Entrez l'id de la personne 2\n");
			Personne pers4 = this.RetrouverPersonne();
			while (pers4 == null){
				System.out.println("\nette personne n'existe pas dans l'arbre\n");
				pers4 = this.RetrouverPersonne();
			}
			System.out.println(pers3.estCousinde(pers4));
			lienDesMenus();
            break;
         case 3:
            // oncle/tante - neveux/nièces
			System.out.println("\nEntrez l'id de l'oncle/la tante\n");
			Personne pers5 = this.RetrouverPersonne();
			while (pers5 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers5 = this.RetrouverPersonne();
			}
			System.out.println("\n Entrez l'id du neveu/la nièce\n");
			Personne pers6 = this.RetrouverPersonne();
			while (pers6 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers6 = this.RetrouverPersonne();
			}
			System.out.println(pers5.estOnclede(pers6));
			lienDesMenus();
            break;
        case 4:
           // ascendant-descendant
			System.out.println("\nEntrez l'id de l'ascendant\n");
			Personne pers7 = this.RetrouverPersonne();
			while (pers7 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers7 = this.RetrouverPersonne();
			}
			System.out.println("\n Entrez l'id du descendant\n");
			Personne pers8 = this.RetrouverPersonne();
			while (pers8 == null){
				System.out.println("\nCette personne n'existe pas dans l'arbre\n");
				pers8 = this.RetrouverPersonne();
			}
			System.out.println(pers7.estAscendantde(pers8));
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

// désérialisation
		ArbreGene arb = new ArbreGene();
	try {
		FileInputStream fis = new FileInputStream("personne.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		arb = (ArbreGene)ois.readObject();
	}catch(IOException | ClassNotFoundException e){
			System.out.println( "\nimpossible de lire le fichier\n");
		}

	// menu
	System.out.println("\n**********************Arbre Généalogique****************************\n");
	System.out.println("\n*                 By SOUMAHORO & DOSSO & KODIA                     *\n");
	System.out.println("\n********************************************************************\n");
	System.out.println("\n Les membres de différents arbres\n");
	arb.affiche();
	arb.lienDesMenus();













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



