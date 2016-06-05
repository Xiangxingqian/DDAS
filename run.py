import os 
os.popen('android create uitest-project -n ddas -t 7 -p .')
os.popen('ant build')
os.popen('adb push bin\ddas.jar /data/local/tmp/')
os.popen('adb shell uiautomator runtest ddas.jar -c AutoSigner')
