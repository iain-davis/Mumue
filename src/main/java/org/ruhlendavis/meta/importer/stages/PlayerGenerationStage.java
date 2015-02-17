package org.ruhlendavis.meta.importer.stages;

import java.util.List;
import java.util.Map.Entry;

import org.ruhlendavis.meta.componentsold.Component;
import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;
import org.ruhlendavis.meta.player.Player;

public class PlayerGenerationStage extends ImporterStage{
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, Component> entry : bucket.getComponents().entrySet()) {
            if (entry.getValue() instanceof GameCharacter) {
                Long id = entry.getKey();
                Player player = new Player();

                List<String> lines = bucket.getComponentLines().get(id);
                String name = entry.getValue().getName();
                String password = lines.get(lines.size() - 1);
                player.setName(name);
                player.setPassword(password);
                bucket.getPlayers().add(player);
                player.getCharacters().add((GameCharacter)entry.getValue());
            }
        }
    }
}
