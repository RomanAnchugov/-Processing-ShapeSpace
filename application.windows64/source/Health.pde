class Health{
  private int posX;
  private int posY;  
  private PImage heartImage;
  private Player player;
  private int size;
  
  public Health(int posX, int posY, Player player){
    this.posX = posX;
    this.posY = posY;   
    this.player = player;
    size = 25;
    heartImage = loadImage("heartIcon.png");
  }
 public void draw(){
    for(int i = 0; i < player.getHealth(); i++){
      image(heartImage, posX, posY + i * size, size, size);
    }
 } 
}