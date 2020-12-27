/******************************************************************************
 * Spine Runtimes Software License v2.5
 *
 * Copyright (c) 2013-2016, Esoteric Software
 * All rights reserved.
 *
 * You are granted a perpetual, non-exclusive, non-sublicensable, and
 * non-transferable license to use, install, execute, and perform the Spine
 * Runtimes software and derivative works solely for personal or internal
 * use. Without the written permission of Esoteric Software (see Section 2 of
 * the Spine Software License Agreement), you may not (a) modify, translate,
 * adapt, or develop new applications using the Spine Runtimes or otherwise
 * create derivative works or improvements of the Spine Runtimes or (b) remove,
 * delete, alter, or obscure any trademarks or any copyright, trademark, patent,
 * or other intellectual property or proprietary rights notices on or in the
 * Software, including any copy thereof. Redistributions in binary or source
 * form must include this license and terms.
 *
 * THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL ESOTERIC SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES, BUSINESS INTERRUPTION, OR LOSS OF
 * USE, DATA, OR PROFITS) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.esotericsoftware.spine36.attachments;

import com.esotericsoftware.spine36.Skin;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/** An {@link AttachmentLoader} that configures attachments using texture regions from an {@link Atlas}.
 * <p>
 * See <a href='http://esotericsoftware.com/spine-loading-skeleton-data#JSON-and-binary-data'>Loading skeleton data</a> in the
 * Spine Runtimes Guide. */
@SuppressWarnings("javadoc")
public class AtlasAttachmentLoader implements AttachmentLoader {
	private TextureAtlas atlas;

	public AtlasAttachmentLoader (TextureAtlas atlas) {
		if (atlas == null) throw new IllegalArgumentException("atlas cannot be null.");
		this.atlas = atlas;
	}

	public RegionAttachment newRegionAttachment (Skin skin, String name, String path) {
		AtlasRegion region = atlas.findRegion(path);
		if (region == null) throw new RuntimeException("Region not found in atlas: " + path + " (region attachment: " + name + ")");
		RegionAttachment attachment = new RegionAttachment(name);
		attachment.setRegion(region);
		return attachment;
	}

	public MeshAttachment newMeshAttachment (Skin skin, String name, String path) {
		AtlasRegion region = atlas.findRegion(path);
		if (region == null) throw new RuntimeException("Region not found in atlas: " + path + " (mesh attachment: " + name + ")");
		MeshAttachment attachment = new MeshAttachment(name);
		attachment.setRegion(region);
		return attachment;
	}

	public BoundingBoxAttachment newBoundingBoxAttachment (Skin skin, String name) {
		return new BoundingBoxAttachment(name);
	}

	public ClippingAttachment newClippingAttachment (Skin skin, String name) {
		return new ClippingAttachment(name);
	}

	public PathAttachment newPathAttachment (Skin skin, String name) {
		return new PathAttachment(name);
	}

	public PointAttachment newPointAttachment (Skin skin, String name) {
		return new PointAttachment(name);
	}
}
