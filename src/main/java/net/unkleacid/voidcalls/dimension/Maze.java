package net.unkleacid.voidcalls.dimension;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Maze {
    public final int width, height;
    public final boolean[][] eastOpen, southOpen;

    public Maze(int width, int height, long seed) {
        this.width  = width;
        this.height = height;
        this.eastOpen  = new boolean[width][height];
        this.southOpen = new boolean[width][height];
        generate(seed);
    }

    private void generate(long seed) {
        boolean[][] visited = new boolean[width][height];
        Deque<int[]> stack = new ArrayDeque<>();
        Random rnd = new Random(seed);

        int cx = 0, cz = 0;
        visited[cx][cz] = true;
        stack.push(new int[]{cx, cz});

        while (!stack.isEmpty()) {
            int[] cell = stack.peek();
            cx = cell[0]; cz = cell[1];
            java.util.List<int[]> neighbors = new java.util.ArrayList<>();
            if (cx > 0 && !visited[cx-1][cz]) neighbors.add(new int[]{cx-1, cz});
            if (cz > 0 && !visited[cx][cz-1]) neighbors.add(new int[]{cx, cz-1});
            if (cx < width-1 && !visited[cx+1][cz]) neighbors.add(new int[]{cx+1, cz});
            if (cz < height-1 && !visited[cx][cz+1]) neighbors.add(new int[]{cx, cz+1});

            if (!neighbors.isEmpty()) {
                int[] nb = neighbors.get(rnd.nextInt(neighbors.size()));
                int nx = nb[0], nz = nb[1];
                if (nx == cx+1) eastOpen[cx][cz] = true;
                if (nz == cz+1) southOpen[cx][cz] = true;
                if (nx == cx-1) eastOpen[nx][nz] = true;
                if (nz == cz-1) southOpen[nx][nz] = true;

                visited[nx][nz] = true;
                stack.push(new int[]{nx, nz});
            } else {
                stack.pop();
            }
        }
    }
}
