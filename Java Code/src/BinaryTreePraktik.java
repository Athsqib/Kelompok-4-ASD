import java.util.*;

/**
 * BINARY TREE PRAKTIK - Berdasarkan materi Binary_Tree_rapi.pptx
 * Disejajarkan dengan variabel & konvensi StudiKasusTree.java
 *
 * Konvensi: root level 0, tinggi dalam edge (leaf = 0, kosong = -1)
 */
public class BinaryTreePraktik {

    // =====================================================================
    // 1. NODE CLASS (Binary Tree umum)
    // =====================================================================
    static class Node {
        char data;
        Node left, right;

        Node(char d) {
            this.data = d;
        }
    }

    // =====================================================================
    // 2. BUILD CONTOH POHON (A-G)
    //        A
    //      /   \
    //     B     C
    //    / \   /
    //   D   E F
    //      /
    //     G
    // =====================================================================
    static Node buildTree() {
        Node A = new Node('A');
        Node B = new Node('B');
        Node C = new Node('C');
        Node D = new Node('D');
        Node E = new Node('E');
        Node F = new Node('F');
        Node G = new Node('G');

        A.left = B;  A.right = C;
        B.left = D;  B.right = E;
        C.left = F;
        E.left = G;

        return A;
    }

    // =====================================================================
    // 3. TRAVERSAL (DFS & BFS)
    // =====================================================================

    static void preorder(Node n, List<String> out) {
        if (n == null) return;
        out.add(String.valueOf(n.data));
        preorder(n.left, out);
        preorder(n.right, out);
    }

    static void inorder(Node n, List<String> out) {
        if (n == null) return;
        inorder(n.left, out);
        out.add(String.valueOf(n.data));
        inorder(n.right, out);
    }

    static void postorder(Node n, List<String> out) {
        if (n == null) return;
        postorder(n.left, out);
        postorder(n.right, out);
        out.add(String.valueOf(n.data));
    }

    static void levelOrder(Node root, List<String> out) {
        if (root == null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            out.add(String.valueOf(cur.data));
            if (cur.left != null) q.add(cur.left);
            if (cur.right != null) q.add(cur.right);
        }
    }

    // =====================================================================
    // 4. OPERASI DASAR
    // =====================================================================

    static int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    static int countLeaves(Node node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }

    static int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static boolean isFull(Node node) {
        if (node == null) return true;
        if (node.left == null && node.right == null) return true;
        if (node.left != null && node.right != null)
            return isFull(node.left) && isFull(node.right);
        return false;
    }

    static boolean isPerfect(Node node) {
        int h = height(node);
        return countNodes(node) == (int)Math.pow(2, h + 1) - 1;
    }

