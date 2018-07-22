package com.qtpselenium.hybrid.driver;

import java.util.Hashtable;
import java.util.Properties;

import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class DriverScript {

	
	public Properties envProp;
	public Properties prop;
	
		// TODO Auto-generated method stub
//Read the Keywords
		public void executeKeywords(String testName, Xls_Reader xls, Hashtable<String,String> testData){
			int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
			System.out.println("Rows "+ rows);
			
			for(int rNum=2;rNum<=rows;rNum++){
				String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
					if(tcid.equals(testName)){
						String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
						String objectKey = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
						String dataKey= xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
						String proceedOnFail=xls.getCellData(Constants.KEYWORDS_SHEET, Constants.PROCEED_COL, rNum);
						String data = testData.get(dataKey);
						System.out.println(tcid +" --- "+ keyword+" --- "+ prop.getProperty(objectKey)+" --- "+ data);
						//test.log(Status.INFO, tcid +" --- "+ keyword+" --- "+ prop.getProperty(objectKey)+" --- "+ data);
						
							
						
					}
				}
			
			
	}

		public Properties getEnvProp() {
			return envProp;
		}

		public void setEnvProp(Properties envProp) {
			this.envProp = envProp;
		}

		public Properties getProp() {
			return prop;
		}

		public void setProp(Properties prop) {
			this.prop = prop;
		}


}
