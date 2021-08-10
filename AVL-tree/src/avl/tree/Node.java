
package avl.tree;


    class Node {
      Word key;
      int height;
      Node left;
      Node right;
 
    Node(Word word) {
        key = word;
        height = 1;
    }
    }
