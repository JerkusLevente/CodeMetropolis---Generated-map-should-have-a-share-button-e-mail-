package codemetropolis.toolchain.placing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.ScrollPane;
import java.awt.event.WindowEvent;

import java.net.URISyntaxException;
import java.io.IOException;

import javax.imageio.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.*;

import java.net.URI;
import java.awt.*;
import java.awt.event.*;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;

/**
 * A megoldott #302 issue-hoz tartozo link {@link https://github.com/codemetropolis/CodeMetropolis/issues/302}.
 * @author Jerkus Levente - IOMW6W
 */

public class CityMapGUI extends Frame {

	private static final long serialVersionUID = 1L;

	private static final int SCALE = 3;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	/**
	 * @param imageToAttach A csatolandó képfájl a létrehozott térképről.
	 */
	BufferedImage imageToAttach = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	public CityMapGUI(BuildableTree buildables) {

		super("Map");
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());

		/**
		 * @param shareBar Egy menüsáv létrehozása az ablakon belül.
		 */
        MenuBar shareBar = new MenuBar();

		/**
		 * @param shareMenu A menüsávon belül egy lenyitható menü.
		 */
        Menu shareMenu = new Menu("Share Map");

		/**
		 * @param shareEmail A lenyitható menün belül található megosztás gomb email keresztül.
		 */
		MenuItem shareEmail = new MenuItem("Share with Email");
		MenuItem shareDiscord = new MenuItem("Share on Discord");

		shareMenu.add(shareEmail);
		shareMenu.add(shareDiscord);

		shareBar.add(shareMenu);
		setMenuBar(shareBar);

		shareEmail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("You have pressed the 'Share with Email' button!");
				/**
				 * @param imageToAttach Fájl lementése a jar fájl mellé
				 */
				try {
                    ImageIO.write(imageToAttach, "png", new File("anImageOfTheMap.png"));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
				/**
				 * @param desktop Egy "Desktop" objektum aminek a segítségével az elapértelmezett bongeszot/email klienst lehet elerni.
				 */
				Desktop desktop = Desktop.getDesktop();
				/**
				 * @param desktop Alapértelmezett email kliens megnyitása a "To:", "Subject:" és "Body:" kitöltésével.
				 */
				try {
					Desktop.getDesktop().mail(new URI("mailto:email@gmail.com?subject=CodeMetropolis_Map&body=see_attached_file&attachment=anImageOfTheMap.png"));
					} catch (URISyntaxException error) {
					error.printStackTrace();
					} catch (IOException error) {
					error.printStackTrace();
					}
			}
		});

		ScrollPane scroller = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scroller.add(new CityMapCanvas(buildables));
		scroller.setSize(WIDTH, HEIGHT);

		add("Center", scroller);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	public class CityMapCanvas extends Canvas {

		private static final long serialVersionUID = 1L;

		Dimension preferredSize;

		private BuildableTree buildables;

		public CityMapCanvas(BuildableTree buildables) {
			super();
			this.buildables = buildables;

			int mapWidth = 0;
			int mapHeight = 0;

			for(Buildable b : buildables.getBuildables()) {
				if( mapWidth < (b.getPositionX() + b.getSizeX()) * SCALE)
					mapWidth = (b.getPositionX() + b.getSizeX()) * SCALE;
				if( mapHeight < (b.getPositionZ() + b.getSizeZ()) * SCALE)
					mapHeight = (b.getPositionZ() + b.getSizeZ()) * SCALE;
			}

			preferredSize = new Dimension(mapWidth + 10, mapHeight + 10);

		}

		@Override
	    public Dimension getPreferredSize() {
	        return preferredSize;
	    }

	    @Override
	    public void paint(Graphics g) {

	    	Graphics2D g2d = (Graphics2D) g;
			/**
			 * @param g2dITA A már létrehozott "imageToAttach" fájlba ennek a segítségével tudunk rajzolni. Leköveti a Canvas-re rajzoltakat.
			 */
			Graphics2D g2dITA = (Graphics2D) imageToAttach.createGraphics();

			g2dITA.setColor(new Color(255, 255, 255));
			g2dITA.fillRect(0, 0, 800, 600);

			for(Buildable b : buildables.getBuildables()) {
	        	switch(b.getType()) {
	        		case GROUND :
	        			g2d.setColor(new Color(240, 180, 100)); //orange
						g2dITA.setColor(new Color(240, 180, 100));
						break;
	        		case GARDEN :
	        			g2d.setColor(new Color(40, 80, 140)); //blue
						g2dITA.setColor(new Color(40, 80, 140));
						break;
	        		case FLOOR :
	        			g2d.setColor(new Color(170, 200, 30));
						g2dITA.setColor(new Color(170, 200, 30));//green
						break;
	        		case CELLAR :
	        			g2d.setColor(new Color(230, 50, 40));
						g2dITA.setColor(new Color(230, 50, 40));//red
						break;
	        		default:
	        			break;
	        	}
	        	g2d.drawRect(b.getPositionX() * SCALE, b.getPositionZ() * SCALE, b.getSizeX() * SCALE, b.getSizeZ() * SCALE);
				g2dITA.drawRect(b.getPositionX() * SCALE, b.getPositionZ() * SCALE, b.getSizeX() * SCALE, b.getSizeZ() * SCALE);
			}
            g2dITA.dispose();
	    }
	}

}

