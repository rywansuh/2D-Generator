package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;

public class World {

    private TETile[][] ryanworld;
    private int WIDTH;
    private int HEIGHT;
    private Random jdong;
    private static int MIN_ROOM_SIZE = 6;
    private static int MAX_ROOM_SIZE = 12;
    private static final int MIN_HALL_LEN = 2;
    private static final int MAX_HALL_LEN = 8;

    private static int K = 40; // density
    private static final double SKIP_PROB = 0.15;
    private static final double BEND_PROB = 0.35;


    public TETile[][] getTETiles(){
        return ryanworld;
    }

    public World(long seed, int width, int height) {
        jdong = new Random(seed);
        MIN_ROOM_SIZE = jdong.nextInt(3) + 3;
        MAX_ROOM_SIZE = MIN_ROOM_SIZE + 6 + jdong.nextInt(3);
        K = 5 + jdong.nextInt(40);
        WIDTH = width;
        HEIGHT = height;
        ryanworld = new TETile[WIDTH][HEIGHT];
        generateWorld();
    }
    public void generateWorld() {
        setEmpty();
        int bottomleftx = 0, bottomlefty = 0;
        int fat = 10, tall = 15;
        int[] attempt = ryanRoom(bottomleftx, bottomlefty, fat, tall);
        if (attempt.length == 0) {
            return; //alternatively, retry making room
        }

        Queue<Exit> jasonExplore = new ArrayDeque<>();

        jsuhExplore(jasonExplore, new int[]{bottomleftx + attempt[0], bottomlefty - 1}, new int[]{0, -1}); //down exit
        jsuhExplore(jasonExplore, new int[]{bottomleftx + attempt[1], bottomlefty + tall}, new int[]{0, 1}); //up exit
        jsuhExplore(jasonExplore, new int[]{bottomleftx - 1, bottomlefty + attempt[0]}, new int[]{-1, 0}); //left exit
        jsuhExplore(jasonExplore, new int[]{bottomleftx + fat, bottomlefty + attempt[1]}, new int[]{1, 0}); //right exit
        while (!jasonExplore.isEmpty()) {
            Exit jasun = jasonExplore.remove();

            if (jdong.nextDouble() < SKIP_PROB) {
                continue;
            }
            makeBranch(jasun, jasonExplore);
        }
    }

    private static class Exit {
        int x, y, dx, dy;
        Exit(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }
    }

