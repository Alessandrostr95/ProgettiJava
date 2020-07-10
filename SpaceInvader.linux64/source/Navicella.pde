class Navicella {
  int x = width/2;
  int verso = 0;
  Navicella(){super();}
  
  void show(){
    fill(255);
    square(this.x, height-40, 40);
  }
  
  void setVerso(int dir){
    this.verso = dir;
  }
  
  void move() {
    this.x += this.verso*5;
    if(this.x+40 > width) this.x = width - 40;
    if(this.x < 0) this.x = 0;
  }
  
}
