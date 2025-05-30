import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Sudoku 
{
    public static void main(String[] args) 
    {
        Start_Game Go=new Start_Game();
    }
}

class Start_Game
{
    //Global declaration section start
    JFrame jr;
    JButton Board_Boxes[][];
    JButton Input_Buttons[];
    int x=0,y=0,Hrz_Round=0,Vrt_Round=0,Total_Sec=0,Mistakes=0,Filled_box=0,min=0,sec=0;
    String SetValue="";
    JLabel mistakeLabel,timer,Levellabel;
    JButton Exit,Stop,Restart,Start;
    Timer time;
    String Selected_level="";
    CardLayout UnHidden_Board_Card;
    JPanel UnHidden_Board_Panel;
    JButton button;
    Boolean Filled=false,Stopped=false,NewGame=false;
    String[] Levels={"Easy","Medium","Hard"};
    int Filled_Row_Button[]=new int[81];
    int Filled_Col_Button[]=new int[81];
    int index=0;
    int One,Two,Three,Four,Five,Six,Seven,Eight,Nine;
    JButton Clicked_Obj;
    int EasyLevelAnswerKay[][]={
        {6,7,2,8,4,9,5,3,1},
        {8,3,1,5,7,2,6,4,9},
        {5,4,9,1,3,6,8,2,7},
        {2,8,4,9,6,1,7,5,3},
        {3,9,6,4,5,7,2,1,8},
        {1,5,7,3,2,8,4,9,6},
        {4,1,5,7,8,3,9,6,2},
        {7,6,3,2,9,4,1,8,5},
        {9,2,8,6,1,5,3,7,4}
    };
    int MediumLevelAnswerKay[][]={
        {2,3,5,4,6,7,1,9,8},
        {4,7,8,5,9,1,3,2,6},
        {1,6,9,3,2,8,7,5,4},
        {6,1,7,9,4,3,5,8,2},
        {8,5,3,2,1,6,4,7,9},
        {9,2,4,8,7,5,6,1,3},
        {7,9,1,6,8,4,2,3,5},
        {5,4,2,1,3,9,8,6,7},
        {3,8,6,7,5,2,9,4,1}
    };
    int HardLevelAnswerKay[][]={
        {7,4,2,5,8,1,6,3,9},
        {9,1,8,7,3,6,5,2,4},
        {6,5,3,4,2,9,7,8,1},
        {2,6,5,3,1,4,8,9,7},
        {3,9,4,8,7,5,1,6,2},
        {8,7,1,6,9,2,3,4,5},
        {5,8,7,2,4,3,9,1,6},
        {1,2,6,9,5,8,4,7,3},
        {4,3,9,1,6,7,2,5,8}
    };

    //Global declaration section end

