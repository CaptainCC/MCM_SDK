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
	//构造函数
	public GetInfo(){
		
	}
	public GetInfo(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	//获取手机硬件信息，前提是调用SDK的app有相关权限
	public String getIMEI(){
		TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei=telephonyManager.getDeviceId();
		return imei;	
	}
	//获取CPU信息
	/** 
	  * 获取CPU序列号 
	  * 
	  * @return CPU序列号(16位) 
	  * 读取失败为"0000000000000000"
	  * 有些手机系统限制就是不能获取CPU序列号 

	public String getCPUSerial() { 
	        String str = "", strCPU = "", cpuAddress = "0000000000000000"; 
	        try { 
	                //读取CPU信息 
	                Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo"); 
	                InputStreamReader ir = new InputStreamReader(pp.getInputStream()); 
	                LineNumberReader input = new LineNumberReader(ir); 
	                //查找CPU序列号 
	                for (int i = 1; i <100 ;i++){
	                        str = input.readLine(); 
	                        if (str != null) { 
	                                //查找到序列号所在行 
	                                if (str.indexOf("Serial") > -1) { 
	                                        //提取序列号 
	                                        strCPU = str.substring(str.indexOf(":") + 1, 
	                                                        str.length()); 
	                                        //去空格 
	                                        cpuAddress = strCPU.trim(); 
	                                        break; 
	                                } 
	                        }else{ 
	                                //文件结尾 
	                                break; 
	                        } 
	                } 
	        } catch (IOException ex) { 
	                //赋予默认值 
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
	

	//生成随机数
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
	
	//接受传过来的PIN码，作为参数传入函数，进行比对
	public String getPin(String pin){
		//通过验证则PIN码正确
		if(pin!=null){
			
		}
		return pin;
	}
	
}
