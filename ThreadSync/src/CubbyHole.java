
class CubbyHole {
    private String contents;
    private boolean available;

    
    
    
    public CubbyHole() {
    	available = false;
	}

    
    
    
	public synchronized String getContents() {
		while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
        notifyAll();
        return contents;
	}

	
	public synchronized void setContents(String contents) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        this.contents = contents;
        available = true;
        notifyAll();
	}

	
	public String getPing() {
        setContents("Ping");
        return getContents();
    }

    public String getPong() {
    	setContents("Pong");
        return getContents();
    }
} // class CubyHole
