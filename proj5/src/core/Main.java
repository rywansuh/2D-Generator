package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.io.File;

public class Main {
    static int WIDTH = 70;
    static int HEIGHT = 45;
    static TETile[][] rtiles;
    static int rtanheight = 1;
    static int rtanwidth = 1;
    static Long seediest;
    static double mousex = 0;
    static double mousey = 0;
    static TERenderer ter;
    static TETile floor;
    static int coincollected = 0;
    static boolean coloned = false;
    static String gurter = "youWannaComeIn.txt";
    public static void main(String[] args) {
        mainMenu();
        floor = new TETile(Tileset.FLOOR, Color.GRAY);
        rtiles[rtanwidth][rtanheight] = Tileset.AVATAR;
        ter.renderFrame(rtiles);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        hud(mousex, mousey);
        while (true) {
            if (mousemoved(StdDraw.mouseX(), StdDraw.mouseY())){
                ter.renderFrame(rtiles);
                hud(mousex, mousey);
            }
            if (StdDraw.hasNextKeyTyped()) {
                move(StdDraw.nextKeyTyped());
                hud(mousex, mousey);
            }

        }

    }
    public static boolean mousemoved(double x, double y) {
        if (mousex != x || mousey != y) {
            mousex = x;
            mousey = y;
            return true;
        }
        return false;
    }
    public static void hud(double x,double y){
        StdDraw.setPenColor(Color.WHITE);
        if (x >= 0 && y >= 0 && x < rtiles.length && y < rtiles[0].length) {
            StdDraw.text(rtiles.length/2, rtiles[0].length-1, "coins collected: "+ coincollected+"              i think you are looking at uh " + rtiles[(int) x][(int) y].description());
        }
        StdDraw.show();
    }

    public static void move(char jidiot) {
        if (jidiot == 'w' && !rtiles[rtanwidth][rtanheight + 1].equals(Tileset.WALL)) {
            if (rtiles[rtanwidth][rtanheight + 1].equals(Tileset.COIN)){
                coincollected++;
            }
            rtiles[rtanwidth][rtanheight + 1] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanheight +=1;

        }
        else if (jidiot == 'a' && !rtiles[rtanwidth - 1][rtanheight].equals(Tileset.WALL)) {
            if (rtiles[rtanwidth - 1][rtanheight].equals(Tileset.COIN)){
                coincollected++;
            }
            rtiles[rtanwidth - 1][rtanheight] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanwidth -=1;

        }
        else if (jidiot == 's' && !rtiles[rtanwidth][rtanheight - 1].equals(Tileset.WALL)) {
            if(rtiles[rtanwidth][rtanheight - 1].equals(Tileset.COIN)){
                coincollected++;
            }
            rtiles[rtanwidth][rtanheight - 1] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanheight -=1;
        }
        else if (jidiot == 'd' && !rtiles[rtanwidth + 1][rtanheight].equals(Tileset.WALL)) {
            if(rtiles[rtanwidth + 1][rtanheight].equals(Tileset.COIN)){
                coincollected++;
            }
            rtiles[rtanwidth + 1][rtanheight] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanwidth +=1;
        }
        if (jidiot == ':') {
            coloned = true;
        } else if ((jidiot == 'q' || jidiot == 'Q') && coloned) {
            exit();
        } else {
            coloned = false;
        }

        ter.renderFrame(rtiles);
    }
    public static void mainMenu() {
        StdDraw.text(0.5, 0.8, "CS61B: BYOW");
        StdDraw.text(0.5, 0.6, "(N) New Game");
        StdDraw.text(0.5, 0.5, "(L) Load Game");
        StdDraw.text(0.5, 0.4, "(Q) Quit Game");
        char jd = nexta();

        if (jd == 'q' || jd == 'Q') {
            System.exit(0);
        }
        else if (jd == 'n' || jd == 'N') {
            StdDraw.clear();
            StdDraw.text(0.5, 0.6, "Enter Seed followed by S:");
            String seedy = "";
            char nextseed = nexta();
            while (nextseed != 's' && nextseed != 'S') {
                seedy += nextseed;
                StdDraw.clear();
                StdDraw.text(0.5, 0.6, "Enter Seed followed by S:");
                StdDraw.text(0.5,0.4,seedy);
                nextseed = nexta();
            }
            seediest = Long.parseLong(seedy);
            World awesomeworld = new World(seediest, WIDTH, HEIGHT - 1);
            rtiles = extraLine(awesomeworld.getTETiles());
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(rtiles);

        } else if (jd == 'l' || jd == 'L') {
            //do this. i think i will implement saving first. so
            File file = new File(gurter);
            In in = new In(file);
            seediest = in.readLong();
            WIDTH = in.readInt();
            HEIGHT = in.readInt();
            boolean what = in.hasNextLine();
            rtiles = new TETile[WIDTH][HEIGHT];
            for (int i = 0; i < rtiles.length; i++) {
                for (int j = 0; j < rtiles[0].length; j++) {
                    int id = in.readInt();
                    rtiles[i][j] = Tileset.getByID(id);
                }
            }
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(rtiles);
        }
    }
    public static char nexta() {
        //get the next key from. the user.
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                return StdDraw.nextKeyTyped();
            }
        }
    }
    public static TETile[][] extraLine(TETile[][] tiles) {
        TETile[][] extra = new TETile[tiles.length][tiles[0].length + 1];
        for (int i = 0; i < tiles.length; i++) {
            extra[i][tiles[0].length] = new TETile(Tileset.NOTHING, Color.BLACK);
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                extra[i][j] = tiles[i][j];
            }
        }
        return extra;
    }
    public static void exit() {
        Out out = new Out(gurter);
        record(out, Long.toString(seediest));
        record(out, Integer.toString(WIDTH));
        record(out, Integer.toString(HEIGHT));
        for (int i = 0; i < rtiles.length; i++) {
            for (int j = 0; j < rtiles[i].length; j++) {
                record(out, Integer.toString(rtiles[i][j].id()));
            }
        }
        //more ambition features will be recorded here
        System.exit(0);
    }
    public static void record(Out out, String thing) {
        out.println(thing);
    }
}
