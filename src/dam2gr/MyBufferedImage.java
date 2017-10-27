/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam2gr;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Jumi
 */
public class MyBufferedImage extends BufferedImage {

    String name;
    int pixelLength;
    int bright;
    int redBright;
    int greenBright;
    int blueBright;
    int squareBright;
    int squareRedBright;
    int squareGreenBright;
    int squareBlueBright;
    int contrast;
    byte[] baDataRasterOriginal;
    boolean gray;
    boolean squareGray;
    Dimension diSquareBrightDimension;

    public MyBufferedImage(BufferedImage bi, String n) {
        super(
                bi.getColorModel(), bi.getRaster(),
                bi.getColorModel().isAlphaPremultiplied(), null);

        //        this.rasterOriginal = GraphicsUtil.copyRaster(bi.getRaster());
        this.baDataRasterOriginal
                = this.copyDataRasterToByteArray(bi.getRaster());

        this.resetBright();
        this.resetSquareBright();

        this.gray = false;
        this.name = n;
        this.diSquareBrightDimension
                = new Dimension(0, 0);

        if (this.getAlphaRaster() != null) {
            this.pixelLength = 4;
        } else {
            this.pixelLength = 3;
        }
    }

    // Publics -----------------------------------------------------------------
    public int getBright() {
        return this.bright;
    }

    public int getBlueBright() {
        return this.blueBright;
    }

    public int getRedBright() {
        return this.redBright;
    }

    public boolean getGray() {
        return this.gray;
    }

    public int getGreenBright() {
        return this.greenBright;
    }

    public String getImageName() {
        return this.name;
    }

    public int getSquareBright() {
        return this.squareBright;
    }

    public int getSquareBlueBright() {
        return this.squareBlueBright;
    }

    public float getSquareSize() {
        float percent;

        percent = (float) this.diSquareBrightDimension.width / (float) this.getWidth();

        return percent;
    }

    public boolean getSquareGray() {
        return this.squareGray;
    }

    public int getSquareGreenBright() {
        return this.squareGreenBright;
    }

    public int getSquareRedBright() {
        return this.squareRedBright;
    }

