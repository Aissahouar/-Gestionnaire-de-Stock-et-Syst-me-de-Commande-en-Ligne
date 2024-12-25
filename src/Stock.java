import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stock  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List <Produit> produits ;

    public Stock(){
        produits = new ArrayList<Produit>();
    }

    public void ajouterProduit(Produit produit){
        produits.add(produit);
    }

    public Produit getProduit(int produitID){
        Produit produitRechercehe = null;
        for(Produit produit:produits){
            if(produit.getId() == produitID){
                produitRechercehe = produit;
                return produitRechercehe;
            }
        }
        return produitRechercehe;
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

    public void modifierProduit(int produitId,String nouveauNom,float nouveauPrix,int nouvelleQte){

        for(Produit produit : produits){
            if(produit.getId() == produitId){
                produit.setNom(nouveauNom);
                produit.setPrix(nouveauPrix);
                produit.setQte(nouvelleQte);
                System.out.println("Produit avec le ID : "+produitId+ "a ete modifie");
                return;
            }

        }
        System.out.println("Produit avec le ID : "+produitId+" n'existe pas");
    }

    @Override
    public String toString() {
        return "Stock{" +
                "produits=" + produits +
                '}';
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }


}
