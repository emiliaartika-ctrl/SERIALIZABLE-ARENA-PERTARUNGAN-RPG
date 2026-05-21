public class Zombie extends Musuh implements BisaLoot{
    
    public Zombie() {
      super("Zombie merangkak",150);  
    }

    @Override
    public void serangPemain(){
        System.out.println(this.namaMusuh + " melompat dan menggigit mangsa! Player -20 HP ");
    }
    
    @Override
    public void suaraKhas() {
        System.out.println(this.namaMusuh + " GRAPPHHHHH...........GRAPPHHH");
    }

    @Override
    public void jatuhkanItem() {
        System.out.println(this.namaMusuh + " Jatuhkan bola api!");
    }
}
