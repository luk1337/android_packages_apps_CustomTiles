/**
 * Copyright (C) 2015-2016 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cyanogenmod.cmtiles;

import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class HeadsUpTile extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();

        updateTile(Settings.Global.getInt(getContentResolver(),
                Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED, 0) != 0);
    }

    @Override
    public void onClick() {
        super.onClick();

        switch (getQsTile().getState()) {
            case Tile.STATE_ACTIVE:
                Settings.Global.putInt(getContentResolver(),
                        Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED, 0);
                updateTile(false);
                break;
            case Tile.STATE_INACTIVE:
                Settings.Global.putInt(getContentResolver(),
                        Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED, 1);
                updateTile(true);
                break;
        }
    }

    private void updateTile(final boolean enable) {
        final Tile tile = getQsTile();
        if (enable) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_heads_up_on));
            tile.setContentDescription(getString(R.string.accessibility_heads_up_on));
            tile.setState(Tile.STATE_ACTIVE);
        } else {
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_heads_up_off));
            tile.setContentDescription(getString(R.string.accessibility_heads_up_off));
            tile.setState(Tile.STATE_INACTIVE);
        }
        tile.updateTile();
    }
}
