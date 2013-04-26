package com.taco.menu;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import com.taco.main.State;
import com.taco.main.StateManager;

public class Menu implements MouseListener {

	private Graphics2D g2d;
	private StateManager gameState;
	private Image menuBck;
	private Image menuGuiBck;
	private Image menuTitle;
	private Image menuChooseTitle;
	private Image menuSelector;
	private Image buttonStart;
	private Image buttonChoose;
	private Image buttonLeft;
	private Image buttonRight;
	private Image buttonSelect;
	/*
	 * private Image playerBoy; private Image playerGirl;
	 */
	private Rectangle menuBckRec;
	private Rectangle menuGuiBckRec;
	private Rectangle menuTitleRec;
	private Rectangle menuChooseTitleRec;
	private Rectangle menuSelectorRec;
	private Rectangle buttonStartRec;
	private Rectangle buttonChooseRec;
	private Rectangle buttonLeftRec;
	private Rectangle buttonRightRec;
	private Rectangle buttonSelectRec;
	private Rectangle playerBoyRec;
	private Rectangle playerGirlRec;
	private Rectangle mouseRec;
	private int menuState;
	private int playerType;
	private boolean mouseHeld;
	private static final int MAINMENU = 0;
	private static final int CHOOSECHAR = 1;
	private static final int PAUSE = 2;
	private static final int MENUOFF = 3;

	public Menu(Image i, StateManager s) {
		g2d = (Graphics2D) i.getGraphics();

		gameState = s;
		menuState = MAINMENU;
		mouseHeld = false;

		menuBck = (new ImageIcon(this.getClass().getResource("menuBck.png")))
				.getImage();
		menuGuiBck = (new ImageIcon(this.getClass().getResource(
				"menuGuiBck.png"))).getImage();
		menuTitle = (new ImageIcon(this.getClass().getResource("menuTitle.png")))
				.getImage();
		menuChooseTitle = (new ImageIcon(this.getClass().getResource(
				"menuChooseTitle.png"))).getImage();
		menuSelector = (new ImageIcon(this.getClass().getResource(
				"menuSelector.png"))).getImage();
		buttonStart = (new ImageIcon(this.getClass().getResource(
				"buttonStart.png"))).getImage();
		buttonChoose = (new ImageIcon(this.getClass().getResource(
				"buttonChoose.png"))).getImage();
		buttonLeft = (new ImageIcon(this.getClass().getResource(
				"buttonLeft.png"))).getImage();
		buttonRight = (new ImageIcon(this.getClass().getResource(
				"buttonRight.png"))).getImage();
		buttonSelect = (new ImageIcon(this.getClass().getResource(
				"buttonSelect.png"))).getImage();
		/*
		 * playerBoy = (new
		 * ImageIcon(this.getClass().getResource("playerBoy.png"))).getImage();
		 * playerGirl = (new
		 * ImageIcon(this.getClass().getResource("playerGirl.png"))).getImage();
		 */
		menuBckRec = new Rectangle(0, 0, menuBck.getWidth(null),
				menuBck.getHeight(null));
		menuGuiBckRec = new Rectangle(0, 0, menuGuiBck.getWidth(null),
				menuGuiBck.getHeight(null));
		menuTitleRec = new Rectangle(0, 0, menuTitle.getWidth(null),
				menuTitle.getHeight(null));
		menuChooseTitleRec = new Rectangle(0, 0,
				menuChooseTitle.getWidth(null), menuChooseTitle.getHeight(null));
		menuSelectorRec = new Rectangle(0, 0, menuSelector.getWidth(null),
				menuSelector.getHeight(null));
		buttonStartRec = new Rectangle(0, 0, buttonStart.getWidth(null),
				buttonStart.getHeight(null));
		buttonChooseRec = new Rectangle(0, 0, buttonChoose.getWidth(null),
				buttonChoose.getHeight(null));
		buttonLeftRec = new Rectangle(0, 0, buttonLeft.getWidth(null),
				buttonLeft.getHeight(null));
		buttonRightRec = new Rectangle(0, 0, buttonRight.getWidth(null),
				buttonRight.getHeight(null));
		buttonSelectRec = new Rectangle(0, 0, buttonSelect.getWidth(null),
				buttonSelect.getHeight(null));
		/*
		 * playerBoyRec = new Rectangle(0, 0, playerBoy.getWidth(null),
		 * playerBoy.getHeight(null)); playerGirlRec = new Rectangle(0, 0,
		 * playerGirl.getWidth(null), playerGirl.getHeight(null));
		 */
		mouseRec = new Rectangle(0, 0, 10, 10);

		menuBckRec.setLocation(0, 0);
		menuGuiBckRec.setLocation(xCenter(menuBckRec, menuGuiBckRec),
				yCenter(menuBckRec, menuGuiBckRec));
		menuTitleRec.setLocation(xCenter(menuGuiBckRec, menuTitleRec),
				(int) (menuGuiBckRec.y + menuGuiBckRec.height * .05));
		menuChooseTitleRec
				.setLocation(
						xCenter(menuGuiBckRec, menuChooseTitleRec),
						(int) (menuTitleRec.y + menuTitleRec.height + (menuGuiBckRec.height * .05)));
		menuSelectorRec
				.setLocation(
						xCenter(menuGuiBckRec, menuSelectorRec),
						(int) (menuChooseTitleRec.y + menuChooseTitleRec.height + (menuGuiBckRec.height * .05)));
		buttonStartRec.setLocation(xCenter(menuGuiBckRec, buttonStartRec),
				(int) (menuGuiBckRec.y + menuGuiBckRec.height * .40));
		buttonChooseRec
				.setLocation(
						xCenter(menuGuiBckRec, buttonChooseRec),
						(int) (buttonStartRec.y + buttonStartRec.height + (menuGuiBckRec.height * .05)));
		buttonLeftRec.setLocation((int) (menuSelectorRec.x
				- buttonLeftRec.width - (menuGuiBckRec.height * .05)),
				yCenter(menuSelectorRec, buttonLeftRec));
		buttonRightRec.setLocation((int) (menuSelectorRec.x
				+ menuSelectorRec.width + (menuGuiBckRec.height * .05)),
				yCenter(menuSelectorRec, buttonRightRec));
		buttonSelectRec
				.setLocation(
						xCenter(menuGuiBckRec, buttonSelectRec),
						(int) (menuSelectorRec.y + menuSelectorRec.height + (menuGuiBckRec.height * .05)));
		playerBoyRec.setLocation(xCenter(menuGuiBckRec, playerBoyRec),
				yCenter(menuSelectorRec, playerBoyRec));
		playerGirlRec.setLocation(xCenter(menuGuiBckRec, playerGirlRec),
				yCenter(menuSelectorRec, playerGirlRec));
	}

