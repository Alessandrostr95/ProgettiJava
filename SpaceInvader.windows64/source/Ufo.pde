class Ufo {
  int x;
  int y;
  int r = 30;
  int dirX = 1;
  boolean rimuovere = false;
  
  Ufo(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  void show(){
    fill(255,255,0);
    noStroke();
    ellipse(this.x, this.y, r, r);
  }
  
  void move(){
    this.x += this.dirX;
  }
  
  void cresci(){
    this.r += 1;
  }
  
  void spostaSotto(){
    this.dirX *= -1;
    this.y += r/2;
  }
  
  void rimuovi(){
    this.rimuovere = true;
  }
  
  void upspeed() {
    if(this.dirX < 0) this.dirX = this.dirX-1;
    else this.dirX = this.dirX+1;
  }
}
