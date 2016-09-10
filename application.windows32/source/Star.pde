public class Star{
  private int posX;
  private int posY;
  private float speed;
  private int color1;
  private int radius;
 
  
  public Star(){
    posX = (int)random(width);
    posY = (int)random(-1000, -100);
    speed = random(1, 3);
    color1 = (int)random(70,255);
    radius = (int) random(10);
    
  }
  public void draw(){    
    fill(color1);
    
    ellipse(posX, posY, radius, radius);
    posY += speed;
    if(posY + radius >= height){
      posY = (int)random(-1000, -100);  
    }    
  }
}