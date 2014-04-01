package com.base.DoomGame;
import java.util.ArrayList;
import com.base.engine.*;

/**
 * Created by Admin on 3/27/14.
 */
public class Level {
    psMesh mesh;
    psShader shader;
    psMaterial material;
    private Bitmap bitmapLevel;
    private static final float SPOT_WIDTH = 1;
    private static final float SPOT_LENGTH = 1;
    private static final float SPOT_HEIGHT = 1;
    private static final int NUM_TEX_EXP = 4;
    private static final int NUM_TEXTURES = ( int )Math.pow( 2, NUM_TEX_EXP );

   // private Door door;
    private ArrayList< Door > doors;

    public Level( String levelName, String textureName ){
        bitmapLevel = new Bitmap( levelName ).FlipX( );
        shader =  psBasicShader.getInstance( );
//        psPhongShader.setAmbientLight(new Vector3f(0.4f, 0.4f, 0.4f));
//        psPhongShader.SetDirectionalLight(new psDirectionalLight(
//            new psBaseLight(new Vector3f(1.0f, 1.0f, 1.0f), 2.0f),
//            new Vector3f( 4, 1.0f, 5)));
        material = new psMaterial(new psTexture( textureName ), new Vector3f(139, 119, 101), 0.1f, 8);
        doors = new ArrayList<Door>( );
        GenerateLevel( );

        psTransform transformTemp = new psTransform( );
        transformTemp.SetTranslation( new Vector3f( 3, 0, 6 ) );
//        door = new Door( transformTemp, material );

    }

    public void Input( ){

    }

    public void Update( ){
        for( Door door : doors ){
            door.Update( );
        }
    }

    public void Render( ){
        for( Door door : doors ){
            door.Render( );
        }
    }

    private void AddDoor( int x, int y ){
        psTransform doorPosition = new psTransform( );
        boolean xDoor = ( bitmapLevel.GetPixel( x, y - 1 ) & 0xFFFFFF ) == 0 &&
                        ( bitmapLevel.GetPixel( x, y + 1 ) & 0xFFFFFF ) == 0;
        boolean yDoor = ( bitmapLevel.GetPixel( x - 1, y ) & 0xFFFFFF ) == 0 &&
                        ( bitmapLevel.GetPixel( x + 1, y ) & 0xFFFFFF ) == 0;
        if( !( xDoor ^ yDoor ) ){
            System.err.println( "NOOOOOOOO! Door Placement Incorrect!!" );
            new Exception( ).printStackTrace( );
            System.exit( 1 );
        }
        if( yDoor ){
            doorPosition.SetTranslation( x, 0, ( y + SPOT_LENGTH / 2 ) );
        }
        else if ( xDoor ){
            doorPosition.SetTranslation( ( x + SPOT_WIDTH / 2 ), 0, y);
            doorPosition.SetRotation( 0,90,0 );
        }
        doors.add( new Door( doorPosition, material ) );
    }

