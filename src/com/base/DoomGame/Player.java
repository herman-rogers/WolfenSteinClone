package com.base.DoomGame;
import com.base.engine.*;
//import javafx.scene.Camera;
import org.lwjgl.input.Keyboard;

/**
 * Created by Admin on 3/27/14.
 */
public class Player {
    psCamera camera;
    psTransform transform;
    private Vector2f centerPosition = new Vector2f( Window.GetWidth() / 2, Window.GetHeight() / 2 );
    private Vector3f movementVector;
    private boolean isMouseLocked = false;
    private final Vector3f ZERO_VECTOR = new Vector3f( 0,0,0 );
    private final float PLAYER_SIZE = 0.2f;

    public Player( Vector3f position ){
        camera = new psCamera( position, new Vector3f( 0, 0, 1 ), new Vector3f( 0, 1, 0 ) );
        transform = new psTransform( );
        transform.SetProjection(70.0f, Window.GetWidth(), Window.GetHeight( ), 0.1f, 1000);
        transform.setCamera( camera );
    }

    public void Input( ){
        if( psInput.GetKey( Keyboard.KEY_ESCAPE ) ){
            isMouseLocked = false;
        }
        if( psInput.GetMouseDown( 0 ) ){
            if( !isMouseLocked ){
                psInput.SetMousePosition( centerPosition );
                isMouseLocked = true;
            } else {
//                Vector2f lineStart = new Vector2f( camera.getPosition( ).GetX( ), camera.getPosition( ).GetY( ) );
//                Vector2f castDirection = new Vector2f( camera.getForward( ).GetX( ), camera.getForward( ).GetZ( ) );
//                Vector2f lineEnd = lineStart.Add( castDirection.Multiply( SH ) )
            }
        }
        movementVector = ZERO_VECTOR;
        if( psInput.GetKey( Keyboard.KEY_W ) ){
            movementVector = movementVector.Add( camera.getForward( ) );
        }
        if(psInput.GetKey( Keyboard.KEY_S ) ){
            movementVector = movementVector.Subtract(camera.getForward());
        }
        if(psInput.GetKey( Keyboard.KEY_A ) ){
            movementVector = movementVector.Add( camera.GetLeft( ) );
        }
        if(psInput.GetKey( Keyboard.KEY_D ) ){
            movementVector = movementVector.Add( camera.GetRight( ) );
        }

        if( isMouseLocked ){
            Vector2f deltaPosition = psInput.GetMousePosition( ).Subtract( centerPosition );
            boolean rotY = deltaPosition.GetX( ) != 0;
            boolean rotx = deltaPosition.GetY( ) != 0;
            if( rotY ){
                camera.RotateY( deltaPosition.GetX( ) * 0.33f );
            }
            if( rotx ){
                camera.RotateX( -deltaPosition.GetY( ) * 0.33f );
            }
            if( rotY || rotx ){
                psInput.SetMousePosition( centerPosition );
            }
        }
    }

    public void Update( ){
        float moveAmount = ( float )( 3 * psTime.GetDelta( ) );
        movementVector.SetY( 0 );
        if( movementVector.Length( ) > 0 ) {
            movementVector = movementVector.Normalize( );
        }
        Vector3f currentPosition = camera.getPosition( );;
        Vector3f newPosition = currentPosition.Add( movementVector.Multiply( moveAmount ) );
        Vector3f collisitonVector = Game.level.CheckCollisions( currentPosition, newPosition, PLAYER_SIZE, PLAYER_SIZE );
        movementVector = movementVector.Multiply( collisitonVector );
        camera.Move( movementVector, moveAmount );
    }

    public void Render( ){

    }
}
