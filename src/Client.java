import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static int compteurId=1;
    private int id;
    private String nom;
    private String prenom;
    List<Produit> panier ;
    List<Commande> commandes;

    public Client(String nom,String prenom){
        this.id=compteurId++;
        this.nom = nom;
        this.prenom = prenom;
        this.panier = new ArrayList<>();
        this.commandes = new ArrayList<>();
    }

    // verifier stock
    public void ajouterAuPanier(Produit produit,int qte){
        if(panier.contains(produit)){
            System.out.println("produit deja au panier");
        }
        produit = new Produit(produit.getId(),produit.getNom(), produit.getPrix(), qte);
        panier.add(produit);
    }

    // verifier stock
    public void passerCommande(Commande commande){
        commandes.add(commande);
    }

    public void voireCatalogueDesProduits(Stock stock){
        System.out.println(stock.getProduits());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Produit> getPanier() {
        return panier;
    }
    public List<Commande> getCommandes(){
        return commandes;
    }

    public void setPanier(List<Produit> panier) {
        this.panier = panier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", panier=" + panier +
                '}';
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }


    public static void sauvegarderCommande(List<Commande> commande,String fichier) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
            oos.writeObject(commande);
        }catch (  IOException e){}
    }
    public static void sauvegarderPanier(List<Produit> panier, String fichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(panier);
        }
    }

    public static  List<Commande> chargerCommande(String fichier){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
            return (List<Commande>) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            return new ArrayList<>();
        }
    }
    public static List<Produit> chargerPanier(String fichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier))) {
            return (List<Produit>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>(); // Retourne un panier vide si le fichier est vide
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Client client = new Client("aissa","houar");
        System.out.println(client.toString());
        String rep;
        Stock stock = null;
        Produit produit = null;
//        FileInputStream inCommande = new FileInputStream("commande.ser");
//        ObjectInputStream oisCommande = new ObjectInputStream(inCommande);
//        FileInputStream inPanier = new FileInputStream("panier.ser");
//        ObjectInputStream oisPanier = new ObjectInputStream(inPanier);
//        try{
//            while (true){
//                client.setPanier((List<Produit>) oisPanier.readObject());
////                client.setCommandes((List<Commande>) oisCommande.readObject());
//            }
//
//        }catch (ClassNotFoundException e){
//
//        }catch (EOFException e){
//
//        }
//        FileOutputStream outCommande = new FileOutputStream("commande.ser");
//        ObjectOutputStream oosCommande = new ObjectOutputStream(outCommande);
//        FileOutputStream outPanier = new FileOutputStream("panier.ser");
//        ObjectOutputStream oosPanier = new ObjectOutputStream(outPanier);

        try {
            client.setPanier(chargerPanier("panier.ser"));
            client.setCommandes(chargerCommande("commande.ser"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du panier : " + e.getMessage());
        }

        try{
            Socket sock = new Socket("localhost",8000);
            InputStream in = sock.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            while((stock=(Stock) ois.readObject()) != null)
            do {
                System.out.println("Choose one of this operations just enter the operation number");
                System.out.println("1_voir catalogue des produits");
                System.out.println("2_ajouter_au_panier");
                System.out.println("3_voir_panier");
                System.out.println("4_passer_commande");
                System.out.println("5_voir_l'etat de mes commandes");
                System.out.println("6_exit");
                rep = scan.next().trim();
                if(rep.equals("1")) {
                    client.voireCatalogueDesProduits(stock);
                }if(rep.equals("2")){
                    System.out.println("entrer l'id que vous souhaitier ajoute au panier");
                    int id = scan.nextInt();
                     produit = stock.getProduit(id);
                    if(produit == null){
                        System.out.println("produit avec l'id : " +id+"n'existe pas");
                    }else{
                        System.out.println("entrer la quantiti que vous souhitier ajouter au panier");
                        int qte = scan.nextInt();
                        while(produit.getQte() < qte){
                            System.out.println("il ya que "+produit.getQte()+" produits");
                            System.out.println("entre un quantitie moins ou egale a "+produit.getQte());
                            qte = scan.nextInt();
                        }
                        client.ajouterAuPanier(produit,qte);
                        sauvegarderPanier(client.getPanier(),"panier.ser");
//                        oosPanier.writeObject(client.getPanier());
//                        oosPanier.flush();

//                            stock.modifierProduit(produit.getId(), produit.getNom(),produit.getPrix(), produit.getQte()-qte);
                    }
                }if(rep.equals("3")){
                    System.out.println(client.getPanier());
                }

                if(rep.equals("4")){
                    System.out.println(client.getPanier());
                    Commande commande = new Commande(client.getId(),ETAT_COMMANDE.EN_PREPARATION,new ArrayList<>(client.getPanier()));
                    System.out.println("vous etes sur que vous aller passer une commande oui/non");
                    String confirmation = scan.next();
                    if(confirmation.equals("oui")){
                        System.out.println("commande a ete bien passe");
                        client.passerCommande(commande);
                        sauvegarderCommande(client.getCommandes(),"commande.ser");
//                        oosCommande.writeObject(commande);
//                        oosCommande.flush();
                        List<Produit> produitsCommandes = client.getPanier();
                        for(Produit produitCommande :produitsCommandes ){
                            stock.modifierProduit(produitCommande.getId(), produitCommande.getNom(), produitCommande.getPrix(), produitCommande.getQte()-1);
                        }
                        client.getPanier().clear();
                        sauvegarderPanier(client.getPanier(),"panier.ser");
//                        oosPanier.writeObject(client.getPanier());
//                        oosPanier.flush();
                    }else{
                        System.out.println("commande annule");
                    }
                }if(rep.equals("5")){
                    System.out.println("voici vos commande");
                    System.out.println(client.getCommandes());
                }
            }while(! rep.equals("6"));


        }catch(IOException e){
//            System.out.println("ioeexception"+e.getMessage());
        }catch (ClassNotFoundException ex){
//            System.out.println("classnotfoundexception"+ex.getMessage());
        }


    }
}
