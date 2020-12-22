package com.esotericsoftware.spine;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.spine.Animation.MixDirection;
import com.esotericsoftware.spine.Animation.MixBlend;
import com.esotericsoftware.spine.attachments.AttachmentLoader;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.esotericsoftware.spine.attachments.ClippingAttachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.esotericsoftware.spine.attachments.MeshAttachment;
import com.esotericsoftware.spine.attachments.PathAttachment;
import com.esotericsoftware.spine.attachments.PointAttachment;

public class BonePlotting {
	static public void main (String[] args) throws Exception {
		// This example shows how to load skeleton data and plot a bone transform for each animation.
		SkeletonJson json = new SkeletonJson(new AttachmentLoader() {
			public RegionAttachment newRegionAttachment (Skin skin, String name, String path) {
				return null;
			}

			public MeshAttachment newMeshAttachment (Skin skin, String name, String path) {
				return null;
			}

			public BoundingBoxAttachment newBoundingBoxAttachment (Skin skin, String name) {
				return null;
			}

			public ClippingAttachment newClippingAttachment (Skin skin, String name) {
				return null;
			}

			public PathAttachment newPathAttachment (Skin skin, String name) {
				return null;
			}

			public PointAttachment newPointAttachment (Skin skin, String name) {
				return null;
			}
		});
		SkeletonData skeletonData = json.readSkeletonData(new FileHandle("spineboy/spineboy-ess.json"));
		Skeleton skeleton = new Skeleton(skeletonData);
		Bone bone = skeleton.findBone("gun-tip");
		float fps = 1 / 15f;
		for (Animation animation : skeletonData.getAnimations()) {
			float time = 0;
			while (time < animation.getDuration()) {
				animation.apply(skeleton, time, time, false, null, 1, MixBlend.first, MixDirection.in);
				skeleton.updateWorldTransform();
				System.out
					.println(animation.getName() + "," + bone.getWorldX() + "," + bone.getWorldY() + "," + bone.getWorldRotationX());
				time += fps;
			}
		}
	}
}
