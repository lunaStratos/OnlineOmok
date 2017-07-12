package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class gameData implements Serializable {
	/**
	 * 
	 */
	// ���۵Ǵ� �������� ���� ����
	private static final long serialVersionUID = 1L;

	// BattleRoom Signal
	public static final int chatPerson = 75; // ���� (������ 70���� )
	public static final int ready = 70; // ���� (������ 70���� )
	public static final int start = 71; // ���� (������ 70���� )

	public static final int whiteGo = 73; // �Ͼᵹ �ø�
	public static final int blackGo = 74; // ������ �ø�

	public static final int winWhite = 76; // ������ �¸�
	public static final int defeatWhite = 77; // ������ �¸�
	public static final int winBlack = 78; // ������ �¸�
	public static final int defeatBlack = 79; // ������ �¸�
	int state; // ���۵Ǵ� ������ ����
	String id; // ��ȭ���� �Է��� ����� �̸�
	String idOpposing;// ���� ���̵�
	String message; // �޼��� ����
	int win; // ��
	int defeat; // defeat
	String player2; // �ӽú���, �ƹ��ų� �־ ��� ����
	makeRoom makeRoomVO;
	ArrayList<String> usernames; // ������ �̸� ���
	ArrayList<makeRoom> makeroom; // �游�鶧 �̰� ���.
	ArrayList<Object> makeroomList; // �� ����� �̰� ���
	int[][] omokTable; // ���ӽ� �̰� ����
	boolean ready1p;
	boolean doolWhiteBlack;

	int x;
	int y;

	// �游��� // �� ������
	public gameData(int state, String id, ArrayList<makeRoom> makeroom) {
		super();
		this.state = state;
		this.id = id;
		this.makeroom = makeroom;
	}
	



	// �������� �Ͼᵹ ���� ����Ʈ����
	public gameData(int state, String id, String idOpposing, String player2, int[][] omokTable, boolean doolWhiteBlack,
			int x, int y) {
		super();
		this.state = state;
		this.id = id;
		this.idOpposing = idOpposing;
		this.player2 = player2;
		this.omokTable = omokTable;
		this.doolWhiteBlack = doolWhiteBlack;
		this.x = x;
		this.y = y;
	}

	// �� �޴� �κ�
	public gameData(int state, String id, int[][] omokTable) {
		super();
		this.state = state;
		this.id = id;
		this.omokTable = omokTable;
	}

	// ä�ýÿ� ���.
	public gameData(int state, String id, String idOpposing, String message, String player2) {
		super();
		this.state = state;
		this.id = id;
		this.idOpposing = idOpposing;
		this.message = message;
		this.player2 = player2;
	}
	// ���б�Ͽ�
	public gameData(int state, String id, int win, int defeat) {
		super();
		this.state = state;
		this.id = id;
		this.win = win;
		this.defeat = defeat;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdOpposing() {
		return idOpposing;
	}

	public void setIdOpposing(String idOpposing) {
		this.idOpposing = idOpposing;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDefeat() {
		return defeat;
	}

	public void setDefeat(int defeat) {
		this.defeat = defeat;
	}

	public String getplayer2() {
		return player2;
	}

	public void setplayer2(String player2) {
		this.player2 = player2;
	}

	public makeRoom getMakeRoomVO() {
		return makeRoomVO;
	}

	public void setMakeRoomVO(makeRoom makeRoomVO) {
		this.makeRoomVO = makeRoomVO;
	}

	public ArrayList<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(ArrayList<String> usernames) {
		this.usernames = usernames;
	}

	public ArrayList<makeRoom> getMakeroom() {
		return makeroom;
	}

	public void setMakeroom(ArrayList<makeRoom> makeroom) {
		this.makeroom = makeroom;
	}

	public ArrayList<Object> getMakeroomList() {
		return makeroomList;
	}

	public void setMakeroomList(ArrayList<Object> makeroomList) {
		this.makeroomList = makeroomList;
	}

	public int[][] getOmokTable() {
		return omokTable;
	}

	public void setOmokTable(int[][] omokTable) {
		this.omokTable = omokTable;
	}

	public boolean isReady1p() {
		return ready1p;
	}

	public void setReady1p(boolean ready1p) {
		this.ready1p = ready1p;
	}

	public boolean isDoolWhiteBlack() {
		return doolWhiteBlack;
	}

	public void setDoolWhiteBlack(boolean doolWhiteBlack) {
		this.doolWhiteBlack = doolWhiteBlack;
	}

}
