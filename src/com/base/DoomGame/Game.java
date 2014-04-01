package com.base.DoomGame;

import com.base.engine.Vector3f;

public class Game {
    //Point Lights
//    psPointLight testPointLightOne = new psPointLight(new psBaseLight(new Vector3f(30, 144, 255), 1.0f),
//            new psAttenuation(0, 0, 1),
//            new Vector3f(-2.0f, 0.0f, 5.0f), 20.0f);
//    psPointLight testPointLightTwo = new psPointLight(new psBaseLight(new Vector3f(244, 164, 96), 1.0f),
//            new psAttenuation(0, 0, 1),
//            new Vector3f(2.0f, 0.0f, 7.0f), 20.0f);
    public static Level level;
    private Player player;

    public Game( ) {
        level = new Level( "levelOne.png", "WolfensteinSpriteSheet.png" );
        player = new Player( new Vector3f( 4, 0.5f, 5 ) );


//        psPhongShader.SetPointLight(new psPointLight[]{testPointLightOne, testPointLightTwo});
    }

    public void Input( ) {
        player.Input( );
    }

    public void Update( ) {
        player.Update( );
//        testPointLightOne.setPosition(new Vector3f(1, -0.5f, 8.0f * (float) (sineTemp + 1.0 / 2.0) + 10));
//        testPointLightTwo.setPosition(new Vector3f(10, -0.5f, 8.0f * (float) (Math.cos(temp) + 1.0 / 2.0) + 10));

    }

    public void Render( ) {
        DrawLevelShaders( );
        level.Update( );
        level.Render( );
    }

    private void DrawLevelShaders( ){
        level.shader.bind( );
        level.shader.UpdateUniforms( player.transform.GetTransformation( ),
                player.transform.GetProjectedTransformation( ), level.material );
        level.mesh.Draw( );
    }
}