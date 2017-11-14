
public class Ping extends Thread {
	private CubbyHole cubby;
	
	
	
	
	public Ping(CubbyHole cubby) {
		this.cubby = cubby;
	}

	
	
	
	public void run() {
		for (int i = 0 ; i < 20; i++) {
			System.out.println(cubby.getPing());
		}
	}
}
