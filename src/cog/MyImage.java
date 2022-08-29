package cog;

import javax.swing.ImageIcon;

public class MyImage extends ImageIcon{
	String name;
	public MyImage(ImageIcon img) {
		this.setImage(img.getImage());
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
