class ScoreNote {
  private int x;
  private int judge;
  private Pointer pointer;
  private ArrayList<Integer> played_note = new ArrayList();

 ScoreNote(int x, int judge, Pointer pointer){
 	this.x = x;
 	this.judge = judge;
 	this.pointer = pointer;
 }

 public int getX() {
    return this.x;
  }
  public int Judge() {
    return this.judge;
  }
  public Pointer pointer() {
    return this.pointer;
  }

  public void addNote(int n)
  {
    if (n<-300) {
      n=0;
    } else if ((-300<=n)&&(n<-270)) {
      n=1;
    } else if ((-270<=n)&&(n<-240)) {
      n=2;
    } else if ((-240<=n)&&(n<-210)) {
      n=3;
    } else if ((-210<=n)&&(n<-180)) {
      n=4;
    } else if ((-180<=n)&&(n<-150)) {
      n=5;
    } else if ((-150<=n)&&(n<-120)) {
      n=6;
    } else if ((-120<=n)&&(n<-90)) {
      n=7;
    } else if ((-90<=n)&&(n<-60)) {
      n=8;
    } else if ((-60<=n)&&(n<-30)) {
      n=9;
    } else if ((-30<=n)&&(n<0)) {
      n=10;
    } else if ((0<=n)&&(n<30)) {
      n=11;
    } else if ((30<=n)&&(n<60)) {
      n=12;
    } else if ((60<=n)&&(n<90)) {
      n=13;
    } else if ((90<=n)&&(n<120)) {
      n=14;
    } else if ((120<=n)&&(n<150)) {
      n=15;
    } else if ((150<=n)&&(n<180)) {
      n=16;
    } else if ((180<=n)&&(n<210)) {
      n=17;
    } else if ((210<=n)&&(n<240)) {
      n=18;
    } else if ((240<=n)&&(n<270)) {
      n=19;
    } else if ((270<=n)&&(n<300)) {
      n=20;
    } else if (300<=n) {
      n=21;
    }
    played_note.add(n);
  }

  public int getNote(int m) {
    return this.played_note.get(m);
  }
}
