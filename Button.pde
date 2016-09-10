public class Button {
  private float x;//позиция по X
  private float y;//позиция по Y
  private float startX, startY;//начальные х и у
  private float bWidth;//ширина кнопки
  private float bHeight;//высота кнопки
  private float startWidth, startHeight;//начальные ширина и высота  
  private color bCurrentColor;
  private color bColor;//цвет кнопки
  private color bHoverColor;//цвет кнопки при наведении
  private color bClickColor;//цвет кнопки при клике
  private PImage bCurrentImage;//картинка внутри кнопки
  private PImage bIdleImage;//картинка при айдл кнопке
  private PImage bHoverImage;//картинка при наведённой кнопке
  private boolean shapeVisible;//видимость шейпа
  private boolean imageVisible;//видимость картинки
  private float imageWidth;//ширина картинки на кнопке
  private float imageHeight;//высота картинки на кнопке
  private float startImageWidth, startImageHeight;//начальные высота картинки и ширина 

  private boolean fadeOut = false;
  private float fadeOutSpeed = 12;

  //shape = 1 - circle
  //shape = 2 - rect
  private int shape;//форма кнопки(квадрат, круг)

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

    //отрисовка шейпа кнопки
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
    //отрисовка картинки   
    if (bCurrentImage != null && imageVisible) {
      image(bCurrentImage, x - imageWidth / 2, y - imageHeight / 2, imageWidth, imageHeight);
    }

    if (fadeOut) {
      fadeOut();
    }
  }

  //Установка формы кнопки
  public void setShape(int shape) {
    this.shape = shape;
  }
  //установка цвета основного
  public void setColor(color bColor) {
    this.bColor = bColor;
  }
  //установка картинки основной
  public void setImage(PImage bImage) {
    this.bIdleImage = bImage;
    this.bHoverImage = bImage;
  }
  //установка цвета кнопки при наведении
  public void setHoverColor(color bHoverColor) {
    this.bHoverColor = bHoverColor;
  }
  //установка картинки при наведении
  public void setHoverImage(PImage bHoverImage) {
    this.bHoverImage = bHoverImage;
  }
  //цвет при клике(doesnt work)
  public void setClickColor(color bClickColor) {
    this.bClickColor = bClickColor;
  }
  //установка размера картинки 
  public  void setImageSize(float imageWidth, float imageHeight) {   
    this.imageWidth = imageWidth;
    this.imageHeight = imageHeight;
    this.startImageWidth = imageWidth;
    this.startImageHeight = imageHeight;
  }
  //установка видимости шейпа
  public void setShapeViseble(boolean shapeVisible) {
    this.shapeVisible = shapeVisible;
  }
  //установка видимости картинки на кнопке
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
}