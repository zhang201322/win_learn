package test;

import android.test.AndroidTestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by 林 on 2014/12/5.
 */
public class StringBufferTest extends AndroidTestCase{
    public void testStringbufferdeleteCharAt(){
        File file = new File("/sdcard/test/StringBufferTestLog1.txt");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            StringBuffer buffer = new StringBuffer();
            buffer.append("0123456789");
            buffer.deleteCharAt(buffer.length()-1);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStringbufferdeleteCharAtLength(){
        File file = new File("/sdcard/test/StringBufferTestLog2.txt");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            StringBuffer buffer = new StringBuffer();
            buffer.append("0123456789");
            buffer.deleteCharAt(buffer.length());
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStringbufferdelete03(){
        File file = new File("/sdcard/test/StringBufferTestLog03.txt");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            StringBuffer buffer = new StringBuffer();
            buffer.append("0123456789");
            buffer.delete(0, 3);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStringbufferdelete1L(){
        File file = new File("/sdcard/test/StringBufferTestLog1L.txt");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            StringBuffer buffer = new StringBuffer();
            buffer.append("0123456789");
            buffer.delete(1, buffer.length());
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testStringbufferdelete1L1(){
        File file = new File("/sdcard/test/StringBufferTestLog1L1.txt");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            StringBuffer buffer = new StringBuffer();
            buffer.append("0123456789");
            buffer.delete(1, buffer.length() - 1);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
