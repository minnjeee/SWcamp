package mvc20230619.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc20230619.dao.DBConnection;
import mvc20230619.dao.StudentDao;
import mvc20230619.dao.StudentDaoImp;
import mvc20230619.dto.StudentDto;

@WebServlet("/student/list.do") //dd에 서블릿의 경로를 등록(자동완성)
public class StudentList extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//list 요청처리 : 페이징, 검색, 정렬
		List<StudentDto> students = null;
		//Servlet에서 요청처리(Controller)와 DB의 자료요청(Model)을 분리시킴
		try {
			Connection conn = DBConnection.getConn();
			StudentDao studentDao = new StudentDaoImp(conn);
			students = studentDao.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("students", students); //view로 forward jsp에 출력할 object 추가
		// http://localhost:8080/contextPath(mvc20230619)/경로(student/list.do)
			// 프로토콜://ip주소/contextPath/경로
			// condtextPath : 톰캣이 여러 프로젝트를 한꺼번에 실행하려고 자동으로 만든 경로
		// 경로의 물리적 위치 (실제로 파일이 저장된 곳)는 webapp 하위
			// webapp/WEB-INF : 클라이언트가 요청할 수 없는 위치, web app 설정 파일 존재 (but, 서버에서는 요청 가능)
		// jsp는 동적리소스인데 정적리소스 취급을 받아서 보안에 취약 => 동적리소스가 정적리소스의 물리적 위치에 존재하면 업로드/실행 가능
			// => 많은 개발자들이 동적리소스의 view로 사용되는 jsp문서를 WEB-INF 하위에 위치시킴
		//**view를 렌더링 or 실행하는 엔진을 템플릿엔진이라 부르고, view templates라고도 부름
		req.getRequestDispatcher("/WEB-INF/templates/student/list.jsp").forward(req, resp);
		// forward : 서블릿이 응답을 완료하지 않고 jsp가 요청 및 응답처리를 완료하겠다!
		
		
	}
}
