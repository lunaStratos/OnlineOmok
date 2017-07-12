package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connect.connect;
import vo.account;
import vo.account_tenshu;

public class serverManager {
	Connection con;
	ArrayList<account> alist;

	public boolean addAccount(account ac) {
		this.con = connect.conn();
		String sql = "insert into account values(?,?)"; // main Ű
		String sql_tenshu = "insert into account_tenshu values(?,?,?)";
		// �ܱ��� ����Ű
		String id = ac.getId();
		String pw = ac.getPw();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql); // account ���
			pst.setString(1, id);
			pst.setString(2, pw);
			pst.executeUpdate(); // end
			pst = con.prepareStatement(sql_tenshu);
			pst.setString(1, id);
			pst.setInt(2, 0); // win
			pst.setInt(3, 0); // defeat
			pst.executeUpdate(); // end
			con.commit(); // commit
			connect.closeSign(con); // close
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public account_tenshu searchAccount(String id, String pw) {
		// TODO Auto-generated method stub
		this.con = connect.conn();
		account_tenshu acc = null;
		String sql = "select a.id, a.password, b.win, b.defeat from account a, account_tenshu b where a.id = b.id and a.id = ? and a.password = ?";
		// �̸� ���� �ֹ� 'Ÿ��' ���� �л� ����
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pw);
			ResultSet rt = pst.executeQuery();
			while (rt.next()) {
				String idGet = rt.getString(1);
				String pwGet = rt.getString(2);
				int win = rt.getInt(3);
				int defeat = rt.getInt(4);
				acc = new account_tenshu(idGet, pwGet,win,defeat);
			}
			connect.closeSign(con); // close
			return acc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return acc;
		}

	}

	public account searchAccountTenshu(String id) { // ���̵� ���� �̱� Ƚ�� ��Ƚ���� Get
		// TODO Auto-generated method stub
		this.con = connect.conn();
		String sql = "select a.id, b.win, b.defeat from account a, account_tenshu b where a.id = b.id and a.id = ?";
		// �̸� ���� �ֹ� 'Ÿ��' ���� �л� ����
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, id);
			ResultSet st = pst.executeQuery(); // get
			account ac = null;
			while (st.next()) {
				String idget = st.getString(1); // id
				int win = st.getInt(2); // pw
				int defeat = st.getInt(3); // pw
				ac = new account_tenshu(idget, "", win, defeat);
			}
			connect.closeSign(con); // close
			return ac;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void updateAccountTenshu(account_tenshu act) {
		String id = act.getId();
		account_tenshu actx = (account_tenshu) searchAccountTenshu(id);
		this.con = connect.conn();
		int win = act.getWin();
		int defeat = act.getDefeat();
		String sql_tenshu_win = "update account_tenshu set win = ? where id = ?";
		String sql_tenshu_defeat = "update account_tenshu set defeat = ? where id = ?";
		PreparedStatement pst = null;
		try {
			if (win == 1) { // �¸��� ���� ������
				pst = con.prepareStatement(sql_tenshu_win);
				pst.setInt(1, actx.getWin() + 1);

			} else if (defeat == 1) { // �й迡 ���� ������ 
				pst = con.prepareStatement(sql_tenshu_defeat);
				pst.setInt(1, actx.getDefeat() + 1);
			}
			pst.setString(2, id); // id�� ���� �˻� �׸�
			pst.executeUpdate();
			con.commit();
			connect.closeSign(con); // close
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean updateAccount(String id, String pw) {
		this.con = connect.conn();
		String sql = "update account set password = ? where id = ?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, pw); // pw �� �ٲ� ��ȣ 
			pst.setString(2, id); // id �� �˻��׸�
			pst.executeUpdate();
			con.commit();
			connect.closeSign(con); // close
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteAccount(String id, String pw) {
		this.con = connect.conn();
		String sql = "delete account where id = ? and password = ?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id); // id �� �˻��׸�
			pst.setString(2, pw); // id �� �˻��׸�
			pst.executeUpdate();
			con.commit();
			connect.closeSign(con); // close
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteAccountTenshu(String id) {
		this.con = connect.conn();
		String sql = "delete account_tenshu where id = ?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, id); // id �� �˻��׸�
			pst.executeUpdate();
			con.commit();
			connect.closeSign(con); // close
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
