/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * MapTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.maptool.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.rptools.maptool.model.drawing.DrawnElement;

public class Layer {

  /** The type of layer (TOKEN, GM, OBJECT or BACKGROUND). */
  public enum LayerType {
    TOKEN("Token"),
    GM("Hidden"),
    OBJECT("Object"),
    BACKGROUND("Background");

    private String displayName;

    LayerType(String displayName) {
      this.displayName = displayName;
    }

    @Override
    public String toString() {
      return displayName;
    }

    /** A simple interface to allow layers to be turned on/off */
    private boolean drawEnabled = true;

    /** @return drawEnabled */
    public boolean isEnabled() {
      return drawEnabled;
    }

    public void setEnabled(boolean enabled) {
      drawEnabled = enabled;
    }
  }

  private String displayName;

  private LayerType layerType;

  private boolean drawEnabled;

  public List<DrawnElement> drawables;

  @Override
  public String toString() {
    return displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isEnabled() {
    return this.drawEnabled;
  }

  public LayerType getLayerType() {
    return this.layerType;
  }

  public void setLayerType(LayerType layerType) {
    this.layerType = layerType;
    if (this.displayName == null) {
      this.displayName = layerType.displayName;
    }
  }

  public Layer(LayerType layerType) {
    this.layerType = layerType;
    this.displayName = layerType.displayName;
    this.drawables = new LinkedList<>();
    this.drawEnabled = true;
  }

  public Layer(String name, LayerType layerType) {
    this(layerType);
    this.displayName = name;
  }

  public Layer(Layer layer) {
    this.layerType = layer.layerType;
    this.drawEnabled = layer.drawEnabled;
    this.drawables = new LinkedList<DrawnElement>();

    // In the following blocks we allocate a new linked list then fill it with null values
    // because the Collections.copy() method requires the destination list to already be
    // of a large enough size. I couldn't find any method that would copy individual
    // elements as it populated the new linked lists except those from the Apache Commons
    // library that use a Transformer and that seemed like a lot more work. :-/
    if (!layer.drawables.isEmpty()) {
      this.drawables = new LinkedList<DrawnElement>();
      this.drawables.addAll(Collections.nCopies(layer.drawables.size(), null));
      Collections.copy(drawables, layer.drawables);
    }
  }
}
