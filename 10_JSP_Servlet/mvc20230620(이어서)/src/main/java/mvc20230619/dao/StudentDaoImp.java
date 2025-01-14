package mvc20230619.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mvc20230619.dto.StudentDto;

public class StudentDaoImp implements StudentDao {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public StudentDaoImp(Connection conn) {//오른쪽버튼 -> Source -> Generate Customer using Fields
		this.conn = conn;
	}

	@Override
	public List<StudentDto> list() throws Exception {
		List<StudentDto> list = null;
		
		String sql = "SELECT * FROM student";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		//여기까지 오류없이 실행되었다는 것은 db에 문제가 없다는 뜻
		list = new ArrayList<>();
		while(rs.next()) {
			StudentDto dto = new StudentDto();
			dto.setNum(rs.getInt("num"));
			dto.setName(rs.getString("name"));
			dto.setPhone(rs.getString("phone"));
			dto.setAddress(rs.getString("address"));
			dto.setBirthday(rs.getString("birthday"));
			list.add(dto);
		}
		
		return list;
	}

	//model을 모듈로 분리해서 재사용
	@Override
	public StudentDto detail(int num) throws Exception {
		StudentDto detail = null;
		String sql = "SELECT * FROM student WHERE num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			detail = new StudentDto();
			detail.setNum(rs.getInt("num"));
			detail.setName(rs.getString("name"));
			detail.setPhone(rs.getString("phone"));
			detail.setAddress(rs.getString("address"));
			detail.setBirthday(rs.getString("birthday"));
		}
		
		return detail;
	}

	@Override
	public int delete(int num) throws Exception {
		int delete = 0;
		
		String sql = "DELETE FROM student WHERE num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		delete = pstmt.executeUpdate();
		
		return delete;
	}

	@Override
	public int update(StudentDto student) throws Exception {
		int update = 0;
		
		String sql ="UPDATE student SET name=?,phone=?,address=?,birthday=? WHERE num=?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, student.getName());
		pstmt.setString(2, student.getPhone());
		pstmt.setString(3,  student.getAddress());
		pstmt.setString(4, student.getBirthday());
		pstmt.setInt(5, student.getNum());
		update = pstmt.executeUpdate();
		
		return update;
	}

	@Override
	public int insert(StudentDto student) throws Exception {
		int insert = 0;
		
		String sql = "INSERT INTO student(num,name,phone,address,birthday)"
				+ " VALUES (?,?,?,?,?)";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, student.getNum());
		pstmt.setString(2, student.getName());
		pstmt.setString(3,student.getPhone());
		pstmt.setString(4, student.getAddress());
		pstmt.setString(5, student.getBirthday());
		insert = pstmt.executeUpdate();
		
		return insert;
	}

}
