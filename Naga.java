public class Naga extends Musuh implements BisaTerbang, BisaLoot {

    public Naga() {
        super("Naga Hitam",500);
    }

    @Override
    public void serangPemain() {
        System.out.println(this.namaMusuh + " menyemburkan nafas api dari udara! Player -50 HP");
    }

    @Override
    public void suaraKhas() {
        System.out.println(this.namaMusuh + " ROAAAAAAARRRRRRRRR ");
    }

    @Override
    public void lepasLandas() {
        System.out.println(this.namaMusuh + " Terbang tinggi! Sulit diserang.");
    }

    @Override
    public void seranganUdara() {
        System.out.println(this.namaMusuh + " Menyemburkan badai api! Pemain -80 HP.");
    }

    @Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " Menabrak Musuh! ");
    }
}
