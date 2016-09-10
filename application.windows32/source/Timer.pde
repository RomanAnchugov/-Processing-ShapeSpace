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
    minBorder = 0.8;
    border = random(leftBorder, rightBorder);
    minusBorder = 0.02;
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