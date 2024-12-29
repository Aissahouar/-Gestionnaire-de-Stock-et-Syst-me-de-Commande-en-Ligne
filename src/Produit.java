import java.io.Serializable;

public class Produit implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int compteurId=1;
    private int id;
    private String nom;

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", qte=" + qte +
                '}';
    }

    private float prix;
    private int qte;

    public Produit(String nom,float prix,int qte){
        this.id=compteurId++;
        this.nom=nom;
        this.prix = prix;
        this.qte=qte;
    }
    public Produit(int id,String nom,float prix,int qte){
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.qte =qte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
