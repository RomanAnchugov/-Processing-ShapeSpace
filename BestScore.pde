class BestScore{
  private int posX;
  private int posY;
  private int size;  
  private int integerColor;
  private boolean fadeOut;
    
  public BestScore(int posX, int posY, int size){
    this.posX = posX;
    this.posY = posY;
    this.size = size;
    textSize(this.size);
    integerColor = (int)random(155, 255);    
    fadeOut = false;
  }
 public void draw(){      
   fill(integerColor);
   textSize(this.size);
   text("Best: " + fileScore, posX, posY);  
   if(fadeOut){
     fadeOut();
   }   
 }  
 public void resetColor(){
   integerColor = (int)random(155, 255);
 }
 public void fadeOut(){
    if(!fadeOut)fadeOut = true;
    if(fadeOut && integerColor >= 10){
      integerColor -= 10;      
    }
    if(fadeOut && integerColor < 10){
      fadeOut = false;                                        
    }
 }
}