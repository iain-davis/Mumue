package org.ruhlendavis.mumue.importer.stages;

import java.util.List;
import java.util.Map.Entry;

import org.ruhlendavis.mumue.importer.ImportBucket;
import org.ruhlendavis.mumue.importer.ImporterStage;
import org.ruhlendavis.mumue.importer.components.Component;
import org.ruhlendavis.mumue.importer.components.GameCharacter;
import org.ruhlendavis.mumue.player.Player;

public class PlayerGenerationStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, Component> entry : bucket.getComponents().entrySet()) {
            if (entry.getValue() instanceof GameCharacter) {
                Long id = entry.getKey();
                Player player = new Player();

                List<String> lines = bucket.getComponentLines().get(id);
                String name = entry.getValue().getName();
                String password = lines.get(lines.size() - 1);
//                player.setName(name);
//                player.setPassword(password);
                bucket.getPlayers().add(player);
//                player.getCharacters().add((GameCharacter)entry.getValue());
            }
        }
    }
}