    static boolean isComplete(Node root) {
        if (root == null) return true;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        boolean nullFound = false;
        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (cur == null) {
                nullFound = true;
            } else {
                if (nullFound) return false;
                q.add(cur.left);
                q.add(cur.right);
            }
        }
        return true;
    }

    static void printTree(Node n, int level) {
        if (n == null) return;
        printTree(n.right, level + 1);
        System.out.println("    ".repeat(level) + n.data);
        printTree(n.left, level + 1);
    }

    // =====================================================================
    // 5. BINARY SEARCH TREE (Mahasiswa)
    // =====================================================================

    static class Mahasiswa {
        int nim;
        String nama;
        double ipk;

        Mahasiswa(int nim, String nama, double ipk) {
            this.nim = nim;
            this.nama = nama;
            this.ipk = ipk;
        }

        @Override
        public String toString() {
            return nim + " | " + nama + " | IPK " + ipk;
        }
    }

    static class BSTNode {
        Mahasiswa data;
        BSTNode left, right;

        BSTNode(Mahasiswa data) {
            this.data = data;
        }
    }

    static class BST {
        BSTNode root;

        boolean insert(Mahasiswa m) {
            if (search(m.nim) != null) return false;
            root = insert(root, m);
            return true;
        }

        private BSTNode insert(BSTNode node, Mahasiswa m) {
            if (node == null) return new BSTNode(m);
            if (m.nim < node.data.nim) node.left = insert(node.left, m);
            else node.right = insert(node.right, m);
            return node;
        }

        Mahasiswa search(int nim) {
            BSTNode cur = root;
            while (cur != null) {
                if (nim == cur.data.nim) return cur.data;
                cur = (nim < cur.data.nim) ? cur.left : cur.right;
            }
            return null;
        }

        boolean delete(int nim) {
            if (search(nim) == null) return false;
            root = delete(root, nim);
            return true;
        }

        private BSTNode delete(BSTNode node, int nim) {
            if (node == null) return null;
            if (nim < node.data.nim) {
                node.left = delete(node.left, nim);
            } else if (nim > node.data.nim) {
                node.right = delete(node.right, nim);
            } else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                BSTNode succ = minNode(node.right);
                node.data = succ.data;
                node.right = delete(node.right, succ.data.nim);
            }
            return node;
        }

        private BSTNode minNode(BSTNode node) {
            while (node.left != null) node = node.left;
            return node;
        }

        Mahasiswa min() {
            return root == null ? null : minNode(root).data;
        }

        Mahasiswa max() {
            if (root == null) return null;
            BSTNode cur = root;
            while (cur.right != null) cur = cur.right;
            return cur.data;
        }

        void inorder(BSTNode n, List<String> out) {
            if (n == null) return;
            inorder(n.left, out);
            out.add(String.valueOf(n.data.nim));
            inorder(n.right, out);
        }

        void preorder(BSTNode n, List<String> out) {
            if (n == null) return;
            out.add(String.valueOf(n.data.nim));
            preorder(n.left, out);
            preorder(n.right, out);
        }

        void postorder(BSTNode n, List<String> out) {
            if (n == null) return;
            postorder(n.left, out);
            postorder(n.right, out);
            out.add(String.valueOf(n.data.nim));
        }

        void levelOrder(BSTNode root, List<String> out) {
            if (root == null) return;
            Queue<BSTNode> q = new LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                BSTNode cur = q.poll();
                out.add(String.valueOf(cur.data.nim));
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
        }

        int size(BSTNode n) {
            if (n == null) return 0;
            return 1 + size(n.left) + size(n.right);
        }

        int height(BSTNode n) {
            if (n == null) return -1;
            return 1 + Math.max(height(n.left), height(n.right));
        }

        void printTree(BSTNode n, int level) {
            if (n == null) return;
            printTree(n.right, level + 1);
            System.out.println("    ".repeat(level) + n.data.nim + " (" + n.data.nama + ")");
            printTree(n.left, level + 1);
        }
    }

    // =====================================================================
    // 6. AVL TREE
    // =====================================================================
    static class AVLNode {
        int data;
        int height;
        AVLNode left, right;

        AVLNode(int d) {
            this.data = d;
            this.height = 0;
        }
    }

    static int height(AVLNode n) { return n == null ? -1 : n.height; }

    static int balanceFactor(AVLNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    static void updateHeight(AVLNode n) {
        if (n != null) n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    static AVLNode rotateRight(AVLNode z) {
        AVLNode y = z.left;
        AVLNode T3 = y.right;
        y.right = z;
        z.left = T3;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    static AVLNode rotateLeft(AVLNode z) {
        AVLNode y = z.right;
        AVLNode T2 = y.left;
        y.left = z;
        z.right = T2;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    static AVLNode insertAVL(AVLNode root, int key) {
        if (root == null) return new AVLNode(key);
        if (key < root.data) root.left = insertAVL(root.left, key);
        else if (key > root.data) root.right = insertAVL(root.right, key);
        else return root;

        updateHeight(root);
        int bf = balanceFactor(root);

        if (bf > 1 && key < root.left.data) return rotateRight(root);
        if (bf < -1 && key > root.right.data) return rotateLeft(root);
        if (bf > 1 && key > root.left.data) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }
        if (bf < -1 && key < root.right.data) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }
        return root;
    }

    static void inorderAVL(AVLNode n, List<String> out) {
        if (n == null) return;
        inorderAVL(n.left, out);
        out.add(n.data + "(h" + n.height + ",BF" + balanceFactor(n) + ")");
        inorderAVL(n.right, out);
    }

    // =====================================================================
    // 7. RED-BLACK TREE
    // =====================================================================
    static class RBNode {
        int data;
        boolean isRed;
        RBNode left, right, parent;

        RBNode(int d, boolean red) {
            this.data = d;
            this.isRed = red;
        }
    }

    static class RedBlackTree {
        RBNode root;
        final RBNode NIL = new RBNode(0, false);

        RedBlackTree() {
            root = NIL;
            NIL.left = NIL.right = NIL.parent = NIL;
        }

        void insert(int key) {
            RBNode z = new RBNode(key, true);
            z.left = z.right = NIL;

            RBNode y = NIL;
            RBNode x = root;
            while (x != NIL) {
                y = x;
                if (key < x.data) x = x.left;
                else if (key > x.data) x = x.right;
                else return;
            }
            z.parent = y;
            if (y == NIL) root = z;
            else if (key < y.data) y.left = z;
            else y.right = z;

            insertFixup(z);
        }

        void insertFixup(RBNode z) {
            while (z.parent.isRed) {
                if (z.parent == z.parent.parent.left) {
                    RBNode y = z.parent.parent.right;
                    if (y.isRed) {
                        z.parent.isRed = false;
                        y.isRed = false;
                        z.parent.parent.isRed = true;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            rotateLeft(z);
                        }
                        z.parent.isRed = false;
                        z.parent.parent.isRed = true;
                        rotateRight(z.parent.parent);
                    }
                } else {
                    RBNode y = z.parent.parent.left;
                    if (y.isRed) {
                        z.parent.isRed = false;
                        y.isRed = false;
                        z.parent.parent.isRed = true;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rotateRight(z);
                        }
                        z.parent.isRed = false;
                        z.parent.parent.isRed = true;
                        rotateLeft(z.parent.parent);
                    }
                }
            }
            root.isRed = false;
        }

        void rotateLeft(RBNode x) {
            RBNode y = x.right;
            x.right = y.left;
            if (y.left != NIL) y.left.parent = x;
            y.parent = x.parent;
            if (x.parent == NIL) root = y;
            else if (x == x.parent.left) x.parent.left = y;
            else x.parent.right = y;
            y.left = x;
            x.parent = y;
        }

        void rotateRight(RBNode x) {
            RBNode y = x.left;
            x.left = y.right;
            if (y.right != NIL) y.right.parent = x;
            y.parent = x.parent;
            if (x.parent == NIL) root = y;
            else if (x == x.parent.right) x.parent.right = y;
            else x.parent.left = y;
            y.right = x;
            x.parent = y;
        }

        void inorder(RBNode n, List<String> out) {
            if (n == NIL) return;
            inorder(n.left, out);
            out.add(n.data + (n.isRed ? "(R)" : "(B)"));
            inorder(n.right, out);
        }
    }

    // =====================================================================
    // UTILITAS
    // =====================================================================
    static String join(List<String> list) {
        return String.join(", ", list);
    }

    static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    // =====================================================================
    // MAIN
    // =====================================================================
    public static void main(String[] args) {
        System.out.println("=== BINARY TREE PRAKTIK (dari Binary_Tree_rapi.pptx) ===");
        System.out.println("Konvensi variabel disejajarkan dengan StudiKasusTree.java\n");

        // 1. Contoh Pohon A-G
        header("1. CONTOH POHON BINARY TREE (A-G)");
        Node root = buildTree();

        List<String> hasil = new ArrayList<>();
        preorder(root, hasil);
        System.out.println("Preorder  : " + join(hasil));
        hasil.clear();

        inorder(root, hasil);
        System.out.println("Inorder   : " + join(hasil));
        hasil.clear();

        postorder(root, hasil);
        System.out.println("Postorder : " + join(hasil));
        hasil.clear();

        levelOrder(root, hasil);
        System.out.println("LevelOrder: " + join(hasil));
        System.out.println();

        System.out.println("Bentuk pohon (kanan di atas):");
        printTree(root, 0);
        System.out.println();

        // 2. Operasi Dasar
        header("2. OPERASI DASAR BINARY TREE");
        System.out.println("CountNodes: " + countNodes(root));
        System.out.println("CountLeaves: " + countLeaves(root));
        System.out.println("Height    : " + height(root));
        System.out.println("IsFull    : " + isFull(root));
        System.out.println("IsPerfect : " + isPerfect(root));
        System.out.println("IsComplete: " + isComplete(root));
        System.out.println();

        // 3. BST Demo
        header("3. BINARY SEARCH TREE - Data Mahasiswa (NIM)");
        int[][] dataMhs = {{50,0},{30,0},{70,0},{20,0},{40,0},{60,0},{80,0}};
        String[] namaMhs = {"Andi","Budi","Citra","Dewi","Eko","Fani","Gita"};
        double[] ipkMhs = {3.50,3.20,3.80,3.10,3.60,3.40,3.90};

        BST bst = new BST();
        for (int i = 0; i < dataMhs.length; i++) {
            bst.insert(new Mahasiswa(dataMhs[i][0], namaMhs[i], ipkMhs[i]));
        }

        System.out.println("Bentuk pohon BST:");
        bst.printTree(bst.root, 0);

        header("Traversal BST");
        hasil.clear(); bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + join(hasil) + "  (terurut naik)");
        hasil.clear(); bst.preorder(bst.root, hasil);
        System.out.println("Preorder  : " + join(hasil));
        hasil.clear(); bst.postorder(bst.root, hasil);
        System.out.println("Postorder : " + join(hasil));
        hasil.clear(); bst.levelOrder(bst.root, hasil);
        System.out.println("LevelOrder: " + join(hasil));

        header("Informasi Pohon BST");
        System.out.println("Size   : " + bst.size(bst.root));
        System.out.println("Height : " + bst.height(bst.root));
        System.out.println("Min    : " + bst.min());
        System.out.println("Max    : " + bst.max());

        header("Search");
        int[] dicari = {40, 99};
        for (int nim : dicari) {
            Mahasiswa m = bst.search(nim);
            System.out.println("Search " + nim + " -> " + (m != null ? m : "not found"));
        }

        header("Insert Duplicate NIM");
        boolean ok = bst.insert(new Mahasiswa(40, "Duplicate", 2.00));
        System.out.println("Insert NIM 40 again -> " + (ok ? "success" : "rejected (NIM exists)"));

        header("Delete (3 cases)");
        System.out.println("Delete 20 (leaf)        : " + bst.delete(20));
        System.out.println("Delete 30 (1 child: 40) : " + bst.delete(30));
        System.out.println("Delete 50 (2 child/root): " + bst.delete(50));
        System.out.println("Delete 99 (not exist)   : " + bst.delete(99));

        System.out.println("\nTree after deletion:");
        bst.printTree(bst.root, 0);

        hasil.clear(); bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + join(hasil));
        System.out.println("Size: " + bst.size(bst.root) + ", Height: " + bst.height(bst.root));

        // 4. AVL Tree Demo
        header("4. AVL TREE (Balance Factor & Rotasi)");
        AVLNode avlRoot = null;
        int[] avlData = {10, 20, 30, 40, 50, 25};
        for (int v : avlData) avlRoot = insertAVL(avlRoot, v);

        hasil.clear(); inorderAVL(avlRoot, hasil);
        System.out.println("Inorder AVL: " + join(hasil));
        System.out.println("Root: " + avlRoot.data);
        System.out.println("Height: " + avlRoot.height + ", BF root: " + balanceFactor(avlRoot));

        // 5. Red-Black Tree Demo
        header("5. RED-BLACK TREE (5 Aturan Warna)");
        RedBlackTree rbt = new RedBlackTree();
        int[] rbData = {10, 20, 30, 40, 50, 25};
        for (int v : rbData) rbt.insert(v);

        hasil.clear(); rbt.inorder(rbt.root, hasil);
        System.out.println("Inorder RBT: " + join(hasil));
        System.out.println("(R)=Merah, (B)=Hitam. Root hitam, no merah bertetangga, black-height sama");
    }
}