package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class loginData implements Serializable {
	/**
	 * 
	 */
	// ���۵Ǵ� �������� ���� ����
	private static final long serialVersionUID = 1L;
	
	//�α��ΰ� ����Ʈ ���ſ� ���
	public static final int login = 1; // �α���

	int state; // ���۵Ǵ� ������ ����
	String id; // ��ȭ���� �Է��� ����� �̸�
	String pw; // ��ȣ (�α��ζ��� ���)
	boolean loginBoolean;
	account_tenshu act ;

	
	public loginData(int state, String id, String pw, boolean loginBoolean, account_tenshu act) {
		super();
		this.state = state;
		this.id = id;
		this.pw = pw;
		this.loginBoolean = loginBoolean;
		this.act = act;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public boolean isLoginBoolean() {
		return loginBoolean;
	}

	public void setLoginBoolean(boolean loginBoolean) {
		this.loginBoolean = loginBoolean;
	}

	public account_tenshu getAct() {
		return act;
	}

	public void setAct(account_tenshu act) {
		this.act = act;
	}

	

}
