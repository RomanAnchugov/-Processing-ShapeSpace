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