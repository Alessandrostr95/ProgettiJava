Snake s = new Snake();
Wall w = new Wall();
int risoluzione = 20;
int righe;
int colonne;
int xFood;
int yFood;
int rate = 10;
boolean alive = true;
int score = 0;


void setup() {
  size(1000,800);
  righe = height / risoluzione;
  colonne = width / risoluzione;
  s.setSpace(righe,colonne);
  xFood = floor(random(0,colonne));
  yFood = floor(random(0,righe));
}

void draw(){
  frameRate(rate/2);
  background(0);
  
  w.show(risoluzione);
  if(alive)s.show(risoluzione);
  else if( !alive ) {
    s.c = color(255,0,0);
    s.show(risoluzione);
    fill(100);
    stroke(255,250,0);
    strokeWeight(10);
    rect(width/4,height/4,500,300);
    
    textSize(50);
    fill(255,250,0);
    textAlign(CENTER,BOTTOM);
    text("SCORE: " + score, width/2,height/2-25); 

    
    noLoop();
  }
  
  for(int i = 0; i < righe; i++){
    for(int j = 0; j < colonne; j++){
      stroke(255,100);
      strokeWeight(0.5);
      noFill();
      rect(j * risoluzione, i * risoluzione, risoluzione, risoluzione);
      fill(255,250,0);
      noStroke();
      rect(xFood * risoluzione + 3, yFood * risoluzione + 3, risoluzione - 6, risoluzione - 6);
      
      if(s.x == xFood && s.y == yFood) {
          xFood = floor(random(0,colonne));
          yFood = floor(random(0,righe));
          score += 10;
          s.mangia();
          rate ++;
      }
      
    }
  }
  if(!s.moove() || w.intersect(s.x*risoluzione,s.y*risoluzione)) {
    alive = false;
  }

}

void mousePressed(){
  w.add(mouseX - mouseX%risoluzione,mouseY - mouseY%risoluzione);
}


void keyPressed(){
  if(keyCode == UP) {
    s.dir(0,-1);
  } else if(keyCode == DOWN) {
    s.dir(0,1);
  } else if(keyCode == RIGHT) {
    s.dir(1,0);
  } else if(keyCode == LEFT) {
    s.dir(-1,0);
  }
}

/*
void keyPressed(){
  switch(key){
    case 'w':  x = (x - 1 + righe) % righe; break;
    case 's':  x = (x + 1 + righe) % righe; break;
    case 'a':  y = (y - 1 + colonne) % colonne; break;
    case 'd':  y = (y + 1 + colonne) % colonne; break;
  }
}
*/
