import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Customer {

 private int timer, request;
 private StartBrewery main;
 private boolean requested;
 private String[] names = { "Bob", "Ather", "Eric", "Mr.Donald", "Ryan", "Hugh Mungus", "Seymor Buts", "George", "Juan", "Obama", "Donald Trump"};
 private String string;
 int name;

 public Customer(StartBrewery main) {
  this.main = main;
  requested = false;
  request = -1;
  timer = 0;
 }

 public void paint(Graphics g) {
  if (requested) {

   g.setFont(new Font("Impact", Font.PLAIN, 22));
   g.setColor(Color.LIGHT_GRAY);
   g.drawString(string, 45, 20);
  }
 }

 public void request() {
  if (timer == 0 && !requested) {
   if (Cauldron.getLevel() == 1)
    request = (int) (4 * Math.random());
   if (Cauldron.getLevel() == 2)
    request = (int) (7 * Math.random());
   if (Cauldron.getLevel() == 3)
    request = (int) (9 * Math.random());
   requested = true;
   name = (int) (names.length * Math.random());
   timer = (int) (2000 * Math.random() + 11000);

   string = names[name] + " has requested a potion of " + main.getPotionName(request);

  }

 }

 public void sell() {
  if (requested) {
   if (main.getPlayer().getPotion(request) > 0) {
    main.getPlayer().changeMoney(main.getPotion(request).getCost() * 2);
    main.getPlayer().removePotion(request);
    requested = false;
   }

  }

 }

 public int getRequest(){
  return request;
 }
 
 public boolean getRequested(){
  return requested;
 }
 
 public void time() {
  if (timer > 0 && !requested)
   timer--;
 }
 
 public String getName(){
   return names[name];
 }

}