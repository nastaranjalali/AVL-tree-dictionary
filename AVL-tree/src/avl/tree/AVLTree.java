package avl.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class AVLTree {

    Node root;

    // A utility function to get the height of the tree
    int height(Node N) {
        if (N == null) {
            return 0;
        }

        return N.height;
    }

    // returns a word that is alphabeticlly first
    Word maxWord(Word a, Word b) {

        int compare = a.word.compareTo(b.word);

        if (compare < 0) {
            return b;
        } else if (compare > 0) {
            return a;
        } else {
            if (a.word.substring(1) == null || b.word.substring(1) == null) {
                Word aSecondChar = new Word(a.word.substring(1), "");
                Word bSecondChar = new Word(b.word.substring(1), "");
                if (maxWord(aSecondChar, bSecondChar) == a) {
                    return a;
                } else {
                    return b;
                }
            } else {
                System.out.println("not allowed");
                return null;
            }

        }
    }

    String maxWord(String a, String b) {
        int compare = a.compareTo(b);
        String error = "";
        if (compare < 0) {
            return b;
        } else if (compare > 0) {
            return a;
        } else {
            if (a.substring(1) == null || b.substring(1) == null) {
                String aSecondChar = a.substring(1);
                String bSecondChar = b.substring(1);
                if (maxWord(aSecondChar, bSecondChar).compareTo(a) == 0) {
                    return a;
                } else {
                    return b;
                }

            } else {
                return error;
            }

        }
    }

    //returns integer max
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // right rotate rooted with y
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        
        return x;
    }

    // left rotate rooted with x 
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

     
        return y;
    }

    // returns Balance factor of node N
    int getBalance(Node N) {
        if (N == null) {
            return 0;
        }

        return height(N.left) - height(N.right);
    }
//adds a word to dic

    Node insert(Node node, Word key) {

        // 1. normal BST insertion 
        if (node == null) {
            return (new Node(key));
        }

        if (maxWord(key, node.key) == node.key) {
            node.left = insert(node.left, key);
        } else if (maxWord(key, node.key) == key) {
            node.right = insert(node.right, key);
        } else // same words are not allowed
        {
            return node;
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                height(node.right));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && maxWord(key, node.left.key) == node.left.key) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && maxWord(key, node.right.key) == key) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && maxWord(key, node.left.key) == key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && maxWord(key, node.right.key) == node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }




//searches a word and if there was not in dic returns similar words

    Word search(Node node, String word) {

        if ((node.right == null && node.left == null) || node.key.word == word) {

            return node.key;
            
        }
        if (maxWord(node.key.word, word) == word) {

            return search(node.right, word);
        }

        return search(node.left, word);

    }

    //writes inOrder(komaki)
    void inOrderOutput(Node node , FileWriter csvWriter) throws IOException {
            if (node == null) {
            return;
        }
       
        inOrderOutput(node.left , csvWriter);
       
    csvWriter.append(node.key.word);
    csvWriter.append(",");
    csvWriter.append(node.key.meaning);
    csvWriter.append("\n");
        inOrderOutput(node.right ,csvWriter);

    }

//exports to a  CSV file 
    void exportToCSV(Node node ,String address) throws IOException {

        FileWriter csvWriter = new FileWriter(address);
        csvWriter.append("Word");
        csvWriter.append(",");
        csvWriter.append("Meaning");
        csvWriter.append("\n");
        inOrderOutput(node, csvWriter);
        csvWriter.flush();
        csvWriter.close();
    }
    //imports from a CSV file
    Node importFromCSV(Node node, String address) throws IOException {
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader((address)));

        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Word[] wordArr = new Word[data.length / 2];
            String[] words = new String[data.length / 2];
            String[] meanings = new String[data.length / 2];
            for (int i = 0; i < data.length; i += 2) {
                words[i] = data[i];
                meanings[i] = data[i + 1];

            }
            for (int i = 0; i < words.length; i++) {

                Word word = new Word(words[i], meanings[i]);
                wordArr[i] = word;
                node = insert(node, wordArr[i]);
            }

        }
        csvReader.close();
        return node;

    }
    

    
//returns the min value in tree
    Node minValueNode(Node node) {
        Node current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    //delete a node
    Node deleteNode(Node node, String key) {
        //normal bst search
        if (node == null) {
            return node;
        }
        if (maxWord(key, node.key.word) == node.key.word) {
            node.left = deleteNode(node.left, key);
        } else if (maxWord(key, node.key.word) == key) {
            node.right = deleteNode(node.right, key);
        } 
        else {

      //one or no child
            if ((node.left == null) || (node.right == null)) {
                Node temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                // No child 
                if (temp == null) {
                    temp = node;
                    node = null;
                } else // One child 
                {
                    node = temp; 
                }                                
            } else {

                // node with two children
                Node temp = minValueNode(node.right);

             
                node.key = temp.key;

      
                node.right = deleteNode(node.right, temp.key.word);
            }
        }

     
        if (node == null) {
            return node;
        }

        // update height
        node.height = max(height(node.left), height(node.right)) + 1;

      
        int balance = getBalance(node);

        
        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
    
//displays words alphabeticlly

    void inOrderShow(Node node) {
        if (node == null) {
            return;
        }

        inOrderShow(node.left);
        System.out.println(node.key.word + "       " + node.key.meaning);
        inOrderShow(node.right);
    }
    // preOrderShow
    void preOrder(Node node) {

        if (node != null) {
            System.out.println(node.key.word + "          " + node.key.meaning);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

}
