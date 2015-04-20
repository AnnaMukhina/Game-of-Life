import java.util.*;

/**
 * @author anna_mukhina
 */
public class GameOfLife {
    private static class Field {
        private int[][] current;

        private int[][] old;

        private final int height;

        private final int width;

        public Field(final int height, final int width)
        {
            this.height = height;
            this.width = width;
            this.current = new int[height][width];
            this.old = new int[height][width];

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    current[i][j] = (int)(Math.random()*2);
                }
            }
        }
    }

    private static class Game {
        public boolean isAlive(Field field){
            boolean alive = false;

            for(int i = 0; i < field.height; i++) {
                for (int j = 0; j < field.width; j++) {
                    if(field.current[i][j] == 1){
                        alive = true;
                    }
                }
            }
            return alive;
        }

        public void createNewGeneration(Field field){
            int[][] newCells = birth(field);

            for(int i = 0; i < field.height; i++) {
                for (int j = 0; j < field.width; j++) {
                    field.old[i][j] = field.current[i][j];
                }
            }

            field.current = death(field);

            for(int i = 0; i < field.height; i++) {
                for (int j = 0; j < field.width; j++) {
                    if (newCells[i][j] == 1) {
                        field.current[i][j] = newCells[i][j];
                    }
                }
            }
        }

        public int[][] birth(Field field){
            int[][] newCells = new int[field.height][field.width];

            for(int i = 0; i < field.height; i++){
                for(int j = 0; j < field.width; j++){
                    if(field.current[i][j] == 0){
                        if(neighbors(i,j,field) == 3){
                            newCells[i][j] = 1;
                        }
                        else{
                            newCells[i][j] = 0;
                        }
                    }
                }
            }
            return newCells;
        }

        public int neighbors(int i, int j, Field field){
            boolean[] neighbors = new boolean[8];

            Arrays.fill(neighbors, false);

            if(i-1 >= 0){
                neighbors[0] = field.current[i - 1][j] == 1;
            }
            if(i+1 < field.height){
                neighbors[1] = field.current[i+1][j] == 1;
            }
            if(j+1 < field.width){
                neighbors[2] = field.current[i][j+1] == 1;
            }
            if(j-1 >= 0){
                neighbors[3]  = field.current[i][j-1] == 1;
            }
            if(i-1 >= 0 && j-1 >= 0){
                neighbors[4] = field.current[i-1][j-1] == 1;
            }
            if(i-1 >=0 && j+1 < field.width){
                neighbors[5] = field.current[i-1][j+1] == 1;
            }
            if(i+1 < field.height && j-1 >= 0){
                neighbors[6] = field.current[i+1][j-1] == 1;
            }
            if(i+1 < field.height && j+1 < field.width){
                neighbors[7] = field.current[i+1][j+1] == 1;
            }

            int numberOfNeighbors = 0;

            for(boolean b:neighbors){
                if(b){
                    numberOfNeighbors++;
                }
            }
            return numberOfNeighbors;
        }

        public int[][] death(Field field){
            int[][] survivedCells = new int[field.height][field.width];

            for(int i = 0; i < field.height; i++){
                for(int j = 0; j < field.width; j++){
                    int num = neighbors(i, j, field);
                    if(num > 3 || num < 2){
                        survivedCells[i][j] = 0;
                    }
                    else{
                        survivedCells[i][j] = field.current[i][j];
                    }
                }
            }
            return survivedCells;
        }

        public void play(Field field) {
            System.out.println("First generation:");

            for(int i = 0; i < field.height; i++){
                for(int j = 0; j < field.width; j++){
                    System.out.print(field.current[i][j]);
                }
                System.out.println();
            }
            System.out.println();

            while(isAlive(field)) {
                createNewGeneration(field);

                if(Arrays.deepEquals(field.old, field.current)) {
                    System.out.println("The following game goes in cycles.");
                    break;
                }
                else{
                    System.out.println("Next generation:");

                    for(int i = 0; i < field.height; i++){
                        for(int j = 0; j < field.width; j++){
                            System.out.print(field.current[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void main(String args[]){
        int height = 5;
        int width = 7;

        Field field = new Field(height, width);
        Game game = new Game();

        game.play(field);
    }
}
