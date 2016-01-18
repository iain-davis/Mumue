package org.mumue.mumue.importer.stages;

import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.ImporterStage;
import org.mumue.mumue.importer.components.GameCharacter;

public class PlayerGenerationStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        bucket.getComponents().entrySet().stream()
                .filter(entry -> entry.getValue() instanceof GameCharacter)
                .forEach(entry -> {
//                Long id = entry.getKey();
//                Player player = new Player();
//                List<String> lines = bucket.getComponentLines().get(id);
//                String name = entry.getValue().getName();
//                String password = lines.get(lines.size() - 1);
//                player.setName(name);
//                player.setPassword(password);
//                bucket.getPlayers().add(player);
//                player.getCharacters().add((GameCharacter)entry.getValue());
                });
    }
}
