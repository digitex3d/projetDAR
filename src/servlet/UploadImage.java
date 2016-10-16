package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.json.JSONException;
import org.json.JSONObject;

import services.service.GeneralTools;
import services.service.ServiceTools;

/**
 * Cette classe reçois des images en paramètre, elle les sauvegarde 
 * dans le dossier spécifié dans la configuration et il réponds avec un
 * objet json qui contient le nombre des images chargés et leur enplacement. 
 */
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String filePath;
	
	public void init(final ServletConfig config) {
        filePath = config.getServletContext().getInitParameter("file-upload");
        
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// L'id qui identifie le logement qu'on est en train d'ajouter d'une façon temporaire.
		String imgid = ServiceTools.generateRandomKey();
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		
		if(imgid == null || !isMultipartContent){
			GeneralTools.serverLog("Erreur de chargement de l'image.");
			try {
				out.write( new JSONObject().
						put("error", "Erreur de chargement de l'image.").toString());

			} catch (JSONException e) {
				e.printStackTrace();

			}

			return;
		}
		
		int nb=0;
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			
			if (!it.hasNext()) {
				GeneralTools.serverLog("Erreur de chargement de l'image.");
				try {
					out.write( new JSONObject().
							put("error", "Erreur de chargement de l'image.").toString());

				} catch (JSONException e) {
					e.printStackTrace();

				}
				return;
			}

			
			
			
			// On parcourt la liste des images
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				
				//TODO: Change to a default path.
				File newFile = new File(filePath, imgid+nb+".png");

				try {
					fileItem.write(newFile);
					
				} catch (Exception e) {
					e.printStackTrace();
				
				}
				
				nb++;
				
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		try {
			JSONObject resu = new JSONObject();
			resu.put("imgid", imgid);
			resu.put("nbimg", nb);
			out.write( resu.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