	public void update(StateManager state) {
		gameState = state;
	}

	public void render() {
		if (gameState.getState() == State.MENU) {
			g2d.drawImage(menuBck, 0, 0, null);
		}

		g2d.drawImage(menuGuiBck, menuGuiBckRec.x, menuGuiBckRec.y, null);

		if (menuState == MAINMENU) {
			g2d.drawImage(menuTitle, menuTitleRec.x, menuTitleRec.y, null);
			g2d.drawImage(buttonStart, buttonStartRec.x, buttonStartRec.y, null);
			g2d.drawImage(buttonChoose, buttonChooseRec.x, buttonChooseRec.y,
					null);
		} else if (menuState == CHOOSECHAR) {
			g2d.drawImage(menuTitle, menuTitleRec.x, menuTitleRec.y, null);
			g2d.drawImage(menuChooseTitle, menuChooseTitleRec.x,
					menuChooseTitleRec.y, null);
			g2d.drawImage(menuSelector, menuSelectorRec.x, menuSelectorRec.y,
					null);
			g2d.drawImage(buttonLeft, buttonLeftRec.x, buttonLeftRec.y, null);
			g2d.drawImage(buttonRight, buttonRightRec.x, buttonRightRec.y, null);
			g2d.drawImage(buttonSelect, buttonSelectRec.x, buttonSelectRec.y,
					null);
			/*
			 * if(getPlayerType() == Player.BOY) g2d.drawImage(playerBoy,
			 * playerBoyRec.x, playerBoyRec.y, null); else
			 * g2d.drawImage(playerGirl, playerGirlRec.x, playerGirlRec.y,
			 * null);
			 */
		} else if (menuState == PAUSE) {

		}
		g2d.draw(mouseRec);

	}

	/**
	 * background = What it's being centered in. </br> foreground = What is
	 * being centered.
	 * 
	 * @param d
	 * @return
	 */
	private int xCenter(Rectangle background, Rectangle foreground) {
		return background.x + (background.width - foreground.width) / 2;
	}

	private int yCenter(Rectangle background, Rectangle foreground) {
		return background.y + (background.height - foreground.height) / 2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseRec.setLocation(e.getX() - 3, e.getY() - 25);

		/*
		 * if(menuState == MAINMENU) { if (mouseRec.intersects(buttonStartRec))
		 * { setMenuState(MENUOFF); gameState.setState(State.GAME);
		 * System.out.println("buttonStart"); } if
		 * (mouseRec.intersects(buttonChooseRec)) { setMenuState(CHOOSECHAR);
		 * System.out.println("buttonChoose"); } } else if(menuState ==
		 * CHOOSECHAR) { if (mouseRec.intersects(buttonLeftRec)) {
		 * if(getPlayerType() == Player.BOY) playerType = Player.GIRL; else
		 * playerType = Player.BOY; System.out.println("buttonLeft"); } if
		 * (mouseRec.intersects(buttonRightRec)) { if(getPlayerType() ==
		 * Player.BOY) playerType = Player.GIRL; else playerType = Player.BOY;
		 * System.out.println("buttonRight"); } if
		 * (mouseRec.intersects(buttonSelectRec)) { setMenuState(MAINMENU);
		 * System.out.println("buttonSelect - playerType set too " +
		 * getPlayerType()); } } else if(menuState == PAUSE) {
		 * 
		 * }
		 */
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseHeld = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseHeld = false;
	}

	private void setMenuState(int s) {
		menuState = s;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getPlayerType() {
		return playerType;
	}

	public int getGameState() {
		return gameState.getState();
	}

}
