import java.util.Scanner;

public class ArenaPertarungan {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Musuh[] gelombangMonster = new Musuh[3];
    gelombangMonster[0] = new Slime();
    gelombangMonster[1] = new Naga();
    gelombangMonster[2] = new Zombie();

    System.out.println("======================================");
    System.out.println("ARENA RPG: GELOMBANG MONSTER");
    System.out.println("=======================================\n");
    System.out.println("AWAS! Sekelompok monster menghadang Anda!");

    boolean isBermain = true;

    while (isBermain) {
      System.out.println("\n---STATUS MONSTER---");
      for (int i = 0; i < gelombangMonster.length; i++) {
        if (gelombangMonster[i].healthPoint > 0) {
          System.out.println(
              (i + 1) + "." + gelombangMonster[i].namaMusuh + " (HP : " + gelombangMonster[i].healthPoint + ")");
        } else {
          System.out.println((i + 1) + "." + gelombangMonster[i].namaMusuh + " (Mati)");
        }
      }
      System.out.println("4.Kabur dari pertarungan");
      System.out.println("\nPilih target monster yang ingin diserang (1/2/3) atau 4 untuk kabur : ");

      try {
        int pilihanTarget = input.nextInt();

        if (pilihanTarget == 4) {
          System.out.println("Anda lari terbirit - birit dari arena....");
          isBermain = false;
          continue;
        }
        if (pilihanTarget < 1 || pilihanTarget > 3) {
          System.out.println("Pilihan tidak valid! Anda membuang giliran.");
          continue;
        }
        int indeksTarget = pilihanTarget - 1;
        if (gelombangMonster[indeksTarget].healthPoint <= 0) {
          throw new TargetMatiException("Tindakan Ilegal : Anda tidak bisa menyerang monster yang sudah mati!");
        }
        System.out.println("Masukkan kekuatan serangan (10-100) : ");
        int power = input.nextInt();
        if (power < 10 || power > 100) {
          throw new SeranganTidakValidException("Kekuatan serangan harus di antara 10 hingga 100!");
        }

        System.out.println("\n>>> HASIL SERANGAN ANDA <<<");
        gelombangMonster[indeksTarget].terimaDamage(power);

        System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");
        for (int i = 0; i < gelombangMonster.length; i++) {
          if (gelombangMonster[i].healthPoint > 0) {
            Musuh monsterAktif = gelombangMonster[i];
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
            System.out.println(gelombangMonster[i].namaMusuh + " sudah mati dan tidak bisa menyerang ");
            if (gelombangMonster[i] instanceof BisaLoot) {
              BisaLoot monsterLoot = (BisaLoot) gelombangMonster[i];
              monsterLoot.jatuhkanItem();
            }
          }
        }
        System.out.println("--------------------------------------------------");
        boolean semuaMonsterMati = true;
        for (int i = 0; i < gelombangMonster.length; i++) {
          if (gelombangMonster[i].healthPoint > 0) {
            semuaMonsterMati = false;
            break;
          }
        }
        if (semuaMonsterMati) {
          System.out.println("\nSelamat! Anda telah menyapu bersih gelombang monster ini!");
          isBermain = false;
        }
      } catch (java.util.InputMismatchException e) {
        System.out.println("ERROR INPUT : Harap masukkan angka yang benar.");
        input.nextLine(); 
      } catch (TargetMatiException e) {
        System.out.println("KESALAHAN GAME : " + e.getMessage());
      } catch (SeranganTidakValidException e) {
        System.out.println("KESALAHAN GAME : " + e.getMessage());
      } catch (Exception e) {
        System.out.println("TERJADI KESALAHAN SISTEM : " + e.getMessage());
      }
    }
    input.close();
    System.out.println("Permainan Berakhir.");
  }
}
