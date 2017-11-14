
public class Runner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CubbyHole ch = new CubbyHole();
		Ping ping = new Ping(ch);
		Pong pong = new Pong(ch);
		
		ping.start();
		pong.start();
	}

}
