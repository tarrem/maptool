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
package net.rptools.maptool.client.tool;

import com.jeta.forms.components.panel.FormPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.client.swing.FormPanelI18N;
import net.rptools.maptool.model.Layer;

public class LayerSelectionDialog extends JPanel {

  private final FormPanel panel;
  private JList<Layer> list;
  private final LayerSelectionListener listener;
  private final Layer[] layerList;

  public LayerSelectionDialog(Layer[] layerList, LayerSelectionListener listener) {
    panel = new FormPanelI18N("net/rptools/maptool/client/ui/forms/layerSelectionDialog.xml");
    this.listener = listener;
    this.layerList = layerList;
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    getLayerList();

    setLayout(new GridLayout(1, 1));
    add(panel);
  }

  public void fireViewSelectionChange() {

    int index = list.getSelectedIndex();
    if (index >= 0 && listener != null) {
      listener.layerSelected(list.getModel().getElementAt(index));
    }
  }

  public void updateViewList() {
    getLayerList()
        .setSelectedValue(MapTool.getFrame().getCurrentZoneRenderer().getActiveLayer(), true);
  }

  //  public void setLayerList(Layer[] layerList) {
  //    this.layerList = layerList;
  //    list = null;
  //    updateViewList(); //  }

  private JList<Layer> getLayerList() {

    if (list == null) {
      list = panel.getList("layerTypeList");

      DefaultListModel<Layer> model = new DefaultListModel<>();
      for (Layer layer : layerList) {
        //      for (Layer layer :
        // MapTool.getFrame().getCurrentZoneRenderer().getZone().getLayerList()) {
        model.addElement(layer);
      }

      list.setModel(model);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      list.addListSelectionListener(
          e -> {
            if (e.getValueIsAdjusting()) {
              return;
            }

            fireViewSelectionChange();
          });
      list.setSelectedIndex(0);
    } else {
      System.out.println("Layer Selection Layers:");
      for (Layer layer : layerList) {
        System.out.println("\t" + layer.getDisplayName());
      }
    }

    return list;
  }

  public void setSelectedLayer(Layer layer) {
    list.setSelectedValue(layer, true);
  }

  public interface LayerSelectionListener {
    public void layerSelected(Layer layer);
  }
}
