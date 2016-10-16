package servlet.redirect;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that redirects to an image source
 */
public class Imgs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filePath;
	
	public void init(final ServletConfig config) {
        filePath = config.getServletContext().getInitParameter("file-upload");
        
    }
       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Imgs() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter out = response.getWriter(); 
		
		// Resource request
		String res = request.getPathInfo();
		int h = Integer.parseInt(request.getParameter("height"));
		int w = Integer.parseInt(request.getParameter("width"));
		
		 String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
		 //TODO: Do not permit to get other contents outside of the dir

		 
	    
	        
			 // Test if the file exists
	        if( !new File(filePath, filename).exists() ){
	        	
	        	//out.println("<p>404 Not Found</p>");
	        	
	        	return;
	        }
	        
	        File file = new File(filePath, filename);
	        
	        BufferedImage img = ImageIO.read(file);
	        BufferedImage scaledImage = createResizedCopy(img, w, h, false);
	        response.setHeader("Content-Type", request.getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        ImageIO.write(scaledImage, "png", response.getOutputStream());
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private BufferedImage createResizedCopy(Image originalImage, 
    		int scaledWidth, int scaledHeight, 
    		boolean preserveAlpha)
    {
    	System.out.println("resizing...");
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    }

}
