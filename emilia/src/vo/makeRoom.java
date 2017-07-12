package vo;

import java.io.Serializable;

public class makeRoom implements Serializable {

	private String title; // ����
	private String makeName; // �� ������ �̸� 1p
	private String makeName2; // ��� ��� 2p
	private int inwon; // �����ο�
	private boolean player1;
	private boolean player2;

	public makeRoom(String title, String makeName, String makeName2, int inwon) {
		super();
		this.title = title;
		this.makeName = makeName;
		this.inwon = inwon;
		this.makeName2 = makeName2;
	}

	public makeRoom(String title, String makeName, String makeName2, int inwon, boolean player1, boolean player2) {
		super();
		this.title = title;
		this.makeName = makeName;
		this.makeName2 = makeName2;
		this.inwon = inwon;
		this.player1 = player1;
		this.player2 = player2;

	}

	public String getMakeName2() {
		return makeName2;
	}

	public boolean isPlayer1() {
		return player1;
	}

	public void setPlayer1(boolean player1) {
		this.player1 = player1;
	}

	public boolean isPlayer2() {
		return player2;
	}

	public void setPlayer2(boolean player2) {
		this.player2 = player2;
	}

	public void setMakeName2(String makeName2) {
		this.makeName2 = makeName2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public int getInwon() {
		return inwon;
	}

	public void setInwon(int inwon) {
		this.inwon = inwon;
	}

	@Override
	public String toString() {
		return "����: " + title + " / �̸� " + makeName + " /  �����ο�: " + inwon + " �����̸� : " + makeName2 + " �÷��̾� ����" + player1
				+ " �÷��̾� 2" + player2;
	}

	// @Override
	// public String toString() {
	// return "����: " + title + " / �̸� " + makeName + " / �����ο�: " + inwon;
	// }

}
