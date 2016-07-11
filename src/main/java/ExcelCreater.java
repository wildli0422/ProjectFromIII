
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Servlet implementation class ExcelCreater
 */
@WebServlet("/ExcelCreater")
public class ExcelCreater extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcelCreater() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/vnd.ms-excel");
		String excelFileName = request.getParameter("excelFileName");
		response.setHeader("content-disposition", "inline; filename=\"" + excelFileName + "\"");
		request.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();
		int trSize = new Integer(request.getParameter("trSize"));
		int tdSize = new Integer(request.getParameter("tdSize"));
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("excelTest");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern((short) 1);
		System.out.println("~~~tr = " + trSize + " , td = " + tdSize);
		for (int i = 1; i <= trSize; i++) {
			HSSFRow row = sheet.createRow(i - 1);
			for (int j = 1; j <= tdSize; j++) {
				HSSFCell cell = row.createCell(j - 1);
				String tdContent = request.getParameter("tr" + i + "td" + j);
				System.out.print(" " + tdContent + " ");
				cell.setCellValue(tdContent);
				if (i == 1) {
					cell.setCellStyle(style);
				}
			}
			System.out.println();
		}

		workbook.write(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
