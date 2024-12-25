import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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

    public Client(String nom,String prenom){
        this.id=compteurId++;
        this.nom = nom;
        this.prenom = prenom;
        panier = new ArrayList<>();
    }

    public void ajouterAuPanier(Produit produit,int qte){
        if(panier.contains(produit)){
            System.out.println("produit deja au panier");
        }
        for(int i=0;i<qte;i++){
            panier.add(produit);
        }

    }

    public void passerCommande(){

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

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Client client = new Client("aissa","houar");
        System.out.println(client.toString());
        String rep;
        Stock stock = null;
        try{
            Socket sock = new Socket("localhost",8000);
            InputStream in = sock.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            while((stock=(Stock) ois.readObject()) != null)

            do {
                System.out.println("Choose one of this operations just enter the operation number");
                System.out.println("1_voir catalogue des produits");
                System.out.println("2_ajouter_au_panier");
                System.out.println("3_exit");
                rep = scan.next().trim();
                if(rep.equals("1")) {
                    client.voireCatalogueDesProduits(stock);
                }if(rep.equals("2")){
                    System.out.println("entrer l'id que vous souhaitier ajoute au panier");
                    int id = scan.nextInt();
                    Produit produit = stock.getProduit(id);
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
//                            stock.modifierProduit(produit.getId(), produit.getNom(),produit.getPrix(), produit.getQte()-qte);



                    }


                }
            }while(! rep.equals("3"));

        }catch(IOException e){
//            System.out.println("ioeexception"+e.getMessage());
        }catch (ClassNotFoundException ex){
//            System.out.println("classnotfoundexception"+ex.getMessage());
        }


    }
}