    private void setEmpty() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                ryanworld[i][j] = new TETile(Tileset.NOTHING, Color.BLACK);
            }
        }
    }

    private void makeBranch(Exit e, Queue<Exit> jasonExplore) {
        for (int attempt = 0; attempt < K; attempt++) {
            int[] direction1 = new int[]{e.dx, e.dy};
            int[] start1 = new int[]{e.x, e.y};
            int length1 = MIN_HALL_LEN + jdong.nextInt(MAX_HALL_LEN - MIN_HALL_LEN + 1);
            if (!canJasonHall(start1, length1, direction1)) {
                continue;
            }

            int end1X = e.x + length1 * e.dx;
            int end1Y = e.y + length1 * e.dy;
            int finalX = end1X, finalY = end1Y;
            int finalDX = e.dx, finalDY = e.dy;
            int len2 = 0;
            int[] direction2 = new int[]{};

            if (jdong.nextDouble() < BEND_PROB) {
                //generate bend.
                direction2 = perendongcular(e.dx, e.dy); // random perp
                len2 = MIN_HALL_LEN + jdong.nextInt(MAX_HALL_LEN - MIN_HALL_LEN + 1);
                if (!canJasonHall(new int[]{end1X, end1Y}, len2, direction2)) {
                    continue;
                }
                finalX = end1X + len2 * direction2[0];
                finalY = end1Y + len2 * direction2[1]; //always the point after.
                finalDX = direction2[0];
                finalDY = direction2[1];
            }

            int[] dimensions = new int[]{MIN_ROOM_SIZE + jdong.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1), MIN_ROOM_SIZE + jdong.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE + 1)};
            int[] drill = new int[]{finalX, finalY};
            int[] bottomleft = dongputeBL(drill, finalDX, finalDY, dimensions);
            if (bottomleft[0] < 0 || bottomleft[1] < 0) continue;
            if (!canRyanRoom(bottomleft[0], bottomleft[1], dimensions[0], dimensions[1])) continue;

            //finally, actually create the tiles.
            jasonHall(start1, length1, direction1, false);
            if (direction2.length != 0) {
                jasonHall(new int[]{end1X, end1Y}, len2, direction2, true);
            }
            int[] nextits = ryanRoom(bottomleft[0], bottomleft[1], dimensions[0], dimensions[1]);
            if (inBounds(finalX, finalY)) {
                ryanworld[finalX][finalY] = new TETile(Tileset.FLOOR, Color.GRAY); // drill hole
            }
            buildExitsuh(jasonExplore, bottomleft, dimensions[0], dimensions[1], nextits);
        }
    }

    public int[] ryanRoom(int bottomleftx, int bottomlefty, int fat, int tall) {
        for (int i = bottomleftx; i < bottomleftx + fat; i++) {
            for (int j = bottomlefty; j < bottomlefty + tall; j++) {
                if (!isValid(i, j)) {
                    return new int[]{};
                }
                if (i == bottomleftx || j == bottomlefty || i == bottomleftx + fat - 1 || j == bottomlefty + tall- 1) {
                    ryanworld[i][j] = new TETile(Tileset.WALL, Color.DARK_GRAY);
                } else {
                    ryanworld[i][j] = new TETile(Tileset.FLOOR, Color.GRAY);
                }
            }
        }
        //generate hallway points
        int[] points = new int[4];
        points[0] = 1 + (int) ((fat - 2) * jdong.nextDouble()); //lengthwise bottom exit
        points[1] = 1 + (int) ((fat - 2) * jdong.nextDouble()); //lengthwise top exit
        points[2] = 1 + (int) ((tall - 2) * jdong.nextDouble()); //heightwise left exit
        points[3] = 1 + (int) ((tall - 2) * jdong.nextDouble()); //heightwise right exit
        return points;
    }
    public int[] jasonHall(int[] begin, int length, int[] increment, boolean bend) {
        for (int i = 0; i < length; i++) {
            for (int j = -1; j <= 1; j++) {
                int[] consider = new int[]{begin[0] + j * increment[1] + i * increment[0], begin[1] + j * increment[0] + i * increment[1]};
                if (!bend || i > 1) {
                    if (j == -1 || j == 1) {
                        ryanworld[consider[0]][consider[1]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
                    } else {
                        ryanworld[consider[0]][consider[1]] =  new TETile(Tileset.FLOOR, Color.GRAY);
                    }
                } else if (i <= 1 && sameType(ryanworld[consider[0]][consider[1]], Tileset.NOTHING)) {
                    if (j == -1 || j == 1) {
                        ryanworld[consider[0]][consider[1]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
                    } else {
                        ryanworld[consider[0]][consider[1]] =  new TETile(Tileset.FLOOR, Color.GRAY);
                    }
                }
            }
        }

        //drill a hole in origin room
        if (!bend) {
            ryanworld[begin[0] + -1 * increment[0]][begin[1] + -1 * increment[1]] = new TETile(Tileset.FLOOR, Color.GRAY);
        } else { //or fill up remaining walls
            ryanworld[begin[0] + -1 * increment[0]][begin[1] + -1 * increment[1]] = new TETile(Tileset.WALL, Color.DARK_GRAY);

            ryanworld[begin[0] + -1 * increment[0] + increment[1]][begin[1] + -1 * increment[1]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
            ryanworld[begin[0] + -1 * increment[0] - increment[1]][begin[1] + -1 * increment[1]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
            ryanworld[begin[0] + -1 * increment[0]][begin[1] + -1 * increment[1] + increment[0]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
            ryanworld[begin[0] + -1 * increment[0]][begin[1] + -1 * increment[1] - increment[0]] = new TETile(Tileset.WALL, Color.DARK_GRAY);
        }
        int tempx = begin[0] + (length + 0) * increment[0] + -1 * increment[0], tempy = begin[1] + (length + 0) * increment[0] + -1 * increment[1];
        if (inBounds(tempx, tempy) && sameType(ryanworld[tempx][tempy], Tileset.WALL)) {
            //ryanworld[tempx][tempy] = new TETile(Tileset.FLOOR, Color.GRAY);
        }
        return new int[]{begin[0] + length * increment[0], begin[1] + length * increment[1]}; //exit point
    }
    private int[] perendongcular(int dx, int dy) {
        if (dx != 0) {
            if (jdong.nextBoolean()) {
                return new int[]{0, 1};
            } else {
                return new int[]{0, -1};
            }
        } else {
            if (jdong.nextBoolean()) {
                return new int[]{1, 0};
            } else {
                return new int[]{-1, 0};
            }
        }
    }
    private int[] dongputeBL(int[] door, int dx, int dy, int[] dims) {
        //door: last tile in hallway. dims: room dims
        int x, y;

        if (dx == 1 && dy == 0) {
            x = door[0];
            int minBottom = door[1] - (dims[1] - 2);
            int maxBottom = door[1] - 1;

            int low = Math.max(0, minBottom);
            int high = Math.min(HEIGHT - dims[1], maxBottom);
            if (low > high) return new int[]{-1, -1};
            y = low + jdong.nextInt(high - low + 1);
        } else if (dx == -1 && dy == 0) {
            x = door[0] - dims[0] + 1;
            int minBottom = door[1] - (dims[1] - 2);
            int maxBottom = door[1] - 1;

            int low = Math.max(0, minBottom);
            int high = Math.min(HEIGHT - dims[1], maxBottom);
            if (low > high) return new int[]{-1, -1};
            y = low + jdong.nextInt(high - low + 1);
        } else if (dx == 0 && dy == 1) {
            y = door[1];
            int minLeft = door[0] - (dims[0] - 2);
            int maxLeft = door[0] - 1;

            int low = Math.max(0, minLeft);
            int high = Math.min(WIDTH - dims[0], maxLeft);
            if (low > high) return new int[]{-1, -1};
            x = low + jdong.nextInt(high - low + 1);
        } else {
            y = door[1] - dims[1] + 1;
            int minLeft = door[0] - (dims[0] - 2);
            int maxLeft = door[0] - 1;

            int low = Math.max(0, minLeft);
            int high = Math.min(WIDTH - dims[0], maxLeft);
            if (low > high) return new int[]{-1, -1};
            x = low + jdong.nextInt(high - low + 1);
        }
        return new int[]{x, y};
    }

    private void buildExitsuh(Queue<Exit> jasonExplore, int[] bottomleft, int fat, int tall, int[] exits) { //queue up the stuff to bfs
        jasonExplore.add(new Exit(bottomleft[0] + exits[0], bottomleft[1] - 1, 0, -1)); //down
        jasonExplore.add(new Exit(bottomleft[0] + exits[1], bottomleft[1] + tall, 0, 1));
        jasonExplore.add(new Exit(bottomleft[0] - 1, bottomleft[1] + exits[2], -1, 0));
        jasonExplore.add(new Exit(bottomleft[0] + fat, bottomleft[1] + exits[3], 1, 0));
    }

    private boolean sameType(TETile jason, TETile ryan) {
        if (jason == null || ryan == null) {
            return false;
        }
        return jason.character() == ryan.character() && jason.description().equals(ryan.description());
    }

    private void jsuhExplore(Queue<Exit> jsuhExplores, int[] start, int[] inc){
        jsuhExplores.add(new Exit(start[0], start[1], inc[0], inc[1]));
    }

    private boolean isNothing(TETile jason) {
        return sameType(jason, Tileset.NOTHING);
    }

    private boolean canRyanRoom(int bottomleftx, int bottomlefty, int fat, int tall) {
        for (int i = bottomleftx; i < bottomleftx + fat; i++) {
            for (int j = bottomlefty; j < bottomlefty + tall; j++) {
                if (!isValid(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean canJasonHall(int[] begin, int length, int[] increment) {
        for (int i = 0; i < length; i++) {
            for (int j = -1; j <= 1; j++) {
                int[] consider = new int[]{begin[0] + j * increment[1] + i * increment[0], begin[1] + j * increment[0] + i * increment[1]};
                if (!isValid(consider[0], consider[1])) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean inBounds(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }
    private boolean isValid(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && isNothing(ryanworld[x][y]);
    }

    private void mark(int[] exit) {
        if (exit.length > 0) {
            ryanworld[exit[0]][exit[1]] = new TETile(Tileset.GRASS, Color.GREEN);
        }
    }
}
