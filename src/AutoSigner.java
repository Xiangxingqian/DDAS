import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class AutoSigner extends UiAutomatorTestCase{

	
	//122,659
	//
	//200,1200; 500,1200
	String mainActivity = "com.alibaba.android.rimet/.biz.home.activity.HomeActivity";
	String signActivity = "com.alibaba.android.rimet/com.alibaba.lightapp.runtime.activity.CommonWebViewActivity";
	public void test(){
		UiDevice uiDevice = getUiDevice();
		int displayWidth = uiDevice.getDisplayWidth();
		int displayHeight = uiDevice.getDisplayHeight();
		Log.v("qian",displayHeight+" "+displayWidth);
		
		clickView("工作");
		clickView("钉钉签到");
		//签到打卡
//		clickPosition(120,640);
		clickPosition((int)(1.0/6*displayWidth), (int)(1.0/2*displayHeight));
		//上班签到
//		clickPosition(180, 1200);
		clickPosition((int)(1.0/4*displayWidth), (int)(1200.0/1280*displayHeight));
		
		//按返回键
		uiDevice.pressBack();
		sleep(2*1000);
		uiDevice.pressBack();
		sleep(5*1000);
		
		//签到打卡
//		clickPosition(120,640);
		clickPosition((int)(1.0/6*displayWidth), (int)(1.0/2*displayHeight));
		//下班签到
//		clickPosition(540, 1200);
		clickPosition((int)(3.0/4*displayWidth), (int)(1200.0/1280*displayHeight));
	}

	public void clickPosition(int x, int y){
		UiDevice uiDevice = getUiDevice();
		uiDevice.click(x, y);
		sleep(10*1000);
	}
	
	
	public void clickView(String text){
		try {
			UiObject uiObject = new UiObject(new UiSelector().text(text));
			uiObject.click();
		} catch (UiObjectNotFoundException e) {
			sleep(5*1000);
			clickView(text);
		}
		sleep(5*1000);
	}
	
	public boolean isSignActivity(){
		String ca = getCurrentActivityName();
		if(ca.equals(signActivity)){
			return true;
		}
		return false;
	}
	
	public static String getCurrentActivityName(){
		try {
			Process exec = Runtime.getRuntime().exec("adb shell dumpsys activity");
			InputStream inputStream = exec.getInputStream();
			byte[] b = new byte[1024];
			StringBuilder sb = new StringBuilder();
			while (inputStream.read(b)!=-1) {
				String string = new String(b);
				sb.append(string);
			}
			inputStream.close();
			String msg = sb.toString();
			String[] split = msg.split("Stack #1");
			String stackMsg = split[split.length-1];
							
			String[] split2 = stackMsg.split("\n");
			for(String s:split2){
				if(s.contains("Run #")){
					String[] split3 = s.split(" ");
					return split3[split3.length-2];
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return " ";
	}
}
