package com.lc.util;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
 
/**
 * ͨ�õĵ���Excel�࣬�����Ҫ�Զ����ʽ�ģ����մ����Լ���д��򷽷���ʵ��,
 * dataList���ÿһ��Object����һ��Ԫ�أ�object[0]��������ţ����ɷ���ʵ����
 * 
 * ��ʾ������Ե�����excel�������˸�ʽ������
 * @author	
 */
public class ExportExcelUtil_format {
	private String title; // �������ı���
	private String[] rowName;// ������������
	private List<Object[]>  dataList = new ArrayList<Object[]>(); // ���������List����
	private HttpServletResponse  response;
	private HttpServletRequest request;
 
	
	/**
	 * ʵ����������
	 * @param title  �������ı����������Ӣ�ģ����Ŀ��ܳ�������
	 * @param rowName ����������������
	 * @param dataList ���������List����
	 * @param response
	 */
	public ExportExcelUtil_format(String title,String[] rowName,List<Object[]>  dataList, HttpServletRequest request, HttpServletResponse  response){
		this.title=title;
		this.rowName=rowName;
		this.dataList=dataList;
		this.response = response;
		this.request = request;
	}
	
	// ��������
	public void exportData() throws Exception{
		HSSFWorkbook workbook =new HSSFWorkbook();      // ����һ��excel����
		HSSFSheet sheet =workbook.createSheet(title);   // �������
		
		
		//sheet.setDefaultRowHeightInPoints(18.5f);
		
		// sheet��ʽ���壬�������涨�����������
		HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook,16);   // ͷ��ʽ(����)
		HSSFCellStyle columnStyle = this.getColumnStyle(workbook,11);         // ������ʽ,�Ӵ�Ч��
		//HSSFCellStyle columnStyle = this.getStyle(workbook,11);                // ������ʽ. ���Ӵ�
		HSSFCellStyle style = this.getStyle(workbook,11);                      // ��Ԫ����ʽ
 
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length-1)));
		/*
		 *  �ϲ���һ�е�������
		 *  ������������
		 */
		HSSFRow rowm  =sheet.createRow(0);        // ��
		rowm.setHeightInPoints(26f);
		HSSFCell cellTiltle =rowm.createCell(0);  // ��Ԫ��
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(title);
		
		
		int columnNum = rowName.length;           // ����еĳ���
		HSSFRow rowRowName = sheet.createRow(1);  // �ڵڶ��д�����
		HSSFCellStyle cells =workbook.createCellStyle();
		cells.setBottomBorderColor(HSSFColor.BLACK.index);  
		rowRowName.setRowStyle(cells);
			
		// ѭ�� �������Ž�ȥ
		for (int i = 0; i < columnNum; i++) {
			HSSFCell cellRowName = rowRowName.createCell(i);
			cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);            // ��Ԫ������
			HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // �õ��е�ֵ
			cellRowName.setCellValue(text);        // �����е�ֵ
			cellRowName.setCellStyle(columnStyle); // ��ʽ
		}
			
		// ����ѯ�����������õ���Ӧ�ĵ�Ԫ����
		for (int i = 0; i < dataList.size(); i++) {
			Object[] obj = dataList.get(i);      //����ÿ������
			HSSFRow row = sheet.createRow(i+2);  //�������������
			for (int j = 0; j < obj.length; j++) {
				 HSSFCell  cell = null;          //���õ�Ԫ����������� 
				 /*
				  * ��һ������������ÿ��ָ����ţ���һ�µĴ�����ʵ��
				  * if(j==0){
					  // ��һ������Ϊ���
					 cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
					 cell.setCellValue(i+1);
				 }else{
					 cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
					 if(!"".equals(obj[j]) && obj[j] != null){
						 cell.setCellValue(obj[j].toString());  //���õ�Ԫ���ֵ  
					 }else{
						 cell.setCellValue("  ");
					 }  
				 }
				  */
				 /*
				  * �ڶ��������ֱ�Ӱ���ʾ�ı���¼�е�˳�򵼳���
				  */
					 cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
					 if(!"".equals(obj[j]) && obj[j] != null){
						 cell.setCellValue(obj[j].toString());  //���õ�Ԫ���ֵ  
					 }else{
						 cell.setCellValue("  ");
					 }  
				 cell.setCellStyle(style); // ��ʽ
			}
		}
		
		//  ���п����ŵ������г��Զ���Ӧ�����Ƕ�����֧�ֲ��Ǻܺ�,Ҳ������linux����ͼ�λ����Ĳ���ϵͳ���±���������˵
		for (int i = 0; i < columnNum; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+888);//�ʵ��ٿ��
		}
		
		if(workbook !=null){
			/*
			 * ������ʽ1���������������ָ�����ļ���
            FileOutputStream fileOutputStream = new FileOutputStream("D:/user.xls");
            workbook.write(fileOutputStream);//������д��ȥ
            fileOutputStream.close();//�ر������
           */
			/*������ʽ2�� ������û��������
			 * 
			 */
			OutputStream out = response.getOutputStream();
			try {
				//ָ�������excel ���ļ���������������ƺ�����ϵ�ǰϵͳ������ʱ��
		        String fileName = title + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
		        String fileName11 = "";
		        String userAgent = request.getHeader("USER-AGENT");
		        /*
		         * ָ����ͬ������б���ʱ���뷽ʽ
		         */
		        if(StringUtils.contains(userAgent, "Firefox") || StringUtils.contains(userAgent, "firefox")){//��������  
		        	fileName11 = new String(fileName.getBytes(), "ISO8859-1");
		        }else{  
		        	fileName11 = URLEncoder.encode(fileName,"UTF-8");   //���������  
		        }
		        String headStr = "attachment; filename=\"" + fileName11 + "\"";
		        response.setContentType("APPLICATION/OCTET-STREAM");
		        response.setCharacterEncoding("UTF-8");
		        response.setHeader("Content-Disposition", headStr);
		        workbook.write(out);
		        out.flush();
		        //workbook.close();
			} catch (Exception e) {
				throw e;
			} finally {
				if (null != out) {
					out.close();
				}
			}
        }  
    }  
			
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook,int fontSize) {  
        // ��������
        HSSFFont font = workbook.createFont();
        //���������С
        font.setFontHeightInPoints((short)fontSize);
        //����Ӵ�
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //������������ 
        font.setFontName("����");
        //������ʽ;
        HSSFCellStyle style = workbook.createCellStyle();
        //����ʽ��Ӧ�����õ�����;    
        style.setFont(font);
        //�����Զ�����;
        style.setWrapText(false);
        //����ˮƽ�������ʽΪ���ж���;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //���ô�ֱ�������ʽΪ���ж���;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
	}  
	
	public HSSFCellStyle getColumnStyle(HSSFWorkbook workbook,int fontSize) {  
        // ��������
        HSSFFont font = workbook.createFont();
        //���������С
        font.setFontHeightInPoints((short)fontSize);
        //����Ӵ�
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //������������ 
        font.setFontName("����");
        //������ʽ;
        HSSFCellStyle style = workbook.createCellStyle();
        //���õױ߿�;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //���õױ߿���ɫ;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //������߿�;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //������߿���ɫ;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //�����ұ߿�;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //�����ұ߿���ɫ;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //���ö��߿�;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //���ö��߿���ɫ;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //����ʽ��Ӧ�����õ�����;    
        style.setFont(font);
        //�����Զ�����;
        style.setWrapText(false);
        //����ˮƽ�������ʽΪ���ж���;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //���ô�ֱ�������ʽΪ���ж���;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
	}
	
	public HSSFCellStyle getStyle(HSSFWorkbook workbook,int fontSize) {
        //��������
        HSSFFont font = workbook.createFont();
        //���������С
        font.setFontHeightInPoints((short)fontSize);
        //����Ӵ�  
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        //������������   
        font.setFontName("����");
        //������ʽ;
        HSSFCellStyle style = workbook.createCellStyle();
        //���õױ߿�;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //���õױ߿���ɫ;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //������߿�;     
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //������߿���ɫ;   
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //�����ұ߿�;   
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //�����ұ߿���ɫ;   
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //���ö��߿�;   
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //���ö��߿���ɫ;    
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //����ʽ��Ӧ�����õ�����;
        style.setFont(font);
        //�����Զ�����;   
        style.setWrapText(false);
        //����ˮƽ�������ʽΪ���ж���;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //���ô�ֱ�������ʽΪ���ж���;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
         
        return style;
	}
}
