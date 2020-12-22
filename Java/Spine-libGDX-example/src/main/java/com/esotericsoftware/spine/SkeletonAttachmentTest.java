package com.esotericsoftware.spine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.attachments.SkeletonAttachment;

public class SkeletonAttachmentTest extends ApplicationAdapter {
	OrthographicCamera camera;
	PolygonSpriteBatch batch;
	SkeletonRenderer renderer;

	Skeleton spineboy, goblin;
	AnimationState spineboyState, goblinState;
	Bone attachmentBone;

	public void create () {
		camera = new OrthographicCamera();
		batch = new PolygonSpriteBatch();
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(true);

		{
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("spineboy/spineboy-pma.atlas"));
			SkeletonJson json = new SkeletonJson(atlas);
			json.setScale(0.6f);
			SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spineboy/spineboy-ess.json"));
			spineboy = new Skeleton(skeletonData);
			spineboy.setPosition(320, 20);

			AnimationStateData stateData = new AnimationStateData(skeletonData);
			stateData.setMix("walk", "jump", 0.2f);
			stateData.setMix("jump", "walk", 0.2f);
			spineboyState = new AnimationState(stateData);
			spineboyState.addAnimation(0, "walk", true, 0);
		}

		{
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("goblins/goblins-pma.atlas"));
			SkeletonJson json = new SkeletonJson(atlas);
			SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("goblins/goblins-pro.json"));
			goblin = new Skeleton(skeletonData);
			goblin.setSkin("goblin");
			goblin.setSlotsToSetupPose();

			goblinState = new AnimationState(new AnimationStateData(skeletonData));
			goblinState.setAnimation(0, "walk", true);

			// Instead of a right shoulder, spineboy will have a goblin!
			SkeletonAttachment skeletonAttachment = new SkeletonAttachment("goblin");
			skeletonAttachment.setSkeleton(goblin);
			Slot slot = spineboy.findSlot("front-upper-arm");
			slot.setAttachment(skeletonAttachment);
			attachmentBone = slot.getBone();
		}
	}

	public void render () {
		spineboyState.update(Gdx.graphics.getDeltaTime());
		spineboyState.apply(spineboy);
		spineboy.updateWorldTransform();

		goblinState.update(Gdx.graphics.getDeltaTime());
		goblinState.apply(goblin);
		goblin.updateWorldTransform(attachmentBone);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.getProjectionMatrix().set(camera.combined);
		batch.begin();
		renderer.draw(batch, spineboy);
		batch.end();
	}

	public void resize (int width, int height) {
		camera.setToOrtho(false);
	}

	public static void main (String[] args) throws Exception {
		new LwjglApplication(new SkeletonAttachmentTest());
	}
}
