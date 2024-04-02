package java_db_kadai.kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {

	public static void main(String[] args) {
		// オブジェクト作成
		Connection con       = null;
		Statement  statement = null;
		
		try {
			//データベース接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"S97740kn!"
				);
			
			System.out.println("データベース接続成功");
			
			// ➀ SQLを実行環境を作成
			statement = con.createStatement();
						
			// ➁ SQLに登録するテーブルの詳細定義
			String sql = """
					 	CREATE TABLE employees (
					 	id      INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
					 	name    VARCHAR(60)  NOT NULL,
					 	email   VARCHAR(255) NOT NULL,
					 	age     INT(11),
					 	address VARCHAR(255)
					 	);
					 	""";
			// ③ 作成したテーブル情報をMySQLに送信し、登録する
			int rowCnt = statement.executeUpdate(sql);
			System.out.println("テーブルを作成：rowCnt=" + rowCnt);
		// 例外発生時
		}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		// close処理
		}finally {
			// 使用したオブジェクトを解放
			// statement != null：オブジェクトが生成済み(nullではないか)かを確認してからcloseする
			// close()メソッドがエラーになるときの有効手段がないため、catch時のSQLExceptionはignoreしている
			if( statement != null ) {
				try { statement.close();} catch(SQLException ignore) {}
			}
			if( con != null ) {
				try { con.close(); } catch(SQLException ignore) {}
			}
		}
	}

}
