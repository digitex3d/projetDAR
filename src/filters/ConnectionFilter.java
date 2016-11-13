package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.auth.AuthentificationTools;

public class ConnectionFilter implements Filter {
	

	public void init(FilterConfig configuration) throws ServletException {}
	
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		
		System.out.println("CONNECTION FILTER : " + path);
		
		if	(	
				path.startsWith("/CSS")	||
				path.startsWith("/js")	||
				path.startsWith("/fragments") ||
				path.startsWith("/resources") ||
				path.startsWith("/forms") ||
				path.startsWith("/login") ||
				path.startsWith("/register") ||
				path.startsWith("/errors") || 
				path.startsWith("/jquery-1.11.0.js") || 
				path.startsWith("/search.js") ||
				path.startsWith("/house-icon")
			) {
			chain.doFilter(request, response);
			System.out.println("CONNECTION FILTER : skipped ");
			return;
		}
		
		System.out.println("Filtre: Vérification session ");
		// Vérification de session
		Cookie[] cookies = request.getCookies();

		// Parsing des cookies pour vérifier la session
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("session_key")){ 
					// Si la clé n'est pas présent dans la base 
					if(  !AuthentificationTools.getSessionKey(cookie.getValue()) ){
						System.out.println("Erreur d'authentification: la clé n'est pas présente dans la base");
						request.getRequestDispatcher("main.jsp").forward(request, response);
					}


				}
			}
		}else{
			request.getRequestDispatcher("main.jsp").forward(request, response);
			
		}
		
		chain.doFilter(request, response);
	}

}
