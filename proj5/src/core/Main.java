package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

public class Main {
    static int WIDTH = 70;
    static int HEIGHT = 45;
    public static void main(String[] args) {
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
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);
            World awesomeworld = new World(Long.parseLong(seedy), WIDTH, HEIGHT);
            TETile[][] worldtiles = awesomeworld.getTETiles();
            ter.renderFrame(worldtiles);

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
