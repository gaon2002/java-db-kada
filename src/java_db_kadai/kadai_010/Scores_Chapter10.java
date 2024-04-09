package java_db_kadai.kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

public static void main(String[] args) {
		// テストの点数順に並べ替える
		// 接続情報
		final String URL = "jdbc:mysql://localhost/challenge_java";
		final String USER = "root";
		final String PASS = "S97740kn!";
		
		// 接続関係の変数設定
		Connection con = null;
		Statement statement = null;
		
		// SQLクエリ内容➀：データを追加　
		String sql1 = "UPDATE scores SET score_math = '95', score_english = '90' WHERE id = 5;";
		String sql2 = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC;";
		
		try {
			//DB接続
			con = DriverManager.getConnection(URL, USER, PASS);
			
			System.out.println("データベース接続成功：" + con);
			
			//クエリ準備
			statement = con.createStatement();
			
			// ➀レコード更新クエリ実行
			System.out.println("レコード更新を実行します");
	        int rowCnt = statement.executeUpdate(sql1);
	        System.out.println( rowCnt + "件のレコードが更新されました");
	        
	        
	        System.out.println("");
	        
	        // ➁並べ替え、データ取得クエリ実行
			System.out.println("数学・英語の点数が高い順に並べ替えました");
			
	        ResultSet result  = statement.executeQuery(sql2);
	        
	        //SQLクエリの実行結果を抽出　
	        while(result.next()) {
	        	 int id = result.getInt("id");
	        	 String name = result.getString("name");
	        	 int math = result.getInt("score_math");
	        	 int english = result.getInt("score_english");
	        	 
	        	 System.out.println(result.getRow() + "件目：生徒id=" + id + "／氏名=" + name + "／数学=" + math + "／英語=" + english);
	        }
	        	 
			
		}catch( SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			if(statement != null) {
				try { statement.close();}catch(SQLException ignore) {}
			}
			if(con != null) {
				try { con.close();}catch(SQLException ignore) {}
			}
		}
	}

}
