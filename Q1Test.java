import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     }

     // write your codes here

     private static final String INVALID = "Invalid traversals";

     /**
      * Decides whether the three sequences can be the inorder, preorder and
      * postorder traversals of one single binary search tree.
      *
      * Approach:
      * 1. Cheap rejects: well formed, equal length, identical node sets.
      * 2. Try all 6 ways of labelling the three strings as
      * (inorder, preorder, postorder).
      * 3. A labelling is accepted only if the inorder candidate is strictly
      * ascending, the preorder candidate rebuilds a tree, and that tree's
      * own inorder, preorder and postorder reproduce all three strings.
      *
      * Everything is iterative, so a 5,000 node chain cannot overflow the stack.
      * Runs in O(n) time per labelling.
      */
     public static String verify(String traversal1, String traversal2, String traversal3) {
          int[][] seq = new int[3][];
          seq[0] = parse(traversal1);
          seq[1] = parse(traversal2);
          seq[2] = parse(traversal3);

          // malformed, empty, or repeated nodes inside one traversal
          for (int[] s : seq) {
               if (s == null || s.length == 0) {
                    return INVALID;
               }
          }

          // three traversals of the same tree must have the same size
          if (seq[0].length != seq[1].length || seq[1].length != seq[2].length) {
               return INVALID;
          }

          // and must contain exactly the same nodes
          if (!sameNodes(seq[0], seq[1]) || !sameNodes(seq[0], seq[2])) {
               return INVALID;
          }

          // each row is {index of inorder, index of preorder, index of postorder}
          int[][] labellings = {
                    { 0, 1, 2 },
                    { 0, 2, 1 },
                    { 1, 0, 2 },
                    { 1, 2, 0 },
                    { 2, 0, 1 },
                    { 2, 1, 0 }
          };

          for (int[] order : labellings) {
               int in = order[0], pre = order[1], post = order[2];
               if (isConsistent(seq[in], seq[pre], seq[post])) {
                    String[] label = new String[3];
                    label[in] = "Inorder";
                    label[pre] = "Preorder";
                    label[post] = "Postorder";
                    return "Traversal 1 - " + label[0]
                              + ", Traversal 2 - " + label[1]
                              + ", Traversal 3 - " + label[2];
               }
          }

          return INVALID;
     }

     /** Turns "3-1-2" into {3, 1, 2}. Returns null if malformed or repeated. */
     private static int[] parse(String traversal) {
          if (traversal == null || traversal.trim().isEmpty()) {
               return null;
          }
          String[] tokens = traversal.trim().split("-");
          int[] values = new int[tokens.length];
          Set<Integer> seen = new HashSet<>();
          for (int i = 0; i < tokens.length; i++) {
               try {
                    values[i] = Integer.parseInt(tokens[i].trim());
               } catch (NumberFormatException e) {
                    return null;
               }
               if (!seen.add(values[i])) {
                    return null; // nodes must be unique
               }
          }
          return values;
     }

     /** True when both arrays hold the same set of values. */
     private static boolean sameNodes(int[] a, int[] b) {
          Set<Integer> pool = new HashSet<>();
          for (int v : a) {
               pool.add(v);
          }
          for (int v : b) {
               if (!pool.remove(v)) {
                    return false;
               }
          }
          return pool.isEmpty();
     }

     /** True when the three sequences all describe one single BST. */
     private static boolean isConsistent(int[] inorder, int[] preorder, int[] postorder) {
          // Fast filter: the inorder traversal of a BST is strictly ascending.
          for (int i = 1; i < inorder.length; i++) {
               if (inorder[i] <= inorder[i - 1]) {
                    return false;
               }
          }

          // Rebuild the one tree the preorder candidate describes, then let the
          // tree speak for itself: regenerate all three traversals and compare.
          Node root = buildFromPreorder(preorder);
          int[] buffer = new int[preorder.length];

          // An ascending inorder is exactly the BST property, so this check is
          // what rejects a tree that is binary but not a search tree.
          inorderInto(root, buffer);
          if (notEqual(buffer, inorder)) {
               return false;
          }

          // Confirms the preorder candidate really was this tree's preorder.
          preorderInto(root, buffer);
          if (notEqual(buffer, preorder)) {
               return false;
          }

          postorderInto(root, buffer);
          return !notEqual(buffer, postorder);
     }

     /**
      * Builds a tree from a preorder sequence in O(n) using an explicit stack.
      * The stack holds the nodes whose right child is still unfilled. A value
      * smaller than the top becomes that node's left child; a larger value
      * becomes the right child of the last node it overtakes.
      */
     private static Node buildFromPreorder(int[] preorder) {
          Node root = new Node(preorder[0]);
          Deque<Node> stack = new ArrayDeque<>();
          stack.push(root);

          for (int i = 1; i < preorder.length; i++) {
               Node node = new Node(preorder[i]);
               if (preorder[i] < stack.peek().value) {
                    stack.peek().left = node;
               } else {
                    Node parent = null;
                    while (!stack.isEmpty() && preorder[i] > stack.peek().value) {
                         parent = stack.pop();
                    }
                    parent.right = node; // the loop above ran at least once
               }
               stack.push(node);
          }
          return root;
     }

     private static void inorderInto(Node root, int[] out) {
          Deque<Node> stack = new ArrayDeque<>();
          Node current = root;
          int k = 0;
          while (current != null || !stack.isEmpty()) {
               while (current != null) {
                    stack.push(current);
                    current = current.left;
               }
               current = stack.pop();
               out[k++] = current.value;
               current = current.right;
          }
     }

     private static void preorderInto(Node root, int[] out) {
          Deque<Node> stack = new ArrayDeque<>();
          stack.push(root);
          int k = 0;
          while (!stack.isEmpty()) {
               Node node = stack.pop();
               out[k++] = node.value;
               if (node.right != null) {
                    stack.push(node.right);
               }
               if (node.left != null) {
                    stack.push(node.left);
               }
          }
     }

     /** Walks root, right, left and fills the array backwards. */
     private static void postorderInto(Node root, int[] out) {
          Deque<Node> stack = new ArrayDeque<>();
          stack.push(root);
          int k = out.length;
          while (!stack.isEmpty()) {
               Node node = stack.pop();
               out[--k] = node.value;
               if (node.left != null) {
                    stack.push(node.left);
               }
               if (node.right != null) {
                    stack.push(node.right);
               }
          }
     }

     private static boolean notEqual(int[] a, int[] b) {
          for (int i = 0; i < a.length; i++) {
               if (a[i] != b[i]) {
                    return true;
               }
          }
          return false;
     }

     /** Plain binary tree node. */
     private static class Node {
          int value;
          Node left;
          Node right;

          Node(int value) {
               this.value = value;
          }
     }
}
