import java.util.*;

/**
 * @author anna_mukhina
 */
public class GameOfLife {

    private final int height;

    private final int width;

    private int[][] oldField;

    private int[][] field;

    public GameOfLife(final int height, final int width)
    {
        this.height = height;
        this.width = width;
        this.field = new int[height][width];
        this.oldField = new int[height][width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                field[i][j] = (int)(Math.random()*2);
            }
        }
    }

    public boolean isAlive(){
        boolean alive = false;

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(field[i][j] == 1){
                    alive = true;
                }
            }
        }
        return alive;
    }

    public void createNewGeneration(){
        int[][] newCells = birth();

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                    oldField[i][j] = field[i][j];
                }
        }

        field = death();

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (newCells[i][j] == 1) {
                    field[i][j] = newCells[i][j];
                }
            }
        }
    }

    public int[][] birth(){
        int[][] newCells = new int[height][width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(field[i][j] == 0){
                    if(neighbors(i,j) == 3){
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

    public int neighbors(int i, int j){
        boolean[] neighbors = new boolean[8];

        Arrays.fill(neighbors, false);

        if(i-1 >= 0){
            neighbors[0] = field[i - 1][j] == 1;
        }
        if(i+1 < height){
            neighbors[1] = field[i+1][j] == 1;
        }
        if(j+1 < width){
            neighbors[2] = field[i][j+1] == 1;
        }
        if(j-1 >= 0){
            neighbors[3]  = field[i][j-1] == 1;
        }
        if(i-1 >= 0 && j-1 >= 0){
            neighbors[4] = field[i-1][j-1] == 1;
        }
        if(i-1 >=0 && j+1 < width){
            neighbors[5] = field[i-1][j+1] == 1;
        }
        if(i+1 < height && j-1 >= 0){
            neighbors[6] = field[i+1][j-1] == 1;
        }
        if(i+1 < height && j+1 < width){
            neighbors[7] = field[i+1][j+1] == 1;
        }

        int numberOfNeighbors = 0;

        for(boolean b:neighbors){
            if(b){
                numberOfNeighbors++;
            }
        }
        return numberOfNeighbors;
    }

    public int[][] death(){
        int[][] survivedCells = new int[height][width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int num = neighbors(i, j);
                if(num > 3 || num < 2){
                    survivedCells[i][j] = 0;
                }
                else{
                    survivedCells[i][j] = field[i][j];
                }
            }
        }
        return survivedCells;
    }

    public void play() {
        System.out.println("First generation:");

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        while(isAlive()) {
            createNewGeneration();

            if(Arrays.deepEquals(oldField, field)) {
                System.out.println("The following game goes in cycles.");
                break;
            }
            else{
                System.out.println("Next generation:");

                for(int i = 0; i < height; i++){
                    for(int j = 0; j < width; j++){
                        System.out.print(field[i][j]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

    public static void main(String args[]){
        int height = 5;
        int width = 7;

        GameOfLife gameOfLife = new GameOfLife(height, width);

        gameOfLife.play();
    }
}
