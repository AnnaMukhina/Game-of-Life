import java.util.*;

/**
 * @author anna_mukhina
 */
public class GameOfLife {

    public boolean isAlive(int[][] field){
        boolean alive = false;

        for(int i = 0;i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if(field[i][j] == 1){
                    alive = true;
                }
            }
        }
        return alive;
    }

    public int[][] createNewGeneration(int[][] field){
        int[][] newCells = birth(field);

        field = death(field);

        for(int i = 0;i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (newCells[i][j] == 1) {
                    field[i][j] = newCells[i][j];
                }
            }
        }

        System.out.println("Next generation:");

        int i = 0;

        while(i < field.length){
            for(int j = 0; j < field.length; j++){
                System.out.print(field[i][j]);
            }

            System.out.println();

            i++;
        }
        System.out.println();

        return field;
    }

    public int[][] birth(int[][] field){
        int[][] newCells = new int[3][3];

        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                if(field[i][j] == 0){
                    if(neighbors(field,i,j) == 3){
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

    public int neighbors(int[][] field, int i, int j){
        boolean[] neighbors = new boolean[8];

        Arrays.fill(neighbors, false);

        if(i-1 >= 0){
            neighbors[0] = field[i - 1][j] == 1;
        }
        if(i+1 < field.length){
            neighbors[1] = field[i+1][j] == 1;
        }
        if(j+1 < field.length){
            neighbors[2] = field[i][j+1] == 1;
        }
        if(j-1 >= 0){
            neighbors[3]  = field[i][j-1] == 1;
        }
        if(i-1 >= 0 && j-1 >= 0){
            neighbors[4] = field[i-1][j-1] == 1;
        }
        if(i-1 >=0 && j+1 < field.length){
            neighbors[5] = field[i-1][j+1] == 1;
        }
        if(i+1 < field.length && j-1 >= 0){
            neighbors[6] = field[i+1][j-1] == 1;
        }
        if(i+1 < field.length && j+1 < field.length){
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

    public int[][] death(int[][] field){
        int[][] survivedCells = new int[3][3];

        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                int num = neighbors(field, i, j);
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

    public static void main(String args[]){
        GameOfLife gameOfLife = new GameOfLife();

        int[][] field = new int[3][3];

        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                field[i][j] = (int)(Math.random()*2);
            }
        }

        System.out.println("First generation:");

        int i = 0;

        while(i < field.length){
            for(int j = 0; j < field.length; j++){
                System.out.print(field[i][j]);
            }
        System.out.println();

        i++;
        }

        System.out.println();

        while(gameOfLife.isAlive(field)){
            field = gameOfLife.createNewGeneration(field);
        }
    }
}
