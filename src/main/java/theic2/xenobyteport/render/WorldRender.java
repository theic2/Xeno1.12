package theic2.xenobyteport.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class WorldRender {

    public void drawEspLine(double sx, double sy, double sz, double ex, double ey, double ez, float r, float g, float b, float a, float scale) {
        double pX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double pY = Minecraft.getMinecraft().getRenderManager().viewerPosY - 1;
        double pZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;


        GlStateManager.pushMatrix();
        GlStateManager.translate(-pX, -pY, -pZ);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, a);
        GlStateManager.glLineWidth(scale);
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(sx, sy, sz);
        GL11.glVertex3d(ex, ey, ez);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    public void drawEspBlock(double x, double y, double z, float r, float g, float b, float a, float scale) {

        double pX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double pY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double pZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;


        float tr = (1 - scale) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-pX, -pY, -pZ);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.color(r, g, b, a);
        GlStateManager.translate(x, y, z);
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translate(tr, tr, tr);
        GlStateManager.scale(scale, scale, scale);
        GL11.glBegin(GL11.GL_QUADS);
        GlStateManager.glVertex3f(1, 1, 0);
        GlStateManager.glVertex3f(0, 1, 0);
        GlStateManager.glVertex3f(0, 1, 1);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(0, 0, 1);
        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(0, 1, 1);
        GlStateManager.glVertex3f(0, 0, 1);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(0, 1, 0);
        GlStateManager.glVertex3f(1, 1, 0);
        GlStateManager.glVertex3f(0, 1, 1);
        GlStateManager.glVertex3f(0, 1, 0);
        GlStateManager.glVertex3f(0, 0, 0);
        GlStateManager.glVertex3f(0, 0, 1);
        GlStateManager.glVertex3f(1, 1, 0);
        GlStateManager.glVertex3f(1, 1, 1);
        GlStateManager.glVertex3f(1, 0, 1);
        GlStateManager.glVertex3f(1, 0, 0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

//    public void drawWayLine(List<double[]> poses, float r, float g, float b, float a) {
//        BlockPos pos = Minecraft.getMinecraft().getRenderViewEntity().getPosition();
//        double pX = pos.getX();
//        double pY = pos.getY();
//        double pZ = pos.getZ();
//
//        GlStateManager.pushMatrix();;
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//         GlStateManager.disableTexture2D();;
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        GlStateManager.disableDepth();
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GlStateManager.enableBlend();
//        GlStateManager.color(r, g, b, a);
//        GL11.glLineWidth(2);
//        GL11.glBegin(GL11.GL_LINE_STRIP);
//        poses.forEach(p -> {
//            GL11.glVertex3d(p[0] - pX, p[1] - pY, p[2] - pZ);
//        });
//        GL11.glEnd();
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_BLEND);
//        GlStateManager.popMatrix();
//    }

}