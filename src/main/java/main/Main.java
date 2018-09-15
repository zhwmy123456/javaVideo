package main;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Main {
    public static void main0(String[] args) {
        //环境配置，将vlc sdk导入到eclipse
        //if(RuntimeUtil.isWindows()){     }
        NativeLibrary.addSearchPath(
                RuntimeUtil.getLibVlcLibraryName(), ".");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        //System.out.println(LibVlc.INSTANCE.libvlc_get_version());
    }
}
