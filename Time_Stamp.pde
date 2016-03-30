//ユーザの入力した音の値と、時間を記録する
import themidibus.*; //Import the library
import javax.sound.midi.MidiMessage; //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html
import javax.sound.midi.SysexMessage;
import javax.sound.midi.ShortMessage;

//主に楽譜の音を管理する用
ScoreNote[][]note = new ScoreNote[4][8];//note[y軸向きに段数][x軸向きに音数
int note_y, note_x = 0;

//midi用
MidiBus myBus; //The MidiBus
int pitchbend, notebus_different=0;//note_yは段落数、note_xで段落内の何番目を弾いているか管理

int channel = 0;
int pitch = 64;
int velocity = 127;
int status_byte = 0xA0; // For instance let us send aftertouch
int channel_byte = 0; // On channel 0 again
int first_byte = 64; // The same note;
int second_byte = 80; // But with less velocity

ArrayList<ScoreNote> played_note;//pitchbendで得たどの程度ずれているかを入れるための配列を用意

//時刻
boolean flag = false;

//txtファイル出力に必要な配列
ArrayList<String> note_number = new ArrayList<String>();
ArrayList<String> now_number = new ArrayList<String>();
ArrayList<String> count = new ArrayList<String>();
ArrayList<String> note_velocity = new ArrayList<String>();
ArrayList<String> result = new ArrayList<String>();
float mill;

void setup() {
//画面
 fullScreen(P2D); // 画面サイズを決定

//midibus用
  MidiBus.list(); // List all available Midi devices on STDOUT. This will show each device's index and name.
  myBus = new MidiBus(this, 0, 0); // Create a new MidiBus object

  //Pointer NoteName = new Pointer (NoteNumber, PointerX, PointerY);
 Pointer A4 = new Pointer(69, -200, -200);
 Pointer B4 = new Pointer(71, 411, 459);
 Pointer C5 = new Pointer(73, 417, 554);
 Pointer D5 = new Pointer(74, 419, 594);
 Pointer E5 = new Pointer(76, -200, -200);
 Pointer F5 = new Pointer(78, 431, 463);
 Pointer G5 = new Pointer(79, 432, 513);
 Pointer A5 = new Pointer(81, 436, 604);

//note[note_y][note_x] = new Note(all_score_PositionX, ×の初期設定, NoteName);
  note[0][0] = new ScoreNote(919, 0, A4);
  note[0][1] = new ScoreNote(1044, 0, B4);
  note[0][2] = new ScoreNote(1172, 0, C5);
  note[0][3] = new ScoreNote(1299, 0, D5);
  note[0][4] = new ScoreNote(1443, 0, E5);
  note[0][5] = new ScoreNote(1577, 0, F5);
  note[0][6] = new ScoreNote(1712, 0, G5);
  note[0][7] = new ScoreNote(1846, 0, A5);

  note[1][0] = new ScoreNote(919, 0, A4);
  note[1][1] = new ScoreNote(1044, 0, B4);
  note[1][2] = new ScoreNote(1172, 0, C5);
  note[1][3] = new ScoreNote(1299, 0, D5);
  note[1][4] = new ScoreNote(1443, 0, E5);
  note[1][5] = new ScoreNote(1577, 0, F5);
  note[1][6] = new ScoreNote(1712, 0, G5);
  note[1][7] = new ScoreNote(1846, 0, A5);

  note[2][0] = new ScoreNote(919, 0, A4);
  note[2][1] = new ScoreNote(1044, 0, B4);
  note[2][2] = new ScoreNote(1172, 0, C5);
  note[2][3] = new ScoreNote(1299, 0, D5);
  note[2][4] = new ScoreNote(1443, 0, E5);
  note[2][5] = new ScoreNote(1577, 0, F5);
  note[2][6] = new ScoreNote(1712, 0, G5);
  note[2][7] = new ScoreNote(1846, 0, A5);

  note[3][0] = new ScoreNote(919, 0, A4);
  note[3][1] = new ScoreNote(1044, 0, B4);
  note[3][2] = new ScoreNote(1172, 0, C5);
  note[3][3] = new ScoreNote(1299, 0, D5);
  note[3][4] = new ScoreNote(1443, 0, E5);
  note[3][5] = new ScoreNote(1577, 0, F5);
  note[3][6] = new ScoreNote(1712, 0, G5);
  note[3][7] = new ScoreNote(1846, 0, A5);

  myBus.sendNoteOn(channel, pitch, velocity); // Send a Midi noteOn
  myBus.sendNoteOff(channel, pitch, velocity); // Send a Midi nodeOff
  myBus.sendMessage(status_byte, channel_byte, first_byte, second_byte);
  myBus.sendMessage(
    new byte[] {
    (byte)0xF0, (byte)0x1, (byte)0x2, (byte)0x3, (byte)0x4, (byte)0xF7
    }
    );
  try { 
    SysexMessage message = new SysexMessage();
    message.setMessage(
      0xF0, 
      new byte[] {
      (byte)0x5, (byte)0x6, (byte)0x7, (byte)0x8, (byte)0xF7
      }, 
      5
      );
    myBus.sendMessage(message);
  } catch(Exception e) {
  }
 
}

