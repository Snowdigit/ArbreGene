package ci.inphbjava;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;



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


    //Constructeur d'une personne sans parent (sommet d'un arbre ou ancêtre)
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
    public void setPrenoms(String prenm) {
       this.prenoms = prenm;
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


// setter sur les enfants
    public void setEnfants(LinkedList<Personne> p) {
     this.enfants = p;
    }



// Fonction qui retourne les fraternels (frères & soeurs) d'une personne


    public LinkedList<Personne> getFraternels() {
        LinkedList<Personne> fraternels = new LinkedList<Personne>();
        if (this.getParent() != null) {
            fraternels.addAll(this.getParent().getEnf());
        }

        fraternels.remove(this); //pour s'enlever soi-même de la liste des fraternels
        return fraternels;
    }

// Fonction qui retourne l'ancêtre de la famille

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
        LinkedList<Personne> ascdts1 = new LinkedList<Personne>();
        LinkedList<Personne> ascendants = new LinkedList<Personne>();
        if (this.getParent() == null) { //pas de parents
            return ascendants;

        } else {
            ascdts1.add(this.getParent());
            ascdts1.addAll(this.getParent().getAscendants());
            for (Personne personne: ascdts1) {
                ascendants.add(personne);
                ascendants.addAll(personne.getFraternels());}

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

    public LinkedList<Personne> getDescendants() {

        LinkedList<Personne> descendants = new LinkedList<Personne>();

        if (this.getEnf().isEmpty()) { //pas de parents

            return descendants;

        } else {

            for (Personne e: this.getEnf() ){
            descendants.add(e);
            descendants.addAll(e.getDescendants());}
             return descendants;
        }


    }



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
                neveux.addAll(f.getEnf());
            }
        }
        return neveux;
    }


    // Fonction qui retourne les cousins d'une personne


    public LinkedList<Personne> getCousins() {
        LinkedList<Personne> cousins = new LinkedList<Personne>();
        if (this.getOncles() != null) {
            for (Personne p : this.getOncles())
                cousins.addAll(p.getEnf());
        }
        return cousins;
    }


    // Fonction pour retourner le frère ainé d'une personne

    public Personne getAine() {
        if (this.parent != null) {
            if (!this.parent.getEnf().isEmpty())
                return this.parent.getEnf().get(0);
            else return null;
}       else return null;
    }



//************** Lien de parenté entre 2 personnes*****************
    /* Ces fonctions nous permettent de savoir si deux personnes qu'on choisira ont
    oui ou non un lien de parenté qu'on choisira
     */

// Fonction qui nous permet de savoir si une personne est l'enfant d'une autre personne

    public boolean estEnfantde(Personne cible){
        if(this.getParent() == null ){
            return false;
        }
        if(this.getParent().equals(cible)) {
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

        if(cible.getParent() == this) {
            return true;
        } else{
            return false;
        }
    }



    // Fonction qui nous permet de savoir si une personne est le fraternel (frère ou soeur) d'une autre personne

    public boolean estFraternelde(Personne cible){
        if(this.equals(cible)){
            return false;
        } else if (this.getParent() == null){
            return false;
        } else if(getParent()  != null && getParent() .equals(cible.getParent()) ){
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

    public boolean estDescendantde(Personne cible) {
        if(cible.getDescendants().contains(this)) {
            return true;
        }else {
            return false;
        }
    }



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
    /* Cette fonction concerne le membre d'un arbre et celui d'un autre arbre
     */


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

        if(this.equals(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est la personne elle-même");
        } else if(this.estEnfantde(cible))  {
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est l'enfant de "+cible.getNom()+" "+ cible.getPrenoms());
        }
          else if(this.estParentde(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est le parent de "+cible.getNom()+" "+ cible.getPrenoms());
        }
         else if(this.estFraternelde(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est le fraternel de "+cible.getNom()+" "+ cible.getPrenoms());

        } else if(this.estCousinde(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est le cousin de "+cible.getNom()+" "+ cible.getPrenoms());

        } else if(this.estAscendantde(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est l'ascendant de "+cible.getNom()+" "+ cible.getPrenoms());

        } else if(this.estDescendantde(cible)){
            System.out.println(this.getNom()+" "+this.getPrenoms()+" est le descendant de "+cible.getNom()+" "+ cible.getPrenoms());



        } else if(this.estNeveude(cible) ){
            System.out.println(this+" est le neveu de "+cible);

        } else if(this.estOnclede(cible) ){
            System.out.println(this+" est l'oncle de "+cible);

        } else if (this.estEtrangerA(cible))
            System.out.println("ils sont étrangers l'un de l'autre");
        }
    }





