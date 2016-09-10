class Score{
  private int posX;
  private int posY;
  private int size;
  private color tColor;
  
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
 
 public void setColor(color tColor){
   this.tColor = tColor;
 } 
}