public class Enemy {
  float posX;
  float posY;
  float size;
  float speed;
  float deg;
  boolean flag;
  int sign;
  float rotation;

  int color1;
  int color2;
  int color3;  

  private Player player;
  private Trail trail;

  public Enemy(Player player) {

    rotation = random(0.05, 0.1);
    this.player = player;
    trail = new Trail(30, this);
    posX = player.getX();    
    size = random(40, 70);
    posY = -size - 30;
    speed = random(5, 8);
    deg = 0;
    flag = false;
    if ((int)random(100) <= 50) {
      sign = 1;
    } else {
      sign = -1;
    }

    color1 = (int)random(100, 255);
    color2 = (int)random(20);
    color3 = (int)random(100, 255);
  }
  public void draw() {
    checkColl();

    trail.draw();
    fill(color1, color2, color3);
    if (flag) {
      posY += speed;
      translate(posX, posY);
      rotate(deg);
      deg += rotation * sign;
      rect(0, 0, size, size);
      resetMatrix();
      if (posY - size - trail.lifeTime * 2 > height) {
        posY = -size - 50;               
        flag = false;
        trail.setLifeTime((int)random(15, 35));
        if (GAME_STATE == 2) {
          SCORE++;
        }
      }
    }
    int n = 2;
    switch((int)random(3)) {
    case 1:
      if (color1 < 255 - n) color1 += n;
      else color1 = 0;
      break;
    case 2:
      if (color2 < 255 - n) color2 += n;
      else color2 = 0;
      break;
    case 3:
      if (color3 < 255 - n) color3 += n;
      else color3 = 0;
      break;
    }
  }
  public void setFlag(boolean flag) {        
    this.flag = flag;
    if (GAME_STATE == 2) {      
      posX = player.getX();
    } else if (GAME_STATE == 1 || GAME_STATE == 3) {      
      posX = random(width);
    }
  }

  public void resetPos() {
    flag = false;
    posY = random(-200, -100);
  }


  //проверка столкновения с игроком
  private void checkColl() {
    if (dist(player.getX(), player. getY(), posX, posY) <= player.getRadius() / 2 + size / 2 && GAME_STATE == 2) {
      posY = -size;
      posX = player.getX();        
      flag = false;
      trail.setLifeTime((int)random(15, 35));
      player.setHealth(player.getHealth() - 1);
      if (player.getHealth() <= 0) {
        GAME_STATE = 1;
        bestScore.resetColor();
        saveScore();
        SCORE = 0;
      }
    }
  }

  //сохранение лучшего результата
  private void saveScore() {
    if(SCORE > fileScore){
      PrintWriter output = createWriter("data/.score");
      output.println(SCORE);
      fileScore = SCORE;
      output.flush();
      output.close();
    }
  }

  public float getRotate() {
    return deg;
  }
  public float getPosX() {
    return posX;
  }
  public float getPosY() {
    return posY;
  }
  public float getSize() {
    return size;
  }
  public int getColor1() {
    return color1;
  }
  public int getColor2() {
    return color2;
  }
  public int getColor3() {
    return color3;
  }
}