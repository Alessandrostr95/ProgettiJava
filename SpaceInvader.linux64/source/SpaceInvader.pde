Navicella ship;

ArrayList<Ufo> ufo = new ArrayList<Ufo>();
ArrayList<Proiettile> proiettili = new ArrayList<Proiettile>();
ArrayList<Proiettile> proiettiliNemici = new ArrayList<Proiettile>();
boolean loose = false;

int t = 0;

void setup(){
  size(800,600);
  ship = new Navicella();
  for(int i = 0; i < 9; i++){
    ufo.add(new Ufo(i*80+80,50));
  }
  for(int i = 0; i < 9; i++){
    ufo.add(new Ufo(i*80+80,100));
  }
  for(int i = 0; i < 9; i++){
    ufo.add(new Ufo(i*80+80,150));
  }
}

void draw(){
  background(50);
  
  ship.show();
  ship.move();
  
  boolean bordo = false; 
  
  /*Disegna ufo ed eventualmente spara un proiettile*/
  for(Ufo u: ufo){
    u.show();
    u.move();
    if(u.x + u.r/2 > width || u.x - u.r/2 < 0) bordo = true;
    if(u.y > height-50) loose = true;
    
    /* casulamente spara un proiettille cattivo */
    if(random(0,1000) > 999-t) {
      pew(u);
    }
  }

  /*se un ufo tocca un bordo l'intera flotta scende di un livello e cambia direzione*/  
  if(bordo){
    for(Ufo u: ufo){
      u.spostaSotto();
    }
  }
  
  /*disegna i proiettili nimici*/
  for(Proiettile p : proiettiliNemici){
    p.show();
    p.moveDown();
    if(p.y > height - 40 && p.x >= ship.x && p.x <= ship.x+40 && p.y < height) loose = true;
    if(p.y > height) p.rimuovere = true;
  }
  
  /* rimuove i proiettili nemici che escono fuori dallo schermo */
  for(int i = proiettiliNemici.size()-1; i>= 0; i--){
    if(proiettiliNemici.get(i).rimuovere){
      proiettiliNemici.remove(i);
    }
  }
  
  
  /* disegna proiettili e controlla quando colpisce un ufo */
  for(Proiettile p : proiettili){
    p.show();
    p.moveUp();
    
    /* controlla se un proiettile colpisce un ufo */
    for(Ufo u: ufo){
      if(p.colpisce(u)) {
        u.rimuovi();
        p.rimuovi();
      }
    }
    /* controlla se un proiettile esce dallo schermo */
    if(p.y < 0){
      p.rimuovi();
    }
  }
  

  /* rimuove i proiettili che colpiscono qualcosa o escono fuori dallo schermo */
  for(int i = proiettili.size()-1; i>= 0; i--){
    if(proiettili.get(i).rimuovere){
      proiettili.remove(i);
    }
  }
  
  /* rimuovere gli ufo */
  for(int i = ufo.size()-1; i>= 0; i--){
    if(ufo.get(i).rimuovere){
      ufo.remove(i);
    }
  }

  if(loose){
    background(50);
    textSize(40);
    fill(255,255,0);
    text("GAME OVER",width/2-100,height/2);
    noLoop();
  }
  
  if(ufo.size() == 0){
    background(50);
    textSize(40);
    fill(255,255,0);
    text("YOU WIN",width/2-100,height/2);
    noLoop();
  }
  
  
  /*incremento la velocita*/
  if(frameCount == 1166){
    for(Ufo u: ufo) u.upspeed();
    t+=2;
    //println("lv1");
  }
  
  if(frameCount == 2332){
    for(Ufo u: ufo) u.upspeed();
    t+=3;
    //println("lv2");
  }
  
  if(frameCount == 3500){
    for(Ufo u: ufo) u.upspeed();
    t+=10;
    //println("lv3");
  }
  
}

void pew(Ufo u) {
  Proiettile p = new Proiettile(u.x+u.r/2,u.y+u.r/2);
  p.col = color(255,0,0);
  proiettiliNemici.add(p);
}

void keyReleased(){
  if(key != ' ')
    ship.setVerso(0);
}

void keyPressed() {
  if(key == ' ') {
    proiettili.add(new Proiettile(ship.x + 20, height-40));
  }
  
  if(keyCode == RIGHT) ship.setVerso(1);
  else if(keyCode == LEFT) ship.setVerso(-1);
}
