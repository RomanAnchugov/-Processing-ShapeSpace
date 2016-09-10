import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Dodge extends PApplet {

//TODO: \u043f\u043b\u0430\u043d\u0435\u0442\u044b \u043f\u0440\u043e\u043b\u0435\u0442\u0430\u0435\u0448\u044c, \u043c\u043e\u0436\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u043e\u043f

Player player;
Enemy enemy[];//\u043c\u0430\u0441\u0441\u0438\u0432 \u0432\u0440\u0430\u0433\u043e\u0432
Star star[];//\u043c\u0430\u0441\u0441\u0438\u0432 \u0437\u0432\u0451\u0437\u0434
Score score;
Health health;
int size = 15;//\u043c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0435 \u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0432\u0440\u0430\u0433\u043e\u0432

Button playBtn;

//timer
Timer timer;
int i;
final float LEFT_BORDER = 0.5f;
final float RIGHT_BORDER = 2.5f;
public static int SCORE;
public static int GAME_STATE;// 1 - menu, 2 - play, 3 - lose

public void setup() { 
  
  frameRate(60);
  ellipseMode(CENTER);
  rectMode(CENTER);   

  playBtn = new Button(width / 2, height / 2, 200, 200);//play button  
  playBtn.setColor(color(255));
  playBtn.setHoverColor(color(255, 255, 255, 200));
  playBtn.setClickColor(color(0, 255, 255, 255));
  playBtn.setImage(loadImage("1.png"));
  playBtn.setHoverImage(loadImage("2.png"));
  playBtn.setImageSize(150, 150);  
  
  player = new Player(width / 2, 0, 25, 0.3f);
  enemy = new Enemy[size];  
  star = new Star[100];
  score = new Score(10, 20, 20);
  health = new Health(width - 30, 10, player);
  
  for (int i = 0; i < size; i++) {
    enemy[i] = new Enemy(player);
  }
  for (int i = 0; i < 100; i++) {
    star[i] = new Star();
  }
  
  GAME_STATE = 1;
  SCORE = 0;

  i = 0;
  timer = new Timer(LEFT_BORDER, RIGHT_BORDER);
  
  
  noStroke();
}
public void draw() {
  background(0);
  
  //TIMER FOR SPAWN ENEMIES
  if (timer.update()) {
    enemy[i].setFlag(true);
    i++;
    if (i >= size) {
      i = 0;
    }
  }

  //DRAW OF OBJECTS
  for (int i = 0; i < 100; i++) {
    star[i].draw();//\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0437\u0432\u0451\u0437\u0434
  }
  for (int i = 0; i < size; i++) {
    enemy[i].draw();//\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0432\u0440\u0430\u0433\u043e\u0432
  }
  if (GAME_STATE == 2) {
    player.draw();//\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0438\u0433\u0440\u043e\u043a\u0430
  } 
  if(GAME_STATE == 1){
  playBtn.draw();//\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u043a\u043d\u043e\u043f\u043a\u0438 play
  }
  score.draw();
  health.draw();
}
public void mousePressed() {  

  //player controll
  if (GAME_STATE == 2) {
    if (mouseX <= width / 2) { //left   
      player.setHspeed(-random(4.5f, 5.5f));
      player.setVertical(-random(4.5f, 7.5f), -random(0.01f, 0.03f));
    }
    if (mouseX > width / 2) { //right
      player.setHspeed(-random(4.5f, 5.5f));
      player.setVertical(random(4.5f, 7.5f), random(0.01f, 0.03f));
    }
  }

  if (playBtn.checkClick() && GAME_STATE == 1) {    
    playBtn.fadeOut();        
    player.setPos(width / 2, height / 2);
    for (int i = 0; i < size; i++) {
      enemy[i].resetPos();
    }
  }
}
public class Button {
  private float x;//\u043f\u043e\u0437\u0438\u0446\u0438\u044f \u043f\u043e X
  private float y;//\u043f\u043e\u0437\u0438\u0446\u0438\u044f \u043f\u043e Y
  private float startX, startY;//\u043d\u0430\u0447\u0430\u043b\u044c\u043d\u044b\u0435 \u0445 \u0438 \u0443
  private float bWidth;//\u0448\u0438\u0440\u0438\u043d\u0430 \u043a\u043d\u043e\u043f\u043a\u0438
  private float bHeight;//\u0432\u044b\u0441\u043e\u0442\u0430 \u043a\u043d\u043e\u043f\u043a\u0438
  private float startWidth, startHeight;//\u043d\u0430\u0447\u0430\u043b\u044c\u043d\u044b\u0435 \u0448\u0438\u0440\u0438\u043d\u0430 \u0438 \u0432\u044b\u0441\u043e\u0442\u0430  
  private int bCurrentColor;
  private int bColor;//\u0446\u0432\u0435\u0442 \u043a\u043d\u043e\u043f\u043a\u0438
  private int bHoverColor;//\u0446\u0432\u0435\u0442 \u043a\u043d\u043e\u043f\u043a\u0438 \u043f\u0440\u0438 \u043d\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0438
  private int bClickColor;//\u0446\u0432\u0435\u0442 \u043a\u043d\u043e\u043f\u043a\u0438 \u043f\u0440\u0438 \u043a\u043b\u0438\u043a\u0435
  private PImage bCurrentImage;//\u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 \u0432\u043d\u0443\u0442\u0440\u0438 \u043a\u043d\u043e\u043f\u043a\u0438
  private PImage bIdleImage;//\u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 \u043f\u0440\u0438 \u0430\u0439\u0434\u043b \u043a\u043d\u043e\u043f\u043a\u0435
  private PImage bHoverImage;//\u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0430 \u043f\u0440\u0438 \u043d\u0430\u0432\u0435\u0434\u0451\u043d\u043d\u043e\u0439 \u043a\u043d\u043e\u043f\u043a\u0435
  private boolean shapeVisible;//\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u044c \u0448\u0435\u0439\u043f\u0430
  private boolean imageVisible;//\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u044c \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438
  private float imageWidth;//\u0448\u0438\u0440\u0438\u043d\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u043d\u0430 \u043a\u043d\u043e\u043f\u043a\u0435
  private float imageHeight;//\u0432\u044b\u0441\u043e\u0442\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u043d\u0430 \u043a\u043d\u043e\u043f\u043a\u0435
  private float startImageWidth, startImageHeight;//\u043d\u0430\u0447\u0430\u043b\u044c\u043d\u044b\u0435 \u0432\u044b\u0441\u043e\u0442\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u0438 \u0448\u0438\u0440\u0438\u043d\u0430 

  private boolean fadeOut = false;
  private float fadeOutSpeed = 12;

  //shape = 1 - circle
  //shape = 2 - rect
  private int shape;//\u0444\u043e\u0440\u043c\u0430 \u043a\u043d\u043e\u043f\u043a\u0438(\u043a\u0432\u0430\u0434\u0440\u0430\u0442, \u043a\u0440\u0443\u0433)

  public Button(float x, float y, float bWidth, float bHeight) {
    this.x = x;
    this.y =  y;
    this.startX = x;
    this.startY = y;
    this.bWidth = bWidth;
    this.bHeight = bHeight;
    this.startWidth = bWidth;
    this.startHeight = bHeight;
    this.imageWidth = bWidth;
    this.imageHeight = bHeight;
    this.startImageWidth = bWidth;
    this.startImageHeight = bHeight;
    shapeVisible = true;
    imageVisible = true;
    this.bColor = color(random(255), random(255), random(255));
    this.bHoverColor = color(random(255), random(255), random(255));
    this.bClickColor = color(random(255), random(255), random(255));
    this.shape = Shape.CIRCLE;
    bCurrentImage = null;
    bHoverImage = null;
    bIdleImage = null;
  }

  public void draw() {

    if (checkHover()) {
      bCurrentColor = bHoverColor;
      bCurrentImage = bHoverImage;
    } else {
      bCurrentColor = bColor;
      bCurrentImage = bIdleImage;
    }

    fill(bCurrentColor);      

    //\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0448\u0435\u0439\u043f\u0430 \u043a\u043d\u043e\u043f\u043a\u0438
    if (shapeVisible) {
      switch(shape) {
      case 1:
        ellipse(x, y, bWidth, bHeight);
        break;
      case 2:
        rect(x, y, bWidth, bHeight);
        break;
      }
    }
    //\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438   
    if (bCurrentImage != null && imageVisible) {
      image(bCurrentImage, x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight);
    }

    if (fadeOut) {
      fadeOut();
    }
  }

  //\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0444\u043e\u0440\u043c\u044b \u043a\u043d\u043e\u043f\u043a\u0438
  public void setShape(int shape) {
    this.shape = shape;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0446\u0432\u0435\u0442\u0430 \u043e\u0441\u043d\u043e\u0432\u043d\u043e\u0433\u043e
  public void setColor(int bColor) {
    this.bColor = bColor;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u043e\u0441\u043d\u043e\u0432\u043d\u043e\u0439
  public void setImage(PImage bImage) {
    this.bIdleImage = bImage;
    this.bHoverImage = bImage;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0446\u0432\u0435\u0442\u0430 \u043a\u043d\u043e\u043f\u043a\u0438 \u043f\u0440\u0438 \u043d\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0438
  public void setHoverColor(int bHoverColor) {
    this.bHoverColor = bHoverColor;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u043f\u0440\u0438 \u043d\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0438
  public void setHoverImage(PImage bHoverImage) {
    this.bHoverImage = bHoverImage;
  }
  //\u0446\u0432\u0435\u0442 \u043f\u0440\u0438 \u043a\u043b\u0438\u043a\u0435(doesnt work)
  public void setClickColor(int bClickColor) {
    this.bClickColor = bClickColor;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0440\u0430\u0437\u043c\u0435\u0440\u0430 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 
  public  void setImageSize(float imageWidth, float imageHeight) {   
    this.imageWidth = imageWidth;
    this.imageHeight = imageHeight;
    this.startImageWidth = imageWidth;
    this.startImageHeight = imageHeight;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438 \u0448\u0435\u0439\u043f\u0430
  public void setShapeViseble(boolean shapeVisible) {
    this.shapeVisible = shapeVisible;
  }
  //\u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438 \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0438 \u043d\u0430 \u043a\u043d\u043e\u043f\u043a\u0435
  public void setImageVisible(boolean imageVisible) {
    this.imageVisible = imageVisible;
  }

  public boolean checkHover() {
    if (dist(mouseX, mouseY, x, y) <= bWidth / 2 && dist(mouseX, mouseY, x, y) <= bHeight / 2) {
      return true;
    } else {
      return false;
    }
  }

  public boolean checkClick() {
    if (dist(mouseX, mouseY, x, y) <= bWidth / 2 && dist(mouseX, mouseY, x, y) <= bHeight / 2) {
      bCurrentColor = bClickColor;
      return true;
    } else {
      return false;
    }
  }

  public void fadeOut() {
    if (!fadeOut)fadeOut = true;
    if (fadeOut && x - bWidth <= width) {
      bCurrentColor = bClickColor;
      x += fadeOutSpeed;
      bWidth -= 5;
      bHeight -= 5;
      imageWidth -= 5;
      imageHeight -= 5;
    }         
    if(fadeOut && x - bWidth > width){
      GAME_STATE = 2;
      x = startX;
      y = startY; 
      bWidth = startWidth;
      bHeight = startHeight;
      imageWidth = startImageWidth;
      imageHeight = startImageHeight;
      fadeOut = false;
      player.setHealth(3);
      
    }
  }
  public void fadeIn(float from, float to) {
  }
}
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

    rotation = random(0.05f, 0.1f);
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


  //\u043f\u0440\u043e\u0432\u0435\u0440\u043a\u0430 \u0441\u0442\u043e\u043b\u043a\u043d\u043e\u0432\u0435\u043d\u0438\u044f \u0441 \u0438\u0433\u0440\u043e\u043a\u043e\u043c
  private void checkColl() {
    if (dist(player.getX(), player. getY(), posX, posY) <= player.getRadius() / 2 + size / 2 && GAME_STATE == 2) {
      posY = -size;
      posX = player.getX();        
      flag = false;
      trail.setLifeTime((int)random(15, 35));
      player.setHealth(player.getHealth() - 1);
      if (player.getHealth() <= 0) {
        GAME_STATE = 1;
      }
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
    vGravity = 0.1f;
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
    
    //\u043e\u0442\u0440\u0438\u0441\u043e\u0432\u043a\u0430 \u0445\u0432\u043e\u0441\u0442\u0430
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
    gravity = 0.3f;
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
      player.setVertical(random(4.5f, 8.5f), random(0.01f, 0.03f));
    } else if (posX + radius / 2 >= width) {
      player.setVertical(-random(4.5f, 8.5f), -random(0.01f, 0.03f));
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
class Score{
  private int posX;
  private int posY;
  private int size;
  private int tColor;
  
  public Score(int posX, int posY, int size){
    this.posX = posX;
    this.posY = posY;
    this.size = size;
    textSize(this.size);
    this.tColor = (int)random(155, 255);
  }
 public void draw(){
   fill(tColor);
   text("Score: " + SCORE, posX, posY);       
 }
 
 public void setColor(int tColor){
   this.tColor = tColor;
 } 
}
public static class Shape{
  public static int CIRCLE = 1;
  public static int RECTANGLE = 2;
}
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
//COMPLETE
public class Timer {
  private float border;
  private float delta;
  private float seconds;
  private float minutes;

  private float leftBorder;
  private float rightBorder;
  private float minusBorder;
  private float minBorder;

  public Timer(float leftBorder, float rightBorder) {   
    this.leftBorder = leftBorder;
    this.rightBorder = rightBorder;
    minBorder = 0.8f;
    border = random(leftBorder, rightBorder);
    minusBorder = 0.02f;
  }

  public boolean update() {
    delta++;
    seconds = delta / 60;    
    if (seconds == 1) {
      delta = 0;
      minutes++;
    }    
    if (seconds + minutes >= border) {
      seconds = 0;
      delta = 0;
      minutes = 0;

      border = random(leftBorder, rightBorder);     
      if (GAME_STATE == 2) {        
        if (leftBorder > minusBorder) {
          rightBorder -= minusBorder;
          leftBorder -= minusBorder;
        } else if (rightBorder > minBorder) {
          rightBorder -= minusBorder;
        }
      }
      return true;
    }
    return false;
  }
}
public class Trail {  
  private int lifeTime;  
  Enemy enemy;

  public Trail(int lifeTime, Enemy enemy) {    
    this.lifeTime = lifeTime;
    this.enemy = enemy;
  }

  public void draw() {    
    for (int i = 1; i <= lifeTime; i++) {
      fill(enemy.getColor1() - enemy.getColor1() / lifeTime * i, enemy.getColor2() - enemy.getColor2() / lifeTime * i, enemy.getColor3() - enemy.getColor3() / lifeTime * i, 155 - 155 / lifeTime * i);
      ellipse(enemy.getPosX(), enemy.getPosY() - i * 10, enemy.getSize() - enemy.getSize() / lifeTime * i, enemy.getSize() - enemy.getSize() / lifeTime * i);
    }
  }
  public void setLifeTime(int lifeTime) {
    this.lifeTime = lifeTime;
  }
}
  public void settings() {  size(480, 720);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Dodge" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
