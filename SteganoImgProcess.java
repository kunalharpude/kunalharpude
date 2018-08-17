/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SteganoImgProcess
/*     */ {
/*     */   String ext;
/*     */   int encodedMsgOffset;
/*     */   
/*     */   boolean encode(BufferedImage input, BufferedImage output, int width, int height, String msg, String outputName)
/*     */   {
/*  20 */     int msgLength = msg.length();
/*     */     
/*     */ 
/*     */ 
/*  24 */     String message = "!encoded!" + msgLength + "!" + msg;
/*  25 */     msgLength = message.length();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  33 */     int[] twoBitMessage = new int[4 * msgLength];
/*     */     
/*     */ 
/*  36 */     for (int i = 0; i < msgLength; i++) {
/*  37 */       char currentChar = message.charAt(i);
/*  38 */       twoBitMessage[(4 * i + 0)] = (currentChar >> '\006' & 0x3);
/*  39 */       twoBitMessage[(4 * i + 1)] = (currentChar >> '\004' & 0x3);
/*  40 */       twoBitMessage[(4 * i + 2)] = (currentChar >> '\002' & 0x3);
/*  41 */       twoBitMessage[(4 * i + 3)] = (currentChar & 0x3);
/*     */     }
/*     */     
/*  44 */     int count = 0;
/*  45 */     for (int i = 0; i < width; i++) {
/*  46 */       for (int j = 0; j < height; j++) {
/*  47 */         if (count >= 4 * msgLength) break;
/*  48 */         int pixel = input.getRGB(i, j);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */         int pixOut = pixel & 0xFFFFFFFC | twoBitMessage[(count++)];
/*     */         
/*  58 */         output.setRGB(i, j, pixOut);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/*  68 */       ImageIO.write(output, "png", new File(outputName));
/*  69 */       return true;
/*     */     } catch (IOException e) {
/*  71 */       e.printStackTrace(); }
/*  72 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String decode(BufferedImage input, int width, int height)
/*     */   {
/*  80 */     if (!isEncoded(input, width, height)) {
/*  81 */       return null;
/*     */     }
/*     */     
/*  84 */     int msgLength = getEncodedLength(input, width, height);
/*     */     
/*  86 */     StringBuffer decodedMsg = new StringBuffer();
/*  87 */     Deque<Integer> listChar = new ArrayDeque();
/*     */     
/*  89 */     int ignore = 0;int count = 0;
/*  90 */     for (int i = 0; i < width; i++) {
/*  91 */       for (int j = 0; j < height; j++) {
/*  92 */         if (ignore < 36 + 4 * (String.valueOf(msgLength).length() + 1)) {
/*  93 */           ignore++;
/*     */         }
/*     */         else
/*     */         {
/*  97 */           if (count++ == 4 * msgLength) {
/*     */             break;
/*     */           }
/* 100 */           int pixel = input.getRGB(i, j);
/* 101 */           int temp = pixel & 0x3;
/*     */           
/* 103 */           listChar.add(Integer.valueOf(temp));
/*     */           
/* 105 */           if (listChar.size() >= 4)
/*     */           {
/* 107 */             int charOut = ((Integer)listChar.pop()).intValue() << 6 | ((Integer)listChar.pop()).intValue() << 4 | ((Integer)listChar.pop()).intValue() << 2 | ((Integer)listChar.pop()).intValue();
/* 108 */             decodedMsg.append((char)charOut);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 114 */     String outputMsg = new String(decodedMsg);
/*     */     
/* 116 */     return outputMsg;
/*     */   }
/*     */   
/*     */   boolean isEncoded(BufferedImage input, int width, int height)
/*     */   {
/* 121 */     StringBuffer decodedMsg = new StringBuffer();
/* 122 */     Deque<Integer> listChar = new ArrayDeque();
/*     */     
/* 124 */     int count = 0;
/* 125 */     for (int i = 0; i < width; i++) {
/* 126 */       for (int j = 0; j < height; count++)
/*     */       {
/* 128 */         if (count == 45) {
/*     */           break;
/*     */         }
/* 131 */         int pixel = input.getRGB(i, j);
/* 132 */         int temp = pixel & 0x3;
/*     */         
/* 134 */         listChar.add(Integer.valueOf(temp));
/*     */         
/* 136 */         if (listChar.size() >= 4)
/*     */         {
/* 138 */           int charOut = ((Integer)listChar.pop()).intValue() << 6 | ((Integer)listChar.pop()).intValue() << 4 | ((Integer)listChar.pop()).intValue() << 2 | ((Integer)listChar.pop()).intValue();
/* 139 */           decodedMsg.append((char)charOut);
/* 140 */           count++;
/*     */         }
/* 126 */         j++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */     String check = new String(decodedMsg);
/* 146 */     System.out.println(check + " " + check.length());
/* 147 */     if (check.compareTo("!encoded!") == 0) {
/* 148 */       System.out.println("true");
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   int getEncodedLength(BufferedImage input, int width, int height)
/*     */   {
/* 158 */     StringBuffer decodedMsg = new StringBuffer();
/* 159 */     Deque<Integer> listChar = new ArrayDeque();
/*     */     
/* 161 */     int count = 0;
/* 162 */     for (int i = 0; i < width; i++) {
/* 163 */       for (int j = 0; j < height; j++) {
/* 164 */         if (count < 36) {
/* 165 */           count++;
/*     */         }
/*     */         else
/*     */         {
/* 169 */           int pixel = input.getRGB(i, j);
/* 170 */           int temp = pixel & 0x3;
/*     */           
/* 172 */           listChar.add(Integer.valueOf(temp));
/*     */           
/* 174 */           if (listChar.size() >= 4)
/*     */           {
/* 176 */             int charOut = ((Integer)listChar.pop()).intValue() << 6 | ((Integer)listChar.pop()).intValue() << 4 | ((Integer)listChar.pop()).intValue() << 2 | ((Integer)listChar.pop()).intValue();
/* 177 */             if ((char)charOut == '!') {
/*     */               break;
/*     */             }
/* 180 */             decodedMsg.append((char)charOut);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 187 */     String length = new String(decodedMsg);
/* 188 */     System.out.println("length is " + Integer.parseInt(length));
/*     */     
/* 190 */     return Integer.parseInt(length);
/*     */   }
/*     */   
/*     */   String getExt() {
/* 194 */     return this.ext;
/*     */   }
/*     */   
/*     */   int getOffset() {
/* 198 */     return this.encodedMsgOffset;
/*     */   }
/*     */ }


/* Location:              D:\life of kunal\DCIM\kunalst1.jar!\SteganoImgProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */