package theic2.xenobyteport.api;

import theic2.xenobyteport.render.Renderer;
import theic2.xenobyteport.utils.Utils;

public interface Xeno {

    String mod_id = "xenobyte";
    String author = "NPE(Not BPEX)";
    String mod_name = "Xeno1.12";
    String mod_version = "1.0.6";
    String format_prefix = "§8[§4" + mod_name + "§8]§r ";

    Renderer render = new Renderer();
    Utils utils = new Utils();

}