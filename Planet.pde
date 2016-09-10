class Planet{
  float x, y;
  float speed;
  float size;  
  color planetColor;
  int planetColor1;
  int planetColor2;
  int planetColor3;
  int craterColor1;
  int craterColor2;
  int craterColor3;
  int alpha;
  
  public Planet(){
    remake();
  }
  
  public void draw(){
    fill(planetColor);
    ellipse(x, y, size, size);
    y += speed;
    if(y - size > height){
      remake();
    }
  }
  private void remake(){    
    size = random(150, 500);
    y = -size - 20;
    speed = random(1, 2);
    if((int)random(100) % 2 == 0){
      x = 0;            
    }else{
      x = width;
    }    
    planetColor1 = (int)random(255);
    planetColor2 = (int)random(255);
    planetColor3 = (int)random(255);
    alpha = (int)random(100, 255);
    planetColor = color(planetColor1, planetColor2, planetColor3, alpha);      
  }
  
}