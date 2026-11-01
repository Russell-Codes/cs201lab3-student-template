import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
      * Checks whether the three given sequences can be the inorder, preorder and
      * postorder traversals of one single binary search tree.
      *
      * Strategy:
      * 1. Cheap rejects first: same length, same set of unique nodes.
      * 2. Try all 6 ways of labelling the three strings as (inorder, preorder,
      * postorder).
      * 3. A labelling is accepted only if the inorder candidate is strictly
      * ascending (true of every BST), the inorder + preorder pair rebuilds a
      * valid tree, and that tree's postorder equals the postorder candidate.
      */
     public static String verify(String traversal1, String traversal2, String traversal3) {
          int[][] seq = new int[3][];
          seq[0] = parse(traversal1);
          seq[1] = parse(traversal2);
          seq[2] = parse(traversal3);

          // malformed, empty, or duplicate nodes inside one traversal
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

     /** Turns "3-1-2" into {3, 1, 2}. Returns null if malformed or duplicated. */
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

     /** True when both arrays hold the same multiset of values. */
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

     /**
      * True when inorder + preorder + postorder all describe one BST.
      */
     private static boolean isConsistent(int[] inorder, int[] preorder, int[] postorder) {
          // The inorder traversal of a BST is the nodes in ascending order.
          for (int i = 1; i < inorder.length; i++) {
               if (inorder[i] <= inorder[i - 1]) {
                    return false;
               }
          }

          // inorder + preorder determine at most one tree, so rebuild it
          Map<Integer, Integer> inorderIndex = new HashMap<>();
          for (int i = 0; i < inorder.length; i++) {
               inorderIndex.put(inorder[i], i);
          }

          TreeBuilder builder = new TreeBuilder(preorder, inorderIndex);
          Node root = builder.build(0, inorder.length - 1);
          if (!builder.valid || builder.consumed != preorder.length) {
               return false;
          }

          // the remaining sequence must be that tree's postorder
          int[] actual = new int[postorder.length];
          collectPostorder(root, actual, new int[1]);
          for (int i = 0; i < postorder.length; i++) {
               if (actual[i] != postorder[i]) {
                    return false;
               }
          }
          return true;
     }

     private static void collectPostorder(Node node, int[] out, int[] cursor) {
          if (node == null) {
               return;
          }
          collectPostorder(node.left, out, cursor);
          collectPostorder(node.right, out, cursor);
          out[cursor[0]++] = node.value;
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

     /**
      * Rebuilds a tree from preorder + inorder. The next unused preorder value is
      * the root of the current inorder window; its position inside that window
      * splits the window into the left and right subtrees. If the value does not
      * sit inside the window, the two sequences contradict each other.
      */
     private static class TreeBuilder {
          private final int[] preorder;
          private final Map<Integer, Integer> inorderIndex;
          private int consumed = 0;
          private boolean valid = true;

          TreeBuilder(int[] preorder, Map<Integer, Integer> inorderIndex) {
               this.preorder = preorder;
               this.inorderIndex = inorderIndex;
          }

          Node build(int low, int high) {
               if (low > high) {
                    return null; // empty subtree
               }
               if (!valid || consumed >= preorder.length) {
                    valid = false;
                    return null;
               }

               int value = preorder[consumed];
               Integer position = inorderIndex.get(value);
               if (position == null || position < low || position > high) {
                    valid = false; // root does not belong to this window
                    return null;
               }
               consumed++;

               Node node = new Node(value);
               node.left = build(low, position - 1);
               node.right = build(position + 1, high);
               return node;
          }
     }
}
