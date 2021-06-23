package ci.inphbjava;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Period;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.time.LocalDate;





public class Personne implements Serializable {
    // pour l'identifiant
    private static int Code = 0;
    private String id;

    //pour les autres attributs
    private String nom;
    private String prenoms;
    private String sexe;
    private String dateNaissance;
    private Personne parent;
    private LinkedList<Personne> enfants;

    //Constructeur d'une personne sans parent (sommet d'un arbre)
    public Personne(String nom, String prenoms, String sexe, String dateNaissance) {
        id = ""+(++Code);
        this.nom = nom;
        this.prenoms = prenoms;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.parent = null;
        this.enfants = new LinkedList<Personne>();

    }

    //Constructeur d'un enfant
    public Personne (String nom, String prenoms, String sexe, String dateNaissance, Personne parent) {
        id = "";
        this.nom = nom;
        this.sexe = sexe;
        this.prenoms = prenoms;
        this.dateNaissance = dateNaissance;
        this.parent = parent;
        this.enfants = new LinkedList<Personne>();
        parent.enfants.add(this);

        }

    // les getters sur les attributs

    public String getId() {
        return id;
    }
    public void setId(String i) {
     this.id = i;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public String getSexe() {
        return sexe;
    }

    public String getdateNaissance() {
        return dateNaissance;
    }

    public Personne getParent() {
        return parent;
    }


    // Fonction toString pour redefinir les infos d'une personne

    public String toString (){

        return getId()+" "+getNom()+" "+getPrenoms()+", "+getSexe()+", "+getdateNaissance();

    }






     // Fonction pour récupérer l'âge

    public int getAge(String date) {

        int age = 0;
    try {
         Date datedenaissance = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        Calendar calendrierNaissance = Calendar.getInstance();
        calendrierNaissance.setTimeInMillis(datedenaissance.getTime());
        Date maintenant = new Date();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(maintenant.getTime());
         age = now.get(Calendar.YEAR) - calendrierNaissance.get(Calendar.YEAR);
    }catch (Exception e){
        System.out.print(" erreur ! ");
        return age;
    }
    return age;
}

 //Fonction pour retourner la liste des enfants d'une personne

 public LinkedList<Personne> getEnf() {
        return this.enfants;
 }

    //Fonction pour retourner la liste triée selon l'âge des enfants d'une personne

    public LinkedList<Personne> getEnfants() {
            LinkedList<Personne> enfantstri = new LinkedList<Personne>();
            Personne aine;
            int index = 0, age = 0;
            while (!enfants.isEmpty()) {
                for (Personne p : enfants) {
                    if (p.getAge(p.dateNaissance) > age) {
                        age = p.getAge(p.dateNaissance);
                        index = enfants.indexOf(p);
                    }

                }
                aine = enfants.get(index);
                enfantstri.add(aine);
                enfants.remove(aine);
                age=0;
            }

        return enfantstri;
    }


    public void setEnfants(LinkedList<Personne> p) {
     this.enfants = p;
    }



    // la méthode donnerNaissance permet d'attribuer un enfant à une personne

    //public void donnerNaissance(Personne person) {

     //   this.enfants.add(person);
   // }

// Fonction qui retourne les fraternels (frères & soeurs) d'une personne


    public LinkedList<Personne> getFraternels() {
        LinkedList<Personne> fraternels = new LinkedList<Personne>();
        if (this.getParent() != null) {
            fraternels.addAll(this.getParent().getEnf());
        }

        fraternels.remove(this); //pour s'enlever soi-même de la liste des fraternels
        return fraternels;
    }


   public Personne getAncetre(){
        Personne ancetre ;
        if(this.getParent() == null ) { // pas de parent
            ancetre = this;
        } else {
            ancetre = this.getParent().getAncetre();

        }

        if (parent != null) { // récursivité pour retourner l'ancêtre d'une personne
            return parent.getAncetre();
        }
        return ancetre;
    }


// Fonction pour retourner les ascendants d'une personne

    public LinkedList<Personne> getAscendants() {
        LinkedList<Personne> ascendants = new LinkedList<Personne>();
        if (parent == null) { //pas de parents
            return ascendants;

        } else {
            ascendants.add(parent);
            ascendants.addAll(parent.getAscendants());
        }
        return ascendants;
    }

    // Fonction pour retourner le nombre d'ascendants d'une personne

     public int nombreAscendants() {
        LinkedList<Personne> ascendants = new LinkedList<Personne>();
        if (this.getParent() == null) { //pas de parents
            return 0;

        } else {
            ascendants.add(this.getParent());
            ascendants.addAll(this.getParent().getAscendants());
        }
        return ascendants.size();
    }



    // Fonction pour retourner les descendants d'une personne
/*
    public LinkedList<Personne> getDescendants() {

       ma 2eme fonction
        Personne p;

        LinkedList<Personne> descendants = new LinkedList<Personne>();
        ArbreGene arbr = new ArbreGene();
        for (p : arbr.arbre) {
            if (p.getAscendants().contains(this)){
                descendants.add(p);}
        }
        return descendants;
        }*/


        /* première fonction
        if (this.enfants.isEmpty()) { //pas de parents
            System.out.println("liste vide");
            return descendants;

        } else {
            System.out.println("liste non vide");
            for (Personne e: this.getEnfants() )
            descendants.add(e);
            //descendants.add(e.getDesc)
           // for (Personne e: enfants )
           // descendants.addAll(e.enfants);
             return this.enfants;
        }


    } */








    // Fonction qui retourne les oncles/tantes d'une personne


    public LinkedList<Personne> getOncles() {
        LinkedList<Personne> oncles = new LinkedList<Personne>();
        if (!this.getParent().getFraternels().isEmpty()) {
            // oncles.addAll(this.getParent().);
            oncles.addAll(this.getParent().getFraternels());
        }
        return oncles;
    }



     // Fonction qui retourne les neveus/nièces d'une personne


    public LinkedList<Personne> getNeveux() {
        LinkedList<Personne> neveux = new LinkedList<Personne>();
        if (this.getFraternels() != null) {
            for (Personne f : this.getFraternels()) {
                neveux.addAll(f.getEnfants());
            }
        }
        return neveux;
    }





    // Fonction qui retourne les cousins d'une personne


    public LinkedList<Personne> getCousins() {
        LinkedList<Personne> cousins = new LinkedList<Personne>();
        if (this.getOncles() != null) {
            for (Personne p : this.getOncles())
                cousins.addAll(p.getEnfants());
        }
        return cousins;
    }





    // Fonction pour retourner le frère ainé d'une personne
    public Personne getAine() {
      /*  Personne p2 = null;
        int age = 0;
        if (!this.getFraternels().isEmpty()) {
            System.out.println(this.getFraternels().size());

            for (Personne p : this.getFraternels()) {
                if (p.getAge(p.dateNaissance) > age) {
                    age = p.getAge(p.dateNaissance);
                    p2 = p;
                }
            }
            return p2;

        }*/
        if (this.parent != null) {
            if (!this.parent.enfants.isEmpty())
                return this.parent.getEnf().get(0);
            else return null;
}       else return null;
    }



//************** Lien de parenté entre 2 personnes*****************

// Fonction qui nous permet de savoir si une personne est l'enfant d'une autre personne

    public boolean estEnfantde(Personne cible){
        if(parent == null ){
            return false;
        }
        if(parent.equals(cible)) {
            return true;
        } else{
            return false;
        }
    }



    // Fonction qui nous permet de savoir si une personne est le parent d'une autre personne

    public boolean estParentde(Personne cible){
        if(this.getEnf().isEmpty()){
            return false;
        }

        if(/*this.getEnf().contains(cible)*/ cible.getParent() == this) {
            return true;
        } else{
            return false;
        }
    }



    // Fonction qui nous permet de savoir si une personne est le fraternel (frère ou soeur) d'une autre personne

    public boolean estFraternelde(Personne cible){
        if(this.equals(cible)){
            return false;
        } else if (parent == null){
            return false;
        } else if(parent != null && parent.equals(cible.getParent()) ){
            return true;
        }
          else{
            return false;
        }
    }




     // Fonction qui nous permet de savoir si une personne est l'ascendant d'une autre personne

    public boolean estAscendantde(Personne cible) {
        if(cible.getAscendants().contains(this)) {
            return true;
        }else {
            return false;
        }
    }



         // Fonction qui nous permet de savoir si une personne est le descendant d'une autre personne
/*
    public boolean estDescendantde(Personne cible) {
        if(cible.getDescendants().contains(this)) {
            return true;
        }else {
            return false;
        }
    }
*/


      // Fonction qui nous permet de savoir si une personne est le cousin d'une autre personne

    public boolean estCousinde(Personne cible){
        Personne p1 = this;
        if(this.equals(cible)){
            return false;
        } else if(cible.getCousins().contains(p1)) {
            return true;
        }else {
            return false;
        }

        }



          // Fonction qui nous permet de savoir si une personne est l'oncle/la tante d'une autre personne
        public boolean estOnclede(Personne cible) {
        if(cible.getOncles().contains(this)) {
            return true;
        }else {
            return false;
        }
    }

      // Fonction qui nous permet de savoir si une personne est le neveu/la nièce d'une autre personne

    public boolean estNeveude(Personne cible) {
        if(cible.getNeveux().contains(this)) {
            return true;
        }else {
            return false;
        }
    }



      // Fonction qui nous permet de savoir si une personne est étranger à une autre personne

    public boolean estEtrangerA(Personne cible){
        Personne p1 = this;
        if(this.equals(cible)){ //à lui-même
            return false;
        } else if(this.estEnfantde(cible) || cible.estEnfantde(p1)){
            return false;
        } else if(this.estFraternelde(cible)){
            return false;
        } else if(this.estCousinde(cible)) {
            return false;
        } else if(this.estAscendantde(cible) || cible.estAscendantde(p1)){
            return false;
        } else if(this.estNeveude(cible) || cible.estNeveude(p1)){
            return false;
        } else {
            return true;
        }
    }




    // Fonction qui nous permet de savoir le lien exact qui lie deux personnes


public void aQuelLienAvec(Personne cible) {

        Personne p1 = this;
        if(this.equals(cible)){
            System.out.println(this+" est la personne elle-même");
        } else if(this.estEnfantde(cible))  {
            System.out.println(this+" est l'enfant de "+cible);
        }
          else if(this.estParentde(cible)){
            System.out.println(this+" est le parent de "+cible);
        }
         else if(this.estFraternelde(cible)){
            System.out.println(this+" est le fraternel de "+cible);

        } else if(this.estCousinde(cible)){
            System.out.println(this+" est le cousin de "+cible);

      /*   } else if(this.estAscendantde(cible)){
            System.out.println(this+" est l'ascendant de "+cible);
*/
       /* } else if(this.estDescendantde(cible)){
            System.out.println(this+" est le descendant de "+cible);
            */


        } else if(this.estNeveude(cible) ){
            System.out.println(this+" est le neveu de "+cible);

        } else if(this.estOnclede(cible) ){
            System.out.println(this+" est l'oncle de "+cible);

        } else {
            System.out.println("ils sont étrangers l'un de l'autre");
        }
    }


}