    private void AddSpecial( int blueValue, int x, int y ){
        if( blueValue == 16 ){
            AddDoor( x, y );
        }
    }

//TODO: REFACTOR
    private void GenerateLevel( ){
        ArrayList< psVertex > vertices = new ArrayList< psVertex >( );
        ArrayList< Integer > indices = new ArrayList< Integer >( );
        for( int i = 0; i < bitmapLevel.getWidth( ); i++ ){
            for( int j = 0; j < bitmapLevel.getHeight( ); j++ ){
                if( ( bitmapLevel.GetPixel( i, j ) & 0xFFFFFF ) == 0 ){
                    continue;
                }
                int texX = ( ( bitmapLevel.GetPixel( i, j ) & 0x00FF00 ) >> 6 ) / NUM_TEXTURES;
                int texY = texX % NUM_TEX_EXP ;
                texX /= NUM_TEX_EXP;

                AddSpecial( ( bitmapLevel.GetPixel( i, j ) & 0x0000FF ), i, j );
                float xHigher = 1.0f - ( float )texX / ( float )NUM_TEX_EXP;
                float xLower = xHigher - 1.0f / ( float )NUM_TEX_EXP;
                float yLower = 1.0f - ( float )texY / ( float )NUM_TEX_EXP;
                float yHigher = yLower - 1.0f / ( float )NUM_TEX_EXP;
                AddFaceVerticesReverse( indices, vertices.size( ) );
                //Floor
                vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );

                //Ceiling
                AddFaceVertices( indices, vertices.size( ) );

                vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT, j * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );

                texX = ( ( bitmapLevel.GetPixel( i, j ) & 0xFF0000 ) >> 16 ) / NUM_TEXTURES;
                texY = texX % NUM_TEX_EXP ;
                texX /= NUM_TEX_EXP;

                xHigher = 1.0f - ( float )texX / ( float )NUM_TEX_EXP;
                xLower = xHigher - 1.0f / ( float )NUM_TEX_EXP;
                yLower = 1.0f - ( float )texY / ( float )NUM_TEX_EXP;
                yHigher = yLower - 1.0f / ( float )NUM_TEX_EXP;

                //Generate Walls
                if( ( bitmapLevel.GetPixel( i, j - 1 ) & 0xFFFFFF ) == 0 ){
                    AddFaceVertices(indices, vertices.size());

                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT, j  * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT,  j  * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );
                }
                if( ( bitmapLevel.GetPixel( i, j + 1 ) & 0xFFFFFF ) == 0 ){
                    AddFaceVerticesReverse(indices, vertices.size());

                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT, ( j + 1 )  * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT,  ( j + 1 )  * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );
                }
                if( ( bitmapLevel.GetPixel( i - 1, j ) & 0xFFFFFF ) == 0 ){
                    AddFaceVerticesReverse(indices, vertices.size());

                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( i  * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT, ( j + 1 )  * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                    vertices.add( new psVertex( new Vector3f( i * SPOT_WIDTH, SPOT_HEIGHT,  j  * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );
                }
                if( ( bitmapLevel.GetPixel( i + 1, j ) & 0xFFFFFF ) == 0 ){
                    AddFaceVertices(indices, vertices.size());

                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, 0, j * SPOT_LENGTH ), new Vector2f( xLower, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 )  * SPOT_WIDTH, 0, ( j + 1 ) * SPOT_LENGTH ), new Vector2f( xHigher, yLower ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT, ( j + 1 )  * SPOT_LENGTH ), new Vector2f( xHigher, yHigher ) ) );
                    vertices.add( new psVertex( new Vector3f( ( i + 1 ) * SPOT_WIDTH, SPOT_HEIGHT,  j  * SPOT_LENGTH ), new Vector2f( xLower, yHigher ) ) );
                }
            }
        }
        psVertex[] vertexArray = new psVertex[ vertices.size( ) ];
        Integer[] intArray = new Integer[ indices.size( ) ];
        vertices.toArray( vertexArray );
        indices.toArray( intArray );
        mesh = new psMesh( vertexArray, psUtil.ToIntArray( intArray ) );
    }

    //TODO: Refactor
    public Vector3f CheckCollisions( Vector3f currentPosition, Vector3f newPosition, float objectWidth, float objectLength ){
        Vector2f collisionVector = new Vector2f( 1.0f, 1.0f );
        Vector3f movementVector = newPosition.Subtract( currentPosition );
        if( movementVector.Length( ) > 0 ){
            Vector2f blockSize = new Vector2f( SPOT_WIDTH, SPOT_LENGTH );
            Vector2f objectSize = new Vector2f( objectWidth, objectLength );
            Vector2f oldPositionTwo = new Vector2f( currentPosition.GetX( ), currentPosition.GetZ( ) );
            Vector2f newPositionTwo = new Vector2f( newPosition.GetX( ), newPosition.GetZ() );
            for( int i = 0; i < bitmapLevel.getWidth( ); i++ ){
                for( int j = 0; j < bitmapLevel.getHeight( ); j++ ){
                    if( ( bitmapLevel.GetPixel( i, j ) & 0xFFFFFF ) == 0 ){
                        collisionVector = collisionVector.Multiply( rectangleCollide(oldPositionTwo, newPositionTwo, objectSize, blockSize.Multiply( new Vector2f( i, j ) ), blockSize ) );
                    }
                }
            }
//            Vector2f doorSize = new Vector2f( door.DOOR_LENGTH, door.DOOR_WIDTH );
//            Vector3f doorPosition3f = door.transform.GetTranslation( );
//            Vector2f doorPosition = new Vector2f( doorPosition3f.GetX( ), doorPosition3f.GetZ( ) );
//            collisionVector = collisionVector.Multiply( rectangleCollide( oldPositionTwo, newPositionTwo, objectSize, doorPosition, doorSize ) );
        }
        return new Vector3f( collisionVector.GetX( ), 0, collisionVector.GetY( ) );
    }

    public Vector2f rectangleCollide(Vector2f currentPosition, Vector2f newPosition, Vector2f sizeOne, Vector2f positionTwo, Vector2f sizeTwo ){
        Vector2f result = new Vector2f( 0,0 );
        if( newPosition.GetX( ) + sizeOne.GetX( ) < positionTwo.GetX( ) ||
            newPosition.GetX( ) - sizeOne.GetX( ) > positionTwo.GetX( ) + sizeTwo.GetX( ) * sizeTwo.GetX( ) ||
            currentPosition.GetY( ) + sizeOne.GetY( ) < positionTwo.GetY( ) ||
            currentPosition.GetY( ) - sizeOne.GetY( ) > positionTwo.GetY( ) + sizeTwo.GetY( ) * sizeTwo.GetY( ) ){
            result.SetX( 1.0f );
        }
        if( currentPosition.GetX( ) + sizeOne.GetX( ) < positionTwo.GetX( ) ||
            currentPosition.GetX( ) - sizeOne.GetX( ) > positionTwo.GetX( ) + sizeTwo.GetX( ) * sizeTwo.GetX( ) ||
            newPosition.GetY( ) + sizeOne.GetY( ) < positionTwo.GetY( ) ||
            newPosition.GetY( ) - sizeOne.GetY( ) > positionTwo.GetY( ) + sizeTwo.GetY( ) * sizeTwo.GetY( ) ) {
            result.SetY( 1.0f );
        }
        return result;
    }

    private void AddFaceVertices(ArrayList<Integer> indices, int verticesSize){
        indices.add( verticesSize );
        indices.add( verticesSize + 1 );
        indices.add( verticesSize + 2 );
        indices.add( verticesSize );
        indices.add( verticesSize + 2 );
        indices.add( verticesSize + 3 );
    }

    private void AddFaceVerticesReverse(ArrayList<Integer> indices, int verticesSize){
        indices.add( verticesSize + 2 );
        indices.add( verticesSize + 1 );
        indices.add( verticesSize );
        indices.add( verticesSize + 3 );
        indices.add( verticesSize + 2 );
        indices.add( verticesSize );
    }
}
