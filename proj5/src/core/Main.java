package core;

import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    static int WIDTH = 70;
    static int HEIGHT = 45;
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        //our seeds
        //4876839304049068870
        //7825621908506650049
        //332935756599973072
        //2363488845041469273
        //6393509978333705358
        long seed1 = 4876839304049068870L;
        World awesomeworld = new World(seed1, WIDTH, HEIGHT);
        TETile[][] worldtiles = awesomeworld.getTETiles();
        ter.renderFrame(worldtiles);
    }
}