void draw(){
 background(0);
 mill = millis(); 

/*
note on/off 演奏位置    ユーザが入力した音番号       音番号を音名に変換         ベロシティ     譜面上の正解音名            データが出力された時刻
ON      1    67                                            G5                          72                     C5                               10:32:52:0675
*/
}

//midibusを管理している
void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data, String bus_name) 
  println();
  print("Status Byte/MIDI Command:"+(int)(data[0] & 0xFF));
  if (((int)(data[0] & 0xFF) >= 224)&&((int)(data[0] & 0xFF) <= 227)) {
    pitchbend = (int)(data[2] & 0xFF) * 128 + (int)(data[1] & 0xFF);
  } 
 for (int i = 1; i < data.length; i++) {
    print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
 }
 for (int i = 1; i < data.length; i++) {
 print(": "+(i+1)+": "+(int)(data[i] & 0xFF));
  }
if (((int)(data[0] & 0xFF) >= 144)&&((int)(data[0] & 0xFF) <= 171)) {
    notebus_different=((data[1] & 0xFF)-(note[note_y][note_x].pointer()).MidiValue())*333+pitchbend-8192;
    note[note_y][note_x].addNote(notebus_different);
  }
if(((int)(data[0] & 0xFF) >= 143)&&((int)(data[0] & 0xFF) <= 150)) {
  //println("velocity:" +(int)(data[2] & 0xFF));
  note_velocity.add(Integer.toString((int)(data[2] & 0xFF)));
}
if (((int)(data[0] & 0xFF) >= 128)&&((int)(data[0] & 0xFF) <= 131)) {
    println();
    flag = true;
    if(flag == true){
    note_number.add(Integer.toString((note[note_y][note_x].pointer()).MidiValue()));
    now_number.add(Integer.toString((int)(data[1] & 0xFF)));
    count.add(""+mill);
    flag = false;
 }
 if ((int)(data[1] & 0xFF)!=(note[note_y][note_x].pointer()).MidiValue()) {
    note[note_y][note_x].judge = 1;    
    }
    if ((int)(data[1] & 0xFF)==(note[note_y][note_x].pointer()).MidiValue()) {
      note_x++;
      
      if (note_x!=0&&note_x==8) {
        note_y++;
        note_x=0;
        if (note_y>3) {
         
          note_y=0;
        }
      }
    }
  }
}

void keyPressed() {
	if (key == 's' || key=='S') {	
	//txtファイル用
  //それぞれの行に文字列をファイルへ書き込む。
  for(int i = 0; i < count.size() ; i++){
	result.add(note_number.get(i) + "," + now_number.get(i) + "," + count.get(i) + "," + note_velocity.get(i));
}
  saveStrings("processing_data.txt", (String[])result.toArray(new String[result.size()-1])); 
}
}

