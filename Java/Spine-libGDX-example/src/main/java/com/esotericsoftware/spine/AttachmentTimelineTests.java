package com.esotericsoftware.spine;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Animation.AttachmentTimeline;
import com.esotericsoftware.spine.attachments.Attachment;

/** Unit tests for {@link AttachmentTimeline}. */
public class AttachmentTimelineTests {
	private final Skeleton skeleton;
	private final Slot slot;
	private final AnimationState state;

	public AttachmentTimelineTests () {
		SkeletonData skeletonData = new SkeletonData();

		BoneData boneData = new BoneData(0, "bone", null);
		skeletonData.getBones().add(boneData);

		skeletonData.getSlots().add(new SlotData(0, "slot", boneData));

		Attachment attachment1 = new Attachment("attachment1") {
			public Attachment copy () {
				return null;
			}
		};
		Attachment attachment2 = new Attachment("attachment2") {
			public Attachment copy () {
				return null;
			}
		};

		Skin skin = new Skin("skin");
		skin.setAttachment(0, "attachment1", attachment1);
		skin.setAttachment(0, "attachment2", attachment2);
		skeletonData.setDefaultSkin(skin);

		skeleton = new Skeleton(skeletonData);
		slot = skeleton.findSlot("slot");

		AttachmentTimeline timeline = new AttachmentTimeline(2);
		timeline.setFrame(0, 0, "attachment1");
		timeline.setFrame(1, 0.5f, "attachment2");

		Animation animation = new Animation("animation", Array.with(timeline), 1);
		animation.setDuration(1);

		state = new AnimationState(new AnimationStateData(skeletonData));
		state.setAnimation(0, animation, true);

		test(0, attachment1);
		test(0, attachment1);
		test(0.25f, attachment1);
		test(0f, attachment1);
		test(0.25f, attachment2);
		test(0.25f, attachment2);

		System.out.println("AttachmentTimeline tests passed.");
	}

	private void test (float delta, Attachment attachment) {
		state.update(delta);
		state.apply(skeleton);
		if (slot.getAttachment() != attachment)
			throw new FailException("Wrong attachment: " + slot.getAttachment() + " != " + attachment);

	}

	static class FailException extends RuntimeException {
		public FailException (String message) {
			super(message);
		}
	}

	static public void main (String[] args) throws Exception {
		new AttachmentTimelineTests();
	}
}
