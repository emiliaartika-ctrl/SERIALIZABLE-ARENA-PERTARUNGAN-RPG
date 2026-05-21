import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ArenaPertarunganDinamis {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    ArrayList<Musuh> gelombangMonster = new ArrayList<>();
    gelombangMonster.add(new Slime());
    gelombangMonster.add(new Naga());
    gelombangMonster.add(new Zombie());

    System.out.println("======================================");
    System.out.println("ARENA RPG: GELOMBANG MONSTER");
    System.out.println("=======================================\n");
    System.out.println("AWAS! Sekelompok monster menghadang Anda!");

    boolean isBermain = true;

    while (isBermain && !gelombangMonster.isEmpty()) {
      System.out.println("\n---STATUS MONSTER---");
      for (int i = 0; i < gelombangMonster.size(); i++) {
        Musuh m = gelombangMonster.get(i);
        System.out.println(
            (i + 1) + "." + m.namaMusuh + " (HP : " + m.healthPoint + ")");
      }
      System.out.println("------------------------------");
      System.out.println("8.[SAVE GAME] Simpan progress pertarungan");
      System.out.println("9.[LOAD GAME] Muat progress pertarungan sebelumnya");
      System.out.println("0.Kabur dari pertarungan");
      System.out.println(
          "\nPilih target monster yang ingin diserang (1-" + gelombangMonster.size() + ") atau aksi lainnya : ");

      try {
        int pilihanTarget = input.nextInt();

        if (pilihanTarget == 0) {
          System.out.println("Anda lari terbirit - birit dari arena....");
          isBermain = false;
          continue;
        }
        else if (pilihanTarget == 8) {
          try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame_rpg.dat"))) {
            out.writeObject(gelombangMonster);
            System.out.println(">>> BERHASIL : Game telah disimpan! <<<");
          } catch (IOException e) {
            System.out.println("GAGAL : Terjadi kesalahan saat menyimpan game. " + e.getMessage());
          }
          continue;
        }
        else if (pilihanTarget == 9) {
          try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame_rpg.dat"))) {
            gelombangMonster = (ArrayList<Musuh>) ois.readObject();
            System.out.println(">>> BERHASIL : Game berhasil dimuat! <<<");
          } catch (FileNotFoundException e) {
            System.out.println(">>> GAGAL : File save game belum ada. Silahkan save game terlebih dahulu!");
          } catch (IOException | ClassNotFoundException e) {
            System.out.println(">>> GAGAL : Terjadi kesalahan saat membaca file save. " + e.getMessage());
          }
          continue;
        }
        if (pilihanTarget < 1 || pilihanTarget > gelombangMonster.size()) {
          System.out.println("Pilihan tidak valid! Anda membuang giliran.");
          continue;
        }
        int indeksTarget = pilihanTarget - 1;
        Musuh target = gelombangMonster.get(indeksTarget);

        System.out.println("Masukkan kekuatan serangan (10-100) : ");
        int power = input.nextInt();
        if (power < 10 || power > 100) {
          throw new SeranganTidakValidException("Kekuatan serangan harus di antara 10 hingga 100!");
        }

        System.out.println("\n>>> HASIL SERANGAN ANDA <<<");
        target.terimaDamage(power);

        if (target.healthPoint <= 0) {
          System.out.println(target.namaMusuh + " hancur menjadi debu!");

          if (target instanceof BisaLoot) {
            BisaLoot monsterLoot = (BisaLoot) target;
            monsterLoot.jatuhkanItem();
          }
          gelombangMonster.remove(indeksTarget);
        }

      } catch (Exception e) {
        System.out.println("Terjadi kesalahan input, silahkan coba lagi.");
        input.nextLine();
        continue;
      }

      if (gelombangMonster.isEmpty()) {
        System.out.println("\nSELAMAT! Semua monster telah dibersihkan!");
        break;
      }

      System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");
      for (int i = 0; i < gelombangMonster.size(); i++) {
        if (gelombangMonster.get(i).healthPoint > 0) {
          Musuh monsterAktif = gelombangMonster.get(i);
          monsterAktif.suaraKhas();

          if (monsterAktif instanceof BisaTerbang) {
            System.out.println("[PERINGATAN SERANGAN UDARA TERDETEKSI]");
            BisaTerbang monsterTerbang = (BisaTerbang) monsterAktif;
            monsterTerbang.lepasLandas();
            monsterTerbang.seranganUdara();
          } else {
            monsterAktif.serangPemain();
          }
        } else {
          System.out.println(gelombangMonster.get(i).namaMusuh + " sudah mati dan tidak bisa menyerang ");
          if (gelombangMonster.get(i) instanceof BisaLoot) {
            BisaLoot monsterLoot = (BisaLoot) gelombangMonster.get(i);
            monsterLoot.jatuhkanItem();
          }
        }
      }
      System.out.println("--------------------------------------------------");
      boolean semuaMonsterMati = true;
      for (int i = 0; i < gelombangMonster.size(); i++) {
        if (gelombangMonster.get(i).healthPoint > 0) {
          semuaMonsterMati = false;
          break;
        }
      }
      if (semuaMonsterMati) {
        System.out.println("\nSelamat! Anda telah menyapu bersih gelombang monster ini!");
        isBermain = false;
      }
    }
    input.close();
    System.out.println("Permainan Berakhir.");
  }
}
