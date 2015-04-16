package cn.mcm.key;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;

/**
 * @author dlc
 * ��������key����128λ��key
 */
public class GenKey {
	private Context context;
	private String pin;
	
	public GenKey(){
		
	}
	//����һ����context��Ϊ�βεĹ��캯��
	public GenKey(Context c){
		context=c;
	}
	
	//����key
	public String genKey(String imei,String ran){
		String key=null;
		//���r���㷨�Ǻ��ή�����
		byte b1[] = imei.getBytes(); 
		byte b2[] = ran.getBytes();
		int temp=0;
		if(b1.length<=b2.length) {
		   temp = b2.length;
		} else{
		   temp = b1.length;
		}
		for(int i=0;i<temp;i++){ 
		  int b=(int)b1[i]^(int)b2[i]; 
		  System.out.println(b);
		  key=""+b;
		}
		//��key�M��hash
		key.hashCode();
		System.out.println(key);
		return key;
	}
	//�ӱ���˽�д洢����ȡ����
	public void writeInternalStoragePrivate(
	        String filename, byte[] content) {
	    try {
	        //MODE_PRIVATE creates/replaces a file and makes 
	        //  it private to your application. Other modes:
	        //    MODE_WORLD_WRITEABLE
	        //    MODE_WORLD_READABLE
	        //    MODE_APPEND
	        FileOutputStream fos = 
	           context.openFileOutput(filename, Context.MODE_PRIVATE);
	        fos.write(content);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
