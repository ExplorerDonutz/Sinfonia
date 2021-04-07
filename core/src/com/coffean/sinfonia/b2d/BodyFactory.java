package com.coffean.sinfonia.b2d;

import com.badlogic.gdx.physics.box2d.*;

import static com.coffean.sinfonia.utils.Constants.PPM;

public class BodyFactory {
    private static BodyFactory thisInstance;
    private final World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) {
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    public Body makeCircle(float posx, float posy, float radius, BodyDef.BodyType bodyType, boolean fixedRotation, short catBits, short maskBits) {
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = catBits;
        fixtureDef.filter.maskBits = maskBits;
        boxBody.createFixture(fixtureDef);
        circleShape.dispose();
        return boxBody;
    }

    public Body makeBox(float posx, float posy, float width, float height, BodyDef.BodyType bodyType, boolean fixedRotation, short catBits, short maskBits) {
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx / 2 / PPM;
        boxBodyDef.position.y = posy / 2 / PPM;
        boxBodyDef.linearDamping = 10.0f;
        boxBodyDef.fixedRotation = fixedRotation;
        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width / 2 / PPM, height / 2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.shape = poly;
        fixtureDef.filter.categoryBits = catBits;
        fixtureDef.filter.maskBits = maskBits;
        boxBody.createFixture(fixtureDef);
        poly.dispose();

        return boxBody;
    }
}
