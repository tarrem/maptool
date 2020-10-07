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
package net.rptools.maptool.client;

import java.util.Set;
import net.rptools.maptool.client.AppActions.*;
import net.rptools.maptool.client.ui.zone.ZoneRenderer;
import net.rptools.maptool.language.I18N;
import net.rptools.maptool.model.GUID;
import net.rptools.maptool.model.Token;

public class TokenActions {
  private static final long serialVersionUID = -3741870412603226747L;

  private boolean areTokensOwned;
  Set<GUID> selectedTokenSet;

  public TokenActions(ZoneRenderer renderer) {
    super();

    setOwnership();
  }

  protected boolean tokensAreOwned() {
    return areTokensOwned;
  }

  private void setOwnership() {
    areTokensOwned = true;
    if (!MapTool.getPlayer().isGM() && MapTool.getServerPolicy().useStrictTokenManagement()) {
      for (GUID tokenGUID : selectedTokenSet) {
        Token token = MapTool.getFrame().getCurrentZoneRenderer().getZone().getToken(tokenGUID);

        if (!token.isOwner(MapTool.getPlayer().getName())) {
          areTokensOwned = false;
          break;
        }
      }
    }
  }

  public static final ClientAction FLIP_HORIZONTAL =
      new TokenAction() {
        {
          init(I18N.getText("token.popup.menu.flip.horizontal"));
        }

        @Override
        protected void executeAction() {
          ZoneRenderer renderer = MapTool.getFrame().getCurrentZoneRenderer();
          Set<GUID> selectedTokenSet = renderer.getSelectedTokenSet();
          for (GUID tokenGUID : selectedTokenSet) {
            Token token = renderer.getZone().getToken(tokenGUID);
            if (token == null) {
              continue;
            }
            token.setFlippedX(!token.isFlippedX());
            MapTool.serverCommand().putToken(renderer.getZone().getId(), token);
          }
          MapTool.getFrame().refresh();
        }
      };

  public static final ClientAction FLIP_VERTICAL =
      new TokenAction() {
        {
          init(I18N.getText("token.popup.menu.flip.vertical"));
        }

        @Override
        protected void executeAction() {
          ZoneRenderer renderer = MapTool.getFrame().getCurrentZoneRenderer();
          Set<GUID> selectedTokenSet = renderer.getSelectedTokenSet();
          for (GUID tokenGUID : selectedTokenSet) {
            Token token = renderer.getZone().getToken(tokenGUID);
            if (token == null) {
              continue;
            }
            token.setFlippedY(!token.isFlippedY());
            MapTool.serverCommand().putToken(renderer.getZone().getId(), token);
          }
          MapTool.getFrame().refresh();
        }
      };

  public static final ClientAction FLIP_ISOMETRIC =
      new TokenAction() {
        {
          init(I18N.getText("token.popup.menu.flip.isometric"));
        }

        @Override
        public void executeAction() {
          ZoneRenderer renderer = MapTool.getFrame().getCurrentZoneRenderer();
          Set<GUID> selectedTokenSet = renderer.getSelectedTokenSet();
          for (GUID tokenGUID : selectedTokenSet) {
            Token token = renderer.getZone().getToken(tokenGUID);
            if (token == null) {
              continue;
            }
            token.setFlippedIso(!token.isFlippedIso());
            MapTool.serverCommand().putToken(renderer.getZone().getId(), token);
          }
          MapTool.getFrame().refresh();
        }
      };

  public abstract static class TokenAction extends ZoneClientAction {
    //    @Override
    //    public boolean isAvailable() {
    //      return super.isAvailable()
    //          && MapTool.getFrame().getCurrentZoneRenderer().getSelectedTokenSet().size() != 0;
    //    }
  }
}
