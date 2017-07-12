package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class roomData implements Serializable {
	/**
	 * 
	 */
	// ���۵Ǵ� �������� ���� ����
	private static final long serialVersionUID = 1L;
	// roomGUI���� ���Ǵ� ä�ð� �������
	public static final int loginAc = 9; // ������ �α��� �� ���÷��ÿ�
	public static final int Allchat = 30; // ����ä�� 30����
	public static final int p2pchat = 31; // ���ΰ� ä��
	// RoomMake ���� ����� Signal
	public static final int roomMake = 50; // �� ����� (�� )
	public static final int roomIn = 51; // �� ����
	public static final int roomDestroy = 52; // �� ������ (�� )
	public static final int roomRefresh = 55;

	int state; // ���۵Ǵ� ������ ����
	String id; // ��ȭ���� �Է��� ����� �̸�
	String idOpposing;// ���� ���̵�
	String pw; // ��ȣ (�α��ζ��� ���)
	String message; // �޼��� ����
	int win; // ��
	int defeat; // defeat
	account acc;
	makeRoom makeRoomVO;
	ArrayList<account_tenshu> usernames; // ������ �̸� ���
	ArrayList<makeRoom> makeroom; // �游�鶧 �̰� ���.
	ArrayList<Object> makeroomList; // �� ����� �̰� ���
	boolean ready1p;
	boolean ready2p;
	boolean gameEnd;

	// Data.loninAc
	public roomData(int state, String id, int win, int defeat, ArrayList<account_tenshu> usernames,
			ArrayList<makeRoom> makeroom) {
		super();
		this.state = state;
		this.id = id;
		this.win = win; // �¸���
		this.defeat = defeat; // �й��
		this.usernames = usernames; // ���� ����Ʈ�� ����� ����Ʈ
		this.makeroom = makeroom; // ��� ���� ���ӹ� ����Ʈ
	}

	// ä�� �� �� ���� �Ҷ� ����մϴ�.
	public roomData(int state, String id, String message) {
		super();
		this.state = state;
		this.id = id;
		this.message = message;
	}

	public roomData(int state, ArrayList<account_tenshu> usernames) {
		super();
		this.state = state;
		this.usernames = usernames;
	}

	// �� ����� �� ���
	public roomData(int state, String id, String idOpposing, makeRoom makeRoomVO, ArrayList<makeRoom> makeroom,
			ArrayList<Object> makeroomList, boolean ready1p, boolean ready2p) {
		super();
		this.state = state;
		this.makeRoomVO = makeRoomVO;
		this.id = id;
		this.idOpposing = idOpposing;
		this.makeroom = makeroom;
		this.makeroomList = makeroomList;
		this.ready1p = ready1p;
		this.ready2p = ready2p;

	}

	// �ӼӸ�
	public roomData(int state, String id, String idOpposing, String message) {
		super();
		this.state = state;
		this.id = id;
		this.idOpposing = idOpposing;
		this.message = message;
	}

	// �� ����
	public roomData(int state, String id, ArrayList<makeRoom> makeroom) {
		super();
		this.state = state;
		this.id = id;
		this.makeroom = makeroom;
	}

	// ���� ������ �� ������
	public roomData(int state, String id, ArrayList<makeRoom> makeroom, boolean gameEnd) {
		super();
		this.state = state;
		this.id = id;
		this.makeroom = makeroom;
		this.gameEnd = gameEnd;
	}

	public boolean isGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
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

	public account getAcc() {
		return acc;
	}

	public void setAcc(account acc) {
		this.acc = acc;
	}

	public makeRoom getMakeRoomVO() {
		return makeRoomVO;
	}

	public void setMakeRoomVO(makeRoom makeRoomVO) {
		this.makeRoomVO = makeRoomVO;
	}

	public ArrayList<account_tenshu> getUsernames() {
		return usernames;
	}

	public void setUsernames(ArrayList<account_tenshu> idList) {
		this.usernames = idList;
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

	public boolean isReady1p() {
		return ready1p;
	}

	public void setReady1p(boolean ready1p) {
		this.ready1p = ready1p;
	}

	public boolean isReady2p() {
		return ready2p;
	}

	public void setReady2p(boolean ready2p) {
		this.ready2p = ready2p;
	}

}
