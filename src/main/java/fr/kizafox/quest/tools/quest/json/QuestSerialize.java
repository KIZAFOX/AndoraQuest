package fr.kizafox.quest.tools.quest.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.kizafox.quest.AndoraQuest;
import fr.kizafox.quest.tools.quest.Quest;
import fr.kizafox.quest.tools.quest.QuestManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Change this line to a short description of the class
 *
 * @author : KIZAFOX
 * @date : 16/09/2023
 * @project : AndoraQuest
 */
public class QuestSerialize {

    protected final AndoraQuest instance;

    private final Gson gson;

    public QuestSerialize(final AndoraQuest instance) {
        this.instance = instance;

        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public void load() {
        final File dataFolder = this.instance.getDataFolder();

        if (!dataFolder.exists()) {
            if(dataFolder.mkdirs()){
                System.out.println("Le dossier de données a été créé avec succès !");
            }
        }

        final File questFile = new File(dataFolder, "quests.json");

        if (!questFile.exists()) {
            try {
                if(questFile.createNewFile()){
                    System.out.println("Le fichier de quêtes a été créé avec succès !");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            if (questFile.exists()) {
                try (final FileReader reader = new FileReader(questFile)) {
                    /*
                      La ligne final Type questListType = new TypeToken<List<Quest>>() {}.getType();
                      permet de spécifier le type de données que Gson doit convertir à partir du JSON.
                      Elle indique que vous attendez une liste de quêtes (List<Quest>) comme résultat lors de la désérialisation du JSON en objets Java.
                      Cela aide Gson à comprendre comment convertir correctement les données JSON en objets Java.
                     */
                    final Type questListType = new TypeToken<List<Quest>>(){}.getType();
                    final List<Quest> loadedQuests = gson.fromJson(reader, questListType);

                    if (loadedQuests != null) {
                        QuestManager.getQuests().clear();
                        QuestManager.getQuests().addAll(loadedQuests);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                QuestManager.getQuests().clear();
            }
        }
    }

    public void save() {
        try {
            final FileWriter writer = new FileWriter(new File(this.instance.getDataFolder(), "quests.json"));
            writer.write(gson.toJson(QuestManager.getQuests()));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
