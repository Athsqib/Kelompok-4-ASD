import java.util.ArrayList;
import java.util.List;

/**
 * STUDI KASUS: Sistem Data Akademik Kampus
 *
 * Bagian 1 - TREE               : struktur folder arsip kampus (general tree, banyak anak)
 * Bagian 2 - BINARY SEARCH TREE : pencarian data mahasiswa berdasarkan NIM
 *
 * Konvensi: root level 0, tinggi dalam edge (leaf = 0, kosong = -1).
 */
public class StudiKasusTree {

    // BAGIAN 1: TREE (struktur folder - general tree)

    static class TreeNode {
        String data;
        List<TreeNode> children = new ArrayList<>();

        TreeNode(String data) {
            this.data = data;
        }

        TreeNode addChild(TreeNode child) {
            children.add(child);
            return this;
        }
    }

    static void printTree(TreeNode node, int level) {
        System.out.println("  ".repeat(level) + "- " + node.data);
        for (TreeNode child : node.children) {
            printTree(child, level + 1);
        }
    }

    static int countNodes(TreeNode node) {
        int total = 1;
        for (TreeNode child : node.children) {
            total += countNodes(child);
        }
        return total;
    }

    static int height(TreeNode node) {
        int max = -1;
        for (TreeNode child : node.children) {
            max = Math.max(max, height(child));
        }
        return 1 + max;
    }

    static int countLeaves(TreeNode node) {
        if (node.children.isEmpty()) return 1;
        int total = 0;
        for (TreeNode child : node.children) {
            total += countLeaves(child);
        }
        return total;
    }

    // BAGIAN 2: BINARY SEARCH TREE (data mahasiswa berdasarkan NIM)

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

    static class Node {
        Mahasiswa data;
        Node left, right;

        Node(Mahasiswa data) {
            this.data = data;
        }
    }

    static class BST {
        Node root;

        boolean insert(Mahasiswa m) {
            if (search(m.nim) != null) return false;
            root = insert(root, m);
            return true;
        }

        private Node insert(Node node, Mahasiswa m) {
            if (node == null) return new Node(m);
            if (m.nim < node.data.nim) node.left = insert(node.left, m);
            else node.right = insert(node.right, m);
            return node;
        }

        Mahasiswa search(int nim) {
            Node cur = root;
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

        private Node delete(Node node, int nim) {
            if (node == null) return null;
            if (nim < node.data.nim) {
                node.left = delete(node.left, nim);
            } else if (nim > node.data.nim) {
                node.right = delete(node.right, nim);
            } else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                Node succ = minNode(node.right);
                node.data = succ.data;
                node.right = delete(node.right, succ.data.nim);
            }
            return node;
        }

        private Node minNode(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        Mahasiswa min() {
            return root == null ? null : minNode(root).data;
        }

        Mahasiswa max() {
            if (root == null) return null;
            Node cur = root;
            while (cur.right != null) cur = cur.right;
            return cur.data;
        }

        void inorder(Node n, List<String> out) {
            if (n == null) return;
            inorder(n.left, out);
            out.add(String.valueOf(n.data.nim));
            inorder(n.right, out);
        }

        void preorder(Node n, List<String> out) {
            if (n == null) return;
            out.add(String.valueOf(n.data.nim));
            preorder(n.left, out);
            preorder(n.right, out);
        }

        void postorder(Node n, List<String> out) {
            if (n == null) return;
            postorder(n.left, out);
            postorder(n.right, out);
            out.add(String.valueOf(n.data.nim));
        }

        void levelOrder(Node root, List<String> out) {
            if (root == null) return;
            java.util.Queue<Node> q = new java.util.LinkedList<>();
            q.add(root);
            while (!q.isEmpty()) {
                Node cur = q.poll();
                out.add(String.valueOf(cur.data.nim));
                if (cur.left != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
        }

        int size(Node n) {
            if (n == null) return 0;
            return 1 + size(n.left) + size(n.right);
        }

        int height(Node n) {
            if (n == null) return -1;
            return 1 + Math.max(height(n.left), height(n.right));
        }

        void printTree(Node n, int level) {
            if (n == null) return;
            printTree(n.right, level + 1);
            System.out.println("    ".repeat(level) + n.data.nim + " (" + n.data.nama + ")");
            printTree(n.left, level + 1);
        }
    }

    // UTILITAS

    static String join(List<String> list) {
        return String.join(", ", list);
    }

    static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    // MAIN

    public static void main(String[] args) {

        // ---------- BAGIAN 1: TREE ----------
        header("BAGIAN 1: TREE - Struktur Folder Arsip Kampus");

        TreeNode root = new TreeNode("Arsip Kampus");
        TreeNode akademik = new TreeNode("Akademik");
        akademik.addChild(new TreeNode("Nilai")).addChild(new TreeNode("Jadwal"));
        TreeNode keuangan = new TreeNode("Keuangan");
        keuangan.addChild(new TreeNode("SPP"));
        TreeNode kemahasiswaan = new TreeNode("Kemahasiswaan");
        root.addChild(akademik).addChild(keuangan).addChild(kemahasiswaan);

        printTree(root, 0);
        System.out.println("Jumlah node : " + countNodes(root));
        System.out.println("Jumlah leaf : " + countLeaves(root));
        System.out.println("Tinggi      : " + height(root));

        // ---------- BAGIAN 2: BST ----------
        header("BAGIAN 2: BST - Data Mahasiswa berdasarkan NIM");

        BST bst = new BST();
        bst.insert(new Mahasiswa(50, "Andi", 3.50));
        bst.insert(new Mahasiswa(30, "Budi", 3.20));
        bst.insert(new Mahasiswa(70, "Citra", 3.80));
        bst.insert(new Mahasiswa(20, "Dewi", 3.10));
        bst.insert(new Mahasiswa(40, "Eko", 3.60));
        bst.insert(new Mahasiswa(60, "Fani", 3.40));
        bst.insert(new Mahasiswa(80, "Gita", 3.90));

        System.out.println("Bentuk pohon (root kiri, kanan atas):");
        bst.printTree(bst.root, 0);

        header("Traversal");
        List<String> hasil = new ArrayList<>();
        bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + join(hasil) + "   (terurut naik)");
        hasil.clear();
        bst.preorder(bst.root, hasil);
        System.out.println("Preorder  : " + join(hasil));
        hasil.clear();
        bst.postorder(bst.root, hasil);
        System.out.println("Postorder : " + join(hasil));
        hasil.clear();
        bst.levelOrder(bst.root, hasil);
        System.out.println("LevelOrder: " + join(hasil));

        header("Informasi Pohon");
        System.out.println("Size      : " + bst.size(bst.root));
        System.out.println("Height    : " + bst.height(bst.root));
        System.out.println("Min (NIM) : " + bst.min());
        System.out.println("Max (NIM) : " + bst.max());

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

        System.out.println();
        System.out.println("Tree after deletion:");
        bst.printTree(bst.root, 0);

        hasil.clear();
        bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + join(hasil));
        System.out.println("Size: " + bst.size(bst.root) + ", Height: " + bst.height(bst.root));
    }
}