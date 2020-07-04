ArrayList<Ball> punti = new ArrayList<Ball>();
PVector g = new PVector(0,98.1);

boolean lock = false;
float x1,y1,x2,y2;

void setup(){
  size(800,600);
  
  for(int i = 0; i < 4; i++) {
    Ball p = new Ball(random(width),300,random(60,400));
    punti.add(p);
  }
  
}

void draw(){
  background(255);
  for(Ball p : punti) {
    p.moove();
    p.draw();
    p.drag(0.5);
    p.applyForce(g);
    p.friction();
  }
  
  if(lock){
    stroke(255,0,0);
    strokeWeight(3);
    line(x1,y1,x2,y2);
    
    //in teoria un triangolo arientato
    pushMatrix();
    translate(x2, y2);
    float a = atan2(x1-x2, y2-y1);
    rotate(a);
    noStroke();
    fill(255,0,0);
    triangle(0,0,-10,-20,10,-20);
    popMatrix();
    
  }

}

void mousePressed() {
  x1 = mouseX;
  y1 = mouseY;
}
void mouseDragged(){
  x2 = mouseX;
  y2 = mouseY;
  lock = true;
}
void mouseReleased() {
  PVector r1 = new PVector(x1,y1);
  PVector r2 = new PVector(x2,y2);
  for(Ball p : punti) {
    p.applyForce(PVector.sub(r2,r1).mult(10));
  }

  x1 = 0;
  y1 = 0;
  x2 = 0;
  y2 = 0;
  lock = false;
}

void mouseClicked(){
  punti.add(new Ball(mouseX,mouseY,random(60,400)));
}
