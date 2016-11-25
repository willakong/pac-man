import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Ghost extends Mover{

	//Costumes TODO
	public static final ImageIcon[] IMAGE =
		{
			new ImageIcon("./images/Ghost0.png"),	
			new ImageIcon("./images/Ghost1.png"),	
			new ImageIcon("./images/Ghost2.png"),
			new ImageIcon("./images/Ghost3.png"),
			new ImageIcon("./images/GhostWeak.png"),
			new ImageIcon("./images/GhostApple.png"),
			new ImageIcon("./images/GhostBert.png"),
			new ImageIcon("./images/GhostErnie.png"),
			new ImageIcon("./images/GhostHelloKitty.png"),
			new ImageIcon("./images/GhostHomer.png"),
			new ImageIcon("./images/GhostR2-D2.png"),
			new ImageIcon("./images/GhostRocker.png")
		};
	
	public Ghost (int gNum){
		this.setIcon(IMAGE[gNum]);
	}
}
