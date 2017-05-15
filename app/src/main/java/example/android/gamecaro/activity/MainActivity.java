package example.android.gamecaro.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import example.android.gamecaro.R;
import example.android.gamecaro.model.TicTacToeGame;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TicTacToeGame mCaro9;

    private Button mBoardButtons[];
    private Button btnNewGame;

    // Various text displayed
    private TextView mInfoText;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;


    // Counters for the wins and ties
    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;


    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        boolean mGameType = getIntent().getExtras().getBoolean("gameType");

        initComponents();
        // create a new game subject
        mCaro9 = new TicTacToeGame();

        // start a new game
        startNewGame(mGameType);
    }

    private void initComponents() {

        //initialize Buttons
        mBoardButtons = new Button[mCaro9.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.btn1);
        mBoardButtons[1] = (Button) findViewById(R.id.btn2);
        mBoardButtons[2] = (Button) findViewById(R.id.btn3);
        mBoardButtons[3] = (Button) findViewById(R.id.btn4);
        mBoardButtons[4] = (Button) findViewById(R.id.btn5);
        mBoardButtons[5] = (Button) findViewById(R.id.btn6);
        mBoardButtons[6] = (Button) findViewById(R.id.btn7);
        mBoardButtons[7] = (Button) findViewById(R.id.btn8);
        mBoardButtons[8] = (Button) findViewById(R.id.btn9);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(this);

        // Setup textviews
        mInfoText = (TextView) findViewById(R.id.info);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);

        // set the initial counter display
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));


    }

    // Start a new Game
    // clears the board and resets all buttons text
    // sets game over to be false
    private void startNewGame(boolean isSingle) {

        this.mIsSinglePlayer = isSingle;

        mCaro9.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            //mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mBoardButtons[i].setBackgroundResource(R.mipmap.ic_blank);
        }

        if (mIsSinglePlayer) {

            mPlayerOneText.setText("Human: ");
            mPlayerTwoText.setText("Android: ");

            if (mPlayerOneFirst) {
                mInfoText.setText(R.string.first_human);
                mPlayerOneFirst = false;
            } else {
                mInfoText.setText(R.string.turn_computer);
                int move = mCaro9.getComputerMove();
                setMove(mCaro9.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        } else {
            mPlayerOneText.setText("Player One: ");
            mPlayerTwoText.setText("Player Two: ");

            if (mPlayerOneFirst) {
                mInfoText.setText(R.string.turn_player_one);
                mPlayerOneFirst = false;
            } else {
                mInfoText.setText(R.string.turn_player_two);
                mPlayerOneFirst = true;
            }
        }


        mGameOver = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNewGame:
                if (v.isSelected()) {
                    v.setSelected(false);
                } else {
                    btnNewGame.setSelected(false);
                    v.setSelected(true);
                }
                startNewGame(mIsSinglePlayer);

                break;
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {

                    if (mIsSinglePlayer) {
                        setMove(mCaro9.PLAYER_ONE, location);

                        int winner = mCaro9.checkForWinner();
                        if (winner == 0) {
                            mInfoText.setText(R.string.turn_computer);
                            int move = mCaro9.getComputerMove();
                            setMove(mCaro9.PLAYER_TWO, move);
                            winner = mCaro9.checkForWinner();
                        }

                        if (winner == 0) {
                            mInfoText.setText(R.string.turn_human);
                        } else if (winner == 1) {
                            mInfoText.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoText.setText(R.string.result_human_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else {
                            mInfoText.setText(R.string.result_android_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    } else {
                        if (mIsPlayerOneTurn) {
                            setMove(mCaro9.PLAYER_ONE, location);
                        } else {
                            setMove(mCaro9.PLAYER_TWO, location);
                        }

                        int winner = mCaro9.checkForWinner();
                        if (winner == 0) {
                            if (mIsPlayerOneTurn) {
                                mInfoText.setText(R.string.turn_player_two);
                                mIsPlayerOneTurn = false;

                            } else {
                                mInfoText.setText(R.string.turn_player_one);
                                mIsPlayerOneTurn = true;
                            }
                        } else if (winner == 1) {
                            mInfoText.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoText.setText(R.string.result_player_one_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else {
                            mInfoText.setText(R.string.result_player_two_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    }


                }
            }
        }
    }


    private void setMove(char player, int location) {
        mCaro9.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        //mBoardButtons[location].setText(String.valueOf(player));
        if (player == mCaro9.PLAYER_ONE) {
            // mBoardButtons[location].setTextColor(Color.BLUE);
            mBoardButtons[location].setBackgroundResource(R.drawable.ic_x);
        } else {
            // mBoardButtons[location].setTextColor(Color.RED);
            mBoardButtons[location].setBackgroundResource(R.drawable.ic_o);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.exitname:
                MainActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
