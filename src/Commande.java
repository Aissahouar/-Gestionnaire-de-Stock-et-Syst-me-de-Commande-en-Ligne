import java.io.Serializable;
import java.util.List;

public class Commande implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int compteurId = 1;
    private int id;
    private int clientId;
    private List<Produit> produitsCommande ;
    private ETAT_COMMANDE etat;


    public Commande(int clientId,ETAT_COMMANDE etat, List<Produit> produits){
        this.clientId = clientId;
        this.id=compteurId++;
        this.etat = etat;
        this.produitsCommande = produits;
    }

    public static int getCompteurId() {
        return compteurId;
    }

    public static void setCompteurId(int compteurId) {
        Commande.compteurId = compteurId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ETAT_COMMANDE getEtat() {
        return etat;
    }

    public void setEtat(ETAT_COMMANDE etat) {
        this.etat = etat;
    }

    public List<Produit> getProduitsCommande() {
        return produitsCommande;
    }

    public void setProduitsCommande(List<Produit> produitsCommande) {
        this.produitsCommande = produitsCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", produitsCommande=" + produitsCommande +
                ", etat=" + etat +
                '}';
    }
}


enum ETAT_COMMANDE {
    EN_PREPARATION("En préparation"),
    EXPEDIEE("Expédiée"),
    LIVREE("Livrée");

    private final String description;

    // Constructeur pour définir la description de chaque état
    ETAT_COMMANDE(String description) {
        this.description = description;
    }

    // Méthode pour récupérer la description
    public String getDescription() {
        return description;
    }
}

