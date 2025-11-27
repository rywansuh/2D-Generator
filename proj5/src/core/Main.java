package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class Main {
    static int WIDTH = 70;
    static int HEIGHT = 45;
    static TETile[][] rtiles;
    static int rtanheight = 1;
    static int rtanwidth = 1;
    static TERenderer ter;
    static TETile floor;
    public static void main(String[] args) {
        mainMenu();
        floor = new TETile(Tileset.FLOOR, Color.GRAY);
        rtiles[rtanwidth][rtanheight] = Tileset.AVATAR;
        ter.renderFrame(rtiles);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        while (true) {
            hud(StdDraw.mouseX(), StdDraw.mouseY());
            if (StdDraw.hasNextKeyTyped()) {
                move(StdDraw.nextKeyTyped());
            }
            ter.renderFrame(rtiles);
        }

    }
    public static void hud(double x,double y){
        StdDraw.setPenColor(Color.WHITE);
        if (x >= 0 && y >= 0 && x < rtiles.length && y < rtiles[0].length) {
            StdDraw.text(rtiles.length/2, rtiles[0].length-1, "i think you are looking at uh " + rtiles[(int) x][(int) y].description());
        }
        StdDraw.show();
    }
    public static void move(char jidiot) {
        if (jidiot == 'w' && !rtiles[rtanwidth][rtanheight + 1].equals(Tileset.WALL)) {
            rtiles[rtanwidth][rtanheight + 1] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanheight +=1;

        }
        else if (jidiot == 'a' && !rtiles[rtanwidth - 1][rtanheight].equals(Tileset.WALL)) {
            rtiles[rtanwidth - 1][rtanheight] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanwidth -=1;

        }
        else if (jidiot == 's' && !rtiles[rtanwidth][rtanheight - 1].equals(Tileset.WALL)) {
            rtiles[rtanwidth][rtanheight - 1] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanheight -=1;
        }
        else if (jidiot == 'd' && !rtiles[rtanwidth + 1][rtanheight].equals(Tileset.WALL)) {
            rtiles[rtanwidth + 1][rtanheight] = Tileset.AVATAR;
            rtiles[rtanwidth][rtanheight] = floor;
            rtanwidth +=1;
        }
        else if (jidiot == 'q') {
            System.exit(0);
        }
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
            String seedy ="";
            char nextseed = nexta();
            while (nextseed != 's' && nextseed != 'S') {
                seedy += nextseed;
                StdDraw.clear();
                StdDraw.text(0.5, 0.6, "Enter Seed followed by S:");
                StdDraw.text(0.5,0.4,seedy);
                nextseed = nexta();
            }
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            World awesomeworld = new World(Long.parseLong(seedy), WIDTH, HEIGHT);
            rtiles = awesomeworld.getTETiles();
            ter.renderFrame(rtiles);
            return;

        }
        else if (jd == 'l' || jd == 'L') {
            //do this
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
}
