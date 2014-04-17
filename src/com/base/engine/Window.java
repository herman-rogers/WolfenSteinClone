package com.base.engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Window {
	public static void CreateWindow( int width, int height, String title ){
        //TODO: Mac OS X
        PixelFormat pixelFormat = new PixelFormat( );
        ContextAttribs contextAttribs = new ContextAttribs( 3, 2 ).withProfileCore(true);
		//
        Display.setTitle( title );
		try {
			Display.setDisplayMode( new DisplayMode( width, height ) );
			Display.create( pixelFormat, contextAttribs );
			Keyboard.create( );
			Mouse.create( );
		} catch ( LWJGLException e ) {
			e.printStackTrace( );
		}
	}
	public static void Render( ){
		Display.update( );
	}
	public static boolean IsCloseRequested( ){
		return Display.isCloseRequested( );
	}
	public static int GetWidth( ){
		return Display.getDisplayMode( ).getWidth( );
	}
	public static int GetHeight( ){
		return Display.getDisplayMode( ).getHeight( );
	}
	public static String GetTitle( ){
		return Display.getTitle( );
	}
	public static void Dispose( ){
		Display.destroy( );
		Keyboard.destroy( );
		Mouse.destroy( );
	}
}
