package pdfTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;



@WebServlet(name = "readPdf", urlPatterns = { "/readPdf" })
@MultipartConfig(fileSizeThreshold = 5000000, maxFileSize = 10000000)
public class readPdf extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Getメソッドの処理
	 *
	 * @param <HttpServletRequest>
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 1 データを用意する
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("Text_1", "test1");
		paraMap.put("Text_2", "test2");
		paraMap.put("Text_3", "test3");

		// 2 PDFフォーム
		PdfReader reader = new PdfReader("D:\\pdf\\testPdf.pdf");
		// 3 新たなpdf作成
		PdfStamper ps;
		try {
			ps = new PdfStamper(reader, new FileOutputStream(
					"D:\\pdf\\my_blspb.pdf"));
			// 4 pdfを取得
			AcroFields s = ps.getAcroFields();
			BaseFont bf = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			s.addSubstitutionFont(bf);
			// 5 フォームにデータを入れる
			Map<String, Item> fieldMap = s.getFields();
			Set<java.util.Map.Entry<String, Item>> set = fieldMap.entrySet();
			Iterator iterator = (Iterator) set.iterator();
			while ((iterator).hasNext()) {
				Entry entry = (Entry) (iterator).next();
				String key = (String) entry.getKey();

				if (paraMap.get(key)!=null) {
					s.setField(key, "" + paraMap.get(key));
//					System.out.println("key="+key+";"+"form1[0].#subform[0].TextField1[0]");
//					s.setField("form1[0].#subform[0].TextField1[0]", "zui");
				}
			}
			ps.setFormFlattening(true);
			ps.close();
		} catch (DocumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		reader.close();
	}

	/**
	 * POSTメソッドの処理
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response); // Getメソッドに受け流す
	}

}
