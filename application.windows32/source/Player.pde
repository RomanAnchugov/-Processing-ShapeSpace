public class Player {

  private float posX;
  private float posY;
  private float radius;
  private float gravity;
  private float hSpeed;
  private float vSpeed;
  private float vGravity;
  private int health;


  //trail
  private int k;//length of trail
  private float trailPlayer[][];
  private int tI;

  //light
  private int lightRadius;

  public Player(float posX, float posY, float radius, float gravity) {
    this.posX = posX;
    this.posY = posY;
    this.radius = radius;
    this.gravity = gravity;
    hSpeed = 0;
    vSpeed = 0;
    vGravity = 0.1;
    health = 3;

    //trail
    k = 20;
    trailPlayer = new float[k][2];
    tI = 0;

    //light
    lightRadius = 13;
  }


  public void draw() {
    light();
    
    trailPlayer[tI][0] = posX;
    trailPlayer[tI][1] = posY;
    
    //отрисовка хвоста
    for (int i = k - 1; i >= 0; i--) {      
      fill(255, 255, 255, 255 / k * i);      
      ellipse(trailPlayer[i][0], trailPlayer[i][1], radius / k * i, radius / k * i);
    }
    
    
    if (tI == k - 1) {
      for (int i = 0; i < k - 1; i++) {
        trailPlayer[i][0] = trailPlayer[i + 1][0];
        trailPlayer[i][1] = trailPlayer[i + 1][1];
      }
    } else {
      tI++;
    }  
    
    fill(255);    
    ellipse(posX, posY, radius, radius);
    
    posY += hSpeed;
    hSpeed += gravity;
    
    if (vSpeed * vGravity / abs(vGravity) > 0) {      
      posX += vSpeed;
      vSpeed -= vGravity;
    } else {
      vSpeed = 0;
    }
    
    checkWindowBounds();
  }

  public void setHspeed(float hSpeed) {
    this.hSpeed = hSpeed;
  }
  
  public void setVertical(float vSpeed, float vGravity) {
    gravity = 0.3;
    this.vSpeed = vSpeed;
    this.vGravity = vGravity;
  }
  
  public void setPos(float posX, float posY) {
    this.posX = posX;
    this.posY = posY;
  }
  
  public void checkWindowBounds() {
    //1 - left , 2 - right
    if (posX - radius / 2 <= 0) {
      player.setVertical(random(4.5, 8.5), random(0.01, 0.03));
    } else if (posX + radius / 2 >= width) {
      player.setVertical(-random(4.5, 8.5), -random(0.01, 0.03));
    }
    if (posY + radius / 2 >= height) {

      hSpeed = 0;
      gravity = 0;
    }
  }
  
  public void light() {
    for (int i = lightRadius; i >= 1; i--) {
      fill(255, 255, 255, i);
      ellipse(posX, posY, i * 3 + 20, i * 3 + 20);
    }
  }
  
  public float getX() {
    return posX;
  } 
  public float getY() {
    return posY;
  }
  public float getRadius(){
    return radius;
  }
  public int getHealth(){
    return health;
  }
  public void setHealth(int health){
    this.health = health;
  }
}