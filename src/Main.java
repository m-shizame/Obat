import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.JSON.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class obat {

    public static void main(String[] args) {

        try {
            URL url = new URL("https://farmasi.mimoapps.xyz/mimoqss2auyqD1EAlkgZCOhiffSsFl6QqAEIGtM");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray obatArr = json.getJSONArray("obat");

            // Sorting by name, price, and stock quantity
            Obat[] obat = new Obat[obatArr.length()];
            for (int i = 0; i < obatArr.length(); i++) {
                JSONObject obj = obatArr.getJSONObject(i);
                obat[i] = new Obat(obj.getString("nama"), obj.getInt("harga"), obj.getInt("stok"));
            }

            Arrays.sort(obat, new Comparator<Obat>() {
                public int compare(Obat o1, Obat o2) {
                    if (!o1.nama.equals(o2.nama))
                        return o1.nama.compareToIgnoreCase(o2.nama);
                    if (o1.harga != o2.harga)
                        return o1.harga - o2.harga;
                    return o2.stok - o1.stok;
                }
            });

            // Print sorted result
            System.out.println("Data obat setelah diurutkan:");
            for (int i = 0; i < obat.length; i++) {
                System.out.println(obat[i].nama + ", " + obat[i].harga + ", " + obat[i].stok);
            }

            // Search by name or price
            Scanner scanner = new Scanner(System.in);
            System.out.println("Cari obat berdasarkan:");
            System.out.println("1. Abjad");
            System.out.println("2. Harga");
            System.out.print("Masukkan pilihan (1/2): ");
            int pilihan = scanner.nextInt();

            if (pilihan == 1) {
                System.out.print("Masukkan huruf awal obat yang ingin dicari: ");
                char c = scanner.next().charAt(0);
                System.out.println("Hasil pencarian obat berdasarkan huruf " + c + ":");
                for (int i = 0; i < obat.length; i++) {
                    if (obat[i].nama.toLowerCase().charAt(0) == Character.toLowerCase(c)) {
                        System.out.println(obat[i].nama + ", " + obat[i].harga + ", " + obat[i].stok);
                    }
                }
            } else if (pilihan == 2) {
                System.out.print("Masukkan harga maksimum obat yang ingin dicari: ");
                int maxHarga = scanner.nextInt();
                System.out.println("Hasil pencarian obat dengan harga maksimum " + maxHarga + ":");
                for (int i = 0; i < obat.length; i++) {
                    if (obat[i].harga <= maxHarga) {
                        System.out.println(obat[i].nama + ", " + obat[i].harga + ", " + obat[i].stok);
                    }
                }
            } else {
                System.out.println("Pilihan tidak valid.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}class Obat {
    public String nama;
    public int harga;
    public int stok;

    public Obat(String nama, int harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }
}
