package Lecture_25;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.Popup;
import javax.swing.plaf.PopupMenuUI;

public class TTT extends JFrame implements ActionListener{
	
	public static final int BOARD_SIZE = 3;
	
	public JButton[][] board = new JButton[BOARD_SIZE][BOARD_SIZE];
	
	private enum GameStatus{
		XWins,
		ZWins,
		Tie,
		Incomplete
	}
	
	private boolean crossTurn = true;
	
	public TTT(){
		super.setSize(700,700);
		super.setResizable(false);
		super.setTitle("TicTacToe");
		GridLayout layout = new GridLayout(BOARD_SIZE,BOARD_SIZE);
		super.setLayout(layout);
		
		Font font = new Font("Times New Roman",1, 250);
	
		for(int i=0;i<BOARD_SIZE;i++){
			for(int j=0;j<BOARD_SIZE;j++){
				JButton button = new JButton("");
//				button.setBackground(Color.white);
				button.setFont(font);
				button.addActionListener(this);
				board[i][j] = button;
				super.add(button);
			}
		}
		
		
		
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		makeMove(clickedButton);
		GameStatus gs = getGameStatus();
		
		if(gs != GameStatus.Incomplete){
			declareWinner(gs);
			int selected = JOptionPane.showConfirmDialog(this,"Do you want to Restart?");
			
			if(selected == JOptionPane.YES_OPTION){
				for(int i=0;i<BOARD_SIZE;i++){
					for(int j=0;j<BOARD_SIZE;j++){
						board[i][j].setText("");
					}
				}
				this.crossTurn = true;
			}
			else{
				super.dispose();
			}
		}
		
	}
	
	public void makeMove(JButton btn){
		if(btn.getText().length() > 0){
			JOptionPane.showMessageDialog(this,"INVALID MOVE");
			return;
		}
		
		if(crossTurn){
			btn.setText("X");
		}
		else{
			btn.setText("0");
		}
		
		crossTurn = !crossTurn;
	}
	
	public GameStatus getGameStatus(){
		// Rows
		int row , col;
		String text1 = "", text2 = "";
		row = 0;
		while(row < BOARD_SIZE){
			col = 0;
			while(col < BOARD_SIZE-1){
				text1 = board[row][col].getText();
				text2 = board[row][col+1].getText();
				if(!text1.equals(text2) || text1.length() == 0){
					break;
				}
				col++;
			}
			if(col == BOARD_SIZE-1){
				return text1.equals("X")?GameStatus.XWins : GameStatus.ZWins;
			}
			row++;
		}
		
		// Cols
		
		col = 0;
		while(col < BOARD_SIZE){
			row = 0;
			while(row < BOARD_SIZE-1){
				text1 = board[row][col].getText();
				text2 = board[row+1][col].getText();
				if(!text1.equals(text2) || text1.length() == 0){
					break;
				}
				row++;
			}
			if(row == BOARD_SIZE-1){
				return text1.equals("X")?GameStatus.XWins : GameStatus.ZWins;
			}
			col++;
		}
		
		// Diagonal 1
		
		row = 0;
		col = 0;
		while(row < BOARD_SIZE-1){
			
			text1 = board[row][col].getText();
			text2 = board[row+1][col+1].getText();
			
			if(!text1.equals(text2) || text1.length() == 0){
				break;
			}
			
			row++;
			col++;
			
		}
		
		if(row == BOARD_SIZE-1){
			return text1.equals("X")?GameStatus.XWins : GameStatus.ZWins;
		}
		
		row = BOARD_SIZE-1;
		col = 0;
		while(row > 0 && col< BOARD_SIZE - 1){
			
			text1 = board[row][col].getText();
			text2 = board[row-1][col+1].getText();
			
			if(!text1.equals(text2) || text1.length() == 0){
				break;
			}
			
			row--;
			col++;
			
		}
		
		if(col ==  BOARD_SIZE-1){
			return text1.equals("X")?GameStatus.XWins : GameStatus.ZWins;
		}
		
		row = 0;
		while(row < BOARD_SIZE){
			col = 0;
			while(col < BOARD_SIZE){
				text1 = board[row][col].getText();
				if(text1.length() == 0){
					return GameStatus.Incomplete;
				}
				col++;
			}
			row++;
		}
		
		return GameStatus.Tie;
	}
	
	public void declareWinner(GameStatus gs){
		if(gs == GameStatus.XWins){
			JOptionPane.showMessageDialog(this,"X Wins");
		}
		else if(gs == GameStatus.ZWins){
			JOptionPane.showMessageDialog(this,"0 Wins");
		}
		else{
			JOptionPane.showMessageDialog(this,"It's a Tie");
		}
	}

}
