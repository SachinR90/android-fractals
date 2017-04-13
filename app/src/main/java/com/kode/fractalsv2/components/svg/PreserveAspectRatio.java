/*
   Copyright 2013 Paul LeBeau, Cave Rock Software Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.kode.fractalsv2.components.svg;

/**
 * The SVGPositioning class tells the renderer how to scale and position the
 * SVG document in the current viewport.  It is roughly equivalent to the
 * {@code preserveAspectRatio} attribute of an {@code <svg>} element.
 * <p>
 * In order for scaling to happen, the SVG document must have a viewBox attribute set.
 * For example:
 *
 * <pre>
 * {@code
 * <svg version="1.1" viewBox="0 0 200 100">
 * }
 * </pre>
 */
public class PreserveAspectRatio {
    private Alignment alignment;
    private Scale     scale;

    /**
     * Stretch horizontally and vertically to fill the viewport.
     */
    public static final PreserveAspectRatio STRETCH = new PreserveAspectRatio(Alignment.None, null);

    /**
     * Keep the document's aspect ratio, but scale it so that it fits neatly inside the viewport.
     * <p>
     * The document will be centred in the viewport and may have blank strips at either the top and
     * bottom of the viewport or at the sides.
     */
    public static final PreserveAspectRatio LETTERBOX = new PreserveAspectRatio(Alignment.XMidYMid,
                                                                                Scale.Meet);


    /**
     * Determines how the document is to me positioned relative to the viewport (normally the
     * canvas).
     * <p>
     * For the value {@code none}, the document is stretched to fit the viewport dimensions. For
     * all
     * other values, the aspect ratio of the document is kept the same but the document is scaled
     * to
     * fit the viewport.
     */
    public enum Alignment {
        /**
         * Document is stretched to fit both the width and height of the viewport. When using this
         * Alignment value, the value of Scale is not used and will be ignored.
         */
        None,
        /** Document is positioned at the centre top of the viewport. */
        XMidYMin,
        /** Document is positioned at the top right of the viewport. */
        XMaxYMin,
        /** Document is positioned at the middle left of the viewport. */
        XMinYMid,
        /** Document is centred in the viewport both vertically and horizontally. */
        XMidYMid,
        /** Document is positioned at the middle right of the viewport. */
        XMaxYMid,
        /** Document is positioned at the bottom left of the viewport. */
        XMinYMax,
        /** Document is positioned at the bottom centre of the viewport. */
        XMidYMax,
        /** Document is positioned at the bottom right of the viewport. */
        XMaxYMax
    }


    /**
     * Determine whether the scaled document fills the viewport entirely or is scaled to
     * fill the viewport without overflowing.
     */
    public enum Scale {
        /**
         * The document is scaled so that it is as large as possible without overflowing the
         * viewport.
         * There may be blank areas on one or more sides of the document.
         */
        Meet,
        /**
         * The document is scaled so that entirely fills the viewport. That means that some of the
         * document may fall outside the viewport and will not be rendered.
         */
        Slice
    }


    public PreserveAspectRatio(Alignment alignment, Scale scale) {
        this.alignment = alignment;
        this.scale = scale;
    }


    /**
     * Returns the alignment value of this instance.
     *
     * @return the alignment
     */
    public Alignment getAlignment() {
        return alignment;
    }


    /**
     * Returns the scale value of this instance.
     *
     * @return the scale
     */
    public Scale getScale() {
        return scale;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PreserveAspectRatio other = (PreserveAspectRatio) obj;
        return alignment == other.alignment && scale == other.scale;
    }
}
