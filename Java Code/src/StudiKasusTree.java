import java.util.ArrayList;
import java.util.List;

/**
 * STUDI KASUS: Sistem Data Akademik Kampus
 *
 * Bagian 1 - TREE            : struktur folder arsip kampus (satu node boleh punya banyak anak)
 * Bagian 2 - BINARY SEARCH TREE : pencarian data mahasiswa berdasarkan NIM
 *
 * Konvensi: root berada di level 0, tinggi dihitung dalam edge (leaf = 0, pohon kosong = -1).
 */
public class StudiKasusTree {

    // =====================================================================
    // BAGIAN 1: TREE (struktur folder)
    // =====================================================================

    static class FolderNode {
        String nama;
        List<FolderNode> anak = new ArrayList<>();

        FolderNode(String nama) {
            this.nama = nama;
        }

        FolderNode tambah(FolderNode child) {
            anak.add(child);
            return this;
        }
    }

    // Cetak pohon dengan indentasi sesuai level (preorder)
    static void cetakFolder(FolderNode node, int level) {
        System.out.println("  ".repeat(level) + "- " + node.nama);
        for (FolderNode child : node.anak) {
            cetakFolder(child, level + 1);
        }
    }

    // Jumlah node = 1 (node ini) + jumlah node di setiap anak
    static int hitungFolder(FolderNode node) {
        int total = 1;
        for (FolderNode child : node.anak) {
            total += hitungFolder(child);
        }
        return total;
    }

    // Tinggi: leaf = 0, node lain = 1 + tinggi anak terdalam
    static int tinggiFolder(FolderNode node) {
        int maks = -1;
        for (FolderNode child : node.anak) {
            maks = Math.max(maks, tinggiFolder(child));
        }
        return 1 + maks; // leaf: 1 + (-1) = 0
    }

    // Hitung leaf (folder tanpa subfolder)
    static int hitungLeaf(FolderNode node) {
        if (node.anak.isEmpty()) return 1;
        int total = 0;
        for (FolderNode child : node.anak) {
            total += hitungLeaf(child);
        }
        return total;
    }

    // =====================================================================
    // BAGIAN 2: BINARY SEARCH TREE (data mahasiswa berdasarkan NIM)
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

    static class Node {
        Mahasiswa data;
        Node left, right;

        Node(Mahasiswa data) {
            this.data = data;
        }
    }

    static class BST {
        Node root;

        // ---- INSERT: NIM lebih kecil ke kiri, lebih besar ke kanan ----
        boolean insert(Mahasiswa m) {
            if (cari(m.nim) != null) return false; // NIM harus unik
            root = insert(root, m);
            return true;
        }

        private Node insert(Node node, Mahasiswa m) {
            if (node == null) return new Node(m);
            if (m.nim < node.data.nim) node.left = insert(node.left, m);
            else node.right = insert(node.right, m);
            return node;
        }

        // ---- SEARCH ----
        Mahasiswa cari(int nim) {
            Node cur = root;
            while (cur != null) {
                if (nim == cur.data.nim) return cur.data;
                cur = (nim < cur.data.nim) ? cur.left : cur.right;
            }
            return null;
        }

        // ---- DELETE (3 kasus: leaf, 1 anak, 2 anak) ----
        boolean hapus(int nim) {
            if (cari(nim) == null) return false;
            root = hapus(root, nim);
            return true;
        }

        private Node hapus(Node node, int nim) {
            if (node == null) return null;
            if (nim < node.data.nim) {
                node.left = hapus(node.left, nim);
            } else if (nim > node.data.nim) {
                node.right = hapus(node.right, nim);
            } else {
                if (node.left == null) return node.right;   // leaf atau hanya anak kanan
                if (node.right == null) return node.left;   // hanya anak kiri
                // 2 anak: ganti dengan successor (node terkecil di subtree kanan)
                Node succ = minNode(node.right);
                node.data = succ.data;
                node.right = hapus(node.right, succ.data.nim);
            }
            return node;
        }

        private Node minNode(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        // ---- MIN & MAX ----
        Mahasiswa min() {
            return root == null ? null : minNode(root).data;
        }

        Mahasiswa max() {
            if (root == null) return null;
            Node cur = root;
            while (cur.right != null) cur = cur.right;
            return cur.data;
        }

        // ---- TRAVERSAL ----
        void inorder(Node n, List<String> out) {      // kiri, node, kanan -> terurut naik
            if (n == null) return;
            inorder(n.left, out);
            out.add(String.valueOf(n.data.nim));
            inorder(n.right, out);
        }

        void preorder(Node n, List<String> out) {     // node, kiri, kanan
            if (n == null) return;
            out.add(String.valueOf(n.data.nim));
            preorder(n.left, out);
            preorder(n.right, out);
        }

        void postorder(Node n, List<String> out) {    // kiri, kanan, node
            if (n == null) return;
            postorder(n.left, out);
            postorder(n.right, out);
            out.add(String.valueOf(n.data.nim));
        }

        // ---- JUMLAH NODE & TINGGI ----
        int jumlah(Node n) {
            if (n == null) return 0;
            return 1 + jumlah(n.left) + jumlah(n.right);
        }

        int tinggi(Node n) {
            if (n == null) return -1;
            return 1 + Math.max(tinggi(n.left), tinggi(n.right));
        }

        // ---- TAMPILAN POHON (miring ke kiri: kanan di atas, kiri di bawah) ----
        void cetakPohon(Node n, int level) {
            if (n == null) return;
            cetakPohon(n.right, level + 1);
            System.out.println("    ".repeat(level) + n.data.nim + " (" + n.data.nama + ")");
            cetakPohon(n.left, level + 1);
        }
    }

