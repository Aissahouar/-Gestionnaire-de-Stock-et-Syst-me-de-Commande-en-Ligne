import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Stock stock = new Stock();
        Produit produit1 = new Produit("p1",10.0f,10);
        Produit produit2 = new Produit("p2",20.0f,20);
        Produit produit3 = new Produit("p3",30.0f,30);
        Produit produit4 = new Produit("p4",40.0f,40);
        Produit produit5 = new Produit("p5",50.0f,50);
        stock.ajouterProduit(produit1);
        stock.ajouterProduit(produit2);
        stock.ajouterProduit(produit3);
        stock.ajouterProduit(produit4);
        stock.ajouterProduit(produit5);
        Client client1 = new Client("houar","aissa");
//        client1.ajouterAuPanier(produit1);
//        client1.ajouterAuPanier(produit2);
        client1.voireCatalogueDesProduits(stock);
//        System.out.println(client1.toString());
        try{
            ServerSocket server = new ServerSocket(8000);
            Socket sock = new Socket();
            while(true){
                sock = server.accept();
                OutputStream out = sock.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(stock);
                oos.flush();
                out.close();
                oos.close();
            }

        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
}