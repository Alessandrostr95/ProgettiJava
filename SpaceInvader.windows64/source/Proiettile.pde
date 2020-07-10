class Proiettile {
  int x;
  int y;
  int r = 5;
  boolean rimuovere = false;
  color col = color(0,255,255);
  
  Proiettile(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  void show(){
    fill(col);
    noStroke();
    ellipse(this.x, this.y, r, r);
  }
  
  void moveUp(){
    this.y -= 5;
  }
  
  void moveDown(){
    this.y += 5;
  }
  
  boolean colpisce(Ufo U){
    float dist = dist(this.x,this.y, U.x, U.y);
    if( dist <= U.r/2 ) return true;
    return false;
  }
  
  void rimuovi(){
    this.rimuovere = true;
  }
}
