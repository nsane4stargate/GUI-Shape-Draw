package choose_draw;


import javax.swing.JFrame;

public class HW3_6347 {
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Paint");
		DrawPad outer = new DrawPad();
		frame.setContentPane( new DrawPad() );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 350);
		frame.setVisible(true);
		

		
	}//end of main method

}