    //Start Game function start
        Start_Game()
        {
            jr=new JFrame("SUDOKU GAMING");
            Game_Setup();

            jr.setSize(572,740);
            jr.setLayout(null);
            jr.setVisible(true);
            jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String choice=(String)JOptionPane.showInputDialog(jr,
            "Please choose your game level to begin :",
            "Select Difficulty Level",
            JOptionPane.QUESTION_MESSAGE,
            null,
            Levels,
            "Ok"
            );

            if("Easy".equals(choice))
            {
                Levellabel.setText("Level : Easy");
                Selected_level="Easy";
                Mistakes=0;
                Filled_box=38;
                Easy_Level();
            }

            if("Medium".equals(choice))
            {
                Levellabel.setText("Level : Medium");
                Selected_level="Medium";
                Mistakes=0;
                Filled_box=30;
                Medium_Level();
            } 

            if("Hard".equals(choice))
            {
                Levellabel.setText("Level : Hard");
                Selected_level="Hard";
                Mistakes=0;
                Filled_box=25;
                Hard_Level();
            }

            if(choice == null) 
            {
                System.exit(0); 
            }

            time=new Timer(1000,new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Total_Sec++;
                    min=Total_Sec/60;
                    sec=Total_Sec%60;
                    timer.setText(String.format("Time : %02d:%02d ",min,sec));
                }
            });
            time.start();
        }
    //Start Game function end

    //Game_Setup function start
        void Game_Setup()
        {
            JLabel nameplatLabel=new JLabel("SUDOKING");
            nameplatLabel.setBounds(200, -30, 200, 100);
            nameplatLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
            jr.add(nameplatLabel);

            mistakeLabel=new JLabel("Mistakes : "+Mistakes+" / 3 ");
            mistakeLabel.setBounds(50, 20, 200, 100);
            mistakeLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            jr.add(mistakeLabel);

            timer =new JLabel("Time : 00:00");
            timer.setBounds(230, 53, 100, 35);
            timer.setFont(new Font("Times New Roman",Font.BOLD,18));
            jr.add(timer);

            Levellabel=new JLabel("Level : ");
            Levellabel.setBounds(380, 20, 200, 100);
            Levellabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            jr.add(Levellabel);

            Print_Board();
            Print_Number_Buttons();
            Print_Control_Buttons();
        }
    //Game_Setup function end

    //Print board function start
        void Print_Board()
        {
            Board_Boxes=new JButton[9][9];
            y=40;
            for(int i=0;i<9;i++)
            {
                y+=50;
                x=0;
                for(int j=0;j<9;j++)
                {
                    Vrt_Round++;
                    if(Hrz_Round%3==0)
                    {
                        x+=2;
                    }

                    if(Hrz_Round%27==0)
                    {
                        y+=2;
                    }
                    Hrz_Round++;
                    x+=50;
                    Board_Boxes[i][j]=new JButton();
                    Board_Boxes[i][j].setBounds(x, y,50,50);
                    Board_Boxes[i][j].setFont(new Font("Times New Roman", Font.BOLD, 20));
                    Board_Boxes[i][j].addActionListener(Board_Listener);
                    Board_Boxes[i][j].setBackground(new Color(225, 235, 245));
                    jr.add(Board_Boxes[i][j]);
                }
            }
        }
    //Print board function end

    //Print Number button function start
        void Print_Number_Buttons()
        {
            Input_Buttons=new JButton[9];
            x=0;
            y=570;
            for(int i=0;i<9;i++)
            {
                x+=51;
                Input_Buttons[i]=new JButton();
                Input_Buttons[i].setText(""+(i+1));
                Input_Buttons[i].setFont(new Font("Times New Roman",Font.BOLD,20));
                Input_Buttons[i].setBounds(x,y,45,45);
                Input_Buttons[i].addActionListener(Values);
                jr.add(Input_Buttons[i]);
            }
        }
    //Print Number button function end

    //Print control button function start
        void Print_Control_Buttons()
        {
            Restart =new JButton("RESTART");
            Restart.setBounds(50, 640, 90, 40);
            jr.add(Restart);
            Restart.addActionListener(e->
            {
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        Board_Boxes[i][j].setText(null);
                    }
                }
                
                Mistakes=0;
                Total_Sec=0;
                if(Selected_level.equals("Easy"))
                {
                    Filled_box=38;
                    Easy_Level();
                }

                if(Selected_level.equals("Medium"))
                {
                    Filled_box=30;
                    Medium_Level();
                }

                if(Selected_level.equals("Hard"))
                {
                    Filled_box=25;
                    Hard_Level();
                }
            });

            Start =new JButton("START");
            Start.setBounds(168, 640, 90, 40);
            jr.add(Start);
            Start.addActionListener(e->
            { 
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        Board_Boxes[i][j].setForeground(Color.black);
                    }
                }
                for(int rc=0;rc<index;rc++)
                {
                    Board_Boxes[Filled_Row_Button[rc]][Filled_Col_Button[rc]].setForeground(Color.blue);
                }
                Stopped=false;
                time.start(); 
            });

            Stop =new JButton("STOP");
            Stop.setBounds(292, 640, 90, 40);
            jr.add(Stop);
            Stop.addActionListener(e->
            {
                for(int i=0;i<9;i++)
                {
                    for(int j=0;j<9;j++)
                    {
                        Board_Boxes[i][j].setForeground(new Color(225, 235, 245));
                    }
                }
                Stopped=true;
                time.stop();  
            });

            Exit =new JButton("EXIT");
            Exit.setBounds(415, 640,90, 40);
            jr.add(Exit);
            Exit.addActionListener(E->
            {
                System.exit(0);
            });
        }
    //Print control button function end

    //Easy level function start
        void Easy_Level()
        {
            Board_Boxes[0][2].setText("2");
            Board_Boxes[1][0].setText("8");
            Board_Boxes[1][1].setText("3");
            Board_Boxes[2][0].setText("5");

            Board_Boxes[0][3].setText("8");
            Board_Boxes[0][4].setText("4");
            Board_Boxes[0][5].setText("9");
            Board_Boxes[1][3].setText("5");
            Board_Boxes[1][4].setText("7");
            Board_Boxes[2][5].setText("6");

            Board_Boxes[1][6].setText("6");

            Board_Boxes[3][0].setText("2");

            Board_Boxes[3][3].setText("9");
            Board_Boxes[3][4].setText("6");
            Board_Boxes[3][5].setText("1");
            Board_Boxes[4][4].setText("5");
            Board_Boxes[4][5].setText("7");

            Board_Boxes[3][6].setText("7");
            Board_Boxes[3][7].setText("5");
            Board_Boxes[4][7].setText("1");
            Board_Boxes[4][8].setText("8");
            Board_Boxes[5][7].setText("9");

            Board_Boxes[6][0].setText("4");
            Board_Boxes[6][1].setText("1");
            Board_Boxes[7][1].setText("6");
            Board_Boxes[7][2].setText("3");

            Board_Boxes[6][5].setText("3");
            Board_Boxes[7][3].setText("2");
            Board_Boxes[7][4].setText("9");
            Board_Boxes[7][5].setText("4");
            Board_Boxes[8][5].setText("5");
            
            Board_Boxes[6][7].setText("6");
            Board_Boxes[6][8].setText("2");
            Board_Boxes[7][6].setText("1");
            Board_Boxes[7][7].setText("8");
            Board_Boxes[7][8].setText("5");
            Board_Boxes[8][6].setText("3");
            Board_Boxes[8][7].setText("7");

            One=4;
            Two=4;
            Three=4;
            Four=3;
            Five=6;
            Six=5;
            Seven=4;
            Eight=4;
            Nine=4;
        }
    //Easy level function end

    //Medium level function start
        void Medium_Level()
        {
            Board_Boxes[0][2].setText("5");
            Board_Boxes[1][1].setText("7");
            Board_Boxes[2][1].setText("6");
            
            Board_Boxes[1][3].setText("5"); 
            Board_Boxes[1][5].setText("1");

            Board_Boxes[0][6].setText("1");
            Board_Boxes[1][6].setText("3");
            Board_Boxes[1][7].setText("2");
            Board_Boxes[2][6].setText("7");
            
            Board_Boxes[4][0].setText("8");
            Board_Boxes[5][0].setText("9");
            Board_Boxes[5][1].setText("2");
            Board_Boxes[5][2].setText("4");
            
            Board_Boxes[3][3].setText("9");
            Board_Boxes[3][4].setText("4");
            Board_Boxes[3][5].setText("3");
            Board_Boxes[4][3].setText("2");
            Board_Boxes[5][4].setText("7");

            Board_Boxes[4][8].setText("9");
            Board_Boxes[5][7].setText("1");

            Board_Boxes[7][0].setText("5");
            Board_Boxes[7][1].setText("4");
            Board_Boxes[8][2].setText("6");

            Board_Boxes[6][5].setText("4");
            Board_Boxes[7][3].setText("1");
            Board_Boxes[7][5].setText("9");
            Board_Boxes[8][5].setText("2");

            Board_Boxes[6][8].setText("5");
            Board_Boxes[8][7].setText("4");
            Board_Boxes[8][8].setText("1");

            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    Board_Boxes[i][j].setForeground(Color.black);
                }
            }

            One=5;
            Two=4;
            Three=2;
            Four=5;
            Five=4;
            Six=2;
            Seven=3;
            Eight=1;
            Nine=4;
        }
    //Medium level function end

  //Hard level function start
        void Hard_Level()
        {
            Board_Boxes[0][0].setText("7");
            Board_Boxes[0][2].setText("2");
            Board_Boxes[2][0].setText("6");
            Board_Boxes[2][1].setText("5");
            Board_Boxes[2][2].setText("3");

            Board_Boxes[0][5].setText("1");

            Board_Boxes[0][7].setText("3");
            Board_Boxes[2][6].setText("7");

            Board_Boxes[3][0].setText("2");
            Board_Boxes[4][1].setText("9");
            Board_Boxes[4][2].setText("4");

            Board_Boxes[3][3].setText("3");
            Board_Boxes[4][3].setText("8");
            Board_Boxes[4][4].setText("7");
            Board_Boxes[5][5].setText("2");

            Board_Boxes[3][6].setText("8");
            Board_Boxes[3][8].setText("7");
            Board_Boxes[5][6].setText("3");
            Board_Boxes[5][7].setText("4");
            
            Board_Boxes[6][1].setText("8");
            Board_Boxes[8][2].setText("9");

            Board_Boxes[6][4].setText("4");

            Board_Boxes[6][8].setText("6");
            Board_Boxes[7][6].setText("4");
            Board_Boxes[8][7].setText("5");

            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    Board_Boxes[i][j].setForeground(Color.black);
                }
            }

            One=1;
            Two=3;
            Three=4;
            Four=4;
            Five=2;
            Six=2;
            Seven=4;
            Eight=3;
            Nine=2;
        }
    //Hard level function end

    //Value button actionlistener start
        ActionListener Values=e->
        {
            Clicked_Obj=(JButton)e.getSource();
            String SetValue=Clicked_Obj.getText();
            if(!Filled)
            {            
               if(Selected_level.equals("Easy"))
                {
                    Check_validity(button,SetValue,EasyLevelAnswerKay);                
                }

                if(Selected_level.equals("Medium"))
                {
                    Check_validity(button,SetValue,MediumLevelAnswerKay);                
                }

                if(Selected_level.equals("Hard"))
                {
                    Check_validity(button,SetValue,HardLevelAnswerKay);                
                }
               
            } 
        };
    //Value button actionlistener end

    //Board button actionlistener start
        ActionListener Board_Listener=e->
        {
            if(Stopped)
            {
                JOptionPane.showMessageDialog(jr,
                "Game is stopped. Please start the game first.",
                "Game Paused",
                JOptionPane.OK_OPTION);
            }
            button=(JButton)e.getSource();
            String Clicked=button.getText();
            
            if(Clicked != null && Clicked.length() == 1 && !Clicked.equals(""))
            {
                Filled=true;
                JOptionPane.showMessageDialog(jr,
                "This cell contains a fixed value and cannot be changed",
                "Sudoku Rule!",
                JOptionPane.OK_OPTION);
                return;
            }
            Filled=false;
        };
    //Board button actionlistener end

    //Check validity function start
        void Check_validity(JButton Obj,String Val,int[][] AnswerKey)
        {
            if(NewGame)
            {
                NewGame=false;
                return;
            }
            int Value=Integer.parseInt(Val);
            for(int i=0;i<9;i++)
            {
                for(int j=0;j<9;j++)
                {
                    if(Board_Boxes[i][j]==Obj)
                    {
                        if(Value==AnswerKey[i][j])
                        {
                            Obj.setText(Val);
                            Obj.setForeground(Color.blue);
                            Filled_Row_Button[index]=i;
                            Filled_Col_Button[index]=j;
                            index++;
                            switch (Value) 
                            {
                                case 1:
                                    One++;
                                    Check_Limit(One);
                                break;
                            
                                case 2:
                                    Two++;
                                    Check_Limit(Two);
                                break;
                            
                                case 3:
                                    Three++;
                                    Check_Limit(Three);
                                break;
                            
                                case 4:
                                    Four++;
                                    Check_Limit(Four);
                                break;
                            
                                case 5:
                                    Five++;
                                    Check_Limit(Five);
                                break;
                            
                                case 6:
                                    Six++;
                                    Check_Limit(Six);
                                break;
                            
                                case 7:
                                    Seven++;
                                    Check_Limit(Seven);
                                break;
                            
                                case 8:
                                    Eight++;
                                    Check_Limit(Eight);
                                break;
                            
                                case 9:
                                    Nine++;
                                    Check_Limit(Nine);
                                break;
                            }
                            Game_Status();
                        }
                        else
                        {
                            Mistakes++;
                            mistakeLabel.setText("Mistakes : "+Mistakes+" / 3 ");                            
                            if(Mistakes<3)
                            {
                                Obj.setText(Val);
                                Obj.setBackground(new Color(255,102,102));
                                JOptionPane.showMessageDialog(jr, 
                                "Incorrect value! Please try again.\n"+Mistakes+" Mistake Done!!!",
                                "Sudoku Rule - Mistake Detected",
                                JOptionPane.OK_OPTION);
                                Obj.setText("");
                                Obj.setBackground(new Color(225, 235, 245));
                            }
                            else
                            {
                                Obj.setText(Val);
                                Obj.setBackground(new Color(255,102,102));
                                JOptionPane.showMessageDialog(jr, 
                                "You have made 3 wrong attempts! The game will end now.",
                                "Sudoku Rule - Game Over!",
                                JOptionPane.OK_OPTION);
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }
    //Check validity function end

    //Game status function start
        void Game_Status()
        {
            Filled_box++;
            if(Filled_box==81)
            {
                time.stop();
                ImageIcon Trophy=new ImageIcon("Sudoku_Trophy.png");
                int Decision=JOptionPane.showOptionDialog(jr, 
                "Congratulations! You won the game!\nDifficulty level : "+Selected_level+"\nMistakes made : "+Mistakes+"\nTime taken : "+String.format("%02d:%02d", min, sec),
                "Sudoku Complete!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                Trophy,
                new String[]{"New Game","Exit"},"Exit");

                if(Decision==JOptionPane.YES_OPTION)
                {
                    NewGame=true;
                    for(int i=0;i<9;i++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            Board_Boxes[i][j].setText(null);
                        }
                    }

                    String choice=(String)JOptionPane.showInputDialog(jr,
                    "Please choose your game level to begin :",
                    "Select Difficulty Level",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Levels,
                    "Ok"
                    );
                    Total_Sec=0;
                    time.start();
                    if("Easy".equals(choice))
                    {
                        Levellabel.setText("Level : Easy");
                        Selected_level="Easy";
                        Mistakes=0;
                        Filled_box=38;
                        Easy_Level();
                    }

                    if("Medium".equals(choice))
                    {
                        System.out.println(SetValue);
                        Levellabel.setText("Level : Medium");
                        Selected_level="Medium";
                        Mistakes=0;
                        Filled_box=30;
                        Medium_Level();
                    } 

                    if("Hard".equals(choice))
                    {
                        Levellabel.setText("Level : Hard");
                        Selected_level="Hard";
                        Mistakes=0;
                        Filled_box=25;
                        Hard_Level();
                    }

                    if(choice == null) 
                    {
                        System.exit(0); 
                    }
                }
                else
                {
                    System.exit(0);
                }
            }
        }
    //Game status function end

    //Check Limit function start
        void Check_Limit(int Counter)
        {
            if(Counter==9)
            {
                Clicked_Obj.setEnabled(false);
            }
        }
    //Check Limit function end
}
