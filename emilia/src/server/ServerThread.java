package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import vo.*;

public class ServerThread implements Runnable {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static ArrayList<account_tenshu> idList = new ArrayList<>();
	private static ArrayList<makeRoom> makeRoom = new ArrayList<>();
	private static HashMap<String, ServerThread> connectList = new HashMap<>();
	private serverManager sm = new serverManager();
	Socket socket;
	String username;
	String addr;
	private static ArrayList<ServerThread> threadList = new ArrayList<>();
	private static ArrayList<ObjectOutputStream> outputList = new ArrayList<>();

	public ServerThread(Socket socket) {
		try {
			this.socket = socket;
			// Ŭ���̾�Ʈ���� ��Ʈ�� ����
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			addr = socket.getInetAddress().getHostAddress();
		} catch (IOException e) {
			System.out.println(addr + "���� ���� ����.");
		}
		// threadList.add(this);
		// outputList.add(output);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		Object getData = null;
		account ac = null;
		account_tenshu ac_tenshu = null;
		try {
			while (flag) {
				getData = input.readObject();
				// System.out.println(id + " > status:" + state + ", state:" +
				// data.getState());
				if (getData instanceof loginData) { // �α��� �κ�
					// protocol, id, pw, loginboolean
					switch (((loginData) getData).getState()) {

					case loginData.login: // �α���
						String id = ((loginData) getData).getId();
						String pw = ((loginData) getData).getPw();
						int nowconn = 0;
						for (int index = 0; index < idList.size(); index++) {
							// ���� �����ڿ� �ִٸ� �α��ν���
							if (idList.get(index).getId().equals(((loginData) getData).getId())) {
								nowconn++;
							}
						}
						if (nowconn >= 1) { // ���� �����ڰ� �ִٸ� �α��� ���з� ����
							((loginData) getData).setLoginBoolean(false);
						} else { // ���� �����ڿ� �ߺ��� �ƴ϶�� �˻� ����
							ac_tenshu = sm.searchAccount(id, pw); // DB���� ���̵� ��ȣ
																	// �˻�
							if (ac_tenshu != null) { // DB�� ���� �ִٸ�
								threadList.add(this); // ������ �߰�
								connectList.put(id, this);
								((loginData) getData).setLoginBoolean(true);
								((loginData) getData).setAct(ac_tenshu);
								idList.add(ac_tenshu); // ���̴� ������ �߰�
								outputList.add(output);
							} else {// DB�� ���� ���ٸ�
								((loginData) getData).setLoginBoolean(false);
							}
						}
						System.out.println("�װ� " + ac_tenshu);
						System.out.println("�α��� : " + ((loginData) getData).isLoginBoolean());
						output.writeObject(getData);
						output.reset();
						// broadCasting(getData);
						break;
					}

				} else if (getData instanceof roomData) { // ������ ����
					switch (((roomData) getData).getState()) {
					case roomData.loginAc: // ���������� ���ºκ� �߿�
						String id = ((roomData) getData).getId();
						ac = sm.searchAccountTenshu(id); // id�� ã�Ƽ� ������ ������
						// ac�� �����ʹ� id(s), pw(""), win(i), defeat(i)�̴�.
						// ���� ���ӵ� �ο��� �����ִ� Array�� �����ش�.
						if (ac instanceof account_tenshu) {
							((roomData) getData).setWin(((account_tenshu) ac).getWin());
							((roomData) getData).setDefeat(((account_tenshu) ac).getDefeat());
						}
						System.out.println(idList);
						((roomData) getData).setUsernames(idList); // ���� �� ����
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData); // �̰� ��ü �Ѹ���
						break;
					case roomData.Allchat: // ���뿡�� ��üä��
						broadCasting(getData); // �̰� ��ü �Ѹ���
						// int state, String id, String message
						break;
					case roomData.p2pchat: // ���뿡�� ��üä��
						broadCasting(getData); // �̰� ��ü �Ѹ���� �� .
						break;

					case roomData.roomMake: // ���ӹ� ����
						makeRoom mk = ((roomData) getData).getMakeRoomVO();
						int nowInwon = mk.getInwon();
						makeRoom.add(mk);
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData);
						break;
					case roomData.roomDestroy: // ���ӹ� ������
						// �ϴ� ��ĭ���� ����� �� //������ ���̵�� id
						System.out.println(((roomData) getData).getId());
						String uids = ((roomData) getData).getId();
						if (((roomData) getData).isGameEnd()) {
							// ���� �������� �� ������
							for (int index = 0; index < makeRoom.size(); index++) {
								if (makeRoom.get(index).getMakeName().equals(uids)) {
									makeRoom.get(index).setInwon(0);
								} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
									makeRoom.get(index).setInwon(0);
								}
							}
						} else {
							// �Ϲ����� �� ������
							for (int index = 0; index < makeRoom.size(); index++) {
								if (makeRoom.get(index).getMakeName().equals(uids)) {
									// 1���� �����ٰ� �Ѵٸ�

									if (makeRoom.get(index).getInwon() == 1) {
										// ���� �Ѹ��� ���
										makeRoom.get(index).setMakeName(null);
										makeRoom.get(index).setInwon(0);
										// �� ó���� ������ >
									} else if (makeRoom.get(index).getInwon() == 2) {
										// ���� �θ��� ��� player1�� player2�� ó���ؾ� �Ѵ�.
										// �ڹٲ���
										if (makeRoom.get(index).getMakeName().equals(uids)) {
											// 2���϶� 1�� �����ٰ� �Ѵٸ�
											makeRoom.get(index).setMakeName(makeRoom.get(index).getMakeName2());
											// 1�� �÷��̾�� 2���� �ް�
											makeRoom.get(index).setMakeName2(null);
											// 2�� �÷��̾�� null�� ��ü����
										} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
											// 2�� ���̵�� ���ٸ�
											makeRoom.get(index).setMakeName2(null);
										}
										makeRoom.get(index).setInwon(1);
										// �ο��� 1�� ��ü (����)
									}

								} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
									// 2���� �������� �ο��� 2�϶��̴�. 1�϶��� ����.
									makeRoom.get(index).setMakeName2(null);
									makeRoom.get(index).setInwon(1);
									// �ο��� 1�� ��ü (����)
								}
							}
							// �� ���� (0���̸� X)

						}
						for (int index = 0; index < makeRoom.size(); index++) {
							if (makeRoom.get(index).getInwon() <= 0) {
								makeRoom.remove(index);
							}
						}

