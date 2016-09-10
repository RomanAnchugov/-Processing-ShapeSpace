//TODO: Flex size

Player player;
Enemy enemy[];//массив врагов
Star star[];//массив звёзд
Score score;
BestScore bestScore;
Health health;
int size = 15;//максимальное количество врагов
int display_width;
int display_height;

Planet planet;

public static int fileScore;
int fileScoreLength;

Button playBtn;

//timer
Timer timer;
int i;
final float LEFT_BORDER = 0.5;
final float RIGHT_BORDER = 2.5;
public static int SCORE;
public static int GAME_STATE;// 1 - menu, 2 - play, 3 - lose

public static int WIDTH_COEF;
public static int HEIGHT_COEF;

void setup() { 
  //size(displayWidth, displayHeight);//для андройда
  //size(480, 720);
  //size(720, 1280);
  size(576, 864);

  //считывание лучшего счёта
  BufferedReader reader = createReader("data/.score"); 
  fileScoreLength = 0;
  try {
    fileScore = Integer.parseInt(reader.readLine());
    int dop = fileScore;
    while (dop != 0) {
      fileScoreLength++;
      dop /= 10;
    }    
  } 
  catch (IOException e) {
    e.printStackTrace();
  }

  frameRate(60);
  ellipseMode(CENTER);
  rectMode(CENTER);   

  WIDTH_COEF = width / 480;
  HEIGHT_COEF = height / 720;

  //настройка кнопки плей
  playBtn = new Button(width / 2, height / 2, 200, 200);//play button  
  playBtn.setColor(color(255));
  playBtn.setHoverColor(color(255, 255, 255, 200));
  playBtn.setClickColor(color(0, 255, 255, 255));
  playBtn.setImage(loadImage("1.png"));
  playBtn.setHoverImage(loadImage("2.png"));
  playBtn.setImageSize(150, 150);  

  player = new Player(width / 2, 0, 25, 0.3);
  enemy = new Enemy[size];  
  star = new Star[100];
  score = new Score(10, 20, 20);
  bestScore = new BestScore(width / 2 - (50 + fileScoreLength * 9) * WIDTH_COEF, height / 2 + 140 * HEIGHT_COEF, 32);
  health = new Health(width - 30, 10, player);

  planet = new Planet();

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

  smooth();
  noStroke();
}

//ГЛАВНЫЙ ЦИКЛ
void draw() {  
  background(0);

  planet.draw();

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
    star[i].draw();//отрисовка звёзд
  }
  for (int i = 0; i < size; i++) {
    enemy[i].draw();//отрисовка врагов
  }
  if (GAME_STATE == 2) {
    player.draw();//отрисовка игрока
  } 
  if (GAME_STATE == 1) {
    playBtn.draw();//отрисовка кнопки play    
    bestScore.draw();
  }
  score.draw();  
  health.draw();
}
void mousePressed() {  

  //player controll
  if (GAME_STATE == 2) {
    if (mouseX <= width / 2) { //left   
      player.setHspeed(-random(4.5, 5.5));
      player.setVertical(-random(4.5, 7.5), -random(0.01, 0.03));
    }
    if (mouseX > width / 2) { //right
      player.setHspeed(-random(4.5, 5.5));
      player.setVertical(random(4.5, 7.5), random(0.01, 0.03));
    }
  }

  if (playBtn.checkClick() && GAME_STATE == 1) {    
    playBtn.fadeOut();  
    bestScore.fadeOut();
    player.setPos(width / 2, height / 2);
    for (int i = 0; i < size; i++) {
      enemy[i].resetPos();
    }
  }
}