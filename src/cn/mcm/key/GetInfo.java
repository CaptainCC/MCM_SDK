package cn.mcm.key;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

public class GetInfo extends Application{
	private Context context;
	//���캯��
	public GetInfo(){
		
	}
	public GetInfo(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	//��ȡ�ֻ�Ӳ����Ϣ��ǰ���ǵ���SDK��app�����Ȩ��
	public String getIMEI(){
		TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
		return imei;	
	}
	//��ȡCPU��Ϣ
	/** 
	  * ��ȡCPU���к� 
	  * 
	  * @return CPU���к�(16λ) 
	  * ��ȡʧ��Ϊ"0000000000000000"
	  * ��Щ�ֻ�ϵͳ���ƾ��ǲ��ܻ�ȡCPU���к� 

	public String getCPUSerial() { 
	        String str = "", strCPU = "", cpuAddress = "0000000000000000"; 
	        try { 
	                //��ȡCPU��Ϣ 
	                Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo"); 
	                InputStreamReader ir = new InputStreamReader(pp.getInputStream()); 
	                LineNumberReader input = new LineNumberReader(ir); 
	                //����CPU���к� 
	                for (int i = 1; i <100 ;i++){
	                        str = input.readLine(); 
	                        if (str != null) { 
	                                //���ҵ����к������� 
	                                if (str.indexOf("Serial") > -1) { 
	                                        //��ȡ���к� 
	                                        strCPU = str.substring(str.indexOf(":") + 1, 
	                                                        str.length()); 
	                                        //ȥ�ո� 
	                                        cpuAddress = strCPU.trim(); 
	                                        break; 
	                                } 
	                        }else{ 
	                                //�ļ���β 
	                                break; 
	                        } 
	                } 
	        } catch (IOException ex) { 
	                //����Ĭ��ֵ 
	                ex.printStackTrace(); 
	        } 
	        return cpuAddress; 
	} 
	*/ 
	public static String getCpuName() {
        try {
                FileReader fr = new FileReader("/proc/cpuinfo");
                BufferedReader br = new BufferedReader(fr);
                String text = br.readLine();
                String[] array = text.split(":\\s+", 2);
                for (int i = 0; i < array.length; i++) {
                }
                return array[1];
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return null;
	}
	

	//���������
	public final String getRandom(int length) {
        char[] numbersAndLetters = null;
        java.util.Random randGen = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            if (randGen == null) {
                randGen = new java.util.Random();
                numbersAndLetters = ("0123456789").toCharArray();
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
        }
        return new String(randBuffer);
    }
	
	//���ܴ�������PIN�룬��Ϊ�������뺯�������бȶ�
	public String getPin(String pin){
		//ͨ����֤��PIN����ȷ
		if(pin!=null){
			
		}
		return pin;
	}
	
}
