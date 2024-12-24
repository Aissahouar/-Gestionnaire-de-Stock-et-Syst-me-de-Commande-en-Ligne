import java.util.ArrayList;
import java.util.List;

public class Stock {

    private List <Produit> produits ;

    public Stock(){
        produits = new ArrayList<Produit>();
    }

    public void ajouterProduit(Produit produit){
        produits.add(produit);
    }

    public void supprimerProduit(int idProduit){
        Produit produitASupprimer = null;
        for (Produit produit : produits) {
            if (produit.getId() == idProduit) {
                produitASupprimer = produit;
                break;
            }
        }
        if (produitASupprimer != null) {
            produits.remove(produitASupprimer);
        } else {
            System.out.println("Produit avec ID " + idProduit + " non trouvé !");
        }
    }

    public void modifierProduit(int produitId){
        Produit produitAtmodifier = null;

        for(Produit produit : produits){
            if(produit.getId() == produitId){
                produitAtmodifier = produit;
                break;
            }
        }
    }


    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }


}