    // =====================================================================
    // PROGRAM UTAMA
    // =====================================================================

    static String gabung(List<String> list) {
        return String.join(", ", list);
    }

    static void garis(String judul) {
        System.out.println();
        System.out.println("=== " + judul + " ===");
    }

    public static void main(String[] args) {

        // ---------- BAGIAN 1: TREE ----------
        garis("BAGIAN 1: TREE - Struktur Folder Arsip Kampus");

        FolderNode root = new FolderNode("Arsip Kampus");
        FolderNode akademik = new FolderNode("Akademik");
        akademik.tambah(new FolderNode("Nilai")).tambah(new FolderNode("Jadwal"));
        FolderNode keuangan = new FolderNode("Keuangan");
        keuangan.tambah(new FolderNode("SPP"));
        FolderNode kemahasiswaan = new FolderNode("Kemahasiswaan");
        root.tambah(akademik).tambah(keuangan).tambah(kemahasiswaan);

        cetakFolder(root, 0);
        System.out.println("Jumlah folder : " + hitungFolder(root));   // 7
        System.out.println("Jumlah leaf   : " + hitungLeaf(root));     // 4
        System.out.println("Tinggi        : " + tinggiFolder(root));   // 2

        // ---------- BAGIAN 2: BST ----------
        garis("BAGIAN 2: BST - Data Mahasiswa berdasarkan NIM");

        BST bst = new BST();
        bst.insert(new Mahasiswa(50, "Andi", 3.50));
        bst.insert(new Mahasiswa(30, "Budi", 3.20));
        bst.insert(new Mahasiswa(70, "Citra", 3.80));
        bst.insert(new Mahasiswa(20, "Dewi", 3.10));
        bst.insert(new Mahasiswa(40, "Eko", 3.60));
        bst.insert(new Mahasiswa(60, "Fani", 3.40));
        bst.insert(new Mahasiswa(80, "Gita", 3.90));

        System.out.println("Bentuk pohon (root di kiri, kanan di atas):");
        bst.cetakPohon(bst.root, 0);

        garis("Traversal");
        List<String> hasil = new ArrayList<>();
        bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + gabung(hasil) + "   (terurut naik)");
        hasil.clear();
        bst.preorder(bst.root, hasil);
        System.out.println("Preorder  : " + gabung(hasil));
        hasil.clear();
        bst.postorder(bst.root, hasil);
        System.out.println("Postorder : " + gabung(hasil));

        garis("Informasi Pohon");
        System.out.println("Jumlah node : " + bst.jumlah(bst.root));   // 7
        System.out.println("Tinggi      : " + bst.tinggi(bst.root));   // 2
        System.out.println("NIM terkecil: " + bst.min());
        System.out.println("NIM terbesar: " + bst.max());

        garis("Pencarian");
        int[] dicari = {40, 99};
        for (int nim : dicari) {
            Mahasiswa m = bst.cari(nim);
            System.out.println("Cari " + nim + " -> " + (m != null ? m : "tidak ditemukan"));
        }

        garis("Insert NIM duplikat");
        boolean ok = bst.insert(new Mahasiswa(40, "Duplikat", 2.00));
        System.out.println("Insert NIM 40 lagi -> " + (ok ? "berhasil" : "ditolak (NIM sudah ada)"));

        garis("Hapus Data (3 kasus)");
        System.out.println("Hapus 20 (leaf)        : " + bst.hapus(20));
        System.out.println("Hapus 30 (1 anak: 40)  : " + bst.hapus(30));
        System.out.println("Hapus 50 (2 anak/root) : " + bst.hapus(50));
        System.out.println("Hapus 99 (tidak ada)   : " + bst.hapus(99));

        System.out.println();
        System.out.println("Bentuk pohon setelah penghapusan:");
        bst.cetakPohon(bst.root, 0);

        hasil.clear();
        bst.inorder(bst.root, hasil);
        System.out.println("Inorder   : " + gabung(hasil));
        System.out.println("Jumlah node: " + bst.jumlah(bst.root) + ", tinggi: " + bst.tinggi(bst.root));
    }
}
