package com.base.DoomGame;
import com.base.engine.*;

/**
 * Created by Admin on 3/27/14.
 */
public class Door{
    public psTransform transform;
    private psMaterial material;
    private static psMesh mesh;
    static final float DOOR_LENGTH = 1.0f;
    static final float DOOR_WIDTH = 0.125f;
    private static final float DOOR_HEIGHT = 1.0f;

    public Door( psTransform transform, psMaterial material ){
        this.transform = transform;
        this.material = material;
        if( mesh == null ){
            psVertex[] vertices = new psVertex[]{ new psVertex( new Vector3f( 0,0,0 ), new Vector2f( 0.5f,1 ) ),
                                                  new psVertex( new Vector3f( 0, DOOR_HEIGHT,0 ), new Vector2f( 0.5f,0.75f ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH, DOOR_HEIGHT,0 ), new Vector2f( 0.75f,0.75f ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH,0,0 ), new Vector2f( 0.75f,1 ) ),

                                                  new psVertex( new Vector3f( 0,0,0 ), new Vector2f( 0.73f,0.75f ) ),
                                                  new psVertex( new Vector3f( 0, DOOR_HEIGHT,0 ), new Vector2f( 0.73f,0.75f ) ),
                                                  new psVertex( new Vector3f( 0, DOOR_HEIGHT, DOOR_WIDTH ), new Vector2f( 0.75f,0.75f ) ),
                                                  new psVertex( new Vector3f( 0,0,DOOR_WIDTH ), new Vector2f( 0.75f,1 ) ),

                                                  new psVertex( new Vector3f( 0,0,DOOR_WIDTH ), new Vector2f( 0.5f,1 ) ),
                                                  new psVertex( new Vector3f( 0, DOOR_HEIGHT, DOOR_WIDTH ), new Vector2f( 0.5f,0.75f ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH, DOOR_HEIGHT, DOOR_WIDTH ), new Vector2f( 0.75f,0.75f ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH,0, DOOR_WIDTH ), new Vector2f( 0.75f,1 ) ),

                                                  new psVertex( new Vector3f( DOOR_LENGTH,0,0 ), new Vector2f( 0,0 ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH, DOOR_HEIGHT,0 ), new Vector2f( 0,0 ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH, DOOR_HEIGHT, DOOR_WIDTH ), new Vector2f( 0,0 ) ),
                                                  new psVertex( new Vector3f( DOOR_LENGTH,0,DOOR_WIDTH ), new Vector2f( 0,0 ) )};
            int[] indices = new int[]{ 0,1,2,
                                       0,2,3,
                                       6,5,4,
                                       7,6,4,
                                       10,9,8,
                                       11,10,8,
                                       12,13,14,
                                       12,14,15 };
            mesh = new psMesh( vertices, indices );
        }
    }

    public void Update( ){

    }

    public void Render( ){
        psShader shader = Game.level.shader;
        shader.UpdateUniforms( transform.GetTransformation( ), transform.GetProjectedTransformation( ), material );
        //TODO: BIND SHADER
        mesh.Draw( );
    }


}
