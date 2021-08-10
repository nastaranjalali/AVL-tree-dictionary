//Nastaran Jalali 982023009 final project
package avl.tree;


import java.io.IOException;
import java.io.FileNotFoundException;


public class Final_project {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        AVLTree tree = new AVLTree();
        //declaring words
        Word word1 = new Word("spear", "neyze");
        Word word2 = new Word("shield", "separ");
        Word word3 = new Word("die", "mordan");
        Word word4 = new Word("axe", "tabar");
        Word word5 = new Word("bow", "kaman");
        Word word6 = new Word("hammer", "potk");
         Word word7 = new Word("hammer", "potk");
        //inserting words to dic
        tree.root = tree.insert(tree.root, word1);
        tree.root = tree.insert(tree.root, word2);
        tree.root = tree.insert(tree.root, word3);
        tree.root = tree.insert(tree.root, word4);
        tree.root = tree.insert(tree.root, word5);
        tree.root = tree.insert(tree.root, word6);
        // tree.root = tree.insert(tree.root, word7);
        //first output
        tree.root = tree.deleteNode(tree.root, "ddd");
        
        System.out.println("first output");
        tree.inOrderShow(tree.root);
        //deleting words
//        tree.root = tree.deleteNode(tree.root, "bow");
//        tree.root = tree.deleteNode(tree.root, "spear");
        //second output
//        System.out.println("second output");
//         tree.inOrderShow(tree.root);
        //searching words
       Word search = new Word();
      search =tree.search(tree.root, "vv");
        System.out.println("result of search  :  " + search.word+ "       "+ search.meaning);
        //export to CSV
//          tree.root = tree.importFromCSV(tree.root, "C:\\Users\\admin\\Documents\\NetBeansProjects\\final_project\\src\\final_project\\Book1.csv");
//        tree.exportToCSV(tree.root, "C:\\Users\\admin\\Documents\\NetBeansProjects\\final_project\\src\\final_project\\Book.csv");
        //import from CSV
      
        //third output
//        System.out.println("third output");
//        tree.inOrderShow(tree.root);
        

        
      
        



        

    }

}



