package core;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.Color;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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
    static TETile vatar = Tileset.AVATAR;
    public static boolean onLight = false;
    public static int lastEmittance = 0;
    public static boolean ryon = true;
    public static void main(String[] args) {
        mainMenu();
        floor = new TETile(Tileset.FLOOR, Color.GRAY);
        rtiles[rtanwidth][rtanheight] = vatar;
        lightupdate(rtiles);
        if (rtiles[1][2].equals(Tileset.LIGHT)) {
            int temp = rtiles[1][2].emittance;
            rtiles[1][2] = new TETile(Tileset.LIGHT, Color.GREEN);
            rtiles[1][2].emittance = temp;
        }
        ter.renderFrame(lightupdate(rtiles));
        StdDraw.setPenColor(Color.WHITE);
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
            if (rtiles[rtanwidth][rtanheight + 1].equals(Tileset.LIGHT)) {
                onLight = true;
                lastEmittance = rtiles[rtanwidth][rtanheight + 1].emittance;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            } else if (onLight) {
                onLight = false;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.LIGHT, Color.YELLOW);
                rtiles[rtanwidth][rtanheight].emittance = lastEmittance;
            } else {
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            }
            rtiles[rtanwidth][rtanheight + 1] = vatar;

            rtanheight +=1;

        }
        else if (jidiot == 'a' && !rtiles[rtanwidth - 1][rtanheight].equals(Tileset.WALL)) {
            if (rtiles[rtanwidth - 1][rtanheight].equals(Tileset.COIN)){
                coincollected++;
            }
            if (rtiles[rtanwidth - 1][rtanheight].equals(Tileset.LIGHT)) {
                onLight = true;
                lastEmittance = rtiles[rtanwidth - 1][rtanheight].emittance;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            } else if (onLight) {
                onLight = false;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.LIGHT, Color.YELLOW);
                rtiles[rtanwidth][rtanheight].emittance = lastEmittance;
            } else {
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            }
            rtiles[rtanwidth - 1][rtanheight] = vatar;
            rtanwidth -= 1;

        }
        else if (jidiot == 's' && !rtiles[rtanwidth][rtanheight - 1].equals(Tileset.WALL)) {
            if(rtiles[rtanwidth][rtanheight - 1].equals(Tileset.COIN)){
                coincollected++;
            }
            if (rtiles[rtanwidth][rtanheight - 1].equals(Tileset.LIGHT)) {
                onLight = true;
                lastEmittance = rtiles[rtanwidth][rtanheight - 1].emittance;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            } else if (onLight) {
                onLight = false;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.LIGHT, Color.YELLOW);
                rtiles[rtanwidth][rtanheight].emittance = lastEmittance;
            } else {
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            }
            rtiles[rtanwidth][rtanheight - 1] = vatar;
            rtanheight -= 1;
        }
        else if (jidiot == 'd' && !rtiles[rtanwidth + 1][rtanheight].equals(Tileset.WALL)) {
            if (rtiles[rtanwidth + 1][rtanheight].equals(Tileset.COIN)){
                coincollected++;
            }
            if (rtiles[rtanwidth + 1][rtanheight].equals(Tileset.LIGHT)) {
                onLight = true;
                lastEmittance = rtiles[rtanwidth + 1][rtanheight].emittance;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            } else if (onLight) {
                onLight = false;
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.LIGHT, Color.YELLOW);
                rtiles[rtanwidth][rtanheight].emittance = lastEmittance;
            } else {
                rtiles[rtanwidth][rtanheight] = new TETile(Tileset.FLOOR, Color.GRAY);
            }
            rtiles[rtanwidth + 1][rtanheight] = vatar;
            rtanwidth +=1;
        }
        if (jidiot == ':') {
            coloned = true;
        } else if ((jidiot == 'q' || jidiot == 'Q') && coloned) {
            exit();
        } else {
            coloned = false;
        }
        if (jidiot == 'x' && rtanwidth == 1 && rtanheight == 2) {
            if (ryon) {
                rtiles[1][2].emittance = 0;
                ryon = false;
            } else {
                rtiles[1][2].emittance = 10;
                ryon = true;
            }
        }
        if (rtiles[1][2].equals(Tileset.LIGHT)) {
            int temp = rtiles[1][2].emittance;
            rtiles[1][2] = new TETile(Tileset.LIGHT, Color.GREEN);
            rtiles[1][2].emittance = temp;
        }
        ter.renderFrame(lightupdate(rtiles));
    }

    public static TETile[][] lightupdate(TETile[][] rtiles) {
        ArrayList<IntPair> ridiots = new ArrayList<IntPair>();
        for (int i = 0; i < rtiles.length; i++) {
            for (int j = 0; j < rtiles[i].length; j++) {
                if (sameType(rtiles[i][j], Tileset.LIGHT)) {
                    if (i == 1 && j == 2) {
                        if (ryon) {
                            ridiots.add(new IntPair(i, j));
                        }
                    } else {
                        ridiots.add(new IntPair(i, j));
                    }
                }
                if (sameType(rtiles[i][j], vatar) && onLight) {
                    if (i == 1 && j == 2) {
                        if (ryon) {
                            rtiles[i][j].emittance = lastEmittance;
                            ridiots.add(new IntPair(i, j));
                        }
                    } else {
                        rtiles[i][j].emittance = lastEmittance;
                        ridiots.add(new IntPair(i, j));
                    }
                }
            }
        }
        for (int i = 0; i < rtiles.length; i++) {
            for (int j = 0; j < rtiles[i].length; j++) {
                rtiles[i][j].brightness = 0;
            }
        }
        for (int i = 0; i < ridiots.size(); i++) {
            rtiles = brighten(ridiots.get(i).x, ridiots.get(i).y, rtiles);
        }
        return rtiles;
    }
    public static TETile[][] brighten (int x, int y, TETile[][] rtiles) {
        int[][] letThereBe = new int[rtiles.length][rtiles[0].length];
        boolean[][] isItTrue = new boolean[rtiles.length][rtiles[0].length];

        Queue<IntPair> q = new ArrayDeque<IntPair>();
        q.add(new IntPair(x, y));
        letThereBe[x][y] = rtiles[x][y].emittance;
        isItTrue[x][y] = true;
        int[][] directisons = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!q.isEmpty()) {
            IntPair p = q.remove();
            if (letThereBe[p.x][p.y] == 0) {
                continue;
            }
            TETile ridiot = rtiles[p.x][p.y];
            if (sameType(ridiot, Tileset.LIGHT) || sameType(ridiot, Tileset.FLOOR) || sameType(ridiot, vatar) || sameType(ridiot, Tileset.COIN)) {
                ridiot.brightness = Math.max(letThereBe[p.x][p.y], ridiot.brightness);
            }
            if (letThereBe[p.x][p.y] == 1) {
                continue;
            }
            for (int[] d : directisons) {
                int newx =  p.x + d[0];
                int newy = p.y + d[1];
                if (newx < 0 || newy < 0 || newx >= rtiles.length || newy >= rtiles[0].length){
                    continue;
                } else if (isItTrue[newx][newy]) {
                    continue;
                } else if (sameType(rtiles[newx][newy], Tileset.WALL)) {
                    continue;
                }
                isItTrue[newx][newy] = true;
                letThereBe[newx][newy] = letThereBe[p.x][p.y] - 1;
                q.add(new IntPair(newx, newy));
            }
        }
        return rtiles;
    }
    public static void mainMenu() {
        StdDraw.enableDoubleBuffering();
        StdDraw.text(0.5, 0.8, "CS61B: BYOW");
        StdDraw.text(0.5, 0.6, "(N) New Game");
        StdDraw.text(0.5, 0.5, "(L) Load Game");
        StdDraw.text(0.5, 0.4, "(Q) Quit Game");
        StdDraw.text(0.5, 0.3, "(C) Change Avatar. Current Avatar: Original");
        StdDraw.show();
        char jd = nexta();

        while (jd == 'c' || jd == 'C') {
            if (vatar.equals(Tileset.AVATAR)) {
                StdDraw.clear();
                StdDraw.text(0.5, 0.8, "CS61B: BYOW");
                StdDraw.text(0.5, 0.6, "(N) New Game");
                StdDraw.text(0.5, 0.5, "(L) Load Game");
                StdDraw.text(0.5, 0.4, "(Q) Quit Game");
                StdDraw.text(0.5, 0.3, "(C) Change Avatar. Current Avatar: Happy");
                StdDraw.show();
                vatar = Tileset.AMOG;
            }
            else {
                StdDraw.clear();
                StdDraw.text(0.5, 0.8, "CS61B: BYOW");
                StdDraw.text(0.5, 0.6, "(N) New Game");
                StdDraw.text(0.5, 0.5, "(L) Load Game");
                StdDraw.text(0.5, 0.4, "(Q) Quit Game");
                StdDraw.text(0.5, 0.3, "(C) Change Avatar. Current Avatar: Original");
                StdDraw.show();
                vatar = Tileset.AVATAR;
            }
            jd = nexta();
        }
        if (jd == 'q' || jd == 'Q') {
            System.exit(0);
        }
        else if (jd == 'n' || jd == 'N') {
            StdDraw.clear();
            StdDraw.text(0.5, 0.6, "Enter Seed followed by S:");
            StdDraw.show();
            String seedy = "";
            char nextseed = nexta();
            while (nextseed != 's' && nextseed != 'S') {
                seedy += nextseed;
                StdDraw.clear();
                StdDraw.text(0.5, 0.6, "Enter Seed followed by S:");
                StdDraw.text(0.5,0.4,seedy);
                StdDraw.show();
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
            rtanwidth = in.readInt();
            rtanheight = in.readInt();
            lastEmittance = in.readInt();
            int booltemp = in.readInt();
            if (booltemp == 1) {
                ryon = true;
            } else {
                ryon = false;
            }
            booltemp = in.readInt();
            if (booltemp == 1) {
                onLight = true;
            } else {
                onLight = false;
            }
            for (int i = 0; i < rtiles.length; i++) {
                for (int j = 0; j < rtiles[0].length; j++) {
                    int id = in.readInt();
                    rtiles[i][j] = Tileset.getByID(id);
                    if (id == 13) {
                        int brightness = in.readInt();
                        rtiles[i][j].emittance = brightness;
                    }
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
        record(out, Integer.toString(rtanwidth));
        record(out, Integer.toString(rtanheight));
        record(out, Integer.toString(lastEmittance));
        if (ryon) {
            record(out, "1");
        } else {
            record(out, "0");
        }
        if (onLight) {
            record(out, "1");
        } else {
            record(out, "0");
        }
        for (int i = 0; i < rtiles.length; i++) {
            for (int j = 0; j < rtiles[i].length; j++) {
                record(out, Integer.toString(rtiles[i][j].id()));
                if (rtiles[i][j].id() == 13) {
                    record(out, Integer.toString(rtiles[i][j].emittance));
                }
            }
        }
        //more ambition features will be recorded here
        System.exit(0);
    }
    public static void record(Out out, String thing) {
        out.println(thing);
    }
    private static boolean sameType(TETile jason, TETile ryan) {
        if (jason == null || ryan == null) {
            return false;
        }
        return jason.character() == ryan.character() && jason.description().equals(ryan.description());
    }
}