						System.out.println(makeRoom);
						((roomData) getData).setMakeroom(makeRoom);
						// ���������� �� ����Ʈ ��
						broadCasting(getData);
						break;

					case roomData.roomIn: // ���ӹ� ����
						/*
						 * ä��VO�� ��� mkid�� ���� ���� ���� ���̵� inId�� �� ���̵�
						 */
						String mkid = ((roomData) getData).getMessage();
						String inId = ((roomData) getData).getId();
						// �����, �� ���̵�, ���� ���� ���̵�
						for (int i = 0; i < makeRoom.size(); i++) {
							if (makeRoom.get(i).getMakeName().equals(mkid)) {
								makeRoom.get(i).setInwon(2);
								makeRoom.get(i).setMakeName2(inId);
							}
						}
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData);
						break;
						
					case roomData.roomRefresh: // ����� �ʱ�ȭ
						broadCasting(getData);
						break;

					}
				} else if (getData instanceof gameData) { // ���Ӻκ�
					System.out.println("������ ���ӵ�����");
					String player1 = ((gameData) getData).getId();
					switch (((gameData) getData).getState()) {
					case gameData.ready: // ����
						// protocol, id, ���� ���̵�, makeroom vo�� ���� Arraylist
						for (int i = 0; i < makeRoom.size(); i++) {
							if (makeRoom.get(i).getMakeName().equals(player1)) {
								makeRoom.get(i).setPlayer1(true);
								((gameData) getData).setMakeroom(makeRoom);
							} else if (makeRoom.get(i).getMakeName2().equals(player1)) {
								makeRoom.get(i).setPlayer2(true);
								((gameData) getData).setMakeroom(makeRoom);
							}

						}
						System.out.println(makeRoom);
						System.out.println(makeRoom.get(0).isPlayer1());
						System.out.println(makeRoom.get(0).isPlayer2());
						broadCasting(getData);
						break;

					case gameData.whiteGo: // ���� ���� �ø��� ���
						// gameData.whiteGo, id, omokTable, true, stoneX,stoneY
						System.out.println(player1);
						broadCasting(getData);
						// �˻�� ���� Ŭ���̾�Ʈ���� �Ѵ�.
						break;
					case gameData.blackGo: // ������ �ø��� ���
						System.out.println(player1);
						broadCasting(getData);
						// �˻�� ���� Ŭ���̾�Ʈ���� �Ѵ�.
						break;

					case gameData.chatPerson: // ���ӹ泻 ä��
						broadCasting(getData);
						// �˻�� ���� Ŭ���̾�Ʈ���� �Ѵ�.
						break;

					case gameData.winWhite: // player1 �¸����
						String idWinW = ((gameData) getData).getId();
						int winWhite = ((gameData) getData).getWin();
						account_tenshu act = new account_tenshu(idWinW, "", winWhite, 0);
						sm.updateAccountTenshu(act);

						break;
					case gameData.defeatWhite: // player2 �й���
						String idDefeatW = ((gameData) getData).getId();
						int defeatWhite = ((gameData) getData).getDefeat();
						account_tenshu actDW = new account_tenshu(idDefeatW, "", 0, defeatWhite);
						sm.updateAccountTenshu(actDW);

						break;
					case gameData.winBlack: // player2 �¸����
						String idWinB = ((gameData) getData).getId();
						int winBlack = ((gameData) getData).getWin();
						account_tenshu actWB = new account_tenshu(idWinB, "", winBlack, 0);
						sm.updateAccountTenshu(actWB);

						break;
					case gameData.defeatBlack: // player1 �й���
						String idDefeatB = ((gameData) getData).getId();
						int defeatBlack = ((gameData) getData).getDefeat();
						account_tenshu actDB = new account_tenshu(idDefeatB, "", 0, defeatBlack);
						sm.updateAccountTenshu(actDB);
						break;

					}
				} else if (getData instanceof accountData) { // ȸ������ �κ�
					switch (((accountData) getData).getState()) {

					case accountData.newAccount:// ���ο� ���� ����
						String id = ((accountData) getData).getId();
						String pw = ((accountData) getData).getPw();
						ac = new account(id, pw);
						boolean accountInduceGet = sm.addAccount(ac);
						// truy false
						((accountData) getData).setFlag(accountInduceGet);
						output.writeObject(getData);
						output.reset();
						break;
					case accountData.modifiyAccount:// ��������
						String idModify = ((accountData) getData).getId();
						String pwModify = ((accountData) getData).getPw();
						boolean accountModifyGet = sm.updateAccount(idModify, pwModify);

						((accountData) getData).setFlag(accountModifyGet);
						output.writeObject(getData);
						output.reset();
						break;
					case accountData.deleteAccount:// ���� ����
						boolean accountDeleteGettenshu = false; // table2
						boolean accountDeleteGet = false;
						String idDelete = ((accountData) getData).getId();
						String pwDelete = ((accountData) getData).getPw();
						ac = sm.searchAccount(idDelete, pwDelete);
						if (ac != null) { // DB�� ���� �ִٸ�
							accountDeleteGettenshu = sm.deleteAccountTenshu(idDelete);
							accountDeleteGet = sm.deleteAccount(idDelete, pwDelete);
						} else {
						}
						((accountData) getData).setFlag(accountDeleteGet);
						output.writeObject(getData);
						output.reset();
						break;

					}
				}

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			threadList.remove(output);
			System.out.println("ClassNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			threadList.remove(output);
			System.out.println("IOException");
			flag = false;
		}
	}

	// connectList
	public void broadCasting(Object data) {
		System.out.println("��ε�ĳ���� : Ŭ���̾�Ʈ �� : " + threadList.size());
		for (ServerThread thread : threadList) {
			try {
				thread.output.writeObject(data);
				// thread.output.writeUnshared(data);
				thread.output.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
