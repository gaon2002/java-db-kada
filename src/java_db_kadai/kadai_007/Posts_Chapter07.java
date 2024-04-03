package java_db_kadai.kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		// 
		Connection con = null;
		PreparedStatement statement = null;
		
		//ユーザーリスト
		//多次元配列？　[行][列]
		String[][] postList = {
				{"1033", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！",           "12"},
				{"1003", "2023-02-09", "今日も頑張ります！",       "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！",       "17"},
				{"1002", "2023-02-10", "明日から連休ですね！",     "20"},
		};
		
		try {
			//データ接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
	                "root",
	                "S97740kn!"	
			);
			System.out.println("DB接続成功：" + con);
			System.out.println("レコード追加を実行します");
			
			//SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES ( ?, ?, ?, ? );";
			//ConnectionクラスのprepareStatement()メソッド
			statement = con.prepareStatement(sql);
		
			//読み込んだ行を書きだす箱準備
			int rowCnt = 0;
			int rowNum = 0;
			//準備したレコードを1行ずつDBに読み込ませる			
			for(int i = 0; i < postList.length; i++) {
				// SQLクエリの「?」部分をリストのデータに置き換え
				statement.setString(1, postList[i][0]); //user_id
				statement.setString(2, postList[i][1]); //posted_at
				statement.setString(3, postList[i][2]); //post_content
				statement.setString(4, postList[i][3]); //likes
				
				// SQLクエリを実行
				rowCnt = statement.executeUpdate();
				rowNum++;
			}
			//追加されたレコード数を表示
			System.out.println( rowNum + "件のレコードが追加されました");
			
			System.out.println("====");
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			//テーブルから必要なデータを抽出して出力する
			//SQLクエリを準備
			String rsql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			//ConnectionクラスのprepareStatement()メソッド
			statement = con.prepareStatement(rsql);
			//SQLクエリを実行（DBMSに送信）
			ResultSet result = statement.executeQuery(rsql);
			
			//SQLクエリの実行結果を抽出
			while(result.next()) {
				
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likes = result.getInt("likes");
				//検索したデータを出力
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes );
			}
			

		}catch(SQLException e) {
			System.out.println("レコード追加プロセスでエラー発生：" + e.getMessage());
		}finally{
			//使用したobjectを解放
			if( statement != null) {
				try{ statement.close(); }catch(SQLException ignore) {}
			}
			if( con != null) {
				try{ con.close(); }catch(SQLException ignore) {}
			}
		}
		
	}
}
