package com.lc.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

 
/**
 * ��������Excel�����ʲô�����أ�  
 * ��Ԫ����ö����ı���ʽ����������ǰ�Լ�ȥת��������poi����ת����
 * ��һ�� �����һ�� �����Ǳ����ֶ�!!!���������������Util���õ���List�ͺ�׼ȷ�ˣ�������ֶ�����л��С�
 * @author 
 * @version 
 * ��ʾ��
 * Ŀǰֻ֧��2003���Excel�ļ��ĵ������
 */
public class ImportExcelUtil {
	private final static String excel2003L =".xls";    //2003- �汾��excel
	//private final static String excel2007U =".xlsx";   //2007+ �汾��excel
	
	static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static short[] yyyyMMdd = {14, 31, 57, 58, 179, 184, 185, 186, 187, 188};
    static short[] HHmmss = {20, 32, 190, 191, 192};
    static List<short[]> yyyyMMddList = Arrays.asList(yyyyMMdd);
    static List<short[]> hhMMssList = Arrays.asList(HHmmss);
    /*
     * ���Դ���
     */
    public static void main(String args[]){
    	try{
    	InputStream in=new FileInputStream("f:/stu.xls");
    	ImportExcelUtil ieu=new ImportExcelUtil();
    	List<List<String>> res=ieu.getBankListByExcel(in, ".xls");
    	for(int i=0;i<res.size();i++)
    		System.out.println(res.get(i).get(0)+"---->"+res.get(i).get(1));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	/**
	 * ��������ȡIO���е����ݣ���װ��List<List<Object>>����
	 * @param in,fileName
	 * @return
	 * @throws IOException 
	 */
	public  List<List<String>> getBankListByExcel(InputStream in,String fileType) throws Exception{
		List<List<String>> list = null;
		
		//����Excel������
		Workbook work = this.getWorkbook(in,fileType);
		if(null == work){
			throw new Exception("����Excel������Ϊ�գ�");
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		
		list = new ArrayList<List<String>>();
		//����Excel�����е�sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if(sheet==null){continue;}
			int totalCell = sheet.getRow(0).getPhysicalNumberOfCells();    //������һ���ж�����
			//������ǰsheet�е�������
			for (int j = sheet.getFirstRowNum()+1; j < sheet.getLastRowNum()+1; j++) {
				row = sheet.getRow(j);
				if(row==null || validateRow(row) || row.getPhysicalNumberOfCells() < totalCell){continue;} //3����������һ��Ϊtrue�Ͳ�����list��ӣ��������˿��л������������������У����Ҫע�⣬Ҫ�����ǰ����ж��Ǳ���ġ�
				//�������е���
				List<String> li = new ArrayList<String>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					li.add(this.getCellData(cell));
				}
				list.add(li);
			}
			// �����������ֻ������һ����������
			break;
		}
		//work.close();
		return list;
	}
	// ���˿��У�������һ�е����ݵ�ȷ��Ϊ�գ�������ԭ���ĸ�ʽ���ڣ���û������ɾ����������������������Ͳ���ʵ������ʵ�Ĵ�
	private boolean validateRow(Row row) throws Exception{
   //		for (Cell cell : row) {
   //			
   //		}
		//ֻ�жϵ�һ�С���һ��Ϊ�վʹ������е�������Ч
		if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK || "".equals(this.getCellData(row.getCell(0)))) {
			return true;
		}
		return false;//���ǿ���
	}
	/**
	 * �����������ļ���׺������Ӧ�ϴ��ļ��İ汾 
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	public  Workbook getWorkbook(InputStream inStr,String fileType) throws Exception{
		Workbook wb = null;
		if(excel2003L.equals(fileType)){
			wb = new HSSFWorkbook(inStr);  //2003-
		}
		/*
		 * else if(excel2007U.equals(fileType)){
			wb = new XSSFWorkbook(inStr);  //2007+
		 } 
		*/
		else{
			throw new Exception("�������ļ���ʽ����");
		}
		return wb;
	}
	
	/**
     * ��ȡ��Ԫ��ֵ(�ַ�������)
     *
     * @param cell
     * @return
	 * @throws Exception 
     */
    public String getCellData(Cell cell) throws Exception {
        String cellValue = "";
        if (cell != null) {
            try {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BLANK://�հ�
                        cellValue = "";
                        break;
                    case Cell.CELL_TYPE_NUMERIC: //��ֵ�� 0----��������Ҳ����ֵ�͵�һ��
                        if (DateUtil.isCellDateFormatted(cell)) {
                            short format = cell.getCellStyle().getDataFormat();
 
                            if (yyyyMMddList.contains(format)) {
                                sFormat = new SimpleDateFormat("yyyy-MM-dd");
                            } else if (hhMMssList.contains(format)) {
                                sFormat = new SimpleDateFormat("HH:mm:ss");
                            }
                            Date date = cell.getDateCellValue();
                            cellValue = sFormat.format(date);
                        } else {
                        	cell.setCellType(Cell.CELL_TYPE_STRING);
                        	cellValue = replaceBlank(cell.getStringCellValue());
                            //Double numberDate = new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//�ƺ������е�����
                            //cellValue = numberDate + "";
                        }
                        break;
                    case Cell.CELL_TYPE_STRING: //�ַ����� 1
                        cellValue = replaceBlank(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA: //��ʽ�� 2
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellValue = replaceBlank(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN: //������ 4
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_ERROR: //���� 5
                        cellValue = "!#REF!";
                        break;
                }
            } catch (Exception e) {
            	throw new Exception("��ȡExcel��Ԫ�����ݳ���" + e.getMessage());
            }
        }
        return cellValue;
    }
    
    public static String replaceBlank(String source) {
        String dest = "";
        if (source != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(source);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }
    
}