    public void incBright(int brightIncr) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.bright += brightIncr;
        this.bright(dataRasterTarget);
    }

    public void resetBright() {
        if (this.bright == 0 & this.redBright == 0
                & this.greenBright == 0 & this.blueBright == 0) {
            return; // ========= Brillo original -> Nada que hacer ============>
        }

        this.bright = 0;
        this.redBright = 0;
        this.greenBright = 0;
        this.blueBright = 0;

        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();
        this.bright(dataRasterTarget);
    }

    public void resetSquareBright() {
        if (this.squareBright == 0 & this.squareRedBright == 0
                & this.squareGreenBright == 0 & this.squareBlueBright == 0) {
            return; // ========== Brillo origina: nada que hacer ==============>
        }

        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareBright = 0;
        this.squareRedBright = 0;
        this.squareGreenBright = 0;
        this.squareBlueBright = 0;

        this.bright(dataRasterTarget);
    }

    public void setBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.bright = brightLevel;
        this.bright(dataRasterTarget);

        if (this.getSquareSize() < 1) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setGray(boolean gray) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.gray = gray;

        if (this.gray) {
            this.gray(dataRasterTarget);
        } else {
            // Convierte a color
            this.setBright(this.bright);
        }
    }

    public void setBlueBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.blueBright = brightLevel;
        this.bright(dataRasterTarget);

        if (this.getSquareSize() < 1) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setGreenBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.greenBright = brightLevel;
        this.bright(dataRasterTarget);

        if (this.getSquareSize() < 1) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setRedBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.redBright = brightLevel;
        this.bright(dataRasterTarget);

        if (this.getSquareSize() < 1) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setSquareBlueBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareBlueBright = brightLevel;
        if (this.getSquareSize() <= 0.99) {
            this.squareBright(dataRasterTarget);
        }

    }

    public void setSquareBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareBright = brightLevel;

        if (this.getSquareSize() <= 0.99) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setSquareDimension(float percent) {
        float newWidth, newHeight;

        newWidth = ((float) this.getWidth()) * percent / 100f;
        newHeight = ((float) this.getHeight()) * percent / 100f;

        this.diSquareBrightDimension.width = (int) newWidth;
        this.diSquareBrightDimension.height = (int) newHeight;

        this.setBright(this.bright);
    }

    public void setSquareGray(boolean gray) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareGray = gray;

        if (this.squareGray) {
            this.squareGray(dataRasterTarget);
        } else {
            // Convierte a color
            this.setSquareBright(this.bright);
        }
    }

    public void setSquareGreenBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareGreenBright = brightLevel;

        if (this.getSquareSize() <= 99) {
            this.squareBright(dataRasterTarget);
        }
    }

    public void setSquareRedBright(int brightLevel) {
        byte[] dataRasterTarget = ((DataBufferByte) this.getRaster().getDataBuffer()).getData();

        this.squareRedBright = brightLevel;

        if (this.getSquareSize() <= 0.999) {
            this.squareBright(dataRasterTarget);
        }
    }

    // Privates ----------------------------------------------------------------
    private void bright(byte[] dataRasterTarget) {
        int r, g, b;
        int newR, newG, newB;

        for (int i = 0; i <= (baDataRasterOriginal.length - 3); i += this.pixelLength) {
            b = Byte.toUnsignedInt(baDataRasterOriginal[i]);
            g = Byte.toUnsignedInt(baDataRasterOriginal[i + 1]);
            r = Byte.toUnsignedInt(baDataRasterOriginal[i + 2]);

            newB = b + this.bright + this.blueBright;
            newG = g + this.bright + this.greenBright;
            newR = r + this.bright + this.redBright;

            newB = (newB > 255 ? 255 : newB);
            newB = (newB < 0 ? 0 : newB);
            newG = (newG > 255 ? 255 : newG);
            newG = (newG < 0 ? 0 : newG);
            newR = (newR > 255 ? 255 : newR);
            newR = (newR < 0 ? 0 : newR);

            dataRasterTarget[i] = (byte) newB;
            dataRasterTarget[i + 1] = (byte) newG;
            dataRasterTarget[i + 2] = (byte) newR;
        }

        // Primero se aplica el brillo y luego se convierte a gris
        if (this.gray) {
            this.gray(dataRasterTarget);
        }
    }

    private byte[] copyDataRasterToByteArray(Raster ras) {
        byte[] baDataRasterCopy;
        byte[] baDataRasterSource;

        if (ras.getDataBuffer().getDataType() != DataBuffer.TYPE_BYTE) {
            throw new IllegalArgumentException("Les dades RGB no son de tipus BYTE");
        }
        baDataRasterSource = ((DataBufferByte) ras.getDataBuffer()).getData();
        baDataRasterCopy = new byte[baDataRasterSource.length];

        System.arraycopy(
                baDataRasterSource, 0, baDataRasterCopy, 0,
                baDataRasterSource.length);

        return baDataRasterCopy;
    }

    private void gray(byte[] dataRasterTarget) {
        int r, g, b;
        byte newRGB;

        this.gray = true;

        for (int i = 0; i <= (dataRasterTarget.length - 3); i += this.pixelLength) {
            r = Byte.toUnsignedInt(dataRasterTarget[i]);
            g = Byte.toUnsignedInt(dataRasterTarget[i + 1]);
            b = Byte.toUnsignedInt(dataRasterTarget[i + 2]);

            newRGB = (byte) ((r + g + b) / 3);

            dataRasterTarget[i] = newRGB;
            dataRasterTarget[i + 1] = newRGB;
            dataRasterTarget[i + 2] = newRGB;
        }
    }


    // To-do tasca 1
    private void squareBright(byte[] dataRasterTarget){

        int r, g, b;
        int newR, newG, newB;

        float tamaño = baDataRasterOriginal.length * this.getSquareSize() / 2;
        int centro = baDataRasterOriginal.length / 2;
        
        for (int i = 0; i <= (baDataRasterOriginal.length - 3); i += this.pixelLength) {
            if(i > centro - tamaño && i < centro + tamaño){
                b = Byte.toUnsignedInt(baDataRasterOriginal[i]);
                g = Byte.toUnsignedInt(baDataRasterOriginal[i + 1]);
                r = Byte.toUnsignedInt(baDataRasterOriginal[i + 2]);

                newB = b + this.squareBright + this.squareBlueBright;
                newG = g + this.squareBright + this.squareGreenBright;
                newR = r + this.squareBright + this.squareRedBright;

                newB = (newB > 255 ? 255 : newB);
                newB = (newB < 0 ? 0 : newB);
                newG = (newG > 255 ? 255 : newG);
                newG = (newG < 0 ? 0 : newG);
                newR = (newR > 255 ? 255 : newR);
                newR = (newR < 0 ? 0 : newR);

                dataRasterTarget[i] = (byte) newB;
                dataRasterTarget[i + 1] = (byte) newG;
                dataRasterTarget[i + 2] = (byte) newR;
            }

        }


    }

    // To-do tasca 1
    private void squareGray(byte[] dataRasterTarget) {

        int r, g, b;
        byte newRGB;

        this.gray = true;

        for (int i = 0; i <= (dataRasterTarget.length - 3); i += this.pixelLength) {
            r = Byte.toUnsignedInt(dataRasterTarget[i]);
            g = Byte.toUnsignedInt(dataRasterTarget[i + 1]);
            b = Byte.toUnsignedInt(dataRasterTarget[i + 2]);

            newRGB = (byte) ((r + g + b) / 3);

            dataRasterTarget[i] = newRGB;
            dataRasterTarget[i + 1] = newRGB;
            dataRasterTarget[i + 2] = newRGB;

        }
    }
}
