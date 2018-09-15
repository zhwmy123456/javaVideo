package main;

import java.awt.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import window.Window;

public class PlayerMain {

    static Window frame;
    //private static final String NATIVE_LIBRARY_SEARCH_PATH = “D:\\Program Files\\VideoLAN\\VLC\\sdk\\lib”;

    public static void main(String[] args) {

        //环境配置，将vlc sdk导入到eclipse

        //if(RuntimeUtil.isWindows()){                  }
        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), ".");  //导入的路径是vlc的安装路径
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);

        //创建主程序界面运行窗体
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    frame=new Window();
                    frame.setVisible(true);
                   // frame.getMediaPlayer().prepareMedia();       //预置播放的视屏
                    new SwingWorker<String, Integer>() {

                        @Override
                        protected String doInBackground() throws Exception {
                            // TODO Auto-generated method stub
                            while (true) {  //获取视频播放进度并且按百分比显示
                                long total=frame.getMediaPlayer().getLength();
                                long curr=frame.getMediaPlayer().getTime();
                                float percent=(float)curr/total;
                                publish((int)(percent*100));
                                Thread.sleep(100);
                            }
                            //return null;
                        }

                        protected void process(java.util.List<Integer> chunks) {
                            for(int v:chunks){
                                frame.getProgressBar().setValue(v);
                            }
                        }
                    }.execute();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //打开文件
    public static void openVideo() {
        JFileChooser chooser=new JFileChooser();
        int v=chooser.showOpenDialog(null);
        if(v==JFileChooser.APPROVE_OPTION){
            File file=chooser.getSelectedFile();
            frame.getMediaPlayer().playMedia(file.getAbsolutePath());
        }
    }

    //退出播放
    public static void exit() {
        frame.getMediaPlayer().release();
        System.exit(0);
    }

    //实现播放按钮的方法
    public static void play() {
        frame.getMediaPlayer().play();
    }

    //实现暂停按钮的方法
    public static void pause() {
        frame.getMediaPlayer().pause();
    }

    //实现停止按钮的方法
    public static void stop() {
        frame.getMediaPlayer().stop();
    }

    //实现点击进度条跳转的方法
    public static void jumpTo(float to) {
        frame.getMediaPlayer().setTime((long)(to*frame.getMediaPlayer().getLength()));
    }

    //实现控制声音的方法
    public static void setVol(int v) {
        frame.getMediaPlayer().setVolume(v);
    }

}