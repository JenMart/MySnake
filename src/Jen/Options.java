package Jen;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Jen Mart on 11/20/2015.
 */
public class Options extends JFrame {
    private JPanel rootPanel;
    private JRadioButton a500x500RadioButton;
    private JRadioButton a700x700DefaultRadioButton;
    private JRadioButton a900x900RadioButton;
    private JRadioButton a50x50RadioButton;
    private JRadioButton a10x10DefaultRadioButton;
    private JRadioButton a25x25RadioButton1;
    private JRadioButton slowRadioButton;
    private JRadioButton normalRadioButton;
    private JRadioButton MEGARadioButton;
    private JCheckBox warpWallsCheckBox;
    private JCheckBox mazeWallsCheckBox;
    private JCheckBox randomCheckBox;
    private JComboBox comboBox1;
    private JButton sumitChangesButton;
//    DrawSnakeGamePanel panel = new DrawSnakeGamePanel();
    private ButtonGroup screenSizeButtons = new ButtonGroup();
    DrawSnakeGamePanel panel;
    SnakeGame snakeGame;
    Snake snake;
    Jen.gameSettings gameSettings;
    private ButtonGroup speedButton = new ButtonGroup();
    private ButtonGroup gridSizeButtons = new ButtonGroup();
    int rScreenSize;
    int gameSpeed;



    public Options(){
        super("Game Options");

        //// Settings setup
        rScreenSize = SnakeGame.gameSettings.getScreenX();
        gameSpeed = SnakeGame.gameSettings.getGameSpeed();


        if(gameSpeed == 500) {
            normalRadioButton.setSelected(true);
        }


        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        JetSetupRadio();
//        gameSettings = settings;

        warpWallsCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });

         mazeWallsCheckBox.addItemListener(new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent e) {
                Options.this.mazeWallsCheckBox.setEnabled(true);
             }
         });
        randomCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });

        warpWallsCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Options.this.warpWallsCheckBox.setEnabled(true);
            }
        });
        sumitChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //need to add reset method here
                varReset();
//                SnakeGame.getSnake().re
                closeWindow();
            }
        });
        /////////////////////////////////////////////////////// Game size Radio Group
        a500x500RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {rScreenSize = 500;}
        });
        a700x700DefaultRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {rScreenSize = 700;}
        });
        a900x900RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {rScreenSize = 900;}
        });
        /////////////////////////////////////////////////////// Grid size Radio Group
//        a10x10DefaultRadioButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {gridSize = 50;}
//        });
//        a25x25RadioButton1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {gridSize = 500;}
//        });
//        a50x50RadioButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {gridSize = 500;}
//        });
        /////////////////////////////////////////////////////// Game Speed Radion Group
        slowRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {gameSpeed = 1000;}});
        normalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {gameSpeed = 500;}
        });
        MEGARadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {gameSpeed = 250;}
        });


    }
    private void JetSetupRadio(){
        Options.this.screenSizeButtons.add(a500x500RadioButton);
        Options.this.screenSizeButtons.add(a700x700DefaultRadioButton);
        Options.this.screenSizeButtons.add(a900x900RadioButton);
        ////////////////////////////////////////////////////////
        Options.this.gridSizeButtons.add(a10x10DefaultRadioButton);
        Options.this.gridSizeButtons.add(a25x25RadioButton1);
        Options.this.gridSizeButtons.add(a50x50RadioButton);
        ////////////////////////////////////////////////////////
        Options.this.speedButton.add(normalRadioButton);
        Options.this.speedButton.add(slowRadioButton);
        Options.this.speedButton.add(MEGARadioButton); //proper naming conventions do not apply to MEGA button.

    }
    public void varReset(){
        if(Options.this.mazeWallsCheckBox.isSelected()){
//            panel.changeAddBlock();
            SnakeGame.gameSettings.setMazeWalls(true);

        }
        if(Options.this.warpWallsCheckBox.isSelected()){
//            snake.makeWarpWalls();
            SnakeGame.gameSettings.setWarpWalls(true);
        }
        int numBlocks = comboBox1.getItemCount();
//        snakeGame.setxPixelMaxDimension(rScreenSize);
//        snakeGame.setyPixelMaxDimension(rScreenSize);
//        snakeGame.setNumOfBlocks(numBlocks);
//        snakeGame.getSnakeFrame().setSize(rScreenSize,rScreenSize);
        SnakeGame.gameSettings.setScreenX(rScreenSize);
        SnakeGame.gameSettings.setScreenY(rScreenSize);
        SnakeGame.gameSettings.setNumBlocks(numBlocks);
        SnakeGame.gameSettings.setGameSpeed(gameSpeed);


    }

    public void closeWindow() {
        SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
