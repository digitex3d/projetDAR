package servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import services.auth.AuthentificationTools;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/getuserinfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); 
		
		
		// On sauvegarde les paramètres
		User user = null;
		
		if(request.getParameter("id") != null){
			int id = Integer.parseInt(request.getParameter("id"));
			// On récuperation de l'utilisateur
			try {
				user = AuthentificationTools.getUser(id);
			} catch (SQLException e) {
				out.write("<p> 404: Not Found </p>");
				e.printStackTrace();
				
			}
			
			if ( user != null ){
			String respHTML=
					"<table>\n"+
							" <tr>\n"+
							" <td>\n"+
								"<p> Nom </p>\n"+
								"</td>\n"+
								" <td>\n"+
									"<p id='profile_name_"+ id +"' >" + user.getName() + "</p>\n"+
								"</td>\n"+
								" </tr>\n"+
								" <tr>\n"+
								" <td>\n"+
									"<p> Prénom </p>\n"+
								"</td>\n"+
								" <td>\n"+
									"<p id='profile_firstname_"+ id +"'>" + user.getFirstname() + "</p>\n"+
								"</td>\n"+
								" </tr>\n"+
								" <tr>\n"+
								" <td>\n"+
									"<p> Email </p>\n"+
								"</td>\n"+
								" <td>\n"+
									"<p id='profile_mail_"+ id +"'> " + user.getEmail() + "</p>\n"+
								"</td>\n"+
								" </tr>\n"+
					"</table>";
			
			out.write(respHTML);
			}else{
				out.write("<p> 404: Not Found </p>");
			}
		}
		else
			out.write("<p> 404: Not Found </p>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
