class Queue{
  private ArrayList l = new ArrayList();
  Queue(){ super(); }
  
  boolean isEmpty(){
    if( this.l.size() == 0 ) return true;
    else return false;
  }
  
  int size() { return this.l.size(); }
  
  void enqueue(Object e) { this.l.add(e); }
  
  void enqueue(Object...Elements) {
      for(Object e : Elements) this.l.add(e);
  }
  
  Object dequeue() { return this.l.remove(0); }
  
  Object first() { return this.l.get(0); }
  
  void show(){ print(this.l); }
  
}
