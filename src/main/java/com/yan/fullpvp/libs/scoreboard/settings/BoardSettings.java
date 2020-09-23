package com.yan.fullpvp.libs.scoreboard.settings;

import com.yan.fullpvp.libs.scoreboard.provider.CustomScoreboard;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Missionary (missionarymc@gmail.com)
 * @since 5/31/2018
 */
@Getter
@Builder
public class BoardSettings {

    private CustomScoreboard boardProvider;

    private ScoreDirection scoreDirection;

}
