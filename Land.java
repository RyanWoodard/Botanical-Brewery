import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

public class Land {

 private boolean planted;
 private Seed[] seeds;
 private static int noSeeds;
 private static int timeLevel = 1;
 private static int spaceLevel = 1;
 private static int currentSeed;
 private int time = 0;
 private boolean grown = false;
 private static StartBrewery main;
 private int x, y, size, seed,time2;
 private static int costTime = 100;//temp
 private static int costSpace = 100;//temp

 public Land(StartBrewery main, int x, int y) throws IOException {
  this.x = x;
  this.y = y;
  size = 100;
  this.main = main;
  currentSeed = -1;
  seeds = main.getSeeds();
 }
 public static int getCostTime(){
  return costTime;
 }
 public static int getCostSpace(){
  return costSpace;
 }


 public void plant() {
  if (standing()) {
   if (!planted && currentSeed != -1) {
    main.getPlayer().removeSeed(currentSeed);
    planted = true;
    seed = currentSeed;
    time = (int) (seeds[seed].getTime() / (timeLevel / 2.0));
    time2 = time;
    if (main.getPlayer().getSeeds(seed) == 0)
     currentSeed = -1;
   }
  }
 }

 public void grow() {
  if (time > 0)
   time--;
  if (planted && time == 0 && !grown) {
   grown = true;

  }

 }

 public void harvest() {
  if (standing()) {
   if (planted && grown) {
    planted = false;
    grown = false;
    main.getPlayer().addIng(seeds[seed].getNum());
   }
  }

 }

 private boolean standing() {
  return main.getPlayer().getY() + 64 >= y && main.getPlayer().getY() + 64 <= y + size
    && main.getPlayer().getX() + 64 >= x && main.getPlayer().getX() + 64 <= (x + size);

 }

 public void paint(Graphics g) {
   double size2 = size;
  g.setColor(Color.YELLOW);
  if (standing())
   g.fillRect(x - 3, y - 3, size + 6, size + 6);
  g.drawImage(Images.getLand(), x, y, size, size, null);
  if (planted)
   g.drawImage(Images.getPlanted(seed), x + 10, y + 10, size - 20, size - 20, null);
  if (grown)
   g.drawImage(Images.getGrown(seed), x + 10, y + 10, size - 20, size - 20, null);
  if(planted && !grown)  { 
      g.setColor(Color.GREEN);
      g.fillRect(x, y+size-5,(int)(size2*((time*1.0)/(time2*1.0))-1), 4);
 g.setColor(Color.black);
     g.drawRect(x, y+size-5,(int)(size2-1), 4);
  }
 }

 public static void paintINV(Graphics g) {
  int x = 0;
  int y = 0;
  int size = 80;
  for (int i = 0; i < 6; i++) {

   g.setColor(Color.GRAY);

   if (i == currentSeed)
    g.setColor(Color.GREEN);

   g.fillRect(x + size * i, y, size, size);

   g.setColor(Color.BLACK);
   g.drawRect(x + size * i, y, size, size);

   g.setColor(Color.GREEN);
   if (main.getPlayer().getSeeds(i) == 0)
    g.setColor(Color.RED);
   if (currentSeed == i)
    g.setColor(Color.BLACK);

   g.setFont(new Font("Impact", Font.PLAIN, 14));

   g.drawImage(Images.getSeed(i), x + size * i, y, size, size, null);
   g.drawString("" + main.getPlayer().getSeeds(i), x + (i + 1) * size - 22, y + 14);

  }
  if (noSeeds > 0) {
   g.setColor(Color.RED);
   g.setFont(new Font("Impact", Font.PLAIN, 40));
   g.drawString("YOU DONT HAVE ENOUGH SEEDS", 400, 400);
   noSeeds--;
  }

 }

 public static void setCurSeed(int xM, int yM) {
  int seedNum;
  int x = 0;
  int y = 0;
  int size = 80;
  // System.out.println((xM - x)/size);
  if (yM >= y && yM <= y + size && xM >= x && xM <= (x + size * 6)) {

   seedNum = (xM - x - 1) / size;
   if (main.getPlayer().getSeeds(seedNum) > 0) {

    currentSeed = seedNum;
    noSeeds = 0;
   } else {
    noSeeds = 200;

   }

  }

 }

 public static int getTimeLevel() {
  return timeLevel;
 }

 public static void increaseTimeLevel() {
  if (timeLevel < 4) {
   timeLevel++;
   costTime*=2;
  }
      if(timeLevel ==4){
  
  costTime = 0;
      }
  
 }

 public static int getSpaceLevel() {
  return spaceLevel;
 }

 public static void increaseSpaceLevel() {
  if (spaceLevel < 4) {    
   spaceLevel++;
   costSpace*=2; 
  }
       if(spaceLevel ==4){
  
  costSpace = 0;
      }
 }
}