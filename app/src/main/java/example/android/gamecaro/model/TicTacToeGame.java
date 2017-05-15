package example.android.gamecaro.model;

import java.util.Random;

/**
 * Created by hanth on 12/05/2017.
 */

public class TicTacToeGame {
    private char mBoard[];
    private final static int BOARD_SIZE = 9;

    public static final char PLAYER_ONE = 'X';
    public static final char PLAYER_TWO = '0';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    public TicTacToeGame() {
        mBoard = new char[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }

        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location) {
        mBoard[location] = player;
    }

    public int getComputerMove() {
        int move;

        for (int i = 0; i < getBoardSize(); i++) {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_TWO;
                if (checkForWinner() == 3) {
                    setMove(PLAYER_TWO, i);
                    return i;
                } else {
                    mBoard[i] = curr;
                }
            }
        }

        for (int i = 0; i < getBoardSize(); i++) {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_ONE;
                if (checkForWinner() == 2) {
                    setMove(PLAYER_TWO, i);
                    return i;
                } else {
                    mBoard[i] = curr;
                }
            }
        }

        do {
            move = mRand.nextInt(getBoardSize());
        } while (mBoard[move] == PLAYER_ONE || mBoard[move] == PLAYER_TWO);

        setMove(PLAYER_TWO, move);

        return move;
    }

    public int checkForWinner(){
        for(int i = 0; i <= 6; i+=3){
            if(mBoard[i] == PLAYER_ONE &&
               mBoard[i+1]  == PLAYER_ONE &&
               mBoard[i+2]  == PLAYER_ONE    )
                return 2;

            if(mBoard[i] == PLAYER_TWO &&
               mBoard[i+1]  == PLAYER_TWO &&
               mBoard[i+2]  == PLAYER_TWO    )
                return 3;

        }

        for(int i = 0; i <= 2;i ++){
            if(mBoard[i] == PLAYER_ONE &&
               mBoard[i+3] == PLAYER_ONE &&
               mBoard[i+6] == PLAYER_ONE )
                return 2;

            if(mBoard[i] == PLAYER_TWO &&
               mBoard[i+3] == PLAYER_TWO &&
               mBoard[i+6] == PLAYER_TWO )
                return 3;

        }

        if((mBoard[0] == PLAYER_ONE &&
            mBoard[4] == PLAYER_ONE &&
            mBoard[8] == PLAYER_ONE ||
            mBoard[2] == PLAYER_ONE &&
            mBoard[4] == PLAYER_ONE &&
            mBoard[6] == PLAYER_ONE))
            return 2;

        if((mBoard[0] == PLAYER_TWO &&
            mBoard[4] == PLAYER_TWO &&
            mBoard[8] == PLAYER_TWO ||
            mBoard[2] == PLAYER_TWO &&
            mBoard[4] == PLAYER_TWO &&
            mBoard[6] == PLAYER_TWO))
            return 3;

        for(int i = 0; i < getBoardSize();i++){
            if(mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO){
                return 0;
            }
        }

        return 1;
    }


}
