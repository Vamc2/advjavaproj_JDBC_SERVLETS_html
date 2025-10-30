package com.codegnan.web;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.codegnan.model.Employee;
import com.codegnan.service.EmployeeService;
import com.codegnan.service.EmployeeServiceimpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("*.do")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService service = new EmployeeServiceimpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		response.setContentType("text/html");
		// Handle lsit.do from menu.
		if (uri.endsWith("list.do")) {
			try (PrintWriter out = response.getWriter()) {
				List<Employee> employees = service.getAllEmployee();
				if (employees == null || employees.isEmpty()) {
					out.println("<h3 style='color:red align='center'>No Employees Found</h3>");
				} else {
					out.println("<html><body bgcolor='DDF7F3'><center><br><br>");
					out.println("<h2 style='color:green'>Employee List</h2>");
					out.println("<table border='1' cellpadding='10'");
					out.println("<tr style='background-color:lightgray'>"
							+ "<th>EmpNo</th><th>Name</th><th>Salary</th><th>Address</th></tr>");
					for (Employee emp : employees) {
						out.println("<tr>");
						out.println("<td>" + emp.getEno() + "</td>");
						out.println("<td>" + emp.getEname() + "</td>");
						out.println("<td>" + emp.getEsal() + "</td>");
						out.println("<td>" + emp.getEaddr() + "</td>");
						out.println("<td>" + "<a href='edit.do?eno=" + emp.getEno() + "'>Edit</a> | "
								+ "<form action='delete.do' method='post' style='display:inline;'>"
								+ "<input type='hidden' name='eno' value='" + emp.getEno() + "'>"
								+ "<input type='submit' value='Delete' "
								+ "onclick=\"return confirm('Are you sure?')\">" + "</form></td>");
						out.println("</tr>");
					}
					out.println("</table></center></body></html>");
				}
			}
		} else {
			if (uri.endsWith("edit.do")) {
				int eno = Integer.parseInt(request.getParameter("eno"));
				Employee emp = service.getEmployeeById(eno);
				try (PrintWriter out = response.getWriter()) {
					if (emp == null) {
						forward("/notExisted.html", request, response);
					} else {
						printEditForm(out, emp);
					}
				}
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String uri = request.getRequestURI();
		try (PrintWriter out = response.getWriter()) {
			if (uri.endsWith("add.do")) {
				int eno = Integer.parseInt(request.getParameter("eno"));
				String ename = request.getParameter("ename");
				float esal = Float.parseFloat(request.getParameter("esal"));
				String eaddr = request.getParameter("eaddr");
				Employee emp = new Employee(eno, ename, esal, eaddr);
				String status = service.addEmployee(emp);
				System.out.println(status);
				redirectToPage(status, request, response);
			} else {
				if (uri.endsWith("search.do")) {
					int eno = Integer.parseInt(request.getParameter("eno"));
					Employee emp = service.getEmployeeById(eno);
					if (emp == null) {
						forward("/notexisted.html", request, response);
					} else {
						out.println("<html><body bgcolor='DDF7E3'><center><br><br><table border='1'>");
						out.println("<tr><td>Employee number</td><td>" + emp.getEno() + "</td></tr>");
						out.println("<tr><td>Employee name</td><td>" + emp.getEname() + "</td></tr>");
						out.println("<tr><td>Employee salary</td><td>" + emp.getEsal() + "</td></tr>");
						out.println("<tr><td>Employee Address</td><td>" + emp.getEaddr() + "</td></tr>");
						out.println("</table></center></body></html>");
					}
				} else {
					if (uri.endsWith("delete.do")) {
						int eno = Integer.parseInt(request.getParameter("eno"));
						String status = service.deleteEmployee(eno);
						redirectToPage(status, request, response);
					} else {
						if (uri.endsWith("update.do")) {
							int eno = Integer.parseInt(request.getParameter("eno"));
							String ename = request.getParameter("ename");
							float esal = Float.parseFloat(request.getParameter("esal"));
							String eaddr = request.getParameter("eaddr");
							Employee emp = new Employee(eno, ename, esal, eaddr);
							String status = service.updateEmployee(emp);
							redirectToPage(status, request, response);
						} else {
							if (uri.endsWith("editform.do")) {
								int eno = Integer.parseInt(request.getParameter("eno"));
								Employee emp = service.getEmployeeById(eno);
								if (emp == null) {
									forward("notexisted.html", request, response);
								} else {
									printEditForm(out, emp);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try (PrintWriter out = response.getWriter()) {
				out.println("<h3 style='color:red'>Error Occured : " + e.getMessage() + "</h3>");
			}
		}
	}
	private void printEditForm(PrintWriter out, Employee emp) {
		out.println("<html><body bgcolor='FAF1E4'><center>");
		out.println("<h2 style='color:blue'>EditEmployee</h2>");
		out.println("<form action='update.do' method='post'>");
		out.println("<table>");
		out.println("<tr><td>Emp No:</td><td><input type='text' name='eno' value='"
		+ emp.getEno()+ "' readonly></td></tr>");
		out.println("<tr><td>Name:</td><td><input type='text' name='ename' value='"
				+ emp.getEname() + "'></td></tr>");
		out.println("<tr><td>Salary:</td><td><input type='text' name='esal' value='"
				+ emp.getEsal() + "'></td></tr>");
		out.println(
				"<tr><td>Address:</td><td><input type='text' name='eaddr' value='"
		+ emp.getEaddr() + "'></td></tr>");
		out.println("<tr><td colspan='2' align='center'><input type='submit' "
		+ "value='Update'></td></tr>");
		out.println("</table></form></center></body></html>");
	}
	private void redirectToPage(String status, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (status.toLowerCase()) {
		case "success" -> forward("/success.html", request, response);
		case "failure" -> forward("/failure.html", request, response);
		case "existed" -> forward("/existed.html", request, response);
		default -> forward("/error.html", request, response);
		}
	}
	private void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}
}
