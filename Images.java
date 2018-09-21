import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
 private static BufferedImage spriteSheet;
 private static BufferedImage[] seeds, planted, grown, ingredients, potions;
 private static BufferedImage land,money,menu,rock;
 
 public static void setImages() throws IOException{
  spriteSheet = ImageIO.read(new File("pictures/spriteSheet.png"));
  money = ImageIO.read(new File("pictures/money.png"));

  seeds = new BufferedImage[6];
  ingredients = new BufferedImage[6];
  potions = new BufferedImage[10];
  planted = new BufferedImage[6];
  grown = new BufferedImage[6];
  
  for(int i = 0; i < 6; i++){
   seeds[i] = spriteSheet.getSubimage(i*16, 0, 16,16);
   ingredients[i] = spriteSheet.getSubimage(i*16, 16, 16,16);
   planted[i] = spriteSheet.getSubimage(0, (3 + i) * 16, 16, 16);
   grown[i] = spriteSheet.getSubimage(16, (3 + i) * 16, 16, 16);
  }
  for(int i = 0; i < potions.length; i ++){
   potions[i] = spriteSheet.getSubimage(i*16, 32, 16, 16);
  }
  
  land = spriteSheet.getSubimage(96, 0, 32, 32);
    menu = ImageIO.read(new File("pictures/menu.png"));
    rock = ImageIO.read(new File("pictures/rock.png"));
 }
 
 public static BufferedImage getSeed(int i){
  return seeds[i];
 }
 public static BufferedImage getIngredient(int i){
  return ingredients[i];
 }
 public static BufferedImage getPotion(int i){
  return potions[i];
 }
 public static BufferedImage getLand(){
  return land;
 }
 public static BufferedImage getGrown(int i){
  return grown[i];
 }
 public static BufferedImage getPlanted(int i){
  return planted[i];
 }
 public static BufferedImage getMoney(){
 return money;
 }
  public static BufferedImage getMenu(){
 return menu;
 }
    public static BufferedImage getRock(){
 return rock;
 }
  
}

